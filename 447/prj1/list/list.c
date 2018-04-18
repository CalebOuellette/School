#include "list.h"
#include <stdio.h>
#include <stdlib.h>
struct nodeStruct
{
  int item;
  struct nodeStruct *next;
};
/** Allocate memory for a node of type struct nodeStruct and initialize* it with the value item. Return a pointer to the new node.*/
struct nodeStruct *List_createNode(int item)
{
  struct nodeStruct *o = malloc(sizeof(struct nodeStruct));
  o->item = item;
  o->next = NULL;
  return o;
}
/** Insert node at the head of the list.*/
void List_insertHead(struct nodeStruct **headRef, struct nodeStruct *node)
{
  node->next = *headRef;
  headRef = &node;
}
/** Insert node after the tail of the list.*/
void List_insertTail(struct nodeStruct **headRef, struct nodeStruct *node);
/** Count number of nodes in the list.* Return 0 if the list is empty, i.e., head == NULL*/
int List_countNodes(struct nodeStruct *head);
/** Return the first node holding the value item, return NULL if none found*/
struct nodeStruct *List_findNode(struct nodeStruct *head, int item);
/** Delete node from the list and free memory allocated to it.* This function assumes that node has been properly set to a valid node * in the list. For example, the client code may have found it by calling * List_findNode(). If the list containsonly one node, the head of the list * should be set to NULL.*/
void List_deleteNode(struct nodeStruct **headRef, struct nodeStruct *node);
/** Sort the list in ascending order based on the item field.* Any sorting algorithm is fine.*/
void List_sort(struct nodeStruct **headRef);
/** Print the list. **/
void List_print(struct nodeStruct **headRef)
{
  struct nodeStruct **list = headRef;
  int i = 0;
  while ((*list)->next != NULL)
  {
    printf("List item #%d value is %d \n", i, (*headRef)->item);
    i++;
    list = &((*list)->next);
  }
  printf("List item #%d value is %d \n", i, (*headRef)->item);
}