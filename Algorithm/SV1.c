#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define WID 4
#define HEI 4

void lg(char* str);

void initialize() {
	srand((unsigned)time(NULL));
}


int main() {
	initialize();


	int qs[WID + 1][HEI + 1];	// 問題用配列
	int tmp = 0;
	int count = 1;

	// 問題生成
	// for(int i = 0; i < WID; i++) {
	// 	for(int j = 0; j < HEI; j++) {
	// 		tmp = rand() % (WID * HEI);	// 0からパネル数-1までの値を生成
	// 	}
	// }

	for(int i = 1; i <= WID; i++) {
		for(int j = 1; j <= HEI; j++) {
			qs[i][j] = count;
			count++;
		}
	}

	// ===== 問題生成 =====
	// 初期配列生成
	lg("初期配列");
	for(int i = 1; i <= WID; i++) {
		for(int j = 1; j <= HEI; j++) {
			
			printf("%4d", qs[i][j]);
			if(j == WID) printf("\n");
		}
	}
	printf("\n");

	// 配列から問題を生成(偶数置換)
	



	return 0;
}


// util
void lg(char* str) {
	printf("\n===== %s =====\n\n", str);
}