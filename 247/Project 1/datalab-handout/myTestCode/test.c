#include <stdio.h>

void printBits(unsigned int num)
{
    unsigned int size = sizeof(unsigned int);
    unsigned int maxPow = 1 << (size * 8 - 1);
    printf("MAX POW : %u\n", maxPow);
    int i = 0, j;
    for (; i < size * 8; ++i)
    {
        // print last bit and shift left.
        printf("%u ", num & maxPow ? 1 : 0);
        num = num << 1;
    }
}

int dl10(int highbit, int lowbit)
{
    int result = 0;
    int i;
    printBits(highbit);
    printBits(lowbit);

    for (i = lowbit; i <= highbit; i++)
    {

        result |= 1 << i;
    }
    printBits(result);
    return result;
}

int dl10solve(int highbit, int lowbit)
{
    //~ & + <<
    int result = 0; //highbit + lowbit;
    int a = ~0 << lowbit;
    int b = ~1 << highbit;

  //  printBits(a);
   // printBits(~b);
    printBits(a & ~b);

    return a & ~b;
}



int test_dl11(int x, int y)
{
    return ~(~x & ~y);
    //return (x | y);
}

int main(int argc, char *argv[])
{
     int a = 31;
     int b = 2;
     printf("Normal");
     dl10(a, b);

     printf("\n Solution \n");
     dl10solve(a, b);
     printf("\n");
      
      

    return 0;
}
