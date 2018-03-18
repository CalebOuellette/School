# Jacobi  Project
347 Project 5

### Authors
  * Caleb Ouellette
  * Chris White


## Building the Project

```
$ make
```

## Running the Program


#### Required Args
* The first argument should always be the number of threads

#### Optional  Args
* -test {testData} This will run a compare between the results of the program and the file you pass in after the test argument. The largest difference in the data will be printed to standard out.
* -o {outPutFileNameHere} If you want the program to output it's results to a file add this flag followed by a filename of where you want the data to go.
* -t {thresholdValue} This will change the threshold to whatever follows the -t flag. by default the threshold is set to 0.00001

##### Examples
To read from input.mtx and output to output.mtx using 5 threads
```
$ ./jacobi 5 -o output.mtx
```
To Read from input.mtx, use a .001 threshold and test against output-0.001.mtx use.
```
$ ./jacobi 1 -test output-0.001.mtx -t .001
```

## Running the experiment
To run the experiment you can use the report.sh script. This will run the program for 1 - 12 threads. It uses bash's time command to get the execution time and will pipe the results to resutls.txt
```
$ ./report.sh
```
