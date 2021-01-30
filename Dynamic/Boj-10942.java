/*
Gold 2
백준 10942 : 팰린드롬?
기준 : 0.5s, 256MB

Time : 884ms
- 다이나믹 프로그래밍 문제
- n개 수열 전체 팰린드롬 여부를 DP를 이용해서 판단 후 질문에서는 O(1) 방식으로 바로 찾도록 함
- 팰린드롬은 1, 2개일때 먼저 판별 후 3개부터는 맨 끝 2개를 제외한 내부가 팰린드롬인지 확인 후 ( O(1) ) + 맨 끝이 서로 같은지 확인
평균 Time : 750 ~ 850ms
- 방식은 똑같으나 배열을 사용하지 않고 StringBuilder를 이용해서 결과값 문자열을 만들고 System.out으로 한번만 출력
- out.write가 100만번 반복해서 시간이 늘어난 듯 하다.
 */

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        int[][] dp = new int[2000][2000];
        int[] arr = new int[2000];

        StringTokenizer st;
        int a, b, m, n;

        n = Integer.parseInt(in.readLine());
        st = new StringTokenizer(in.readLine());
        for(int i=0; i<n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        for(int i=0; i<n; i++){
            dp[i][i] = 1;
            if((i != n-1) && (arr[i] == arr[i+1]))
                dp[i][i+1] = 1;
        }

        for(int i=2; i<n; i++){
            for(int j=0; j+i<n; j++){
                if((dp[j+1][j+i-1] != 0) && (arr[j] == arr[j+i])){
                    dp[j][j+i] = 1;
                }
            }
        }

        m = Integer.parseInt(in.readLine());
        for(int i=0; i<m; i++){
            st = new StringTokenizer(in.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            out.write(dp[a-1][b-1] + "\n");
        }
        out.flush();
    }
}