// Shell starter file
// You may make any changes to any part of this file.

#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>
#include <sys/param.h>
#include <sys/types.h>
#include <sys/wait.h>

#define COMMAND_LENGTH 1024
#define NUM_TOKENS (COMMAND_LENGTH / 2 + 1)

#define HISTORY_DEPTH 10
char history[HISTORY_DEPTH][COMMAND_LENGTH];
int historyStart = 0;
int historyCount = 1;

void add_to_history(char *command)
{
  historyCount++;
  strcpy(history[historyStart], command);
  historyStart = (historyStart + 1) % HISTORY_DEPTH;
}

void print_history()
{
  for (int i = 0; i < 10; i++)
  {
    if ((10 - i) < historyCount)
    {
      int x = (i + historyStart) % 10;
      char str[10];
      sprintf(str, "%d", (historyCount - (10 - i)));
      write(STDERR_FILENO, str, strlen(str));
      write(STDERR_FILENO, "\t", strlen("\t"));
      write(STDERR_FILENO, history[x], strlen(history[x]));
      write(STDERR_FILENO, "\n", strlen("\n"));
    }
  }
}

char *get_command_by_history(int i)
{
  int x = historyStart - (historyCount - i);
  return history[x];
}

void check_background(int token_count, char *tokens[], _Bool *in_background)
{
  if (token_count > 0 && strcmp(tokens[token_count - 1], "&") == 0)
  {
    *in_background = true;
    tokens[token_count - 1] = 0;
  }
}

/**
 * Command Input and Processing
 */

/*
 * Tokenize the string in 'buff' into 'tokens'.
 * buff: Character array containing string to tokenize.
 *       Will be modified: all whitespace replaced with '\0'
 * tokens: array of pointers of size at least COMMAND_LENGTH/2 + 1.
 *       Will be modified so tokens[i] points to the i'th token
 *       in the string buff. All returned tokens will be non-empty.
 *       NOTE: pointers in tokens[] will all point into buff!
 *       Ends with a null pointer.
 * returns: number of tokens.
 */
int tokenize_command(char *buff, char *tokens[], _Bool *in_background)
{
  int token_count = 0;
  _Bool in_token = false;
  int num_chars = strnlen(buff, COMMAND_LENGTH);
  for (int i = 0; i < num_chars; i++)
  {
    switch (buff[i])
    {
    // Handle token delimiters (ends):
    case ' ':
    case '\t':
    case '\n':
      buff[i] = '\0';
      in_token = false;
      break;

    // Handle other characters (may be start)
    default:
      if (!in_token)
      {
        tokens[token_count] = &buff[i];
        token_count++;
        in_token = true;
      }
    }
  }
  tokens[token_count] = NULL;
  check_background(token_count, tokens, in_background);
  return token_count;
}

/**
 * Read a command from the keyboard into the buffer 'buff' and tokenize it
 * such that 'tokens[i]' points into 'buff' to the i'th token in the command.
 * buff: Buffer allocated by the calling code. Must be at least
 *       COMMAND_LENGTH bytes long.
 * tokens[]: Array of character pointers which point into 'buff'. Must be at
 *       least NUM_TOKENS long. Will strip out up to one final '&' token.
 *       tokens will be NULL terminated (a NULL pointer indicates end of tokens).
 * in_background: pointer to a boolean variable. Set to true if user entered
 *       an & as their last token; otherwise set to false.
 */

void read_command(char *buff, char *tokens[], _Bool *in_background)
{
  *in_background = false;

  // Read input
  int length = read(STDIN_FILENO, buff, COMMAND_LENGTH - 1);

  if (length < 0 && (errno != EINTR))
  {
    perror("Unable to read command from keyboard. Terminating.\n");
    exit(-1);
  }

  // Null terminate and strip \n.
  buff[length] = '\0';
  if (buff[strlen(buff) - 1] == '\n')
  {
    buff[strlen(buff) - 1] = '\0';
  }

  if (buff[0] != '!')
  {
    add_to_history(buff);
  }

  // Tokenize (saving original command string)
  int token_count = tokenize_command(buff, tokens, in_background);
  if (token_count == 0)
  {
    return;
  }
}

/**
 * forks the process and executes the child.
**/
void exec_child(char **tokens, _Bool in_background)
{
  const pid_t pid = fork();
  if (pid == 0)
  {
    int result = execvp(tokens[0], tokens);
    if (result == -1)
    {
      write(STDERR_FILENO, strerror(errno), strlen(strerror(errno)));
      write(STDERR_FILENO, "\n", strlen("\n"));
    }
  }
  else if (pid == -1)
  {
    perror("fork");
  }
  else
  {
    if (!in_background)
    {
      int status;
      waitpid(pid, &status, 0);
    }
  }
}

/**
 * Have you played left for dead?
**/
void kill_the_zombies()
{
  // Cleanup any previously exited background child processes
  // (The zombies)
  while (waitpid(-1, NULL, WNOHANG) > 0)
    ; // do nothing.
}

void print_cwd()
{
  char *path = malloc(MAXPATHLEN * sizeof(char *));
  getcwd(path, MAXPATHLEN);
  write(STDOUT_FILENO, path, strlen(path));
  free(path);
}

void interpret_tokens(char **tokens, _Bool in_background)
{
  if (strcmp(tokens[0], "exit") == 0)
  {
    exit(EXIT_SUCCESS);
  }
  else if (strcmp(tokens[0], "pwd") == 0)
  {
    print_cwd();
    write(STDOUT_FILENO, "\n", strlen("\n"));
  }
  else if (strcmp(tokens[0], "cd") == 0)
  {
    int err = chdir(tokens[1]);
    if (err == -1)
    {
      write(STDERR_FILENO, strerror(errno), strlen(strerror(errno)));
      write(STDERR_FILENO, "\n", strlen("\n"));
    }
  }
  else if (strcmp(tokens[0], "history") == 0)
  {
    print_history();
  }
  else if (strcmp(tokens[0], "") == 0)
  {
  }
  else if (tokens[0][0] == '!')
  {
    print_history();
    if (tokens[0][1] == '!')
    {
      char *command = get_command_by_history(historyCount - 1);
      add_to_history(command);
      char *tokens[NUM_TOKENS];
      _Bool in_background = false;
      tokenize_command(command, tokens, &in_background);
      exec_child(tokens, in_background);
    }
    else
    {
      char *num = tokens[0] + 1;
      int i = atoi(num);
      if (i == 0)
      {
        write(STDERR_FILENO, "Not a valid history number \n", strlen("Not a valid history number \n"));
      }
      else if (i >= historyCount)
      {
        write(STDERR_FILENO, "Not a valid history number \n", strlen("Not a valid history number \n"));
      }
      else
      {
        char *command = get_command_by_history(i);
        add_to_history(command);
        char *tokens[NUM_TOKENS];
        _Bool in_background = false;
        tokenize_command(command, tokens, &in_background);
        exec_child(tokens, in_background);
      }
    }
  }
  else
  {
    exec_child(tokens, in_background);
  }
}

void sig_handler(int i)
{
  write(STDOUT_FILENO, "\n", strlen("\n"));
  print_history();
  print_cwd();
  write(STDOUT_FILENO, "> ", strlen("> "));
}
/**
 * Main and Execute Commands
 */
int main(int argc, char *argv[])
{
  signal(SIGINT, sig_handler);

  char input_buffer[COMMAND_LENGTH];
  char *tokens[NUM_TOKENS];
  while (true)
  {

    // Get command
    // Use write because we need to use read() to work with
    // signals, and read() is incompatible with printf().
    print_cwd();
    write(STDOUT_FILENO, "> ", strlen("> "));
    _Bool in_background = false;

    read_command(input_buffer, tokens, &in_background);
    // DEBUG: Dump out arguments:
    for (int i = 0; tokens[i] != NULL; i++)
    {
      write(STDOUT_FILENO, "   Token: ", strlen("   Token: "));
      write(STDOUT_FILENO, tokens[i], strlen(tokens[i]));
      write(STDOUT_FILENO, "\n", strlen("\n"));
    }
    if (in_background)
    {
      write(STDOUT_FILENO, "Run in background.", strlen("Run in background."));
    }

    /**
		 * Steps For Basic Shell:
		 * 1. Fork a child process
		 * 2. Child process invokes execvp() using results in token array.
		 * 3. If in_background is false, parent waits for
		 *    child to finish. Otherwise, parent loops back to
		 *    read_command() again immediately.
		 */
    kill_the_zombies();
    interpret_tokens(tokens, in_background);
  }
  return 0;
}
