

int factories;
int kids;
int seconds;

int main(int argc, const char *argv[])
{

  parseArgs(argc, argv);//    1.        Extract    arguments
  
  //    2.        Initialize    modules
  //    3.        Launch    candy-­‐factory    threads
  //    4.        Launch    kid    threads
  //    5.        Wait    for    requested    time
  //    6.        Stop    candy-­‐factory    threads
  //    7.        Wait    until    no    more    candy
  //    8.        Stop    kid    threads
  //    9.        Print    statistics
  //    10.    Cleanup    any    allocated    memory
  }


parseArgs(int argc, const char *argv[]){
  assert(argc == 3 && "Should be 3 args");
  factories = atof(argv[1]);
  kids = atof(argv[2]);
  seconds = atof(argv[3]);
}