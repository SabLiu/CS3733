//============================================================================
// Name        : LucusNumbers.cpp
// Author      : Erich Schwarzrock
// Version     :
// Copyright   : Your copyright notice
//============================================================================

#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
using namespace std;

/*
 * Used for tail recurtion
 */
typedef struct returnVal{
	int first;
	int second;
};

/*
 * Tail recution implementaton of the lucus number problem
 */
struct returnVal* LLinear(int n){
	struct returnVal* rv = (struct returnVal*)malloc(sizeof(struct returnVal));
	//base case
	if(n == 0){
		rv->first = 2;
	}else if(n == 1){
		rv->first = 1;
		rv->second = 2;
	}else{
		struct returnVal* ret = LLinear(n-1);
		//set the previous return value
		rv->second = ret->first;
		//set this return value
		rv->first = ret->first + ret->second;
		free(ret);
	}
	return rv;
}

/*
 * Non tail recursive implementation of Lucus numbers
 */
int LExponential(int n){
	//base case
	if(n == 0){
		return 2;
	}else if(n == 1){
		return 1;
	}else{
		return LExponential(n-1) + LExponential(n-2);
	}
}

/*
 * Find the Lucus numbers using tail recurtion
 */
void linearMain(int x){
	int y = 0;
	//iterate through
	while(y<x){
		struct returnVal* ret = LLinear(y);
		printf("%d, ", ret->first);
		y++;
		free(ret);
	}
	//print last value
	struct returnVal* ret = LLinear(x);
	printf("%d\n", ret->first);
	free(ret);
	printf("No need to time it because it is so fast and adding 1 to N doesn't really change the time cost\n");
}

/*
 * find the lucus numbers with exponential growth
 */
void exponetialMain(int x){
	if(x>100){
		printf("too large of a number\n");
		return;
	}
	clock_t start;
	clock_t end;
	clock_t array[100];
	double ratios[100];
	double average = 0;
	int y = 0;
	//print out the numbers
	while(y<x){
		start = clock();
		printf("%d, ", LExponential(y));
		end = clock();
		array[y] = end-start;
		y++;
	}
	start = clock();
	printf("%d\n\n\n\n", LExponential(x));


	printf("times ");
	end = clock();
	array[x] = end - start;
	y = 0;
	//print out the time needed to calculate that number
	while(y<x){
		printf("%ld, ", array[y]);
		y++;
	}
	printf("%ld\n\n\n\n", array[x]);

	printf("ratios");
	//we start at 18 to prevent divition by 0 errors
	//calculate the ratios
	y = 18;
	while(y<=x-1){
		ratios[y] = 1.0*array[y+1]/array[y];
		y++;
	}
	//calculate the average
	y = 18;
	while(y<=x){
		average+= ratios[y];
		y++;
	}
	average = average/(x-18);
	printf("average ratio = %f\n", average);

}


int main(int argc, char* argv[]) {
	if(argc == 3){
		int x = stoi(argv[1]);
		if(x>0){
			if(strcmp("L", argv[2]) == 0){
				linearMain(x);
			}else if(strcmp("E", argv[2]) == 0){
				exponetialMain(x);
			}
		}
	}
	return 0;
}
