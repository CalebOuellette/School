#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <pthread.h>
#include <unistd.h>

void *fibonacci(void *arg);

int *seqenceValues;
int length;
int main(int argc, char *argv[])
{
  printf("\nPID is: %d \t Username: %s\n", getpid(), getlogin());
  assert(argc == 2);
  length = atoi(argv[1]);
  assert(length > 0);
  seqenceValues = malloc(sizeof(int) * length);

  pthread_t worker;
  pthread_create(&worker, NULL, &fibonacci, NULL);
  pthread_join(worker, NULL);
  for (int i = 0; i < length; i++)
  {
    printf("value %d \n", seqenceValues[i]);
  }
}

void *fibonacci(void *arg)
{
  seqenceValues[0] = 1;
  if (length >= 2)
  {
    seqenceValues[1] = 1;
  }
  for (int i = 2; i < length; i++)
  {
    seqenceValues[i] = seqenceValues[i - 1] + seqenceValues[i - 2];
  }
  return NULL;
}