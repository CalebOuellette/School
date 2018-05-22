#include <stdbool.h>
#include "bbuff.h"
#include <pthread.h>
int count = 0;
int in = 0;
int out = 0;
void *buffer[BUFFER_SIZE];

pthread_mutex_t lock;

void bbuff_init(void)
{
  pthread_mutex_init(&lock, NULL);
};
void bbuff_blocking_insert(void *item)
{
  pthread_mutex_lock(&lock);
  while (count == BUFFER_SIZE)
    ; // do nothing
  buffer[in] = item;
  in = (in + 1) % BUFFER_SIZE;
  count++;
  pthread_mutex_unlock(&lock);
};
void *bbuff_blocking_extract(void)
{
  pthread_mutex_lock(&lock);
  while (count == 0)
    ; // do nothing
  void *nextConsumed = buffer[out];
  out = (out + 1) % BUFFER_SIZE;
  count--;
  pthread_mutex_unlock(&lock);
  return nextConsumed;
};