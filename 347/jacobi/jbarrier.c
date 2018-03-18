
#include <pthread.h>
#include <stdlib.h>
#include "jbarrier.h"
#include <stdio.h>
struct jbarrier_st
{
  int noth;
  int threadsWaiting;
  pthread_cond_t synced;
  pthread_mutex_t m;
};
typedef struct jbarrier_st jbarrier;

/* jbarrier Init
* jbarrier **b 
* int noth - Number of threads
*/
void jbarrier_init(jbarrier **b, int noth)
{
  *b = malloc(sizeof(struct jbarrier_st));
  (*b)->noth = noth;
  (*b)->threadsWaiting = 0;
  pthread_mutex_init(&(*b)->m, NULL);
  pthread_cond_init(&(*b)->synced, NULL);
}

/* jbarrier Sync
* jbarrier **b 
* void (*onSync)() - A function that is called when all threads show up, but before they are released.
* Sync waits for all threads to show up, then lets them go.
*/
void jbarrier_sync(jbarrier **b, void (*onSync)())
{
  pthread_mutex_lock(&(*b)->m);
  (*b)->threadsWaiting += 1;
  if ((*b)->threadsWaiting == (*b)->noth)
  {

    (*b)->threadsWaiting = 0;
    onSync();
    pthread_cond_broadcast(&((*b)->synced));
  }
  else
  {
    pthread_cond_wait(&((*b)->synced), &((*b)->m));
  }

  pthread_mutex_unlock(&((*b)->m));
}