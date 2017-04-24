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
    unsigned int a, cnt;

    x = x < 0 ? -x - 1 : x;
    a = (unsigned int)x;
    for (cnt = 0; a; a >>= 1, cnt++)
        ;
    return (int)(cnt + 1);
}

int solve(int x)
{

    int m = x >> 31;
    unsigned int v =  (~x + 1 & m) | (x & ~m); // compute the next highest power of 2 of 32-bit v
    int ptwo = !(v & (v - 1));
    v |= v >> 1;
    v |= v >> 2;
    v |= v >> 4;
    v |= v >> 8;
    v |= v >> 16;

    int c;
    c = v - ((v >> 1) & 0x55555555);
    c = ((c >> 2) & 0x33333333) + (c & 0x33333333);
    c = ((c >> 4) + c) & 0x0F0F0F0F;
    c = ((c >> 8) + c) & 0x00FF00FF;
    c = ((c >> 16) + c) & 0x0000FFFF;
    return  (m & (c + 1 - ptwo)) | (~m & (c + 1));
    //count exponent
}

// ~ & ^ | + >>
int main(int argc, char *argv[])
{
    unsigned a = 1;

    printf("\n Inputs");
    //  printBits(b);
    printBits(a);

    int y = test(a);

    //printBits(y);
    printf("\n Should be");
    printf("\n  %i \n", y);
    int x = solve(a);
    printf("\n  %i \n", x);
    //   printBits(x);
    return 0;
}
