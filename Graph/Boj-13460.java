/*
Gold 2
백준 13460 : 구슬 탈출 2
기준 : 2s, 512MB

Time : 274ms
- BFS를 이용한 구슬 탈출 여부 확인
- 방문여부 체크를 굴러온 방향에 대해서만 체크했고, flag를 사용해서 if문 while문이 많아짐

평균 Time : 136 ~ 200ms
- 빨간구슬, 파란구슬 각각의 굴러온 거리를 체크하고 겹치는 경우 더 먼거리에서 온 것을 한 칸 뒤로 배치함
- 빨간구슬, 파란구슬을 합쳐서 방문체크 배열을 사용

--> 이와같이 
*/

import java.io.*;
import java.util.*;

public class Main {
    public static int[][] next = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public static int min;
    public static int n;
    public static int m;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        char[][] arr = new char[10][10];
        min = 100;
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        Point ball = new Point();
        Queue<Point> q = new LinkedList<>();
        String str;
        for(int i=0; i<n; i++){
            str = in.readLine();
            for(int j=0; j<m; j++){
                arr[i][j] = str.charAt(j);
                if(arr[i][j] == 'R'){
                    ball.rx = i;
                    ball.ry = j;
                }

                if(arr[i][j] == 'B'){
                    ball.bx = i;
                    ball.by = j;
                }
            }
        }

        ball.depth = 0;
        ball.way = -1;
        q.add(ball);

        findHole(q, arr);

        if(min == 100){
            System.out.println(-1);
        } else{
            System.out.println(min);
        }
    }

    public static boolean validCheck(int x, int y){
        return (x >= 0) && (x < n) && (y >= 0) && (y < m);
    }

    public static void findHole(Queue<Point> q, char[][] arr){
        Point temp;
        int rx, ry, bx, by;
        int depth, way;
        int rflag, bflag, rend, bend;

        while(!q.isEmpty()){
            temp = q.poll();
            depth = temp.depth;
            way = temp.way;

            if(depth == 10)
                continue;

            for(int i=0; i<4; i++){
                if(i == way){
                    continue;
                }
                rflag = 0;
                bflag = 0;
                rx = temp.rx;
                ry = temp.ry;
                bx = temp.bx;
                by = temp.by;

                while(true){
                    if(validCheck(rx, ry) && (arr[rx][ry] != '#')){
                        rend = 0;
                        if((rx + next[i][0] != bx) || (ry + next[i][1] != by)) {
                            rx += next[i][0];
                            ry += next[i][1];
                        } else {
                            rend = 1;
                        }

                        if(arr[rx][ry] == 'O')
                            rflag = 1;
                    } else
                        rend = 1;

                    if(validCheck(bx, by) && (arr[bx][by] != '#')){
                        bend = 0;
                        if((bx + next[i][0] != rx) || (by + next[i][1] != ry)) {
                            bx += next[i][0];
                            by += next[i][1];
                        } else {
                            bend = 1;
                        }

                        if(arr[bx][by] == 'O')
                            bflag = 1;
                    } else
                        bend = 1;

                    if((rend == 1) && (bend == 1))
                        break;
                }

                rx -= next[i][0];
                bx -= next[i][0];
                ry -= next[i][1];
                by -= next[i][1];

                if(bflag == 1){
                    continue;
                } else if(rflag == 1){
                    min = depth + 1;
                    return;
                }

                q.add(new Point(rx, ry, bx, by, depth+1, i));

            }

        }
    }
}

class Point{
    int by;
    int rx;
    int ry;
    int bx;
    int depth;
    int way;

    Point(){}

    Point(int rx, int ry, int bx, int by, int depth, int way) {
        this.by = by;
        this.rx = rx;
        this.ry = ry;
        this.bx = bx;
        this.depth = depth;
        this.way = way;
    }
}

