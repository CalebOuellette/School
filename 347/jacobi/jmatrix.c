
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <math.h>

#include "jmatrix.h"
#include "jbarrier.h"
#include "arg_parse.h"

/*
* Loads the matrix in from a file
*/
matrix *loadMatrix(char *filePath)
{
  FILE *makefile = fopen(filePath, "r");
  if (makefile == NULL)
  {
    fprintf(stderr, "Failed to open matrix data in file: %s \n", filePath);
    return NULL;
  }
  size_t bufsize = 0;
  char *line = NULL;

  getline(&line, &bufsize, makefile);

  matrix *input = malloc(sizeof(matrix));
  int lineNum = 0;

  while (lineNum < matrixSize)
  {
    int cursor = 0;
    char **row = build_arg_pointers(line, matrixSize);
    while (cursor < matrixSize)
    {
      (*input)[lineNum][cursor] = atof(row[cursor]);
      cursor++;
    }
    lineNum++;
    getline(&line, &bufsize, makefile);
  }
  return input;
}
/* Copy Matrix Sides 
* copies the sides of one matrix to another.
*/
void copyMatrixSides(matrix *src, matrix *dest)
{

  for (int j = 0; j < matrixSize; j++)
  {
    (*dest)[0][j] = (*src)[0][j];
    (*dest)[matrixSize - 1][j] = (*src)[matrixSize - 1][j];
  }
  for (int i = 0; i < matrixSize; i++)
  {
    (*dest)[i][0] = (*src)[i][0];
    (*dest)[i][matrixSize - 1] = (*src)[i][matrixSize - 1];
  }
}
/* Print Matrix
* prints the matrix to a file.
*/
void printMartix(matrix *input, char *filePath)
{
  FILE *file;

  file = fopen(filePath, "w+");

  for (int i = 0; i < matrixSize; i++)
  {
    for (int j = 0; j < matrixSize; j++)
    {
      fprintf(file, "%.12f ", (*input)[i][j]);
    }
    fprintf(file, "\n");
  }

  fclose(file);
}

/* Matrix Average
* Calculates the average from the for each square based off the surrounding squares.
*/
float matrixRowAverage(matrix *src, matrix *dest, int rowNum)
{
  float localMaxChange = 0;
  for (int j = 1; j < (matrixSize - 1); j++)
  {

    (*dest)[rowNum][j] = (((*src)[rowNum - 1][j] + (*src)[rowNum + 1][j] + (*src)[rowNum][j - 1] + (*src)[rowNum][j + 1]) / 4);
    if (fabsf((*dest)[rowNum][j] - (*src)[rowNum][j]) > localMaxChange)
    {
      localMaxChange = fabsf((*dest)[rowNum][j] - (*src)[rowNum][j]);
    }
  }

  return localMaxChange;
}

/* Matrix Compare
* Finds the largest difference between two matrices and returns it
*/
float matrixCompare(matrix *a, matrix *b)
{
  float error = 0;
  for (int i = 0; i < matrixSize; i++)
  {
    for (int j = 0; j < matrixSize; j++)
    {
      if (fabsf((*a)[i][j] - (*b)[i][j]) > error)
      {
        error = fabsf((*a)[i][j] - (*b)[i][j]);
      }
    }
  }
  return error;
}

/* Matrix Average X Rows
* Calls matrix Row average for every increment and starts at the offset
* this divides the world depending on the thread.
*/
float matrixAverageXRows(matrix *src, matrix *dest, int offset, int increment)
{
  float maxChange = 0;
  float rowMaxChange = 0;
  for (int i = 1 + offset; i < matrixSize - 1; i += increment)
  {
    rowMaxChange = matrixRowAverage(src, dest, i);
    if (rowMaxChange > maxChange)
    {
      maxChange = rowMaxChange;
    }
  }
  return maxChange;
}