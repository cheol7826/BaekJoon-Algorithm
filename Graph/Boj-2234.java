/*
Gold 4
백준 2234 : 성곽
기준 : 2s, 128MB

Time : 140ms
- 그래프 문제 ( DFS를 사용했음, BFS로도 가능하다 )
- 각 벽에 대한 정보를 비트마스킹으로 해결
- dfs를 이용해서 벽을 부수지 않았을 때 방의 수와 최대 방 크기를 구한다
--> 이 과정에서 방문 배열에 각각의 방 번호를 부여해주고 방 번호에 대한 방 크기를 저장해준다
- 벽을 부순 이후 방 크기를 계산하기 위해서 dfs를 한번 더 돈다
--> 탐색 중 벽 하나를 두고 다른방이 나오는 경우 찾기 ( 벽 부수고 합칠 수 있는 방 )
같은 방 번호에서만 dfs를 탐색하고 다른 방이 나오면 위에서 방의 크기를 구했기 때문에
두 개를 더한 값의 최댓값을 찾는다 --> 벽을 부순 뒤 합쳐진 방의 최대 크기

평균 Time : 140 ~ 188ms
 */

import java.io.*;
import java.util.*;

public class Main {
    public static int roomcount;
    public static int n, m;
    public static int Rmax;
    public static int[][] next = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder result = new StringBuilder();
        int[][] arr = new int[50][50];
        int[][] visit = new int[50][50];
        boolean[][] v2 = new boolean[50][50];
        int[] room = new int[2501];
        StringTokenizer st = new StringTokenizer(in.readLine());
        int cnt = 0;
        int max = 0;

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        for(int i=0; i<m; i++){
            st = new StringTokenizer(in.readLine());
            for(int j=0; j<n; j++){
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                if(visit[i][j] == 0){
                    roomcount = 0;
                    cnt++;
                    visit[i][j] = cnt;
                    dfs(arr, visit, i, j, cnt);
                    room[cnt] = roomcount;
                    if(max < roomcount)
                        max = roomcount;
                }
            }
        }

        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                if(!v2[i][j]) {
                    v2[i][j] = true;
                    exdfs(visit, v2, room, i, j);
                }
            }
        }

        result.append(cnt).append("\n");
        result.append(max).append("\n");
        result.append(Rmax).append("\n");
        System.out.println(result.toString());
    }

    public static void exdfs(int[][] arr, boolean[][] v, int[] room, int i, int j){
        int x, y, val, cnt = arr[i][j];
        for(int k=0; k<4; k++){
            x = i + next[k][0];
            y = j + next[k][1];

            if(validCheck(x, y, m, n))
                continue;

            if((arr[x][y] == cnt) && (!v[x][y])){
                v[x][y] = true;
                exdfs(arr, v, room, x, y);
            }

            if((arr[x][y] != cnt)){
                val = room[cnt] + room[arr[x][y]];
                if(Rmax < val)
                    Rmax = val;
            }
        }
    }

    public static void dfs(int[][] arr, int[][] v, int i, int j, int cnt){
        int x, y, val;
        roomcount++;

        for(int k=0; k<4; k++){
            x = i + next[k][0];
            y = j + next[k][1];

            val = (int)Math.pow(2, k);

            if(validCheck(x, y, m, n))
                continue;

            if(((arr[i][j] & val) == 0) && (v[x][y] == 0)){
                v[x][y] = cnt;
                dfs(arr, v, x, y, cnt);
            }
        }
    }




    public static boolean validCheck(int x, int y, int m, int n){
        return (x < 0) || (x >= m) || (y < 0) || (y >= n);
    }
}
