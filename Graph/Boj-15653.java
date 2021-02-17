/*
Gold 2
백준 15653 : 구슬 탈출 4
기준 : 2s, 512MB

Time : 132ms ( java 8 : 76ms )
- BFS 문제
- 이전에 푼 구슬탈출 2와 비슷한 문제
- 구슬은 겹칠 수 없기 때문에 굴러온 거리를 계산해서 겹친 경우 한칸을 빼주는 식으로 구현
- 파란색이 구멍에 빠지는 경우는 불가능한 경우로 미리 제외하고
- 빨간색이 빠지는 경우에만 bfs단계를 변수로 두어서 그 값을 최소값으로 넣음 --> 제일 먼저 나온 결과가 최소가 될 수 밖에 없다
- 코드에서 굴러온 방향으로는 다시 가지 않도록 하였으나 특정 조건에서 무한루프가 발생하여 visit배열을 통해서
방문여부 체크를 하였음.

평균 Time : 72 ~ 80ms ( java 8 기준 )
 */

import java.io.*;
import java.util.*;

public class Main {
    public static int min;
    public static int[][] next = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        char[][] arr = new char[10][10];
        boolean[][][][] visit = new boolean[10][10][10][10];
        StringTokenizer st = new StringTokenizer(in.readLine());
        Point red;
        Point blue;
        min = 10000000;

        Queue<Point> r = new LinkedList<>();
        Queue<Point> b = new LinkedList<>();
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        String str;
        for(int i=0; i<n; i++){
            str = in.readLine();
            for(int j=0; j<m; j++){
                arr[i][j] = str.charAt(j);

                if(arr[i][j] == 'R') {
                    red = new Point(i, j, -1, 0);
                    r.add(red);
                }
                else if(arr[i][j] == 'B') {
                    blue = new Point(i, j, -1, 0);
                    b.add(blue);
                }

                if((arr[i][j] != '#') && (arr[i][j] != 'O'))
                    arr[i][j] = '.';
            }
        }

        bfs(r, b, arr, visit);
        if(min == 10000000)
            min = -1;
        System.out.println(min);
    }

    public static void bfs(Queue<Point> r, Queue<Point> b, char[][] arr, boolean[][][][] visit){
        Point tr, tb;
        int rx, ry, bx, by;
        int rcnt, bcnt;

        while(!r.isEmpty()){
            tr = r.poll();
            tb = b.poll();
            visit[tr.x][tr.y][tb.x][tb.y] = true;

            for(int i=0; i<4; i++){
                if((tr.way != -1) && ((tr.way + 2) % 4 == i))
                    continue;

                rx = tr.x;
                ry = tr.y;
                bx = tb.x;
                by = tb.y;
                rcnt = 0;
                bcnt = 0;


                while(arr[rx+next[i][0]][ry+next[i][1]] == '.'){
                    rcnt++;
                    rx += next[i][0];
                    ry += next[i][1];
                }

                while(arr[bx+next[i][0]][by+next[i][1]] == '.'){
                    bcnt++;
                    bx += next[i][0];
                    by += next[i][1];
                }

                if(arr[bx+next[i][0]][by+next[i][1]] == 'O')
                    continue;

                if(arr[rx+next[i][0]][ry+next[i][1]] == 'O'){
                    min = tr.depth+1;
                    return;
                }

                if((rx == bx) && (ry == by)){
                    if(rcnt > bcnt){
                        rx -= next[i][0];
                        ry -= next[i][1];
                    } else{
                        bx -= next[i][0];
                        by -= next[i][1];
                    }
                }

                if((rx == tr.x) && (ry == tr.y) && (bx == tb.x) && (by == tb.y))
                    continue;

                if(visit[rx][ry][bx][by])
                    continue;

                visit[rx][ry][bx][by] = true;
                r.add(new Point(rx, ry, i, tr.depth+1));
                b.add(new Point(bx, by, i, tb.depth+1));
            }
        }
    }
}

class Point{
    int x;
    int y;
    int way;
    int depth;

    public Point(int x, int y, int way, int depth) {
        this.x = x;
        this.y = y;
        this.way = way;
        this.depth = depth;
    }
}