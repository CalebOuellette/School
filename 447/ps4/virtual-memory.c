#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#define MEGABYTE 1024 * 1024
#define GIGABYTE 1024 * 1024 * 1024
int main()
{
  char *ptr;
  long i = 0;
  while (1)
  {
    struct timespec sleepValue;
    sleepValue.tv_nsec = 5000000; // sleep for 5ms
    sleepValue.tv_sec = 0;        // sleep for 1sec
    ptr = malloc(GIGABYTE);       // you use MEGABYTE if small memory
    if (ptr == NULL)
    {
      printf("%ld GB could be allocated before malloc failed\n", i * sizeof(size_t));
      return (0);
    }
    //memset(ptr, i, GIGABYTE); //Line A(uncomment for Q6).

    nanosleep(&sleepValue, NULL);
    i++;
  }
  return (0);
}