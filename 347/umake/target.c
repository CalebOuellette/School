#include <assert.h>
#include <errno.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#include "arg_parse.h"
#include "target.h"

typedef struct target_st *List;
typedef struct target_st Target;

typedef struct string_st *String_List;
typedef struct string_st String_Node;

struct string_st
{
	char *line;
	String_List rest;
};

struct target_st
{
	List rest;
	char *name;
	String_List dependencies;
	String_List rules;
};

static List global_list = NULL;

/* New Target
* Creates a new target and adds it to the global list.
*/
Target *new_target(char *name);
/* Append Rule
* Adds a rule to a target
*/
void append_rule(Target *target, char *line);
void append_dependent(Target *target, char *line);
static void append_string(String_List *list, char *line);
/* Find Target
* Finds a target by name from the global list;
*/
Target *find_target(const char *targetName);

/* For Each String
* runs a loop on a string list type.
* used by for each rule and for each dependency.
*/
static void for_each_string(String_List rules, list_action action);

/* For Each Dependency
* runs a loop on the rules
*/
void for_each_rule(Target *t, list_action action);
/* For Each Dependency
* runs a loop on the dependencys
*/
void for_each_dependency(Target *t, list_action action);

/*  Free Line
* One line function to free line. Created this function so i could re-
* use the code from for_each_rule and for_each_dependency.
*/
static void free_line(char *line);

/* Free Node
* Frees a node.
*/
static void free_node(Target *target);
/* Free List
* Frees up the memory we are using and will destroy the global list.
*/
void free_list();

/* 
* debugging prints
*/
//static void print_list();
//static void print_rules();

/*
* Creates a target based off of a line and adds it to the global list.
*/
Target *new_target(char *inName)
{
	char *name = strdup(inName);

	List *list = &global_list;
	assert(list != NULL);

	Target *n = malloc(sizeof(Target));
	if (n != NULL)
	{
		n->rest = NULL;
		n->name = name;
		n->dependencies = NULL;
		n->rules = NULL;

		while (*list != NULL)
		{
			list = &((*list)->rest);
		}
		*list = n;
	}

	return n;
}

/*
* Appends a rule to a give node
*/
void append_rule(Target *target, char *line)
{
	String_List *rules = &(target->rules);
	append_string(rules, line);
}
void append_dependent(Target *target, char *name)
{

	String_List *dep = &(target->dependencies);
	append_string(dep, name);
}

static void append_string(String_List *list, char *line)
{
	line = strdup(line);
	String_Node *s = malloc(sizeof(String_Node));
	s->line = line;
	s->rest = NULL;
	while (*list != NULL)
	{
		list = &((*list)->rest);
	}
	*list = s;
}

Target *find_target(const char *targetName)
{

	List *list = &global_list;
	while (*list != NULL)
	{

		if (strcmp((*list)->name, targetName) == 0)
		{
			return *list;
		}
		list = &((*list)->rest);
	}
	return NULL;
}

static void for_each_string(String_List rules, list_action action)
{
	while (rules != NULL)
	{
		action(rules->line);
		rules = rules->rest;
	}
}

static void for_each_string_with_void(String_List rules, list_action_void action, void *any)
{
	while (rules != NULL)
	{
		action(rules->line, any);
		rules = rules->rest;
	}
}

void for_each_rule(Target *t, list_action action)
{
	String_List rules = t->rules;
	for_each_string(rules, action);
}

void for_each_dependency(Target *t, list_action action)
{
	String_List rules = t->dependencies;
	for_each_string(rules, action);
}

void for_each_dependency_with_void(Target *t, list_action_void action, void *any)
{
	String_List rules = t->dependencies;
	for_each_string_with_void(rules, action, any);
}

static void free_line(char *line)
{
	free(line);
}

static void free_node(Target *target)
{
	free(target->name);
	for_each_rule(target, free_line);
	for_each_dependency(target, free_line);
	free(target->rules);
	free(target->dependencies);
	//	free(target);
}

/*
* Not totally sure how to test this or if I am doing it right. 
* The idea of this function is to call free on literally everything.
* uses two pointers to move one forward while freeing the pointer 
* behind it.
*/
void free_list()
{
	List *list = &global_list;
	while (*list != NULL)
	{
		Target *t = *list;
		list = &((*list)->rest);
		free_node(t);
	}
	List *listtwo = &global_list;
	while (*listtwo != NULL)
	{
		Target *t = *list;
		listtwo = &((*listtwo)->rest);
		free(t);
	}
}

// /*
// * Debug function
// */
// static void print_rules(List list)
// {
// 	String_List rules = list->rules;
// 	while (rules != NULL)
// 	{
// 		printf("Rule: %s\n", rules->line);
// 		rules = rules->rest;
// 	}
// }

// /*
// * Debug function
// */
// static void print_list()
// {
// 	List list = global_list;
// 	while (list != NULL)
// 	{
// 		printf("Target: %s\n", list->name);
// 		print_rules(list);
// 		list = list->rest;
// 	}
// }