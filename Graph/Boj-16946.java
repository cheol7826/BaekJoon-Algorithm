/*
Gold 2
백준 16946 : 벽 부수고 이동하기 4
기준 : 2s, 512MB

Time : 756ms ( java 8 : 600ms )
- 그래프 문제 ( BFS를 사용 )
- 하나의 벽을 제거했을 때 인접한 공간의 수를 구하는 문제
- 처음에는 인접행렬 탐색하면서 벽을 만났을 때 주변이 빈공간이면 그 지점부터 bfs하는 방식으로 구현
--> 시간초과가 발생함 ( O(nm) * O(nm) --> 약 1000 ^ 4 )

- 0으로 연결된 부분의 갯수를 일차원 배열에 저장한다 ( 해당 지점에 번호를 붙임 )
- 이후 1로 된 부분을 탐색할 때 상하좌우에 해당하는 번호가 있으면 그 값을 더한다 ( O(nm) + O(nm) + O(1) )

평균 Time : 500 ~ 640ms ( java 8 기준 )
 */

import java.io.*;
import java.util.*;

public class Main {
    public static int[][] graph;
    public static int[][] next = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public static int[] counting;
    public static int n, m;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        graph = new int[1000][1000];
        int[][] visit = new int[1000][1000];
        int[][] result = new int[1000][1000];
        counting = new int[1000001];
        String str;
        StringBuilder res = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        int cnt = 0;
        int x, y;

        for(int i=0; i<n; i++){
            str = in.readLine();
            for(int j=0; j<m; j++){
                graph[i][j] = str.charAt(j) - '0';
            }
        }

        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if((graph[i][j] == 0) && (visit[i][j] == 0)){
                    cnt++;
                    bfs(visit, i, j, cnt);
                }
            }
        }

        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(graph[i][j] == 1){
                    for(int k=0; k<4; k++){
                        x = i + next[k][0];
                        y = j + next[k][1];

                        if(validCheck(x, y))
                            continue;

                        if(prevVal(visit, i, j, x, y, k))
                            continue;

                        if(visit[x][y] != 0)
                            result[i][j] += counting[visit[x][y]];
                    }
                    result[i][j]++;
                    result[i][j] %= 10;
                }
            }
        }

        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                res.append(result[i][j]);
            }
            res.append("\n");
        }

        System.out.println(res.toString());
    }

    public static boolean prevVal(int[][] visit, int i, int j, int x, int y, int k){
        int nx, ny;
        for(int v=k-1; v >= 0; v--){
            nx = i + next[v][0];
            ny = j + next[v][1];

            if(validCheck(nx, ny))
                continue;

            if(visit[nx][ny] == visit[x][y])
                return true;
        }

        return false;


    }

    public static void bfs(int[][] visit, int i, int j, int cnt){
        Queue<Point> q = new LinkedList<>();
        q.add(new Point(i, j));
        visit[i][j] = cnt;
        Point temp;
        int room = 0;
        int x, y;

        while(!q.isEmpty()){
            temp = q.poll();
            room++;

            for(int k=0; k<4; k++){
                x = temp.x + next[k][0];
                y = temp.y + next[k][1];

                if(validCheck(x, y))
                    continue;

                if((graph[x][y] == 0) && (visit[x][y] == 0)){
                    visit[x][y] = cnt;
                    q.add(new Point(x, y));
                }
            }
        }

        counting[cnt] = room;
    }

    public static boolean validCheck(int x, int y){
        return (x < 0) || (x >= n) || (y < 0) || (y >= m);
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
