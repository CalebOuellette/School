#include "list.h"
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
struct nodeStruct
{
  int item;
  struct nodeStruct *next;
};
/** 
 * Allocate memory for a node of type struct nodeStruct and initialize
 *  it with the value item. Return a pointer to the new node.
 **/
struct nodeStruct *List_createNode(int item)
{
  struct nodeStruct *o = malloc(sizeof(struct nodeStruct));
  o->item = item;
  o->next = NULL;
  return o;
}
/** 
 * Insert node at the head of the list.
 **/
void List_insertHead(struct nodeStruct **headRef, struct nodeStruct *node)
{
  assert(node != NULL && "node can not be null");
  if (*headRef != NULL)
  {
    node->next = *headRef;
  }
  *headRef = node;
}
/** Insert node after the tail of the list.*/
void List_insertTail(struct nodeStruct **headRef, struct nodeStruct *node)
{
  assert(node != NULL && "node can not be null");

  struct nodeStruct **list = headRef;
  while ((*list)->next != NULL)
  {
    list = &((*list)->next);
  }
  (*list)->next = node;
}
/** Count number of nodes in the list.
 *  Return 0 if the list is empty, i.e., head == NULL
 **/
int List_countNodes(struct nodeStruct *head)
{
  assert(head != NULL && "head can not be null");
  int i = 1;
  struct nodeStruct **list = &head;
  while ((*list)->next != NULL)
  {
    i++;
    list = &((*list)->next);
  }
  return i;
}
/** Return the first node holding the value item, return NULL if none found*/
struct nodeStruct *List_findNode(struct nodeStruct *head, int item)
{
  assert(head != NULL && "head can not be null");
  struct nodeStruct *val = NULL;
  struct nodeStruct *list = head;
  while (list->next != NULL && (list->item != item))
  {
    list = list->next;
  }
  if (list->item == item)
  {
    val = list;
  }
  return val;
}

void List_remove_node(struct nodeStruct **headRef, struct nodeStruct *node)
{
  assert(headRef != NULL && "headRef can not be null");
  assert(node != NULL && "node can not be null");

  struct nodeStruct **list = headRef;
  struct nodeStruct *lastNode = NULL;
  while ((*list)->next != NULL && *list != node)
  {
    lastNode = *list;
    list = &((*list)->next);
  }
  if (lastNode != NULL)
  {
    lastNode->next = (*list)->next;
  }
  else
  {
    *headRef = (*list)->next;
  }
}
/** 
 * Delete node from the list and free memory allocated to it.
 * This function assumes that node has been properly set to a valid node 
 * in the list. 
 * For example, the client code may have found it by calling 
 * List_findNode(). 
 * If the list containsonly one node, the head of the list  
 * should be set to NULL.
 **/
void List_deleteNode(struct nodeStruct **headRef, struct nodeStruct *node)
{
  List_remove_node(headRef, node);
  free(node);
}

/** Sort the list in ascending order based on the item field.
 * Any sorting algorithm is fine.*/
void List_sort(struct nodeStruct **headRef)
{
  assert(headRef != NULL && "headRef can not be null");
  struct nodeStruct **list = headRef;
  struct nodeStruct *sorted = NULL;
  struct nodeStruct *lastNode = NULL;
  while ((*list) != NULL)
  {
    lastNode = *list;
    List_remove_node(list, lastNode);
    lastNode->next = NULL;
    List_insert_in_order(&sorted, lastNode);
  }

  *headRef = sorted;
}

void List_insert_in_order(struct nodeStruct **headRef, struct nodeStruct *node)
{
  assert(node != NULL && "node can not be null");
  if (headRef == NULL)
  {
    headRef = &node;
    return;
  }

  struct nodeStruct **list = headRef;
  struct nodeStruct *lastNode = NULL;
  while ((*list) != NULL && (*list)->item < node->item)
  {
    lastNode = *list;
    list = &((*list)->next);
  }
  if (lastNode != NULL)
  {
    node->next = lastNode->next;
    lastNode->next = node;
  }
  else
  {

    node->next = *list;
    *headRef = node;
  }
}
/** 
 * Print the list. 
**/
void List_print(struct nodeStruct **headRef)
{
  assert(headRef != NULL && "headRef can not be null");
  printf("Head \n");
  struct nodeStruct **list = headRef;
  int i = 0;
  while ((*list)->next != NULL)
  {
    printf("List item #%d value is %d \n", i, (*list)->item);
    i++;
    list = &((*list)->next);
  }
  printf("List item #%d value is %d \n", i, (*list)->item);
  printf("Tail \n");
  printf("length: %d \n", List_countNodes(*headRef));
}

void List_node_print(struct nodeStruct *node)
{
  assert(node != NULL && "node can not be null");
  printf("value is %d \n", node->item);
}