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
    for (i = 0; i < 32; i += 2)
        if ((x & (1 << i)) == 0)
            return 0;
    return 1;
}

int solve(int x)
{


    int v =  ~x; //if we get one digit back from y we are good.
    
   return v && !(v & (v - 1));
}

// ~ & ^ | + >>
int main(int argc, char *argv[])
{
    int a = 2147483645;

    int b = 99;
    printf("\n Inputs");
    //  printBits(b);
    printBits(a);
    printf("\n Should be");
    int y = test(a);
    printBits(y);
    printf("\n Is");
    int x = solve(a);
    printBits(x);
    return 0;
}
