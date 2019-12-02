//============================================================================
// Name        : MagicSquare.cpp
// Author      : Erich Schwarzrock
// Version     :
// Copyright   : Your copyright notice
//============================================================================

#include <iostream>
#include <stdbool.h>
using namespace std;

/*
 * The Square
 */
int square[4][4] = {
		{1, 14, 14, 4},
		{11, 7, 6, 9},
		{8, 10, 10, 5},
		{13, 2, 3, 15}
};
/*
int square[4][4] = {
		{1, 1, 1, 1},
		{1, 1, 1, 1},
		{1, 1, 1, 1},
		{1, 1, 1, 1}
};
*/
/*
 * Checks if the square is magic
 */
bool isMagic(){
	//gets the target number all rows and colums should be
	int target = square[0][0] + square[0][1] + square[0][2] + square[0][3];
	bool magic = true;
	int i = 1;
	//checks rows
	while(i<4 && magic){
		if(target != square[i][0] + square[i][1] + square[i][2] + square[i][3]){
			magic = false;
		}
		i++;
	}
	//checks colums
	i = 0;
	while(i<4 && magic){
		if(target != square[0][i] + square[1][i] + square[2][i] + square[3][i]){
			magic = false;
		}
		i++;
	}
	return magic;
}

/*
 * adds four squares together, where the upper left one is 0, the upper right is 3, and the lower right is 15
 * 0 1 2 3
 * 4 5 6 7
 * ect
 */
int addSquares(int squares[4]){
	int sum = square[squares[0]/4][squares[0]%4] + square[squares[1]/4][squares[1]%4]
			+ square[squares[2]/4][squares[2]%4] + square[squares[3]/4][squares[3]%4];
	return sum;
}

/*
 * checkas all posibal combinations of 4 squares and add which ones sum up to the target value
 * index is which index the function is iterating through
 * squars is the array to iterate through
 * target is the target value
 */
int iterateThroughFourCombos(int index, int squares[4], int target){
	int i = squares[index];
	int numCombos = 0;
	//iterate through i
	while(i<13+index){
		squares[index] = i;
		int x = index+1;
		//set all the indexes' values infront of the curent one to be greater
		//than the curent indexes value to prevent counting the same combonation twice
		while(x<4){
			squares[x] = i+x-index;
			x++;
		}
		//base case
		if(index == 3){
			if(addSquares(squares) == target){
				numCombos++;
			}
		}else{
			//have the next index iterat through
			numCombos += iterateThroughFourCombos(index+1, squares, target);
		}
		i++;
	}
	return numCombos;
}

/*
 * Finds the number of ways in the magic square to get the same sum as each row and colum using 4 tiles
 */
void findSameFour(){
	int target = square[0][0] + square[0][1] + square[0][2] + square[0][3];
	int squares[4] = {0, 1, 2, 3};
	printf("num combos = %d\n", iterateThroughFourCombos(0, squares, target));
}
/*
 * adds all the squares given their index, -1 is no square
 */
int addAllSquares(int squares[16]){
	int x = 0;
	int sum = 0;
	while(x<16){
		if(squares[x] >=0){
			sum += square[squares[x]/4][squares[x]%4];
		}
		x++;
	}
	return sum;
}

/*
 * iterates through all posibal combintations of squares to find how many ways one can make the target value
 * index is the curent index that is being iterated
 * target is the value that it wants to add up too
 */
int iterateThrough(int index, int squares[16], int target){
	int i = squares[index];
	int numCombos = 0;
	//iterate through, I cant be too big because then there wont be enought squares left for the rest of the indexes
	//if index 0 is 15 there is no more squares left to put the rest of the indexes in
	while(i<=index){
		squares[index] = i;
		int x = index+1;
		//set all the indexes' values infront of the curent one to be greater
		//than the curent indexes value to prevent counting the same combonation twice
		while(x<16){
				squares[x] = squares[x-1]+1;
				x++;
		}
		//base case
		if(index == 15){
			if(addAllSquares(squares) == target){
				numCombos++;
			}
		}else{
			//iterate throught the next index
			numCombos += iterateThrough(index+1, squares, target);
		}
		i++;
	}
	return numCombos;
}

/*
 * Finds the number of ways in the magic square to get the same sum as each row and colum using any number of tiles
 */
void findSame(){
	int target = square[0][0] + square[0][1] + square[0][2] + square[0][3];
	int squares[16] = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0};
	int x = 15;
	int sum = 0;
	while(x>=0){
		squares[x] = 0;
		sum += iterateThrough(x, squares, target);
		x--;
	}
	printf("num = %d\n", sum);
}

/*
 * Finds the number of ways in the magic square to get any number using any number of tiles
 * n the target number
 */
void findSameNumber(int n){
	int target = n;
	int squares[16] = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0};
	int x = 15;
	int sum = 0;
	while(x>=0){
		squares[x] = 0;
		sum += iterateThrough(x, squares, target);
		x--;
	}
	printf("%d = %d\n", n, sum);
}


/*
 * Finds the number of ways in the magic square to get all numbers between 0 and the sum of the entire magic square
 *  using any number of tiles
 */
void findAllNums(){
	int squares[16] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
	int max = addAllSquares(squares);
	int x = 0;
	while(x <= max){
		findSameNumber(x);
		x++;
	}

}

/*
 * main
 */
int main() {
	if(isMagic()){
		findSameFour();
		findSame();
		findAllNums();
	}
	return 0;
}
