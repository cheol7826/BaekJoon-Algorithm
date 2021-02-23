/*
Silver 2
백준 11279 : 최대 힙
기준 : 1s, 256MB

Time : 300ms
- 자료구조 ( Heap 문제 )
- 힙의 기본개념이 부족해서 조금 고생했던 문제
- 힙 : 이진 트리를 이용하는 것으로 부모노드가 자식노드의 값보다 항상 커야함을 이용 ( 최대 힙 기준 )
- 삽입연산은 쉽게 구현하였음 ( 마지막에 넣고 제자리를 찾아올라가는 방식 )
- 삭제연산은 조금 시간이 걸렸었다 ( root 값을 지우고, 맨 마지막 값을 root로 올려 아래로 찾아가는 방식 )
--> 무조건 자식노드보다 커야하기 때문에 자식 노드 중 큰 것과 부모노드를 비교하는 방식 (단순 while문으로 해결 가능 ) 

평균 Time : 292 ~ 356ms
 */

import java.io.*;
import java.util.*;

public class Main {
    public static StringBuilder result;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int[] arr = new int[100001];
        int n = Integer.parseInt(in.readLine());
        int num, size = 1;

        result = new StringBuilder();
        for(int i=0; i<n; i++){
            num = Integer.parseInt(in.readLine());

            if(num == 0){
                if(size == 1){
                    result.append(0).append("\n");
                } else{
                    result.append(arr[1]).append("\n");
                    heapDelete(arr, size);
                    size--;
                }
            } else {
                if(size == 1){
                    arr[size] = num;
                } else {
                    heapInsert(arr, size, num);
                }
                size++;
            }
        }

        System.out.println(result.toString());
    }

    public static void heapDelete(int[] arr, int size){
        int prev = 1;
        int idx = 2;
        int val = arr[size - 1];

        while(idx < size - 1){
            if((idx + 1 < size - 1) && (arr[idx] < arr[idx+1])){
                idx++;
            }

            if(arr[idx] < val){
                arr[prev] = val;
                return;
            }

            arr[prev] = arr[idx];
            prev = idx;
            idx *= 2;
        }

        arr[prev] = val;
    }

    public static void heapInsert(int[] arr, int size, int num){
        int prev = size;
        int next = size / 2;

        while(next >= 1){
            if(arr[next] < num) {
                arr[prev] = arr[next];
                prev = next;
                next /= 2;
            } else{
                arr[prev] = num;
                return;
            }
        }

        arr[1] = num;
    }
}
