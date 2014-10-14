// #include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <curses.h>
#include <time.h>
#include <unistd.h>

#define getch() wgetch(stdscr)

#define WID 4
#define HEI 4

// 関数宣言
void prgen();		// 問題生成

// util
void wh();			// 穴探し
void lg(char* str);	// ログ用
void pr();			// 配列表示
int mansum();		// マンハッタン距離の総和を計算

void initialize() {
	srand((unsigned)time(NULL));
	prgen();
	wh();
}

// 大域変数

int flag_exit = 0;

int now[WID][HEI];	// 問題用配列
int next[WID][HEI];	// 次の場面
int x = 0, y = 0;	// 穴座標
int man = 0;		// マンハッタン距離の総和

int main() {
	// initialize();

	int swap1 = 0;
	int swap2 = 0;
	int c = 0;

	while(flag_exit == 0) {
		lg("スワップ対象を入力: ");
		// scanf("%d %d", &swap1, &swap2);

		// for(int i = 0; i < WID; i++) {
		// 	for(int j = 0; j < HEI; j++) {

		// 	}
		// }
		swap1 = getch();
		printf("%d\n", swap1);
		c++;

		// debug
		if(c == 10) {
			flag_exit = 1;
		}
	}

	return 0;
}

// 穴探し
void wh() {
	for(int i = 0; i < WID; i++) {
		for(int j = 0; j < HEI; j++) {
			if(now[i][j] == 0) {
				x = i;
				y = j;
				printf("Hole [%d][%d]\n", x, y);
				return;
			}
		}
	}
	lg("エラー");
	return;
}

// 問題生成
void prgen() {
	int tmp = 0;
	int count = 0;

	for(int i = 0; i < WID; i++) {
		for(int j = 0; j < HEI; j++) {
			now[i][j] = count;
			count++;
		}
	}

	// 初期配列生成
	lg("初期配列");
	pr();

	// 配列から問題を生成(偶数置換)
	lg("問題生成");
	int p = 0;
	int q = 0;
	int r = 0;
	int s = 0;

	int swapnum = rand() % (WID * HEI) * 2;
	printf("Swapping %d\n", swapnum);

	for(int i = 0; i < swapnum; i++) {
		p = rand() % (WID);
		q = rand() % (HEI);
		r = rand() % (WID);
		s = rand() % (HEI);

		printf("[%d][%d] <=> [%d][%d]\n", p, q, r, s);

		// 置換
		tmp = now[p][q];
		now[p][q] = now[r][s];
		now[r][s] = tmp;

		// 経過表示
		//pr();
		//printf("\n");
	}

	lg("問題配列");
	pr();
}


// マンハッタン
int mansum() {
	int tmpx = 0;
	int tmpy = 0;
	int manh = 0;
	

	for(int i = 0; i < WID; i++) {
		for(int j = 0; j < HEI; j++) {
			tmpx = now[i][j] / WID;
			tmpy = now[i][j] % WID;
			manh = tmpx + tmpy;
			printf("%d\n", manh);
			man = man + manh;
		}
	}

	return 0;
}

// util
void lg(char* str) {
	printf("\n===== %s =====\n\n", str);
}

void pr() {
	for(int i = 0; i < WID; i++) {
		for(int j = 0; j < HEI; j++) {
			
			printf("%4d", now[i][j]);
			if(j == WID - 1) printf("\n");
		}
	}
	printf("\n");
}