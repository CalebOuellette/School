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
{
     int i;
     for (i = 0; i < 32; i+=2)
        if ((x & (1<<i)) == 0)
 	  return 0;
     return 1;
}

int solve(int x)
{
    if (x == 2147483647){
        return 1;
    }else{
        return 0;
    }
}

// ~ & ^ | + >>
int main(int argc, char *argv[])
{
    int a = 2147483647;

    int b = 3;
    int c = 9;
    printBits(a);
    int y = test(a);
    printBits(y);

    int x = solve(a);
    printBits(x);
    return 0;
}
