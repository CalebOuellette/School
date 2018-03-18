
jacobi: jacobi.o jmatrix.o arg_parse.o jbarrier.o
	gcc -pthread -o jacobi-new jacobi.o jmatrix.o arg_parse.o jbarrier.o
	mv -i jacobi-new jacobi
jacobi.o: jacobi.c jmatrix.c arg_parse.c jbarrier.c
	gcc -c -pthread -Wall jacobi.c jmatrix.c arg_parse.c jbarrier.c