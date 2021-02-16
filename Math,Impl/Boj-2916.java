/*
Gold 4
백준 2916 : 각도기
기준 : 1s, 128MB

Time : 132ms ( java 8 : 76ms )
- 수학 문제
- 각도가 주어졌을 때 임의의 두 각의 최대공약수의 배수에 해당하는 각을 만들 수 있다
- 두 각이 서로소이면 1 ~ 360 까지 모든 각을 만들 수 있음
- 주어진 각도들을 2쌍을 지어서 최대공약수를 찾은 뒤 이것의 배수들을 체크
- 주어지는 각도 리스트에 360도를 포함해서 계산한다 ( 한 각도를 통해서 무조건 360도를 만들 수 있기 때문 )
- 찾을 각도를 O(1)방식으로 한번에 체크하여 판단한다.

평균 Time : 72 ~ 100ms ( java 8 기준 )
- 각들을 서로 빼거나 더하면서 새로운 각이 나왔을 때 똑같이 이어나가는 방식
- gcd를 사용하지 않고 배열 탐색을 이용해서 풀이한 경우가 많았다
- 다이나믹 프로그래밍으로도 풀 수 있다.
 */

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        StringBuilder result = new StringBuilder();
        int[] arr = new int[11];
        int[] valid = new int[361];
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(in.readLine());
        for(int i=0; i<n; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }
        arr[n] = 360;

        Arrays.sort(arr, 0, n+1);

        for(int i=0; i<n+1; i++){
            for(int j=i+1; j<n+1; j++){
                getGcd(arr[j], arr[i], valid);
            }
        }
        int val;
        st = new StringTokenizer(in.readLine());
        for(int i=0; i<k; i++){
            val = Integer.parseInt(st.nextToken());
            if(valid[val] == 1)
                result.append("YES\n");
            else
                result.append("NO\n");
        }

        System.out.println(result.toString());
    }

    public static void getGcd(int x, int y, int[] valid){
        int a = x;
        int b = y;
        int mod = a % b;
        while(true){
            if(mod == 0){
                for(int i=0; i<360; i+=b){
                    valid[i] = 1;
                }
                return;
            }

            a = b;
            b = mod;
            mod = a % b;
        }
    }
}
