#ifndef _Target_H_
#define _Target_H_

struct target_st;
typedef struct target_st *List;
typedef struct target_st Target;
typedef void (*list_action)(char *);
typedef void (*list_action_void)(char *, void *);

void for_each_rule(Target *t, list_action l);
void for_each_dependency(Target *t, list_action l);
void for_each_dependency_with_void(Target *t, list_action_void action, void *any);
Target *find_target(const char *targetName);
void free_list();
Target *new_target(char *line);
void append_rule(Target *target, char *line);
void append_dependent(Target *target, char *line);
#endif
