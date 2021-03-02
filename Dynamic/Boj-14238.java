/*
Gold 3
백준 14238 : 출근 기록
기준 : 2s, 512MB

Time : 172ms ( java 8 : 108ms )
- 다이나믹 프로그래밍 문제 ( DP를 A, B, C 갯수의 배열로 체크 )
- 주어진 문자열에서 가능한 순열을 찾아서 출력해야 하는 문제
- DFS + DP를 이용해서 조건에 맞는 순열을 찾은 뒤 return하면서 해당 단계의 문자열을 추가하고 reverse하여 출력
---
- 먼저 A, B, C의 개수를 세고 각각 A, B, C의 개수가 될 때 까지 선택
- dp 배열을 a, b, c의 갯수, 이전 선택 값, 현재 선택 값 의 5차원 배열을 두고
해당 배열의 값이 0인 경우에만 들어간다 ( 1인경우는 이미 방문한 경우로 들어가지 않음 )
- A의 경우 특별한 조건이 없지만 B, C의 경우 -1, -2 번째에 나왔었는지 체크해야 한다 ( prev, cur 값으로 체크하였음 )
- 조건에 맞는 순열을 찾으면 A, B, C의 갯수를 센 배열을 0으로 초기화 하여 다시 진입하지 못하도록 한다
- 만약 결과값이 빈문자열이면 -1을 출력해준다
평균 Time : 80 ~ 132ms ( java 8 기준 )
 */

import java.io.*;
import java.util.*;

public class Main {
    public static StringBuilder result;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String str = in.readLine();
        result = new StringBuilder();
        int[] arr = new int[3];
        int[] cnt = new int[3];
        int[][][][][] dp = new int[51][51][51][3][3];

        for(int i=0; i<str.length(); i++){
            arr[str.charAt(i)-'A'] += 1;
        }

        if(arr[0] > cnt[0]){
            cnt[0]++;
            dp[1][0][0][0][0] = 1;
            func(arr, cnt, dp, 0, 0);
            cnt[0]--;
        }

        if(arr[1] > cnt[1]){
            cnt[1]++;
            dp[0][1][0][0][1] = 1;
            func(arr, cnt, dp, 0, 1);

            cnt[1]--;
        }

        if(arr[2] > cnt[2]){
            cnt[2]++;
            dp[0][0][1][0][2] = 1;
            func(arr, cnt, dp, 0,2);

            cnt[2]--;
        }

        if(result.toString().equals("")) {
            System.out.println(-1);
        } else {
            System.out.println(result.reverse().toString());
        }

    }
    public static boolean func(int[] arr, int[] cnt, int[][][][][] dp, int prev, int cur){
        if((arr[0] == cnt[0]) && (arr[1] == cnt[1]) && (arr[2] == cnt[2])){
            arr[0] = 0;
            arr[1] = 0;
            arr[2] = 0;
            result.append((char)('A' + cur));
            return true;
        }

        for(int i=0; i<3; i++){
            if((i != 0) && (cur == i))
                continue;

            if(i == 2){
                if(prev == i){
                    continue;
                }
            }
            if(arr[i] > cnt[i]){
                cnt[i]++;
                if(dp[cnt[0]][cnt[1]][cnt[2]][cur][i] == 0) {
                    dp[cnt[0]][cnt[1]][cnt[2]][cur][i] = 1;
                    if (func(arr, cnt, dp, cur, i)) {
                        result.append((char) ('A' + cur));
                        return true;
                    }
                }
                cnt[i]--;
            }
        }

        return false;
    }
}
