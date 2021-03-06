/*
Gold 1
백준 12969 : ABC
기준 : 2s, 512MB

Time : 208ms ( java 8 : 140ms )
- 다이나믹 프로그래밍 문제
- 주어진 조건을 만족하는 문자열 찾기
- s, a, b, k로 하는 4차원 배열을 두고 찾는다 ( a, b수가 정해지면 c는 자동으로 정해진다 )
- 현재 단계에서 A, B, C 중 하나를 추가할 수 있고 그 때 k쌍의 갯수는 이전에 존재하는 A, B의 수에 따라 결정된다
- A를 추가하면 0개, B를 추가하면 a개, C를 추가하면 a+b개 --> 각 단계마다 해당하는 문자열을 전달하고
- 주어진 조건을 만족하면 return하면서 문자열을 역으로 추적한 뒤 reverse한다.

평균 Time : 72 ~ 148ms ( java 8 )
 */

import java.io.*;
import java.util.*;

public class Main {
    public static StringBuilder result;
    public static int n, k;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        boolean[][][][] dp = new boolean[31][31][31][436];
        result = new StringBuilder();

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        if(ABC(dp, 0, 0, 0, 0, ' ')){
            System.out.println(result.reverse().toString());
        } else{
            System.out.println(-1);
        }
    }

    public static boolean ABC(boolean[][][][] dp, int s, int a, int b, int val, char c){
        if(s == n){
            if(val == k){
                result.append(c);
                return true;
            }

            return false;
        }

        if(!dp[s+1][a+1][b][val]){
            dp[s+1][a+1][b][val] = true;
            if(ABC(dp, s+1, a+1, b, val, 'A')){
                if(c != ' ')
                    result.append(c);
                return true;
            }
        }

        if(!dp[s+1][a][b+1][val+a]){
            dp[s+1][a][b+1][val+a] = true;
            if(ABC(dp, s+1, a, b+1, val+a, 'B')){
                if(c != ' ')
                result.append(c);
                return true;
            }
        }

        if(!dp[s+1][a][b][val+a+b]){
            dp[s+1][a][b][val+a+b] = true;
            if(ABC(dp, s+1, a, b, val+a+b, 'C')){
                if(c != ' ')
                result.append(c);
                return true;
            }
        }

        return false;
    }

}
