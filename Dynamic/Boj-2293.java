/*
Silver 1
백준 2293 : 동전 1
기준 : 0.5s, 4MB

Time : 144ms
- 다이나믹 프로그래밍 문제
- 중복이 발생하지 않도록 DP로직을 구현해야함 + 메모리가 4MB에 0.5초라서 2차원 배열로는 불가능한 문제이다
--> 10000 * 100 배열로 시도했으나 실패
- 동전의 가치 순서대로 k원까지 배열을 탐색 ( 한 개의 동전으로 만들 수 있는 방법 수를 저장 )
- 다음 동전 가치부터 이전에 적용했던 배열에 더하면서 진행 ( ex 2동전으로 3을 만들때 dp[2]에 dp[1]을 더함 )
- 동전 가치가 목표 값보다 초과하는 경우에는 continue 적용
평균 Time : 130 ~ 150ms
 */

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int[] arr = new int[101];
        int[] dp = new int[10001];


        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        for(int i=1; i<=n; i++) {
            arr[i] = Integer.parseInt(in.readLine());
        }

        dp[0] = 1;
        for(int i=1; i<=n; i++){
            if(arr[i] > 10000)
                continue;

            for(int j=arr[i]; j<=k; j++){
                dp[j] += dp[j-arr[i]];
            }
        }

        System.out.println(dp[k]);
    }
}