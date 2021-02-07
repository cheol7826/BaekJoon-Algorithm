/*
Gold 5
백준 14499 : 주사위 굴리기
기준 : 2s, 512MB

Time : 140ms
- 구현, 시뮬레이션 문제
- 주사위의 값을 저장하는 공간을 n+2, m+2, 2 크기의 3차원 배열로 생성
- 현재 주사위가 위치한 지점에서 4방향으로 이동할 때 그 위치에서의 6면을 기록해주는 형태
- 출력은 해당지점의 윗면 ( 즉 x,y,0 지점을 출력 )
- 굴러가는 방향의 왼, 오른쪽은 변화가 없기 때문에 상, 하 부분만 잘 옮겨주면 된다.

평균 Time : 140 ~ 180ms
- 다른분들은 주사위가 이동하는 것이 아닌 값이 이동하는 형태로 구현
- 전개도 배열을 만들어서 각 면의 번호를 부여한 후 이동시키는 형태
( 복잡한 연산 필요없이 각 이동방향의 왼, 오른쪽을 제외한 4개의 면만 바꾸는 방식이다 )
- 숫자를 바꾼 뒤 바닥이 0인지만 확인 후 값만 변경
 */

import java.io.*;
import java.util.*;

public class Main {
    public static int[][] next = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder result = new StringBuilder();
        int[][] arr = new int[20][20];
        int mov;
        int[][][] dice = new int[22][22][2];

        st = new StringTokenizer(in.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        for(int i=0; i<n; i++){
            st = new StringTokenizer(in.readLine());
            for(int j=0; j<m; j++){
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int tx, ty, temp;
        st = new StringTokenizer(in.readLine());

        // 주사위 시작 지점이 0이 아니면 바닥면에 기록
        if(arr[x][y] != 0){
            dice[x+1][y+1][1] = arr[x][y];
        }

        for(int i=0; i<k; i++){
            mov = Integer.parseInt(st.nextToken()) - 1;

            tx = x + next[mov][0];
            ty = y + next[mov][1];

            if(validCheck(tx, ty, n, m))
                continue;

            // 현 주사위 위치의 뒷면 ( 상 부분 ) 을 temp에 저장 ( 값을 스왑해주어야 하기 때문 )
            temp = dice[x+1-next[mov][0]][y+1-next[mov][1]][0];
            // 그릐고 뒷면에 현 위치의 바닥면을 저장
            dice[x+1-next[mov][0]][y+1-next[mov][1]][0] = dice[x+1][y+1][1];
            // 바닥면에는 앞면을 저장
            dice[x+1][y+1][1] = dice[tx+1][ty+1][0];

            // 바닥면이 0인지 체크
            if(arr[tx][ty] == 0){
                arr[tx][ty] = dice[tx +1][ty +1][0];
                dice[tx+1][ty+1][1] = arr[tx][ty];
            } else{
                dice[tx +1][ty +1][1] = arr[tx][ty];
                arr[tx][ty] = 0;
            }

            // 현 위치의 윗면은 다음 위치에서 앞면에 위치하기 때문에 미리 기록 후
            dice[tx+1+next[mov][0]][ty+1+next[mov][1]][0] = dice[x+1][y+1][0];

            // 저장하지 않은 부분들을 기록한다
            // 왼쪽, 오른쪽, 윗면, 뒷면만 기록하면 된다 ( 바닥과 앞면은 위에서 기록함 )
            // 현재 위치에서 가는방향을 제외한 3면은 다음 위치에서의 면과 동일하기 때문에 ( 왼, 오는 그대로 / 뒷면은 위에서 변경 ) 그대로 기록
            // 가는 방향은 위에서 저장한 temp를 기록 ( 현 위치의 뒷면이 다음 위치에서 윗면이므로 )
            for(int j=0; j<4; j++){
                if(j == mov){
                    dice[tx+1][ty+1][0] = temp;
                } else{
                    dice[tx+1+next[j][0]][ty+1+next[j][1]][0] = dice[x+1+next[j][0]][y+1+next[j][1]][0];
                }
            }

            result.append(dice[tx+1][ty+1][0]);
            result.append("\n");

            x = tx;
            y = ty;
        }

        System.out.println(result);
    }

    public static boolean validCheck(int x, int y, int n, int m){
        return (x < 0) || (x >= n) || (y < 0) || (y >= m);
    }
}