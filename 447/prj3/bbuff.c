#include <stdbool.h>
#include "bbuff.h"
#include <pthread.h>
#include <stdio.h>
int count = 0;
int in = 0;
int out = 0;
void *buffer[BUFFER_SIZE];

pthread_mutex_t lock  = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t empty = PTHREAD_COND_INITIALIZER;
pthread_cond_t full = PTHREAD_COND_INITIALIZER;

void bbuff_init(void)
{
};
void bbuff_blocking_insert(void *item)
{
  pthread_mutex_lock(&lock); 
  if (count == BUFFER_SIZE){
    pthread_cond_wait(&full, &lock);
  }
  buffer[in] = item;
  in = (in + 1) % BUFFER_SIZE;
  count++;
  pthread_cond_signal( &empty ); 
  pthread_mutex_unlock(&lock);
  return;
};
void *bbuff_blocking_extract(void)
{
  pthread_mutex_lock(&lock);
  if (count == 0){
    pthread_cond_wait(&empty, &lock);
  }
  void *nextConsumed = buffer[out];
  out = (out + 1) % BUFFER_SIZE;
  count--;
  pthread_cond_signal( &full ); 
  pthread_mutex_unlock(&lock);
  return nextConsumed;
};

_Bool bbuff_is_empty(void)
{
  if (count == 0)
  {
    return true;
  }
  return false;
}
