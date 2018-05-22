#include <stdbool.h>
#include "bbuff.h"
#include <pthread.h>
#include <stdio.h>
int count = 0;
int in = 0;
int out = 0;
void *buffer[BUFFER_SIZE];

pthread_mutex_t lock;
pthread_cond_t zero;
void bbuff_init(void)
{
  pthread_mutex_init(&lock, NULL);
  pthread_cond_init(&zero, NULL);
};
void bbuff_blocking_insert(void *item)
{
  pthread_mutex_lock(&lock); 
  while (count == BUFFER_SIZE)
    ; // do nothing
  buffer[in] = item;
  in = (in + 1) % BUFFER_SIZE;
  count++;
  if(count == 1){
    pthread_cond_signal(&zero);
  }
  pthread_mutex_unlock(&lock);
  return;
};
void *bbuff_blocking_extract(void)
{
  pthread_mutex_lock(&lock);
  if(count == 0){
    pthread_cond_wait(&zero, &lock);
  }
  void *nextConsumed = buffer[out];
  out = (out + 1) % BUFFER_SIZE;
  count--;
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
