/*
Gold 2
백준 12100 : 2048(easy)
기준 : 1s, 512MB

Time : 220ms
- 재귀호출을 이용한 완전탐색 문제 + 구현, 시뮬레이션 문제
- 4^5번 안으로 완전탐색이 가능하다
- 특정 방향으로 움직일 때 이동 방향으로 같은 값이 있으면 한번만 합치고 그렇지 않으면 그냥 이동한다 (2048 게임)
- 일반적인 방식은 구현이 잘 되었으나 16 0 0 16 이나 0 0 16 16 16 0 8 8 과 같이 사이에 0이 존재하는 경우를 구현하기가 힘들었다
- 크게 3가지 방식으로 나누었다 ( 두개 포인터를 이용 )
1. 연속된 두 값이 0이 아니면서 같은 경우
- 이동 방향에 가장 가까우면서 비어있는 곳에 두배를 해서 저장

2. 다음값이 0이거나 현재값이 0인경우
- 다음값이 0이면 0이 아닐때 까지 포인터를 이동시킨 후 같은값이면 2배해서 저장 
다른 값인 경우 다음 포인터가 존재하면 값을 저장하지 않고, 다음 포인터가 범위를 벗어나면 해당 값을 그냥 저장

- 현재값이 0이면 다음 포인터 값 상관 없이 다음 포인터로 저장하지 않고 이동

3. 0이 아니면서 값이 다른 경우
- 현재 값을 그냥 저장한 후 다음 포인터로 이동

평균 Time : 184 ~ 208ms 
 */

import java.io.*;
import java.util.*;

public class Main {
    public static int[][] next = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public static int max;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int[][] arr = new int[20][20];
        int n = Integer.parseInt(in.readLine());
        max = 0;

        for(int i=0; i<n; i++){
            st = new StringTokenizer(in.readLine());
            for(int j=0; j<n; j++){
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        func(arr, 0, n);
        System.out.println(max);

    }

    public static void func(int[][] arr, int depth, int n){
        // 5번 이동했으면 그때 값들 중 최댓값 찾고 반환
        if(depth == 5){
            calcMax(arr, n);
            return;
        }

        int[][] temp;

        // 상 하 좌 우 이동
        for(int i=0; i<4; i++){
            temp = new int[n][n];
            // 이동시키는 함수
            moveMap(arr, temp, n, i);
            // 다음 단계로 넘어감
            func(temp, depth+1, n);
        }
    }

    // 이동시키는 함수
    public static void moveMap(int[][] arr, int[][] temp, int n, int way){
        int x = 0, y = 0;
        int nx, ny, tx, ty, value;

        // 이동 방향에 따라 초기값을 설정해준다 ( ex 0번 방향이면 x = 1번, y = 2번방향으로 탐색 )
        switch (way){
            case 0:
                x = 0;
                y = 0;
                break;
            case 1:
                x = 0;
                y = n-1;
                break;
            case 2:
                x = n-1;
                y = n-1;
                break;
            case 3:
                x = n-1;
                y = 0;
                break;
        }
        // 이동 후 배열에 저장하는 부분
        // nx ny는 현재 배열의 포인터 tx ty는 저장할 배열의 포인터
        while(validCheck(x, y, n)){
            tx = x;
            ty = y;
            nx = x;
            ny = y;
            
            while(validCheck(nx, ny, n)){
                // 다음값이 범위를 벗어나지 않으면
                if(validCheck(nx+next[(way+2)%4][0], ny+next[(way+2)%4][1], n)){
                    // 현재값이 0이 아니고 다음값과 같으면
                    if((arr[nx][ny] != 0) && (arr[nx][ny] == arr[nx + next[(way+2)%4][0]][ny + next[(way+2)%4][1]])) {
                        // 저장할 위치에 2배하여 저장 후 2칸 뒤로 이동 ( 여기서 한칸 이동 후 맨 아래에서 1칸 더 이동 )
                        temp[tx][ty] = arr[nx][ny] * 2;
                        nx += next[(way + 2) % 4][0];
                        ny += next[(way + 2) % 4][1];
                    } 
                    // 현재값이 0이거나 다음값이 0인경우 (둘 중에 하나라도 )
                    else if((arr[nx][ny] == 0) || (arr[nx + next[(way+2)%4][0]][ny + next[(way+2)%4][1]] == 0)){
                        // 현재 값을 기억하고 포인터를 조정
                        value = arr[nx][ny];
                        nx += next[(way+2)%4][0];
                        ny += next[(way+2)%4][1];

                        // 현재 값이 0이면 위로 다시 올라가서 다음 포인터부터 진행
                        if(value == 0)
                            continue;

                        // 현재 값이 0이 아니면 다음 포인터에 0이 아닌 값이 있는지 확인
                        while(validCheck(nx, ny, n) && (arr[nx][ny] == 0)){
                            nx += next[(way+2)%4][0];
                            ny += next[(way+2)%4][1];
                        }

                        // 0이 아닌값이 있을 때
                        if(validCheck(nx, ny, n)){
                            //같으면 2배해서 저장
                            if(value == arr[nx][ny]){
                                temp[tx][ty] = value * 2;
                            }
                            // 다르면 기억했던 값을 저장 후 포인터를 한칸 앞으로 조정
                            // 맨 마지막에서 다음 포인터로 이동하기 때문에 현재값을 건너 뛰게 된다
                            else{
                                temp[tx][ty] = value;
                                nx += next[way][0];
                                ny += next[way][1];
                            }
                        }
                        // 범위를 벗어나는 경우
                        else{
                            temp[tx][ty] = value;
                        }
                    }
                    else{
                      temp[tx][ty] = arr[nx][ny];
                    }
                }
                // 다음값이 범위를 벗어나는 경우
                // 현재 값을 그냥 저장한다
                else{
                    temp[tx][ty] = arr[nx][ny];
                }

                tx += next[(way+2)%4][0];
                ty += next[(way+2)%4][1];
                nx += next[(way+2)%4][0];
                ny += next[(way+2)%4][1];
            }

            x += next[(way+1)%4][0];
            y += next[(way+1)%4][1];
        }
    }

    public static boolean validCheck(int x, int y, int n){
        return (x >= 0) && (x < n) && (y >= 0) && (y < n);
    }

    public static void calcMax(int[][] arr, int n){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(max < arr[i][j])
                    max = arr[i][j];
            }
        }

    }
}
