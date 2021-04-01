/*
Gold 3
백준 1937 : 욕심쟁이 판다
기준 : 2s, 256MB

Time : 560ms
- DP를 이용한 그래프 탐색
- 배열의 처음부터 탐색하면서 이동하는 위치의 값이 0이면 dfs로 탐색하고 0이 아니면 해당값으로 대체
- 특정 지점에서 더이상 이동이 불가능하면 해당 지점의 값을 1로하고 그 값을 리턴
- 리턴으로 돌아왔을 때 4방향 중 가장 큰값을 자기자신과 더한 뒤 그 값을 리턴
--> 재귀호출로 동작하여 깊이를 탐색한다.

평균 Time : 480 ~ 532ms
 */
import java.io.*;
import java.util.*;

public class Main {
    public static int[][] next = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public static int max;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int[][] arr = new int[500][500];
        int[][] dp = new int[500][500];
        max = 0;
        int n = Integer.parseInt(in.readLine());

        for(int i=0; i<n; i++){
            st = new StringTokenizer(in.readLine());
            for(int j=0; j<n; j++){
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i=0; i<n; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[i][j] == 0) {
                    dp[i][j] = 1;
                    dp[i][j] = dfs(arr, dp, n, i, j);
                }
            }
        }

        System.out.println(max);
    }

    public static int dfs(int[][] arr, int[][] dp, int n, int i, int j){
        int level = 0;
        int x, y;

        for(int k=0; k<4; k++){
            x = i + next[k][0];
            y = j + next[k][1];

            if(validCheck(x, y, n) || (arr[x][y] <= arr[i][j]))
                continue;

            if(dp[x][y] == 0){
                dp[x][y] = 1;
                level = Math.max(dfs(arr, dp, n, x, y), level);
            } else{
                level = Math.max(dp[x][y], level);
            }
        }

        dp[i][j] += level;

        if(max < dp[i][j])
            max = dp[i][j];

        return dp[i][j];
    }

    public static boolean validCheck(int x, int y, int n){
        return (x < 0) || (x >= n) || (y < 0) || (y >= n);
    }
}


