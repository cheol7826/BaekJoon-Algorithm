/*
Platinum 5
백준 14003 : 가장 긴 증가하는 부분수열 5
기준 : 3s, 512MB

Time : 720ms
- LIS문제 ( n 이 100만 개 )
- 기존에는 DP를 이용해서 O(n^2) 형태로 계산했었는데 이번에는 n이 커서 불가능하다
- 길이가 a인 LIS의 마지막 값 중 가장 최근에 찾은 값을 저장하는 배열을 만들고
해당하는 숫자가 어떤 길이의 마지막 값인지를 저장한다 ( 이 때 배열탐색을 이분탐색으로 찾는다 )
- 만약 같은 값을 찾았다면 a-1에 해당하는 인덱스를 가리키도록 하고
- 그렇지 않은 경우는 해당 숫자가 어떤 길이에 들어가야 하는지 파악 후 그 길이의 -1에 해당하는 인덱스를 넣는다
- 가장 마지막에 존재하는 숫자를 기준으로 인덱스를 역으로 탐색하여 LIS를 만들어서 출력한다.
평균 Time : 664 ~ 800ms
 */

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int[][] arr = new int[1000000][2];
        int[] res = new int[1000000];
        int n = Integer.parseInt(in.readLine());
        StringBuilder result = new StringBuilder();

        int max = 0;
        st = new StringTokenizer(in.readLine());
        arr[0][1] = -1;
        for(int i=0; i<n; i++){
            arr[i][0] = Integer.parseInt(st.nextToken());
        }

        res[0] = 0;
        
        for(int i=1; i<n; i++){
            // 현재 값이 가장 긴 LIS의 마지막 값보다 큰 경우
            // 가장 긴 길이 + 1의 마지막 값에 해당하므로 인덱스를 가리키게 하고 저장
            if(arr[res[max]][0] < arr[i][0]){
                arr[i][1] = res[max];
                res[max+1] = i;
                max++;
            }// 그렇지 않은 경우는 이분탐색으로 길이가 몇에 해당하는지 확인
            else
                binarySearch(arr, res, i, max);
        }

        // 찾은 인덱스들을 바탕으로 LIS 배열을 만든다
        findResult(result, arr, res[max]);
        result.append("\n");
        System.out.println(max+1);
        System.out.println(result.toString());
    }

    public static void binarySearch(int[][] arr, int[] res, int idx, int n){
        int start = 0;
        int end = n;
        int mid;

        // 이분탐색
        while(start < end){
            mid = (start+end)/2;
            if(arr[res[mid]][0] > arr[idx][0]){
                end = mid;
            } else if(arr[res[mid]][0] < arr[idx][0]){
                start = mid+1;
            } else{
                // 같은 값을 찾았으면 그 값이 가리키는 인덱스를 저장
                // LIS현재 길이에서 -1 인 경우
                arr[idx][1] = arr[res[mid]][1];
                res[mid] = idx;
                return;
            }
        }

        // 같은 값을 찾지 못한 경우 ( end가 항상 마지막으로 탐색한 인덱스를 가리킴 )
            if (arr[res[end]][0] > arr[idx][0]) {
                // 마지막 탐색 값이 현재 값보다 크기 때문에 길이 -1인 값을 가리킴
                if(end != 0)
                    arr[idx][1] = res[end-1];
                else
                    // 0인 경우는 왼쪽으로 작은 수가 하나도 없다는 의미
                    arr[idx][1] = -1;

                res[end] = idx;
            } else {
                // 마지막 탐색 값이 현재 값보다 작기 때문에 해당 값을 가리키고
                // 길이 +1에 현재 값을 저장
                arr[idx][1] = res[end];
                res[end + 1] = idx;
            }

    }

    public static void findResult(StringBuilder result, int[][] arr, int idx){
        if(idx == -1){
            return;
        }

        findResult(result, arr, arr[idx][1]);
        result.append(arr[idx][0]);
        result.append(" ");
    }
}