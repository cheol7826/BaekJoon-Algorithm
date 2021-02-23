/*
Gold 5
백준 1600 : 말이 되고픈 원숭이
기준 : 2s, 256MB

Time : 708ms
- BFS 문제
- 일반적인 1칸 탐색과 특수한 탐색 방법이 섞여있는 문제
- 점프 후 방문배열을 따로 구현해야 함 ( 점프 한 뒤 도달하지 못할 수 있음 )

* validCheck 과정에서 continue한 것을 발견하지 못해서 오래걸렸음
( 다른 방향도 탐색해야 하는데 continue를 해서 WR 발생 )

평균 Time : 500 ~ 620ms
 */

import java.io.*;
import java.util.*;

public class Main {
    public static int min;
    public static int[][] next = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        min = 50000;
        int[][] arr = new int[200][200];
        int[][][] visit = new int[200][200][31];

        int k = Integer.parseInt(in.readLine());
        st = new StringTokenizer(in.readLine());
        int w = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());

        for(int i=0; i<h; i++){
            st = new StringTokenizer(in.readLine());
            for(int j=0; j<w; j++){
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        bfs(arr, visit, h, w, k);

        if(min == 50000)
            min = -1;

        System.out.println(min);

    }

    public static boolean validCheck(int x, int y, int h, int w){
        return (x < 0) || (x >= h) || (y < 0) || (y >= w);
    }

    public static void bfs(int[][] arr, int[][][] visit, int h, int w, int k){
        Queue<Point> q = new LinkedList<>();
        q.add(new Point(0, 0, 0, 0));
        visit[0][0][0] = 1;
        Point temp;
        int x, y, jump, depth;

        while(!q.isEmpty()){
            temp = q.poll();
            jump = temp.jump;
            depth = temp.depth;


            if((temp.x == h-1) && (temp.y == w-1)){
                if(min > depth)
                    min = depth;
                continue;
            }

            for(int i=0; i<4; i++){
                x = temp.x + next[i][0];
                y = temp.y + next[i][1];

                if(!validCheck(x, y, h, w)) {
                    if ((arr[x][y] == 0) && (visit[x][y][jump] == 0)) {
                        visit[x][y][jump] = 1;
                        q.add(new Point(x, y, jump, depth + 1));
                    }
                }

                if(jump < k){
                    x += next[i][0];
                    y += next[i][1];

                    x += next[(i+1)%4][0];
                    y += next[(i+1)%4][1];

                    if(!validCheck(x, y, h, w)) {
                        if ((arr[x][y] == 0) && (visit[x][y][jump + 1] == 0)) {
                            visit[x][y][jump + 1] = 1;
                            q.add(new Point(x, y, jump + 1, depth + 1));
                        }
                    }

                    x += next[(i+3)%4][0] * 2;
                    y += next[(i+3)%4][1] * 2;

                    if(!validCheck(x, y, h, w)) {
                        if ((arr[x][y] == 0) && (visit[x][y][jump + 1] == 0)) {
                            visit[x][y][jump + 1] = 1;
                            q.add(new Point(x, y, jump + 1, depth + 1));
                        }
                    }
                }
            }
        }
    }
}

class Point{
    int x;
    int y;
    int jump;
    int depth;


    public Point(int x, int y, int jump, int depth) {
        this.x = x;
        this.y = y;
        this.jump = jump;
        this.depth = depth;
    }
}
