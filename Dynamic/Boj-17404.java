/*
Gold 4
백준 17404 : RGB거리 2
기준 : 0.5s, 128MB

Time : 140ms
- 다이나믹 프로그래밍 문제
- 맨 마지막과 처음값을 비교해주어야 하기 때문에 처음 선택한 색을 표시하는 방식으로 3차원 배열을 구성
- 일정한 점화식을 생성하기 위해서 선택되지 않은 첫번째 집은 dp에 max값을 부여한다
- n-2까지 최소로 색칠한 경우를 구한 뒤 n-1(마지막 색칠) 에서 첫번째 색칠한 집과 비교
--> i, j로 구분했으며 두 값이 같을때 pass함으로써 생략해준다.

** 선택되지 않은 집에 max값을 부여하지 않고 진행한 부분을 찾는데 조금 오래 걸린 문제이다

평균 Time : 140 ~ 152ms
 */

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int[][] arr = new int[1000][3];
        int[][][] dp = new int[1000][3][3];
        int n = Integer.parseInt(in.readLine());
        int min = 10000000;

        for(int i=0; i<n; i++){
            st = new StringTokenizer(in.readLine());
            arr[i][0] = Integer.parseInt(st.nextToken());
            arr[i][1] = Integer.parseInt(st.nextToken());
            arr[i][2] = Integer.parseInt(st.nextToken());
        }

        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++){
                if(i == j)
                    dp[0][j][i] = arr[0][i];
                else
                    dp[0][j][i] = 5000000;
            }
        }

        for(int i=1; i<n-1; i++){
            for(int j=0; j<3; j++){
                dp[i][0][j] = arr[i][0] + Math.min(dp[i-1][1][j], dp[i-1][2][j]);
                dp[i][1][j] = arr[i][1] + Math.min(dp[i-1][0][j], dp[i-1][2][j]);
                dp[i][2][j] = arr[i][2] + Math.min(dp[i-1][0][j], dp[i-1][1][j]);
            }
        }

        for(int j=0; j<3; j++){
            for(int i=0; i<3; i++){
                if(i == j)
                    continue;

                dp[n-1][i][j] = arr[n-1][i] + Math.min(dp[n-2][(i+1)%3][j], dp[n-2][(i+2)%3][j]);

                if(min > dp[n-1][i][j])
                    min = dp[n-1][i][j];
            }
        }

        System.out.println(min);
    }
}
