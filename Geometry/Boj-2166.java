/*
Gold 4
백준 10986 : 나머지 합
기준 : 1s, 256MB

Time : 548ms
- 누적 합 문제
- ** 각 누적합의 나머지 그룹 배열을 만들어서 계산 ( 나머지 그룹을 생각하기 조금 힘들었다 )
- 나머지가 0인 경우는 해당 갯수 + 두개를 뽑는 수
- 0이 아닌 경우는 해당 나머지에 해당하는 누적합 갯수에서 2개를 선택하는 경우의 수를 더하면
전체 구간의 갯수를 구할 수 있다. 

평균 Time : 536 ~ 572ms
 */

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        long[] mod = new long[1001];
        long val = 0;
        long result = 0;
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(in.readLine());
        for(int i=0; i<n; i++){
            val += Long.parseLong(st.nextToken());
            mod[(int)(val % m)]++;
        }

        result = mod[0];
        for(int i=0; i<m; i++){
            result += (mod[i] * (mod[i]-1)) / 2;
        }

        System.out.println(result);
    }
}
