/*
Gold 2
백준 9527 : 1의 개수 세기
기준 : 1s, 128MB

Time : 132ms ( java 8 : 76ms )
- 수학문제
- 두개의 숫자 중에서 a-1 까지의 1의 갯수와 b 까지의 1의 갯수를 구해서 빼주는 방식
- 1의 개수는 각 2진수 자릿수 별로 / % 연산을 이용해서 구했다
- 각 자리별로 0 1이 나오는 특징이 존재한다
--> 1부터 기준
- 2^0 자리는 1 0 - 1 0 .... (2개 패턴의 반복)
- 2^1 자리는 0 1 1 0 - 0 1 1 0 .... (4개 패턴의 반복)
- 2^2 자리는 0 0 0 1 1 1 1 0 - 0 0 0 1 1 1 1 0 .... (8개 패턴의 반복)
- 다음과 같은 규칙을 찾았다
2^1자리 부터 -->  2^n-1 만큼의 0이 반복되고 2^n 만큼의 1이 반복된다
즉 해당 숫자를 (2^n+1로 나눈 몫 * 2^n) + (2^n+1로 나눈 나머지에서 나오는 1의 수) 로 나누어서 구했다. 

평균 Time : 70 ~ 116ms ( java 8 기준 )
 */

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        long a = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());

        long val1 = calcOne(a-1);
        long val2 = calcOne(b);
        System.out.println(val2-val1);
    }

    public static long calcOne(long number){
        if(number == 0)
            return 0;

        if(number == 1)
            return 1;

        int point = 1;
        long sum = 0;
        long mod, zero, prev;

        // 2^0 을 1번째로 두고 계산
        // 즉 n+1 == point
        while(Math.pow(2, point-1) <= number){
            // 현재 자릿수 계산 (2^0자리 2^1자리 ... )
            prev = (long)Math.pow(2, point-1);
            
            // 2^n+1로 나눈 몫을 2^n과 곱해서 더한다
            sum += (number / (long)Math.pow(2, point)) * prev;
            
            // 나머지를 통해서 남은 1의 갯수를 구함
            mod = number % (long)Math.pow(2, point);
            // 처음 0이 반복되는 갯수
            zero = (long)Math.pow(2, point-1)-1;
            
            // 1이 아닌경우 ( 2^0자리가 아닌 경우만 )
            if((point != 1) && (mod > zero)){
                // 1이 2^n번 반복하기 떄문에 최대 횟수를 넘지 않음 (prev)
                sum += Math.min(mod-zero, prev);
            }

            // 2^0의 경우 나머지가 1이면 1이 한번 더 나온것이므로 더해준다
            if((point == 1) && (mod == 1)){
                sum++;
            }
            point++;
        }

        return sum;
    }
}
