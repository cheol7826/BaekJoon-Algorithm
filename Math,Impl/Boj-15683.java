/*
Gold 5
백준 15683 : 감시
기준 : 1s, 512MB

Time : 456ms
- 구현 + 브루트 포스 문제
- 처음에는 bfs를 사용해서 큐를 복사하는 과정에서 시간이 오래 걸렸었다 ( 700ms )
- 큐를 사용하지 않고 list를 이용해서 해당 지점부터 CCTV 범위를 확장시키는 형태
- 브루트 포스 방식으로 각 cctv가 가리키는 방향을 정한 뒤 각 cctv 방향을 배열에 기록
평균 Time : 200 ~ 372ms
 */

import java.io.*;
import java.util.*;

public class Main {
    public static int[][] next = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public static ArrayList<Point> list;
    public static int min;
    public static int[][] arr;
    public static int[][] visit;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        arr = new int[8][8];
        visit = new int[8][8];
        int[][] way = new int[8][8];
        min = 100;
        list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(in.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        for(int i=0; i<n; i++){
            st = new StringTokenizer(in.readLine());
            for(int j=0; j<m; j++){
                arr[i][j] = Integer.parseInt(st.nextToken());
                if((arr[i][j] != 0) && (arr[i][j] != 6)) {
                    list.add(new Point(i, j));
                } else if(arr[i][j] == 6)
                    way[i][j] = 7;
            }
        }

        findWay(way, n, m, 0);

        System.out.println(min);
    }

    public static void findWay(int[][] way, int n, int m, int depth){
        if(depth == list.size()){
            calcRange(way, n, m);
            return;
        }
        Point temp = list.get(depth);
        for(int i=0; i<4; i++){
            way[temp.x][temp.y] = i+1;
            findWay(way, n, m, depth+1);
            way[temp.x][temp.y] = 0;
        }
    }

    public static void calcRange(int[][] way, int n, int m){
        for(int i=0; i<n; i++){
           for(int j=0; j<m; j++){
               visit[i][j] = way[i][j];
           }
        }
        Point temp;
        boolean[] valid = new boolean[4];
        int x, y, count = 0;
        int idx = 0;

        while(idx < list.size()){
            temp = list.get(idx);
            Arrays.fill(valid, false);
            setWay(valid, temp, visit);

            x = temp.x;
            y = temp.y;

            for(int i=0; i<4; i++){
                if(!valid[i])
                    continue;

                while(!validCheck(x, y, n, m)) {
                    if (visit[x][y] == 7)
                        break;

                    if (visit[x][y] == 0)
                        visit[x][y] = -1;

                    x += next[i][0];
                    y += next[i][1];
                }
                x = temp.x;
                y = temp.y;
            }
            idx++;
        }

        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(visit[i][j] == 0)
                    count++;
            }
        }

        if(min > count)
            min = count;
    }

    public static boolean validCheck(int x, int y, int n ,int m){
        return (x < 0) || (x >= n) || (y < 0) || (y >= m);
    }

    public static void setWay(boolean[] v, Point t, int[][] visit){
        int val = arr[t.x][t.y];
        int way = visit[t.x][t.y]-1;

        v[way] = true;
        switch (val){
            case 2:
                v[(way+2)%4] = true;
                return;
            case 3:
                v[(way+1) % 4] = true;
                return;
            case 4:
                v[(way+3)%4] = true;
                v[(way+1)%4] = true;
                return;
            case 5:
                Arrays.fill(v, true);
        }
    }
}

class Point{
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}