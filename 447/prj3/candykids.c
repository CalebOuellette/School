#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <stdbool.h>
#include <unistd.h>

#include "bbuff.h"
#include "stats.h"
// Prototypes
void parse_args(int argc, const char *argv[]);

double current_time_in_ms(void);
void *candy_factory(void *arg);
void create_factories();

void create_kids();
void *candy_kid(void *arg);

void stop_threads(pthread_t tid[], int count);

int FACTORIES;
int KIDS;
int SECONDS;

_Bool factories_running = true;
_Bool kids_running = true;

int factorysCreate = 0;
pthread_mutex_t fclock  = PTHREAD_MUTEX_INITIALIZER;


typedef struct
{
  int factory_number;
  double time_stamp_in_ms;
} candy_t;

int main(int argc, const char *argv[])
{

  parse_args(argc, argv); //    1.        Extract    arguments
  printf("%d kids, %d facotires, %d time", KIDS , FACTORIES ,SECONDS );
  bbuff_init();           //    2.        Initialize    modules
  stats_init(FACTORIES);
  pthread_t factoryt[FACTORIES];
  create_factories(factoryt); //    3.        Launch    candy-­‐factory    threads
  pthread_t kidt[KIDS];
  create_kids(kidt); //    4.        Launch    kid    threads
  int t = 0;         //    5.        Wait    for    requested    time
  while (t < SECONDS)
  {
    printf("Time  %ds: \n", t);
    sleep(1);
    t++;
  }

  printf("Stopping Factories \n");
  factories_running = false;
  stop_threads(factoryt, FACTORIES); //    6.        Stop    candy-­‐factory    threads

  while (bbuff_is_empty() == false) //    7.        Wait    until    no    more    candy
    ;
  printf("Stopping Kids \n");
  kids_running = false;
  stop_threads(kidt, KIDS); //    8.        Stop    kid    threads
  stats_display(); //    9.        Print    statistics
  //    10.    Cleanup    any    allocated    memory
  return EXIT_SUCCESS;
}

void parse_args(int argc, const char *argv[])
{
  if (argc != 4)
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

void create_kids(pthread_t tid[])
{
  int start[KIDS];
  for (int i = 0; i < KIDS; i++)
  {
    start[i] = i;
    pthread_create(&(tid[i]), NULL, &candy_kid, (void *)&start[i]);
  }
}

void *candy_kid(void *arg)
{
  pthread_setcanceltype(PTHREAD_CANCEL_ASYNCHRONOUS,NULL);
  pthread_setcancelstate(PTHREAD_CANCEL_ENABLE, NULL);
  while (kids_running)
  {
    int wait = rand() % 2;
    candy_t *candy =(candy_t*) bbuff_blocking_extract();
    stats_record_consumed(candy->factory_number, current_time_in_ms() - candy->time_stamp_in_ms);
    free(candy);
    sleep(wait);
  }
  return NULL;
}

void create_factories(pthread_t tid[])
{
  int start[FACTORIES];
  for (int i = 0; i < FACTORIES; i++)
  {
    start[i] = i;
    pthread_create(&(tid[i]), NULL, &candy_factory, (void *)&(start[i]));
  }
}

void *candy_factory(void *arg)
{
  int threadID;
  pthread_mutex_lock(&fclock); 
  threadID = factorysCreate;
  factorysCreate++;
  pthread_mutex_unlock(&fclock); 
  printf("Factory %d started \n", threadID);
  while (factories_running)
  {
    int wait = rand() % 4;
    printf("Factory %d ships candy & waits %ds \n", threadID, wait);
    candy_t *candy = malloc(sizeof(candy_t));
    if(candy == 0){
      printf("Malloc ERROR");
    }
    candy->factory_number = threadID;
    candy->time_stamp_in_ms = current_time_in_ms();
    bbuff_blocking_insert(candy);
    stats_record_produced(threadID);
    sleep(wait);
  }
  printf("Candy-Factory %d done \n", threadID);
  return NULL;
}

double current_time_in_ms(void)
{
  struct timespec now;
  clock_gettime(CLOCK_REALTIME, &now);
  return now.tv_sec * 1000.0 + now.tv_nsec / 1000000.0;
}

void stop_threads(pthread_t tid[], int count)
{
  for (int i = 0; i < count; i++)
  {
    printf("Stopping thread \n");
    pthread_cancel(tid[i]);
    pthread_join(tid[i], NULL);
  }
}

