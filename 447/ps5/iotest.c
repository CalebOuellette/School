#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define NUM_OF_TIMES 1000000

int main(int argc, char *argv[])
{
  int j;
  for (j = 0; j < NUM_OF_TIMES; j++)
  {
    write(1, "x", 1);
    // printf("x");
  }
return 0;}