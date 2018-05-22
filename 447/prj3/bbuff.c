#include <stdbool.h>
#include "bbuff.h"
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

int count = 0;
int in = 0;
int out = 0;
void *buffer[BUFFER_SIZE];
_Bool ending = false;

int prodWaiters = 0;
int consumerWaiter = 0;

pthread_mutex_t bblock  = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t bbempty = PTHREAD_COND_INITIALIZER;
pthread_cond_t bbfull = PTHREAD_COND_INITIALIZER;
pthread_cond_t producersComplete = PTHREAD_COND_INITIALIZER;

void bbuff_init()
{
};

void bbuff_blocking_insert(void *item)
{
  pthread_mutex_lock(&bblock); 
  while (count == BUFFER_SIZE){
    pthread_cond_wait(&bbfull, &bblock);
  }
  count++;
  buffer[in] = item;
  in = (in + 1) % (BUFFER_SIZE);
  if(count == 1){
    pthread_cond_signal( &bbempty ); 
  }
  pthread_mutex_unlock(&bblock);
  return;
};

void *bbuff_blocking_extract()
{
  pthread_mutex_lock(&bblock);
  while (count == 0){
    pthread_cond_wait(&bbempty, &bblock);
    printf("waited\n");
  }
  count--;
  
  void *nextConsumed = buffer[out];
  out = (out + 1) % (BUFFER_SIZE);
  if(count == 9){
    pthread_cond_signal( &bbfull ); 
  }
  pthread_mutex_unlock(&bblock);
  return nextConsumed;
};