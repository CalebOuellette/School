#ifndef BBUFF_H
#define BBUFF_H
#define BUFFER_SIZE 10
void bbuff_init();
void bbuff_blocking_insert(void *item);
void *bbuff_blocking_extract();
_Bool bbuff_is_empty();
void bbuff_flush_consumers();
#endif