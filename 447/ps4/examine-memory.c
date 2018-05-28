#include <stdio.h>
int sum;
int func(int x) { sum += x; }
int main(int argc, char *argv)
{
  sum = 5;
  func(10);
  printf("Sum= %d\n", sum);
}