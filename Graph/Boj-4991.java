/*
Gold 2
백준 4991 : 로봇 청소기
기준 : 1s, 256MB

Time : 400ms
- 그래프 문제
- 처음에는 완전탐색 형태로 풀어서 시간이 오래 걸렸었다 ( 1500ms )
--> 먼지에 방문했을 때 다음 먼지로 가능 경우를 여러개로 나누어서 찾았고 맨 마지막 먼지에 도착했을 때 이동거리가 최소인 값을 찾음
- 중간에 백트래킹을 이용해서 최소값보다 커지면 탐색 중단하는 식으로 했다
- 먼지가 최대 10개이기 때문에 O(nm) * O(v!)으로 백트래킹을 안하면 시간 초과가 발생
백트래킹을 적용 해도 1500ms가 걸렸다

- 두번째 풀이는 각각 먼지의 좌표를 구한 뒤 먼지 간 거리를 나타내는 배열을 만든다 최대 10 * 10의 배열
- 이후 해당 배열을 완전탐색하여 최소 거리를 구한다.
- 먼지간 거리를 구할 때 bfs를 사용했고 완전탐색에서 재귀호출을 사용
- bfs에서 O(nm) * O(v^2) + O(v^2) 로 시간초과가 발생하지 않고 구할 수 있다.


평균 Time : 356 ~ 612ms
 */
import java.io.*;
import java.util.*;

public class Main {
    public static int[][] next = {{-1, 0}, {0, 1}, {1 ,0}, {0, -1}};
    public static int min;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder result = new StringBuilder();
        char[][] g = new char[20][20];
        int n, m, count;
        String str;
        Point[] arr;

        while(true){
            st = new StringTokenizer(in.readLine());
            m = Integer.parseInt(st.nextToken());
            n = Integer.parseInt(st.nextToken());
            int[][] distance = new int[11][11];
            boolean[] check = new boolean[11];
            arr = new Point[11];
            min = 2000000000;
            int val, flag = 0;
            count = 1;
            if((n == 0) && (m == 0))
                break;

            for(int i=0; i<n; i++){
                str = in.readLine();
                for(int j=0; j<m; j++){
                    g[i][j] = str.charAt(j);
                    if(g[i][j] == 'o'){
                        arr[0] = new Point(i, j);
                    }

                    if(g[i][j] == '*'){
                        arr[count++] = new Point(i, j);
                    }
                }
            }


            for(int i=0; i<count; i++){
                for(int j=i+1; j<count; j++){
                    val = bfs(g, n, m, arr[i], arr[j]);

                    if(val != -1){
                        distance[i][j] = val;
                        distance[j][i] = val;
                    } else{
                        flag = 1;
                        break;
                    }
                }
                if(flag == 1)
                    break;
            }

            if(flag == 1){
                min = -1;
            } else{
                getMin(distance, check, count,0, 0, 0);
            }
            result.append(min).append('\n');
        }

        System.out.println(result.toString());
    }

    public static void getMin(int[][] d, boolean[] check, int count, int depth, int choice, int sum){
        if(count == depth+1){
            if(min > sum)
                min = sum;
            return;
        }

        for(int i=1; i<count; i++){
            if(i == choice)
                continue;

            if(!check[i]){
                check[i] = true;
                getMin(d, check, count, depth+1, i, sum + d[choice][i]);
                check[i] = false;
            }
        }
    }

    public static int bfs(char[][] g, int n, int m, Point a, Point b){
        Queue<Point> q = new LinkedList<>();
        int[][] visit = new int[n][m];
        q.add(a);
        visit[a.x][a.y] = 1;
        Point temp;
        int x, y;

        while(!q.isEmpty()){
            temp = q.poll();

            if((temp.x == b.x) && (temp.y == b.y)){
                return visit[temp.x][temp.y] - 1;
            }

            for(int i=0; i<4; i++){
                x = temp.x + next[i][0];
                y = temp.y + next[i][1];

                if(validCheck(x, y, n, m)){
                    continue;
                }

                if((visit[x][y] == 0) && (g[x][y] != 'x')){
                    visit[x][y] = visit[temp.x][temp.y] + 1;
                    q.add(new Point(x, y));
                }
            }
        }

        return -1;
    }


    public static boolean validCheck(int x, int y, int n, int m){
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

