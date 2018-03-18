//By Caleb Ouellette & Chris White

#include <stdio.h>
#include <stdlib.h>
#include <strings.h>
#include <pthread.h>
#include <unistd.h>
#include "arg_parse.h"
#include "jmatrix.h"
#include "jbarrier.h"

// Prototypes

void *runProgram(void *arg);

void onthreadSync();

void parseArgs();
// Constants
float THRESHOLD = .00001;

// Globals

pthread_mutex_t updateGlobalMax;

int noth;
float maxChange = 1;
float iterationMax = 0;
char *file = "./input.mtx";

matrix *output;
matrix *input;

jbarrier *waitBarrier;

int shouldOutput = 0;
int shouldTest = 0;
char *testFile;

int main(int argc, const char *argv[])
{
  parseArgs(argc, argv);
  pthread_mutex_init(&updateGlobalMax, NULL);
  jbarrier_init(&waitBarrier, noth);
  if (access(file, F_OK) == -1)
  {
    fprintf(stderr, "Exiting. Could not find input file : %s \n", file);
    return EXIT_FAILURE;
  }
  input = loadMatrix(file);
  output = malloc(sizeof(matrix));
  copyMatrixSides(input, output);

  pthread_t tid[noth];
  int start[noth];
  for (int i = 0; i < noth; i++)
  {
    start[i] = i;
    pthread_create(&(tid[i]), NULL, &runProgram, (void *)&start[i]);
  }

  for (int i = 0; i < noth; i++)
  {
    pthread_join(tid[i], NULL);
  }
  printf("Exectued for %d threads\n", noth);

  if (shouldTest == 1)
  {
    output = loadMatrix(testFile);
    float error = matrixCompare(output, input);
    printf("Largest error: %f between output and %s \n", error, testFile);
  }

  if (shouldOutput == 1)
  {
    printMartix(input, "output.mtx");
  }

  free(input);
  free(output);
  return EXIT_SUCCESS;
}

/* On Thread Sync
* Called once everytime the threads sync, and prepares the state for the next iteration.
* We take a short cut on the write back by just swapping the two matrices
*/
void onthreadSync()
{
  maxChange = iterationMax;
  matrix *temp;
  temp = input;
  input = output;
  output = temp;
  iterationMax = 0;
}

/* Run Program
*  This is the heart of the program. Each thread executes this until the matrix has been jacobied.
*/
void *runProgram(void *arg)
{
  int threadID;
  threadID = *(int *)arg;

  while (maxChange > THRESHOLD)
  {
    float threadMaxChange = matrixAverageXRows(input, output, threadID, noth);
    pthread_mutex_lock(&updateGlobalMax);
    if (threadMaxChange > iterationMax)
    {
      iterationMax = threadMaxChange;
    }
    pthread_mutex_unlock(&updateGlobalMax);
    jbarrier_sync(&waitBarrier, &onthreadSync);
  }
  return NULL;
}

/* Parse Args
* Deals with the input flags.
* Is there something build in to deal with this? I should look into that...
*/
void parseArgs(int argc, const char *argv[])
{
  noth = atoi(argv[1]);
  int a = 2;
  while (a < argc)
  {
    if (strcmp(argv[a], "-test") == 0)
    {
      shouldTest = 1;
      testFile = strdup(argv[a + 1]);
      a++;
    }
    else if (strcmp(argv[a], "-t") == 0)
    {
      THRESHOLD = atof(argv[a + 1]);
      a++;
    }
    else if (strcmp(argv[a], "-o") == 0)
    {
      shouldOutput = 1;
    }
    a++;
  }
}