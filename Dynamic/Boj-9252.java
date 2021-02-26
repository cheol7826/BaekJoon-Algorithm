/*
Gold 5
백준 9252 : LCS2
기준 : 1s, 256MB

Time : 180ms
- 다이나믹 프로그래밍 문제
- DP로 푸는 대표적인 문제인 LCS문제 이다
- 가장 긴 LCS의 길이와 그 LCS 문자열이 무엇인지 출력해야 한다
- O(nm)의 형태로 각각의 문자열을 전부 탐색한다
- 문자열이 서로 같으면 대각선 위의 값에서 +1, 다른 경우 n-1 m, n m-1 중에서 큰 값을 선택
- 최대값을 기억한 뒤 배열을 역추적하여 해당하는 문자열을 찾는다
평균 Time : 160 ~ 188ms
 */

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int[][] dp = new int[1001][1001];
        StringBuilder result = new StringBuilder();
        String str1 = in.readLine();
        String str2 = in.readLine();
        int x = 0, y = 0;
        int max = 0;

        for(int i=0; i<str1.length(); i++){
            for(int j=0; j<str2.length(); j++){
                if(str1.charAt(i) == str2.charAt(j)){
                    dp[i+1][j+1] = dp[i][j] + 1;
                    if(max < dp[i+1][j+1]) {
                        max = dp[i + 1][j + 1];
                        x = i+1;
                        y = j+1;
                    }
                } else{
                    dp[i+1][j+1] = Math.max(dp[i][j+1], dp[i+1][j]);
                }
            }
        }

        while (dp[x][y] != 0) {
            if (str1.charAt(x - 1) == str2.charAt(y - 1)) {
                result.append(str1.charAt(x - 1));
                x--;
                y--;
            } else {
                if (dp[x][y - 1] > dp[x - 1][y]) {
                    y--;
                } else {
                    x--;
                }
            }
        }

        System.out.println(max);
        System.out.println(result.reverse().toString());
    }
}
