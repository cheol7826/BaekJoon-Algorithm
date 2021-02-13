/*
Gold 5
백준 14502 : 연구소
기준 : 2s, 512MB

Time : 912ms
- bfs + 브루트 포스 문제
- 그래프 탐색을 하면서 벽을 세울 수 있는 모든 경우의 수를 탐색해야 함
- 3개의 벽이 세워졌을 때 바이러스( = 값이 2 )가 확산되는 것을 bfs를 통해서 판단
- 이후 남아있는 0의 개수를 파악함
평균 Time : 300 ~ 400ms
- 시간이 약 600ms 정도 더 걸렸는데 벽을 세울 때 세운 지점부터 다음 벽을 판단하는 것이 아닌 0, 0부터 파악하기 때문이라고 생각함
- 인덱스를 같이 넘겨서 그 지점부터 다시탐색하거나, / %를 이용해서 일차원 배열 연산으로 진행하면 될 듯 하다.
 */

import java.io.*;
import java.util.*;

public class Main {
    public static int[][] next = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public static int max;
    public static int[][] visit;
    public static int ncount;
    public static Queue<Point> queue;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int[][] arr = new int[8][8];
        visit = new int[8][8];
        StringTokenizer st = new StringTokenizer(in.readLine());
        max = 0;
        queue = new LinkedList<>();
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        for(int i=0; i<n; i++){
            st = new StringTokenizer(in.readLine());
            for(int j=0; j<m; j++){
                arr[i][j] = Integer.parseInt(st.nextToken());
                if(arr[i][j] != 0)
                    ncount++;
                if(arr[i][j] == 2)
                    queue.add(new Point(i, j));
            }
        }

        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(arr[i][j] == 0){
                    arr[i][j] = 1;
                    wall(arr, n, m, 1);
                    arr[i][j] = 0;
                }
            }
        }

        System.out.println(max);
    }

    public static void bfs(int[][] arr, int n, int m){
        Queue<Point> q = new LinkedList<>(queue);
        Point temp;
        int x, y;
        int count = 0;

        for(int i=0; i<n; i++)
            Arrays.fill(visit[i], 0);

        while(!q.isEmpty()){
            temp = q.poll();

            for(int i=0; i<4; i++){
                x = temp.x + next[i][0];
                y = temp.y + next[i][1];

                if(validCheck(x, y, n, m))
                    continue;

                if((arr[x][y] == 0) && (visit[x][y] == 0)){
                    visit[x][y] = 1;
                    count++;
                    q.add(new Point(x, y));
                }
            }
        }

        if(max < n*m-count-ncount-3) {
            max = n*m-count-ncount-3;
        }
    }

    public static boolean validCheck(int x, int y, int n, int m){
        return (x < 0) || (x >= n) || (y < 0) || (y >= m);
    }

    public static void wall(int[][] arr, int n, int m, int depth){
        if(depth == 3){
            bfs(arr, n, m);
            return;
        }

        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(arr[i][j] == 0){
                    arr[i][j] = 1;
                    wall(arr, n, m, depth+1);
                    arr[i][j] = 0;
                }
            }
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