#include "list.h"
#include <assert.h>
#include <stdlib.h>
int main()
{
  struct nodeStruct *list = NULL;
  struct nodeStruct *node2 = List_createNode(2);
  struct nodeStruct *node0 = List_createNode(0);
  struct nodeStruct *node1 = List_createNode(9);
  struct nodeStruct *node3 = List_createNode(3);
  struct nodeStruct *node4 = List_createNode(1);
  List_insertHead(&list, node0);
  List_insertTail(&list, node2);
  List_insertHead(&list, node4);
  List_insertHead(&list, node1);
  List_insertHead(&list, node3);

  List_print(&list);
  List_sort(&list);
  List_print(&list);
  return 0;
}