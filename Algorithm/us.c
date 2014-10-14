#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <termios.h>
#include <sys/time.h>


#define WID 4
#define HEI 4

//-----------------------------------------------------------------
void init_keyboard();
void close_keyboard();
int kbhit();
int readkey();
void pr();          // 配列表示
void lg(char* str);
int wh(int n);
//-----------------------------------------------------------------
static struct termios init_tio;     // kbhit用
int now[WID][HEI];  // 問題用配列
int next[WID][HEI]; // 次の場面
int x = 0, y = 0;   // 座標
int man = 0;        // マンハッタン距離の総和
int seln = 0; 
int ttt = 0;

int main()
{
    int ch,count=0;

    // 初期配列生成
    lg("初期配列");
    int tmp = 0;
    int prc = 0;
    int swap = 0;
    int swapcnt = 0;
    int selectcnt = 0;

slabel:
    ch = 0;
    count=0;
    tmp = 0;
    prc = 0;
    swap = 0;
    swapcnt = 0;
    selectcnt = 0;

    for(int i = 0; i < WID; i++) {
        for(int j = 0; j < HEI; j++) {
            now[i][j] = prc;
            prc++;
        }
    }
    pr();

loop1:
    printf("選択してください\n");
    scanf("%d", &seln);
    if(wh(seln) == -1) {
        goto loop1;
    }
    selectcnt++;
    
    
    init_keyboard();
    while ( ch != 'p' ) {

        if ( kbhit() ) {
            ch = readkey();

            switch(ch) {
                case 'r':
                    goto slabel;
                    break;
                case 'z':
                    goto loop1;
                    break;
                case 'a':
                    ttt = now[x][y];
                    if(y - 1 != -1) {
                        now[x][y] = now[x][y - 1];
                        now[x][y - 1] = ttt;
                        y--;
                        swapcnt++;
                    }
                    pr();
                    break;
                case 'd':
                    ttt = now[x][y];
                    if(y + 1 != HEI) {
                        now[x][y] = now[x][y + 1];
                        now[x][y + 1] = ttt;
                        y++;
                        swapcnt++;
                    }
                    pr();
                    break;
                case 'w':
                    ttt = now[x][y];
                    if(x - 1 != -1) {
                        now[x][y] = now[x - 1][y];
                        now[x - 1][y] = ttt;
                        x--;
                        swapcnt++;
                    }
                    pr();
                    break;
                case 's':
                    ttt = now[x][y];
                    if(x + 1 != WID) {
                        now[x][y] = now[x + 1][y];
                        now[x + 1][y] = ttt;
                        x++;
                        swapcnt++;
                    }
                    pr();
                    break;
                default:
                    // pr();
                    break;
            }
        }
    }

end:

    close_keyboard();
   
    return 0;
}

//-----------------------------------------------------------------
void init_keyboard()  { tcgetattr(0,&init_tio); }
void close_keyboard() { tcsetattr(0,TCSANOW,&init_tio); }

// kbhit
int kbhit()
{
    struct termios tio;
    struct timeval tv;
    fd_set rfds;
    // set up terminal
    memcpy(&tio,&init_tio,sizeof(struct termios));
    tio.c_lflag &= ~(ICANON);
    tcsetattr(0,TCSANOW,&tio);
    // do not wait
    FD_ZERO(&rfds);
    FD_SET(0,&rfds);
    tv.tv_usec = 0;
    tv.tv_sec  = 0;
    select(1,&rfds,NULL,NULL,&tv);
    // back to initial terminal mode
    tcsetattr(0,TCSANOW,&init_tio);
    return (FD_ISSET(0,&rfds)?1:0);
}

// キー入力
int readkey()
{
    int ch;
    struct termios tio;
    memcpy(&tio,&init_tio,sizeof(struct termios));
    tio.c_lflag &= ~(ICANON | ECHO);
    tcsetattr(STDIN_FILENO,TCSANOW,&tio);
    read(0,&ch,1);
    tcsetattr(0,TCSANOW,&init_tio);
    return ch;
}

int wh(int key) {
    for(int i = 0; i < WID; i++) {
        for(int j = 0; j < HEI; j++) {
            if(now[i][j] == key) {
                x = i;
                y = j;
                return 0;
            }
        }
    }
    lg("エラー");
    return -1;
}

void lg(char* str) {
    printf("\n===== %s =====\n\n", str);
}

void pr() {
    printf("\n");
    printf("swap %d : selt %d\n", swapcnt, selectcnt);
    for(int i = 0; i < WID; i++) {
        for(int j = 0; j < HEI; j++) {
            
            printf("%4d", now[i][j]);
            if(j == WID - 1) printf("\n");
        }
    }
    printf("\n");
}