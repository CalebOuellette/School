#ifndef _ARG_PARSE_
#define _ARG_PARSE_

/* Arg Parse
 * line   The command line to execute.
 * This function interprets the arguments of a given line.
 * The funciton will be looking for the format (Tab)(argument1)(space)(argument
 * (2)... 
 */
char **arg_parse(char *line, int *argcp);

#endif
