#include "list.h"
#include <assert.h>

int main()
{
  struct nodeStruct *node0 = List_createNode(0);
  struct nodeStruct *node1 = List_createNode(1);
  struct nodeStruct *node2 = List_createNode(2);
  struct nodeStruct *node3 = List_createNode(3);
  List_insertHead(&node1, node2);
  List_insertHead(&node1, node3);
  List_insertTail(&node1, node0);
  List_print(&node1);

  struct nodeStruct *found = List_findNode(node1, 0);
  List_node_print(found);

  return 0;
}