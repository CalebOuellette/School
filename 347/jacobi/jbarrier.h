#ifndef _jbarrier_H_
#define _jbarrier_H_
struct jbarrier_st;
typedef struct jbarrier_st jbarrier;
void jbarrier_init(jbarrier **b, int noth);
void jbarrier_sync(jbarrier **b, void (*onSync)());
#endif
