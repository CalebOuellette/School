#include <time.h>
#include <unistd.h>
#include <sys/syscall.h>
#include <stdio.h>

/*
* 1. 
*    a. 
*/

void one()
{
  for (int i = 0; i < 1000000; i++)
  {
  }
}

double timespec_to_ms(struct timespec *ts)
{
  return ts->tv_sec * 1000.0 + ts->tv_nsec / 1000000.0;
}
int main()
{
  struct timespec start_time, end_time;
  clock_gettime(CLOCK_PROCESS_CPUTIME_ID, &start_time);
  /* begin timing */
  one();
  /* end timing */
  clock_gettime(CLOCK_PROCESS_CPUTIME_ID, &end_time);
  printf("%f ms\n", timespec_to_ms(&end_time) - timespec_to_ms(&start_time));
}
