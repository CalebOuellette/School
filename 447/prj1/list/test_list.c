#include "list.h"
#include <assert.h>

int main()
{

  struct nodeStruct *node1 = List_createNode(1);
  struct nodeStruct *node2 = List_createNode(2);
  //struct nodeStruct *node3 = List_createNode(3);
  List_insertHead(&node1, node2);
  List_print(&node2);

  return 0;
}