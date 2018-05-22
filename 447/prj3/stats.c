#include <stdlib.h>
#include  <stdio.h> 
static int* init_int_array( int s);
static double* init_double_array( int s);

int producers;

int* created;
int* consumed;
double* avg;
double* max;
double* min;

void stats_init(int num_producers){
  producers = num_producers;
  created = init_int_array(num_producers);
  consumed = init_int_array(num_producers);
  avg = init_double_array(num_producers);
  max = init_double_array(num_producers);
  min = init_double_array(num_producers);
};
void stats_cleanup(void){
  free(created);
  free(consumed);
  free(avg);
  free(max);
  free(min);
};
void stats_record_produced(int factory_number){
  created[factory_number]++;
};
void stats_record_consumed(int factory_number, double delay_in_ms){
  consumed[factory_number]++;
  if(min[factory_number] > delay_in_ms || min[factory_number] == 0){
    min[factory_number] = delay_in_ms;
  }
  if(max[factory_number] < delay_in_ms){
    max[factory_number] = delay_in_ms;
  }
  avg[factory_number] = ((avg[factory_number]* (consumed[factory_number] - 1) + delay_in_ms )/ consumed[factory_number]);
};
void stats_display(void){
  printf("%-8s%-8s%-8s%-10s%-10s%-10s\n", "Factory#", "#Made", "#Eaten", "Min Delay", "Avg Delay", "Max Delay");  
  for(int i = 0; i < producers; i++){
    printf("%-8d%-8d%-8d%-10.5f%-10.5f%-10.5f\n", i, created[i], consumed[i], min[i], avg[i], max[i]);
  }
  //"%-25s%-20s%-10.2f%-10.2f%-10.2f\n"
};

static int * init_int_array( int s){
  int * a =(int *)malloc(s * sizeof(int));
  for(int i = 0; i < s; i++){
    a[i] = 0;
  }
  return a;
}

static double * init_double_array(int s){
  double * a =(double *)malloc(s * sizeof(double));
  for(int i = 0; i < s; i++){
    a[i] = 0;
  }
  return a;
}