/*
Gold 4
백준 1717 : 집합의 표현
기준 : 2s, 128MB

Time : 680ms
- 자료구조 ( Disjoint-Set ) 문제
- 0 연산에 대해서는 union을 수행하고 1에서는 find를 통해서 같은 집합인지 체크
- union의 경우 a, b에 대해서 각 집합의 최상단 값을 찾고 두 개가 같은지 확인한다
--> 다른 경우 a, b는 다른 집합이므로 b의 값을 a로 바꿔서 같은 집합을 만들어 준다

* 최상단의 값만 바꿨더니 3000ms 이상이 걸렸다, a, b에 대해서도 적용을 해주었더니 600ms로 줄어들었다
- while로 찾는 값이 확 줄어들어서 그런 듯 하다.

평균 Time : 452 ~ 500ms
- find를 while로 하지 않고 재귀호출을 통해서 같은 집합의 원소들을 전부 최상단 값으로 변경해주는 방식
- 탐색한 모든 값들을 바꿨기 때문에 더 빠르게 찾는 것 같다.
 */

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        StringBuilder result = new StringBuilder();
        int[] arr = new int[1000001];
        int opt, a, b;
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        for(int i=0; i<=n; i++){
            arr[i] = i;
        }

        for(int i=0; i<m; i++) {
            st = new StringTokenizer(in.readLine());

            opt = Integer.parseInt(st.nextToken());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());

            if (opt == 1) {
                a = findHead(arr, a);
                b = findHead(arr, b);
                if(a == b)
                    result.append("YES").append("\n");
                else
                    result.append("NO").append("\n");
            } else {
                union(arr, a, b);
            }
        }

        System.out.println(result.toString());
    }

    public static void union(int[] arr, int a, int b){
        int x, y;

        x = findHead(arr, a);
        y = findHead(arr, b);

        if(x != y){
            arr[y] = x;
        }
    }
    
    // 재귀호출 사용
    public static int findHead(int[] arr, int val){
        int x;
        if(arr[val] == val){
            return val;
        }

        x = findHead(arr, arr[val]);
        arr[val] = x;
        return x;
    }
    
    // 재귀호출 사용 X
//    public static int findHead(int[] arr, int val){
//        int x = val;
//
//        while(x != arr[x]){
//            x = arr[x];
//        }
//
//        return x;
//    }
}
