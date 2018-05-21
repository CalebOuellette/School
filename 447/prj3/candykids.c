#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <stdbool.h>

#include "bbuff.h"
#include "stats.h"
// Prototypes
void parse_args(int argc, const char *argv[]);

double current_time_in_ms(void);
void *candy_factory(void *arg);
void create_factories();

int FACTORIES;
int KIDS;
int SECONDS;

_Bool factories_running = true;
_Bool kids_running = true;

typedef struct
{
  int factory_number;
  double time_stamp_in_ms;
} candy_t;

int main(int argc, const char *argv[])
{

  parse_args(argc, argv); //    1.        Extract    arguments

  //    2.        Initialize    modules
  create_factories(); //    3.        Launch    candy-­‐factory    threads
  //    4.        Launch    kid    threads
  //    5.        Wait    for    requested    time
  //    6.        Stop    candy-­‐factory    threads
  //    7.        Wait    until    no    more    candy
  //    8.        Stop    kid    threads
  //    9.        Print    statistics
  //    10.    Cleanup    any    allocated    memory
}

void parse_args(int argc, const char *argv[])
{
  if (argc != 3)
  {
    fprintf(stderr, "Must enter 3 arguments <#FACTORIES> <#kids> <#seconds>");
    exit(0);
  }
  for (int i = 1; i < argc; i++)
  {
    if (atof(argv[i]) < 0)
    {
      fprintf(stderr, "All args must be greater then 0.");
      exit(0);
    }
  }
  FACTORIES = atof(argv[1]);
  KIDS = atof(argv[2]);
  SECONDS = atof(argv[3]);
}

void create_factories()
{
  pthread_t tid[FACTORIES];
  int start[FACTORIES];
  for (int i = 0; i < FACTORIES; i++)
  {
    start[i] = i;
    pthread_create(&(tid[i]), NULL, &candy_factory, (void *)&start[i]);
  }
}

void *candy_factory(void *arg)
{

  int threadID;
  threadID = *(int *)arg;
  while (factories_running)
  {
    candy_t *candy = malloc(sizeof(candy_t));
    candy->factory_number = threadID;
    candy->time_stamp_in_ms = current_time_in_ms();
    bbuff_blocking_insert(candy);
  }

  return NULL;
}

double current_time_in_ms(void)
{
  struct timespec now;
  clock_gettime(CLOCK_REALTIME, &now);
  return now.tv_sec * 1000.0 + now.tv_nsec / 1000000.0;
}