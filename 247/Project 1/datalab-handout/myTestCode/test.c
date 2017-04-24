#include <stdio.h>

void printBits(unsigned int num)
{
    unsigned int size = sizeof(unsigned int);
    unsigned int maxPow = 1 << (size * 8 - 1);
    printf("\n");
    int i = 0, j;
    for (; i < size * 8; ++i)
    {
        // print last bit and shift left.
        printf("%u ", num & maxPow ? 1 : 0);
        num = num << 1;
    }
}

int test(int x)
{     printf("hi");
    int i;
     for (i = 0; i < 32; i+=2)
       printBits((1<<i));
        if ((x & (1<<i)) == 0)
 	  return 0;
     return 1; 
}

int solve(int x)
{   
    int test = ~x & 0x55555555;
     printBits(~x);
     printBits(0x55555555);
    return !!(test);
}

// ~ & ^ | + >>
int main(int argc, char *argv[])
{
    int a = -2147483648;

    int b = 99;
    printf("\n Inputs");
    //  printBits(b);
    printBits(a);
  
    int y = test(a);
      printf("\n Should be");
    printBits(y);
   
    int x = solve(a);

     printf("\n Is");
    printBits(x);
    return 0;
}
