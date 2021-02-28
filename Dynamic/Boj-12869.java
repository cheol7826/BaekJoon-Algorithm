/*
Gold 4
백준 12869 : 뮤탈리스크
기준 : 2s, 512MB

Time : 136ms
- 다이나믹 프로그래밍 문제 ( DFS + DP )
- dp기법인 메모제이션을 이용해서 이미 방문한 부분은 다시 가지않고 최소인 경우는 방문하도록 함
- 꼭 점화식을 이용해서 bottom up 방식만이 dp가 아니다 ( *** )
- 입력받은 세 값을 시작으로 0 0 0 까지 진행한다.
- 체력이 소모되는 경우의 수를 적용하여 재귀호출을 한다. 여기에서 재방문 여부를 체크하지 않으면 시간 초과가 발생한다
- visit 배열을 두고 처음 방문했거나 방문한 값보다 더 낮은 값으로 방문하는 경우에만 함수를 호출한다
- 음수가 되는 경우에는 0으로 바꾸어 주고 0, 0, 0이 된 경우 그 때의 공격 횟수 중 최솟값을 저장한다
평균 Time : 136 ~ 176ms
 */

import java.io.*;
import java.util.*;

public class Main {
    public static int min;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int[] arr = new int[3];
        int[][][] visit = new int[61][61][61];
        min = 1000000;
        int n = Integer.parseInt(in.readLine());
        st = new StringTokenizer(in.readLine());
        for(int i=0; i<n; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        scv(visit, arr[0], arr[1], arr[2], 0);

        System.out.println(min);
    }

    public static void scv(int[][][] dp, int a, int b, int c, int cnt){
        a = Math.max(a, 0);
        b = Math.max(b, 0);
        c = Math.max(c, 0);


        if((a == 0) && (b == 0) && (c == 0)){
            if(min > cnt)
                min = cnt;
            return;
        }

        if((dp[a][b][c] != 0) && (dp[a][b][c] <= cnt))
            return;

        dp[a][b][c] = cnt;
        scv(dp, a - 9, b - 3, c - 1, cnt + 1);
        scv(dp, a - 9, b - 1, c - 3, cnt + 1);
        scv(dp, a - 3, b - 9, c - 1, cnt + 1);
        scv(dp, a - 3, b - 1, c - 9, cnt + 1);
        scv(dp, a - 1, b - 3, c - 9, cnt + 1);
        scv(dp, a - 1, b - 9, c - 3, cnt + 1);

    }
}
