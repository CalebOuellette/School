/* CSCI 347 micro-make
 * 09 AUG 2017, Aran Clauson
 * 16 JAN 2017, Caleb Ouellette
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <string.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <time.h>

#include "arg_parse.h"
#include "target.h"
#include "expand.h"

/* CONSTANTS */

/* PROTOTYPES */
/* Capture Env Var
* Process a line to capture the enviroment varible.
*/
void captureEnvVar(char *line);

/* Capture Target
* 
*/
Target *captureTarget(char *line);

/* Process Line
 * line   The command line to execute.
 * This function interprets line as a command line.  It creates a new child
 * process to execute the line and waits for that process to complete. 
 */
void processline(char *line);

/* Execute Target
* Finds targets by name
* executes dependents by recursion
* executes rules
*/
void executeTarget(char *name, void *time);

/* Main entry point.
 * argc    A count of command-line arguments s
 * argv    The command-line argument valus
 * 
 * Micro-make (umake) reads from the uMakefile in the current working
 * directory.  The file is read one line at a time.  Lines with a leading tab
 * character ('\t') are interpreted as a command and passed to processline minus
 * the leading tab.
 */
/* Get Modifcation Time 
*	returns Null if no file is found.
*/
struct timespec getModifcationTime(char *path);

int main(int argc, const char *argv[])
{

	FILE *makefile = fopen("./uMakefile", "r");
	if (makefile == NULL)
	{
		fprintf(stderr, "Failed to open uMakefile \n");
		return EXIT_FAILURE;
	}
	size_t bufsize = 0;
	char *line = NULL;

	ssize_t linelen = getline(&line, &bufsize, makefile);
	Target *lastTarget = NULL;
	while (-1 != linelen)
	{
		if (strchr(line, '#') != NULL)
		{
			strchr(line, '#')[0] = '\0';
		}
		if (line[linelen - 1] == '\n')
		{
			linelen -= 1;
			line[linelen] = '\0';
		}

		if (line[0] != '\t' && strchr(line, ':') != NULL)
		{
			lastTarget = captureTarget(line);
		}
		else if (strchr(line, '=') != NULL)
		{
			captureEnvVar(line);
		}
		else if (line[0] == '\t')
		{
			append_rule(lastTarget, line);
		}

		linelen = getline(&line, &bufsize, makefile);
	}
	int i = 1;
	while (argv[i] != NULL)
	{
		char *name = strdup(argv[i]);
		struct timespec newTime = getModifcationTime(name);
		executeTarget(name, &newTime);
		free(name);
		i++;
	}

	free(line);
	free_list();
	return EXIT_SUCCESS;
}

struct timespec getModifcationTime(char *path)
{
	struct stat stats;
	stat(path, &stats);
#ifdef __MACH__
	return stats.st_mtimespec;
#else
	return stats.st_mtim;
#endif
}
/* Execute Target
 * Recusively find and execute targets.
 */
void executeTarget(char *name, void *input)
{
	struct timespec *newestTime = input;
	Target *x = find_target(name);
	if (x != NULL)
	{
		struct timespec newTime;
		for_each_dependency_with_void(x, executeTarget, &newTime);
		struct timespec t = getModifcationTime(name);

		if (t.tv_sec == 0 || newTime.tv_sec == 0 || t.tv_sec < newTime.tv_sec)
		{
			for_each_rule(x, processline);
		}
	}
	struct timespec t = getModifcationTime(name);
	if (newestTime->tv_sec == 0 || t.tv_sec > newestTime->tv_sec)
	{
		*newestTime = t;
	}
}

/* Capture Target
 * 
 */
Target *captureTarget(char *line)
{
	int count = 0;
	strchr(line, ':')[0] = ' ';
	Target *target = NULL;
	char **args = arg_parse(line, &count);
	if (count > 0)
	{
		target = new_target(args[0]);
		int a = 1;
		while (args[a] != NULL)
		{
			append_dependent(target, args[a]);
			a++;
		}
	}
	free(args);
	return target;
}

/* Capture Env Var
 * breaks a line up on the = and sets the env var.
 */
void captureEnvVar(char *line)
{
	strchr(line, '=')[0] = ' ';
	int count = 0;
	char **lineArgs = arg_parse(line, &count);
	if (count < 1)
	{
		setenv(lineArgs[0], lineArgs[1], 1);
	}
	else
	{
		setenv(lineArgs[0], "", 1);
	}
	free(lineArgs);
}

/* Process Line
 * 
 */
void processline(char *line)
{
	line = strdup(line);
	char newLine[1024];
	if (expand(line, newLine, 1024))
	{

		int count = 0;
		char **args = arg_parse(newLine, &count);

		if (count > 0)
		{

			int x = 0;
			int appendOutput = 0;
			int truncateOutput = 0;
			int fileInput = 0;

			while (x < count)
			{
				if (strcmp(args[x], ">") == 0)
				{
					args[x] = NULL;
					truncateOutput = x + 1;
				}
				else if (strcmp(args[x], ">>") == 0)
				{
					args[x] = NULL;
					appendOutput = x + 1;
				}
				else if (strcmp(args[x], "<") == 0)
				{
					args[x] = NULL;
					fileInput = x + 1;
				}
				x++;
			}

			const pid_t cpid = fork();
			switch (cpid)
			{

			case -1:
			{
				perror("fork");
				break;
			}

			case 0:
			{
				if (truncateOutput || appendOutput)
				{

					close(1);
					int fd;
					if (truncateOutput)
					{
						fd = open(args[truncateOutput],
											O_WRONLY | O_CREAT | O_TRUNC,
											0666);
					}
					else
					{
						fd = open(args[appendOutput],
											O_WRONLY | O_CREAT | O_APPEND,
											0666);
					}
					if (fd != 1)
					{
						perror("open");
						exit(1);
					}
				}
				if (fileInput)
				{
					printf("%s \n", args[fileInput]);
					close(0);
					int fd = open(args[fileInput],
												O_RDONLY,
												0666);
					if (fd != 0)
					{
						perror("open");
						exit(1);
					}
				}

				execvp(args[0], args);
				perror("execvp");
				exit(EXIT_FAILURE);
				break;
			}

			default:
			{
				int status;
				const pid_t pid = wait(&status);
				if (-1 == pid)
				{
					perror("wait");
				}
				else if (pid != cpid)
				{
					fprintf(stderr,
									"wait: expected process %d, but waited for process %d",
									cpid, pid);
				}
				break;
			}
			}
		}
		free(args);
	}
	else
	{
		printf("Failed to expand line. \n");
	};
	free(line);
}
