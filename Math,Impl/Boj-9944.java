/*
Gold 3
백준 9944 : N*M보드 완성하기
기준 : 3s, 256MB

Time : 508ms
- 백트래킹을 이용한 구현문제
- DFS를 이용해서 .인 지점에서 이동하는 경로를 탐색 각 depth마다 모든 . 지점을 방문했는지 체크한다
- 전부 방문했다면 그 때의 depth를 최솟값과 비교하여 저장
- 더 이상 갈수 없는 곳인경우 백트래킹을 통해서 이전상태로 돌아간다

평균 Time : 388 ~ 628ms
 */
import java.io.*;
import java.util.*;

public class Main {
    public static int[][] next = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public static int min, n, m;
    public static boolean[][] visit;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder result = new StringBuilder();
        StringTokenizer st;
        String str, temp;
        int cnt = 1;

        while((str = in.readLine()) != null){
            st = new StringTokenizer(str);
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            min = 1000002;
            char[][] arr = new char[n][m];

            for(int i=0; i<n; i++){
                temp = in.readLine();
                for(int j=0; j<m; j++){
                    arr[i][j] = temp.charAt(j);
                }
            }

            for(int i=0; i<n; i++){
                for(int j=0; j<m; j++){
                    if(arr[i][j] != '*'){
                        visit = new boolean[n][m];
                        visit[i][j] = true;
                        findWay(arr, i, j, 0);
                    }
                }
            }

            if(min == 1000002)
                min = -1;

            result.append("Case ").append(cnt);
            result.append(": ").append(min).append("\n");
            cnt++;
        }

        System.out.println(result.toString());
    }

   public static void findWay(char[][] arr, int i, int j, int depth){
        int x, y;
        if(depth > 1000000)
            return;

        if(visitCheck(arr, visit)){
            if(min > depth)
                min = depth;

            return;
        }

        for(int k=0; k<4; k++){
            x = i + next[k][0];
            y = j + next[k][1];

            if(!validCheck(x, y) || visit[x][y] || (arr[x][y] == '*'))
                continue;

            while(validCheck(x, y) && !visit[x][y] && (arr[x][y] != '*')){
                visit[x][y] = true;
                x += next[k][0];
                y += next[k][1];
            }

            x -= next[k][0];
            y -= next[k][1];

            findWay(arr, x, y, depth+1);

            while((x != i) || (y != j)){
                visit[x][y] = false;
                x -= next[k][0];
                y -= next[k][1];
            }
        }
   }

    public static boolean visitCheck(char[][] arr, boolean[][] v){
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if((arr[i][j] == '.') && !v[i][j])
                    return false;
            }
        }

        return true;
    }

    public static boolean validCheck(int x, int y){
        return (x >= 0) && (x < n) && (y >= 0) && (y < m);
    }
}


