#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "expand.h"

static char *getFirstVarName(char *line, int *length)
{
	if (line[0] != '$' || line[1] != '{')
	{
		//Not really the start of a var.
		return NULL;
	}
	line = &line[2];
	char *end = strchr(line, '}');
	if (end == NULL)
	{
		printf("Can't find closing bracket.");
		return NULL;
	}
	int varSize = end - line;
	char *str = malloc(sizeof(char) * (varSize + 1));
	strncpy(str, line, varSize);
	str[varSize + 1] = '\0';
	*length = *length + 2 + varSize + 1;
	return str;
}

int expand(char *orig, char *new, int newsize)
{
	int o_cursor = 0;
	int n_cursor = 0;
	while (orig[o_cursor] != '\0' && n_cursor < newsize)
	{
		if (orig[o_cursor] != '$')
		{
			new[n_cursor] = orig[o_cursor];
			o_cursor++;
			n_cursor++;
		}
		else
		{
			int nameLength = 0;
			char *varName = getFirstVarName(&orig[o_cursor], &nameLength);
			if (varName != NULL)
			{
				o_cursor = o_cursor + nameLength;
				char *varValue = getenv(varName);

				if (varValue != NULL)
				{
					int i = 0;
					while (varValue[i] != '\0' && n_cursor < newsize)
					{
						new[n_cursor] = varValue[i];
						n_cursor++;
						i++;
					}
				}
				free(varName);
			}
			else
			{
				new[n_cursor] = orig[o_cursor];
				o_cursor++;
				n_cursor++;
			}
		}
	};

	if (n_cursor == newsize && orig[o_cursor] != '\0')
	{
		printf("Buffer of size %d is too small \n", newsize);
		return 0;
	}
	else
	{
		new[n_cursor] = '\0';
	}
	return 1;
};
