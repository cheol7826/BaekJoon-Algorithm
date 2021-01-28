/*
Gold 4
백준 6087 : 레이저 통신
기준 : 1s, 128MB

Time : 384ms
- BFS를 이용하는 그래프 문제
- 이전 방향에 대한 정보를 기록하여 체크
- 누적 거울 설치 수에 대한 정보를 큐에 같이 저장하지 않고 visit배열에 계속 최신화 한 것이 문제가 되었다.
--> visit에 계속 초기화를 하면 해당 지점을 변경한 뒤 C에 도달하지 못했을 때 그 값을 계속 사용하게 되기 때문에 값이 다르게 나온다.

- 큐에 누적 거울 수를 저장하고 C에 도착했을 때 가진 거울의 수로 체크하는 것이 중요
평균 Time : 156 ~ 208ms
- 우선순위 큐를 사용하여 거울 수가 가장 적은 것 먼저 빼는 방식
- C에 도달하면 바로 리턴한다.
--> 일반 큐 사용 시 300ms ~ 400ms 걸리는 듯 하다
 */

import java.io.*;
import java.util.*;

public class Main {
    public static int[][] next = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public static int min;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        char[][] arr = new char[100][100];
        int[][] visit = new int[100][100];
        StringTokenizer st = new StringTokenizer(in.readLine());
        String str;
        int w = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());
        Point p1 = new Point();
        Point p2 = new Point();
        min = 2000000;

        for(int i=0; i<h; i++){
            str = in.readLine();
            for(int j=0; j<w; j++){
                arr[i][j] = str.charAt(j);
                if(arr[i][j] == 'C'){
                    p2 = p1;
                    p1 = new Point(i, j, -1, 0);
                }
            }
        }

        bfs(arr, visit, h, w, p1, p2);
        System.out.println(min);
    }

    public static void bfs(char[][] arr, int[][] visit, int h, int w, Point start, Point end){
        Point temp;
        int x, y;
        int ways, add;
        Queue<Point> q = new LinkedList<>();
        q.add(start);
        visit[start.x][start.y] = 1;

        while(!q.isEmpty()){
            temp = q.poll();
            ways = temp.way;

            if((temp.x == end.x) && (temp.y == end.y)){
                if(min > temp.count) {
                    min = temp.count;
                }
            }

            for(int i=0; i<4; i++){
                x = temp.x + next[i][0];
                y = temp.y + next[i][1];

                if(validCheck(x, y, h, w) || (arr[x][y] == '*'))
                    continue;

                if((ways == i) || (ways == -1))
                    add = 0;
                else
                    add = 1;

                if((visit[x][y] == 0) || ((visit[x][y] != 0) && (visit[x][y] >= temp.count + add))) {
                    visit[x][y] = temp.count + add;
                    q.add(new Point(x, y, i, visit[x][y]));
                }

            }
        }
    }

    public static boolean validCheck(int x, int y, int h, int w){
        return (x < 0) || (x >= h) || (y < 0) || (y >= w);
    }
}

class Point{
    int x;
    int y;
    int way;
    int count;

    Point(){}
    Point(int x, int y, int way, int count){
        this.x = x;
        this.y = y;
        this.way = way;
        this.count = count;
    }
}