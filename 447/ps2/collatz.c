#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <unistd.h>
void collatz(int value)
{
  printf("value %d \n", value);
  assert(value >= 1);
  if (value == 1)
  {
    return;
  }
  else if (value % 2)
  {
    //if odd
    value = (value * 3) + 1;
    collatz(value);
  }
  else
  {
    //if even
    value = value / 2;
    collatz(value);
  }
}

int main(int argc, char *argv[])
{
  printf("\nPID is: %d \t Username: %s\n", getpid(), getlogin());
  assert(argc == 2);
  int value = atoi(argv[1]);

  const pid_t cpid = fork();

  if (cpid == 0)
  {
    //child
    collatz(value);
  }
  else
  {
    //parent
    int status;
    const pid_t pid = wait(&status);
    return EXIT_SUCCESS;
  }
}