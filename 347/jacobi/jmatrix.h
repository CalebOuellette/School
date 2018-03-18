#ifndef _jmatrix_H_
#define _jmatrix_H_
#define matrixSize 1024
typedef float matrix[matrixSize][matrixSize];
matrix *loadMatrix(char *filePath);
void copyMatrixSides(matrix *src, matrix *dest);
void printMartix(matrix *input, char *filePath);
float matrixRowAverage(matrix *src, matrix *dest, int rowNum);
float matrixCompare(matrix *a, matrix *b);
float matrixAverageXRows(matrix *src, matrix *dest, int offset, int increment);
#endif
