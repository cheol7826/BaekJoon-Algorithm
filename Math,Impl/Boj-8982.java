/*
Gold 3
백준 8982 : 수족관 1
기준 : 1s, 128MB

Time : 216ms ( java 8 기준 : 176ms )
- 구현 문제
- 수족관 최대 넓이가 40000 * 40000까지 가능한데 2차원배열을 쓰면 메모리 초과 발생
- 1차원 배열로 수족관의 높이를 저장한 뒤 또다른 1차원 배열을 통해서 현재 물의 높이를 저장하는 배열 사용
--> 공간복잡도 O(n)

- 구멍에서 왼쪽과 오른쪽을 탐색하면서 물이 빠지는 높이를 계산
--> 중간에 물의 높이가 0인경우 ( 전부 다 빠진경우 )는 해당 높이 이후로는 더이상 뺄 물이 없는 경우로 탐색 중지
( 위 부분을 해주지 않아서 1200 ~ 1600ms 정도로 시간이 오래 걸렸다 )

평균 Time : 148 ~ 400ms ( java 8 )
 */
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int x, y, last = 0, a, b, c, height;
        int[] arr = new int[40001];
        int[] water = new int[40001];
        int sum = 0;

        int n = Integer.parseInt(in.readLine());

        for(int i=0; i<n; i++){
            st = new StringTokenizer(in.readLine());
            x = Integer.parseInt(st.nextToken());
            y = Integer.parseInt(st.nextToken());

            arr[x] = y;
            if(last != x){
                for(int j=last+1; j<x; j++)
                    arr[j] = y;
            }
            last = x;
        }

        for(int i=0; i<last; i++) {
            water[i] = arr[i];
        }

        int k = Integer.parseInt(in.readLine());

        for(int i=0; i<k; i++){
            st = new StringTokenizer(in.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());

            height = b;

            for(int j=a; j<c; j++)
                water[j] = 0;

            for(int j=a-1; j>=0; j--){
                if(water[j] == 0)
                    break;
                if (height > arr[j]) {
                    height = arr[j];
                }
                if(arr[j] == water[j]){
                    water[j] -= height;
                } else{
                    if(arr[j] - water[j] < height)
                    water[j] -= height - (arr[j] - water[j]);
                }
            }

            height = b;

            for(int j=c; j<last; j++){
                if(water[j] == 0)
                    break;
                if (height > arr[j]) {
                    height = arr[j];
                }

                if(arr[j] == water[j]){
                    water[j] -= height;
                } else{
                    if(arr[j] - water[j] < height)
                        water[j] -= height - (arr[j] - water[j]);
                }
            }
        }

        for(int i=0; i<last; i++){
            sum += water[i];
        }
        System.out.println(sum);
    }
}


