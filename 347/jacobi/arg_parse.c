
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <ctype.h>

/* Arg count
 * line   The command line to execute.
 * This is a helper function to arg_parse that counts how many arguments are in 
 * a given line.
 */
static int arg_count(char *line);

/* Build Arg Pointers 
* line   The command line to execute.
* This function will use the line input to fill the argArray with pointers to 
* where each arguments starts in line.
* This function will also add a '\0' after each argument to make sure the 
* strings don't run together.
*/
char **build_arg_pointers(char *line, int argArrayCount);

/* Arg Parse
 * Changes a line to an array of pointers to strings with a 
 * Null as the last item in that string.
 */
char **arg_parse(char *line, int *argcp)
{
  assert(line != NULL && "line can't be null");
  *argcp = arg_count(line);
  return build_arg_pointers(line, *argcp);
}

/* Arg Count
 * There are two states to processing a line. 1. The cursor is in white space. 
 * 2. The cursor is not in whitespace (it's on a arg).
 * I look for the transition between those two states and count how many times 
 * we go from whitespace to arg. Then return that count.
 * I currently assume the only possible white space is a space or a tab.
 */
static int arg_count(char *line)
{
  assert(line != NULL && "line can't be null");
  int argCount = 0;
  int cursorOnArg = 0;
  int i = 0;
  while (line[i] != '\0')
  {
    if (line[i] == '\t' || line[i] == ' ')
    {
      if (cursorOnArg == 1)
      {
        cursorOnArg = 0;
      }
    }
    else
    {
      if (cursorOnArg == 0)
      {
        argCount += 1;
        cursorOnArg = 1;
      }
    }
    i++;
  }
  return argCount;
}

/* Build Arg Pointers
 * Uses the same logic as Arg Count. to see if the cursor is in white space or 
 * not.
 * On transition from whitespace to arg, pointer is added to argArray at the 
 * start of that arg.
 * On transition from arg to whitespace, a '\0' character is added to the 
 * string.
 */
char **build_arg_pointers(char *line, int argArrayCount)
{
  assert(line != NULL && "line should not be null");
  char **argArray = malloc((argArrayCount + 1) * sizeof(char *));
  argArray[argArrayCount] = NULL;

  // This method assumes that the memory allocated in argArray is equal to
  // the number of arguments in line.
  int cursorOnArg = 0;
  int i = 0;
  int argCount = 0;
  while (line[i] != '\0' && argCount <= argArrayCount)
  {
    if (isspace(line[i]))
    {
      if (cursorOnArg == 1)
      {
        line[i] = '\0';
        cursorOnArg = 0;
      }
    }
    else
    {
      if (cursorOnArg == 0)
      {
        argArray[argCount] = &line[i];
        cursorOnArg = 1;
        argCount += 1;
      }
    }
    i++;
  }
  return argArray;
}
