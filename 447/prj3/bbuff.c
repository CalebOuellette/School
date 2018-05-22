#include <stdbool.h>
#include "bbuff.h"
#include <pthread.h>
#include <stdio.h>
int count = 0;
int in = 0;
int out = 0;
void *buffer[BUFFER_SIZE];

pthread_mutex_t bblock  = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t bbempty = PTHREAD_COND_INITIALIZER;
pthread_cond_t bbfull = PTHREAD_COND_INITIALIZER;

void bbuff_init()
{
};
void bbuff_blocking_insert(void *item)
{
  pthread_mutex_lock(&bblock); 
  while (count == BUFFER_SIZE){
    pthread_cond_wait(&bbfull, &bblock);
  }
  buffer[in] = item;
  in = (in + 1) % (BUFFER_SIZE);
  count++;
  pthread_cond_signal( &bbempty ); 
  pthread_mutex_unlock(&bblock);
  return;
};
void *bbuff_blocking_extract()
{
  pthread_mutex_lock(&bblock);
  while (count == 0){
    pthread_cond_wait(&bbempty, &bblock);
  }
  void *nextConsumed = buffer[out];
  out = (out + 1) % (BUFFER_SIZE);
  count--;
  pthread_cond_signal( &bbfull ); 
  pthread_mutex_unlock(&bblock);
  return nextConsumed;
};

_Bool bbuff_is_empty()
{
  if (count == 0)
  {
    return true;
  }
  return false;
}