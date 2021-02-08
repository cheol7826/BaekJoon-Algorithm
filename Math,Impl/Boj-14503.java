/*
Gold 5
백준 14503 : 로봇 청소기
기준 : 2s, 512MB

Time : 140ms
- 구현, 시뮬레이션 문제
- 문제에서 주어진 대로 왼쪽 방향을 탐색하도록 한다 ( mod 연산을 이용 )
- 갈 방향이 없으면 뒤로 후진하는데 청소한 구역을 1로해버리면 벽으로 인식하고 종료해버림
- 청소한 구역을 0, 1이 아닌 값으로 설정하여 청소한 구역으로 이동할 수 있도록 설정함
- 그 외 특별히 어려운 것은 없었음

평균 Time : 132 ~ 150ms
 */

import java.io.*;
import java.util.*;

public class Main {
    public static int[][] next = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        int[][] arr = new int[50][50];

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(in.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());

        for(int i=0; i<n; i++){
            st = new StringTokenizer(in.readLine());
            for(int j=0; j<m; j++){
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int k;
        int cnt = 0;
        while(true){
            if(arr[r][c] == 0){
                arr[r][c] = 2;
                cnt++;
            }

            for(k=0; k<4; k++){
                d = (d+3) % 4;
                if((arr[r+next[d][0]][c+next[d][1]] == 0)){
                    r += next[d][0];
                    c += next[d][1];
                    break;
                }
            }

            if(k != 4){
                continue;
            }

            if((arr[r-next[d][0]][c-next[d][1]] == 1))
                break;

            r -= next[d][0];
            c -= next[d][1];
        }

        System.out.println(cnt);
    }

}