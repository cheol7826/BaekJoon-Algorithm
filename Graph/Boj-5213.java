/*
Gold 3
백준 5213 : 과외맨
기준 : 1s, 256MB

Time : 764ms ( java 8 : 640ms )
- BFS 문제
- 기존에는 한칸씩 체크해야하는 문제였다면 이 문제는 2칸씩 묶어서 생각해야 한다
- BFS 과정에서 좌, 우는 2칸을 기준으로 상, 하는 1칸을 기준으로 하되 왼쪽, 오른쪽 경우를 생각해야 한다
- 좌, 우는 단순하게 1개의 타일만 비교하면 되므로 쉽게 구현
- 상, 하는 짝수줄의 경우 n개, 홀수줄의 경우 n-1개 이므로 각각 나누어서 찾아야 한다
- 마지막칸에 도달하지 못하는 경우는 가장 큰 번호에 도달하는 방법을 출력해야 함 --> 처음에는 이부분을 간과하였음
- 도달하는 방법까지 출력해야 하므로 이동 과정을 저장해야 할 필요가 있다
--> 처음에는 queue에 저장할 때 ArrayList를 같이 저장하게 하여 값을 복사하도록 했으나 이 과정에서 시간이 오래걸렸다 ( 1300ms )
--> list배열을 두어서 처음으로 도달한 칸에는 다시 도달하지 못하기 때문에 이전 칸의 정보를 저장하도록 구현 ( 시간 단축 )

** 기존의 bfs는 상, 하, 좌, 우 탐색 시 공통점을 가지고 탐색던 문제를 많이 접했는데 
case를 나누어서 탐색하는 부분에서 이해하는 시간이 좀 오래걸렸다.
평균 Time : 520 ~ 700ms ( java 8 기준 )
 */

import java.io.*;
import java.util.*;

public class Main {
    public static int[] list;
    public static int max;
    public static int[][] next = {{-1 ,0}, {0, 1}, {1, 0}, {0, -1}};
    public static StringBuilder result;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        result = new StringBuilder();
        int[][] arr = new int[500][1000];
        list = new int[500*500];
        boolean[][] visit = new boolean[500][1000];
        int left, right;
        int n = Integer.parseInt(in.readLine());
        max = 0;

        for(int i=0; i<n; i++){
            for(int j=0; j<2*n; j+=2){
                if((i % 2 == 1) && (j == 2*n - 2))
                    continue;

                st = new StringTokenizer(in.readLine());
                left = Integer.parseInt(st.nextToken());
                right = Integer.parseInt(st.nextToken());

                if(i % 2 == 0){
                    arr[i][j] = left;
                    arr[i][j+1] = right;
                } else{
                    arr[i][j+1] = left;
                    arr[i][j+2] = right;
                }
            }
        }

        bfs(arr, visit, n);
        resultPrint(max, 1);
        System.out.println(result.toString());
    }

    public static void resultPrint(int idx, int depth){
        if(list[idx] == 0){
            result.append(depth).append("\n");
            result.append(idx).append(" ");
            return;
        }

        resultPrint(list[idx], depth+1);
        result.append(idx).append(" ");
    }

    public static void bfs(int[][] arr, boolean[][] visit, int n){
        Queue<Point> q = new LinkedList<>();
        Point temp, ntemp;
        temp = new Point(0, 0, 1);

        q.add(temp);
        visit[0][0] = true;
        int x, y, idx, nidx, nx, ny;

        while(!q.isEmpty()){
            temp = q.poll();
            x = temp.x;
            y = temp.y;
            idx = temp.idx;

            if(max < idx){
                max = idx;
            }

            for(int i=0; i<4; i++){
				// 상, 하를 탐색할 때
                if(i % 2 == 0){
                    nx = x + next[i][0];
                    ny = y + next[i][1];

					// 해당 타일의 번호찾기
					// 아래방향은 n만큼 증가하고 위방향은 n-1만큼 감소
                    nidx = idx + n * next[i][0];
                    if(i == 0)
                        nidx++;
					
					// 왼쪽 타일을 탐색하는 경우
                    if((y != 0) && !validCheck(nx, ny-1, n)){
                        if(!visit[nx][ny-1] && (arr[x][y] == arr[nx][ny])){
                            visit[nx][ny-1] = true;
                            ntemp = new Point(nx, ny-1, nidx-1);
                            list[nidx-1] = idx;
                            q.add(ntemp);
                        }
                    }

					// 오른쪽 타일을 탐색하는 경우
                    if((y != 2*n - 2) && !validCheck(nx, ny+1, n)){
                        if(!visit[nx][ny+1] && (arr[x][y+1] == arr[nx][ny+1])){
                            visit[nx][ny+1] = true;
                            ntemp = new Point(nx, ny+1, nidx);
                            list[nidx] = idx;
                            q.add(ntemp);
                        }
                    }

                }
				// 좌, 우를 탐색할 때
                else{
                    nx = x + next[i][0] * 2;
                    ny = y + next[i][1] * 2;

                    if(validCheck(nx, ny, n) || visit[nx][ny])
                        continue;

                    nidx = idx + next[i][1];

                    if(((i == 1) && (arr[nx][ny] == arr[x][y+1])) ||
                            ((i == 3) && (arr[nx][ny+1] == arr[x][y]))){
                        visit[nx][ny] = true;
                        ntemp = new Point(nx, ny, nidx);
                        list[nidx] = idx;
                        q.add(ntemp);
                    }
                }
            }

        }
    }

    public static boolean validCheck(int x, int y, int n){
        return (x < 0) || (x >= n) || (y < 0) || (y >= 2*n);
    }
}

class Point{
    int x;
    int y;
    int idx;

    public Point(int x, int y, int idx) {
        this.x = x;
        this.y = y;
        this.idx = idx;
    }
}
