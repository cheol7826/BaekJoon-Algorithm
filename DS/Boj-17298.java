/*
Gold 4
백준 17298 : 오큰수
기준 : 1s, 512MB

Time : 988ms
- 스택을 이용하는 문제
- 맨 마지막은 오큰수가 존재하지 않으므로 무조건 -1
- case가 100만이고 1초이므로 O(n)이나 O(nlogn)으로 찾아야 한다
- 현재 위치 기준으로 다음값이 오큰수가 아니면 스택에 push
- 오큰수가 맞으면 결과 배열에 다음값을 저장하고 스택에 존재하는 값을 pop하고 그 값도 오큰수가 맞는지 확인 ( 스택에는 무조건 내림차순으로 저장되어 있음 )
- 오큰수가 아니면 다음 값으로 진행
평균 Time : 972 ~ 1100ms
 */

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int[] arr = new int[1000000];
        int[] result = new int[1000000];
        int n = Integer.parseInt(in.readLine());
        Stack<Integer> stack = new Stack<>();
        StringBuilder res = new StringBuilder();

        st = new StringTokenizer(in.readLine());
        for(int i=0; i<n; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int idx;
        for(int i=0; i<n-1; i++){
            if(arr[i] < arr[i+1]){
                result[i] = arr[i+1];

                while(!stack.isEmpty()){
                    idx = stack.pop();
                    if(arr[idx] < arr[i+1]){
                        result[idx] = arr[i+1];
                    } else{
                        stack.push(idx);
                        break;
                    }
                }
            } else{
                stack.push(i);
            }
        }

        result[n-1] = -1;

        while(!stack.isEmpty()){
            idx = stack.pop();
            result[idx] = -1;
        }

        for(int i=0; i<n; i++){
            res.append(result[i]);
            res.append(" ");
        }
        res.append("\n");
        System.out.println(res.toString());
    }
}