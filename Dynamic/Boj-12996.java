/*
Gold 3
백준 12996 : Acka
기준 : 2s, 512MB

Time : 620ms
- 다이나믹 프로그래밍 문제 ( 재귀호출을 이용한 다이나믹 프로그래밍 )
- a, b, c가 참여할 수 있는 횟수가 0이 될 때 까지 재귀호출
- 이 과정에서 중복되는 계산이 발생할 수 있는데 한번 방문한 경우에는 dp로 저장한 값을 불러서 사용
- 음수가 되는 경우는 불가능한 경우로 0 리턴  --> 아예 접근하지 못하게 하면 시간을 더 단축할 수 있을 듯

평균 Time : 220 ~ 300ms
 */

import java.io.*;
import java.util.*;

public class Main {
    public static long mod = 1000000007;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        long[][][][] dp = new long[51][51][51][51];
        int s = Integer.parseInt(st.nextToken());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        for(int i=0; i<=s; i++){
            for(int j=0; j<=a; j++){
                for(int k=0; k<=b; k++){
                    for(int l=0; l<=c; l++){
                        dp[i][j][k][l] = -1;
                    }
                }
            }
        }

        System.out.println(find(dp, s, a, b, c));
    }

    public static long find(long[][][][] dp, int s, int a, int b, int c){
        long val = 0;


        if(s <= 0){
            if((a == 0) && (b == 0) && (c == 0))
                return 1;
            else
                return 0;
        }

        if((a < 0) || (b < 0) || (c < 0))
            return 0;


        if(dp[s][a][b][c] != -1){
            return dp[s][a][b][c];
        }

        val = (val + find(dp, s-1, a-1, b, c)) % mod;
        val = (val + find(dp, s-1, a, b-1, c)) % mod;
        val = (val + find(dp, s-1, a, b, c-1)) % mod;
        val = (val + find(dp, s-1, a-1, b-1, c)) % mod;
        val = (val + find(dp, s-1, a-1, b, c-1)) % mod;
        val = (val + find(dp, s-1, a, b-1, c-1)) % mod;
        val = (val + find(dp, s-1, a-1, b-1, c-1)) % mod;

        dp[s][a][b][c] = val;
        return val;
    }
}
