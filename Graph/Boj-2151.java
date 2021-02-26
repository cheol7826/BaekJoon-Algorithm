/*
Gold 4
백준 2151 : 거울 설치
기준 : 2s, 128MB

Time : 136ms
- BFS 문제
- 그래프 탐색 시 한칸씩 탐색하는 것이 아니라 거울을 놓을 수 있는 지점이나 문에 도달할 때 까지 탐색
- 거울지점 (!) 에 왔을 때 이전에 온 방향 정보를 큐에 같이 저장하고 방문 배열에는 거울을 몇개 놓았는지 체크
- 해당 지점에 방문하지 않았거나 더 적은 횟수로 방문한 경우에만 큐에 넣는다

--> 4방향 탐색 시 이전 방향과 같은방향 or 처음으로 시작하는 경우 0, 그렇지 않은 경우를 1로 두고
- 거울 지점에서 해당 값을 더해서 진행하는 방식이다. 
평균 Time : 132 ~ 152ms
 */

import java.io.*;
import java.util.*;

public class Main {
    public static int min;
    public static int[][] next = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        char[][] arr = new char[50][50];
        int[][] visit = new int[50][50];
        Point start = new Point();
        min = 50000;
        int n = Integer.parseInt(in.readLine());

        String str;
        for(int i=0; i<n; i++){
            str = in.readLine();
            for(int j=0; j<n; j++){
                arr[i][j] = str.charAt(j);
                if(arr[i][j] == '#')
                    start = new Point(i, j, -1, 0);
            }
        }

        bfs(arr, visit, start, n);

        System.out.println(min);
    }

    public static void bfs(char[][] arr, int[][] v, Point start, int n){
        Queue<Point> q = new LinkedList<>();
        q.add(start);
        v[start.x][start.y] = 1;
        Point temp;
        int x, y, mir;

        while(!q.isEmpty()){
            temp = q.poll();

            if(((start.x != temp.x) || (start.y != temp.y)) && (arr[temp.x][temp.y] == '#')){
                if(min > temp.cnt)
                    min = temp.cnt;
                continue;
            }

            for(int i=0; i<4; i++){
                if((temp.way == -1) || (temp.way == i)){
                    mir = 0;
                } else{
                    mir = 1;
                }
                x = temp.x + next[i][0];
                y = temp.y + next[i][1];

                while(!validCheck(x, y, n)){
                    if(arr[x][y] != '.'){
                        break;
                    }

                    x += next[i][0];
                    y += next[i][1];
                }

                if(validCheck(x, y, n) || (arr[x][y] == '*')){
                    continue;
                }

                if(((arr[x][y] == '!') || (arr[x][y] == '#')) && ((v[x][y] == 0) || (v[x][y] > mir+temp.cnt))){
                    v[x][y] = mir+temp.cnt;
                    q.add(new Point(x, y, i, mir + temp.cnt));
                }
            }
        }
    }

    public static boolean validCheck(int x, int y, int n){
        return (x < 0) || (x >= n) || (y < 0) || (y >= n);
    }
}

class Point{
    int x;
    int y;
    int way;
    int cnt;

    Point(){

    }
    public Point(int x, int y, int way, int cnt) {
        this.x = x;
        this.y = y;
        this.way = way;
        this.cnt = cnt;
    }
}
