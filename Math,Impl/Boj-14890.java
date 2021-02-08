/*
Gold 3
백준 14890 : 경사로
기준 : 2s, 512MB

Time : 164ms
- 구현, 시뮬레이션 문제
- 주어진 경사로의 길이에 맞춰서 경사로를 놓아 길을 완성하는 문제
- row우선탐색, column우선탐색 두가지를 두어 이중 for문 2개로 구현 ( 로직은 똑같고 방향만 다르다 )
- 3가지로 나누어서 체크 ( 높아지는 경우, 평평한 경우, 낮아지는 경우 )
1. 평평한 경우 : 높아지는 경우를 대비하여 이동한 거리를 계산하면서 이동
2. 높아지는 경우 : (1)에서 계산해 온 거리를 바탕으로 높아지는 부분이 등장했을 때 거리가 충분한 지 확인한 후 이동
--> 거리가 충분하지 않으면 break로 탈출하여 다음 줄 탐색
3. 낮아지는 경우( 이 부분이 살짝 까다로웠다 ) : 경사로가 1칸일때와 2칸이상 인 경우를 나누어서 봄
- 1칸인 경우는 연속으로 내려가는 부분이 나오면 계속 놓을 수 있다
- 2칸이상은 내려가는 부분을 기준으로 더 탐색하여 놓을 수 있는지 체크해야 한다 ( 평지 탐색하는 부분에 놓을 수 있는지 체크 )
- 거리를 valid로 두었고, 3번 경우를 분리하기 위해 flag를 두었다.
평균 Time : 144 ~ 168ms
 */

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        int[][] arr = new int[100][100];

        int n = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());
        int count = 0;
        int valid;
        int flag;

        for(int i=0; i<n; i++){
            st = new StringTokenizer(in.readLine());
            for(int j=0; j<n; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // row 우선 탐색
        for(int i=0; i<n; i++){
            valid = 1;
            flag = 0;
            for(int j=0; j<n-1; j++){
                // 평지인 경우
                if(arr[i][j] == arr[i][j+1]){
                    // 이동거리를 늘리고
                    valid++;

                    // 낮아지는 부분 탐색 후에 경사로를 놓을 수 있는 경우
                    // j+1부분까지 경사로를 두기 때문에 valid를 0으로 초기화
                    if((flag == 1)&& (valid >= l)){
                        flag = 0;
                        valid = 0;
                    }
                // 높아지는 경우    
                } else if(arr[i][j] < arr[i][j+1]){
                    // 두칸 이상 차이나면 break
                    if(arr[i][j] + 1 != arr[i][j+1])
                        break;

                    // 이동거리가 충분하지 않은 경우 break
                    if((valid < l) || (flag == 1))
                        break;

                    // j+1부터 다시 놓을수 있으므로 1로 초기화
                    valid = 1;
                    
                // 낮아지는 경우    
                } else{
                    // 2칸 이상 낮아지는 경우 break;
                    if(arr[i][j] -1 != arr[i][j+1])
                        break;

                    // 이전에 낮아지는 부분을 만나서 경사로를 탐색하던 도중 
                    // 다시 내리막을 만났으므로 이동불가능 판정
                    if(flag == 1)
                        break;
                    
                    // 낮아지는 부분 탐색 상태
                    flag = 1;
                    valid = 1; // j+1부터 바로 놓을 수 있기 때문
                    // 경사로가 1인 경우는 바로 낮아지는 경우에 대해서 놓을 수 있으므로
                    // flag를 0으로 바꾸고 j+1에 바로 놓기가 가능하기 때문에 valid도 1로 함
                    if(l == 1){
                        flag = 0;
                        valid = 0;
                    }
                }

                // 다음 칸이 도착칸일때
                if(j == n-2) {
                    // 도착지점까지 포함하여 경사로를 놓는 경우
                    if((flag == 1) && (valid >= l))
                        count++;
                    // 그 외는 경사로를 이미 놓기때문에 그냥 추가해주면 된다.
                    else if((flag == 0))
                        count++;
                }
            }
        }

        // column 우선 탐색 ( row와 동일한 로직을 사용 )
        for(int i=0; i<n; i++){
            valid = 1;
            flag = 0;
            for(int j=0; j<n-1; j++){
                if(arr[j][i] == arr[j+1][i]){
                    valid++;

                    if((flag == 1) && (valid >= l)){
                        flag = 0;
                        valid = 0;
                    }
                } else if(arr[j][i] < arr[j+1][i]){
                    if(arr[j][i] + 1 != arr[j+1][i])
                        break;

                    if((valid < l) || (flag == 1))
                        break;

                    valid = 1;
                } else{
                    if(arr[j][i] -1 != arr[j+1][i])
                        break;

                    if(flag == 1)
                        break;
                    flag = 1;
                    valid = 1;
                    if(l == 1){
                        flag = 0;
                        valid = 0;
                    }
                }

                if(j == n-2) {
                    if((flag == 1) && (valid >= l))
                        count++;
                    else if(flag == 0)
                        count++;
                }
            }
        }

        System.out.println(count);
    }
}