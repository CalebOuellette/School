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


  int test_dl15(int x, int n, int m)
  {
 
    unsigned int nmask, mmask;
 
    switch(n) {
    case 0:
      nmask = x & 0xFF;
      x &= 0xFFFFFF00;
      break;
    case 1:
      nmask = (x & 0xFF00) >> 8;
      x &= 0xFFFF00FF;
      break;
    case 2:
      nmask = (x & 0xFF0000) >> 16;
      x &= 0xFF00FFFF;      
      break;
    default:
      nmask = ((unsigned int)(x & 0xFF000000)) >> 24;
      x &= 0x00FFFFFF;
      break;
     }
 
    switch(m) {
    case 0:
      mmask = x & 0xFF;
      x &= 0xFFFFFF00;
      break;
    case 1:
      mmask = (x & 0xFF00) >> 8;
      x &= 0xFFFF00FF;
      break;
    case 2:
      mmask = (x & 0xFF0000) >> 16;
      x &= 0xFF00FFFF;      
      break;
    default:
      mmask = ((unsigned int)(x & 0xFF000000)) >> 24;
      x &= 0x00FFFFFF;
      break;
    }
 
    nmask <<= 8*m;
    mmask <<= 8*n;
 
    return x | nmask | mmask;
  }
 
 
//    You may assume that 0 <= n <= 3, 0 <= m <= 3
//    Legal ops: ! ~ & ^ | + << >>
//    Max ops: 25
//    Rating: 2

int test_dl15solve(int x, int n, int m){
     nmask = x & 0xFF >> (8 * n);
      x &= 0xFFFFFF00;



    return 1;
}



int main(int argc, char *argv[])
{
    int a = 1;
    int b = 2;
    int c = 2;

    int y = test_dl15(a, b, c);
    printBits(y);

    int x = test_dl15solve(a, b, c);
     printBits(x);
    return 0;
}
