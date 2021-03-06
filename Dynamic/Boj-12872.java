/*
Gold 2
백준 12872 : 플레이리스트
기준 : 2s, 512MB

Time : 136ms ( java 8 : 76ms )
- 다이나믹 프로그래밍 문제
- 점화식 찾기가 까다로웠던 문제
- 선택한 총 노래 수 / 노래 종류의 수 로 나누어서 찾기
- 노래 p개를 선택할 때 n개가 전부 쓰여야 한다 --> p-1 n-1 + p-1 n 인 경우를 더하면 된다
- 단! 같은 노래 사이에는 적어도 m개의 곡이 있어야 하므로 p-1 n의 경우에서 m가지의 경우를 빼서 계산한다

평균 Time : 72 ~ 92ms ( java 8 )
 */

import java.io.*;
import java.util.*;

public class Main {
    public static long mod = 1000000007;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        long[][] dp = new long[101][101];
        long val1, val2;

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());

        dp[1][1] = n;

        if(m == 0){
            for(int i=1; i<=p; i++){
                dp[i][1] = n;
            }
        }

        for(int i=2; i<=p; i++){
            for(int j=2; j<=n; j++){
                val1 = (dp[i-1][j-1] * (n-(j-1))) % mod;
                if(j-m > 0)
                    val2 = (dp[i-1][j] * (j-m)) % mod;
                else
                    val2 = 0;
                dp[i][j] = (val1 + val2) % mod;
            }
        }

        System.out.println(dp[p][n]);
    }
}
