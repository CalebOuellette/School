#include <time.h>
#include <unistd.h>
#include <sys/syscall.h>
#include <stdio.h>

/*
* 1. 
*    a. WWU work machine 
*    b. 4.5 ms
*    c. 4.5 ns
* 2. 
*    a. 6.3 ms
*    c. 6.3 ns
* 3. 
*    a.
*/

void one()
{
  for (int i = 0; i < 1000000; i++)
  {
  }
}

void two()
{
  for (int i = 0; i < 1000000; i++)
  {
    getpid();
  }
}

void three()
{
  for (int i = 0; i < 1000000; i++)
  {
    syscall(SYS_getpid);
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
  //one();
  //two();
  three();
  /* end timing */
  clock_gettime(CLOCK_PROCESS_CPUTIME_ID, &end_time);
  printf("%f ms\n", timespec_to_ms(&end_time) - timespec_to_ms(&start_time));
}