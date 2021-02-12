/*
Gold 3
백준 2933 : 미네랄
기준 : 1s, 128MB

Time : 292ms
- 구현, 시뮬레이션 문제
- 그래프를 이용한 구현문제로 미네랄이 없어졌을 때 그래프 탐색을 통해서 단일 그래프의 개수를 파악하고
바닥으로 떨어질 수 있는지 여부를 체크 후 떨어질 수 있으면 이동시키는 형태

- 입력받은대로 미네랄을 제거하고 dfs를 통해서 그래프 개수 탐색
- 그래프 탐색하면서 공중에 떠있는 경우 해당 점부터 낮아지는 방향으로 높이체크
- 그래프 탐색 후 최소 높이가 결정되면 해당 높이만큼 그래프를 이동시킨다.
평균 Time : 300 ~ 500ms
 */

import java.io.*;
import java.util.*;

public class Main {
    public static int[][] next = {{0, 1}, {-1, 0}, {0, -1}, {1, 0}};
    public static int min;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        StringBuilder result = new StringBuilder();
        int n, h;
        int[][] arr = new int[100][100];
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        String str;

        for(int i=r-1; i>=0; i--){
            str = in.readLine();
            for(int j=0; j<c; j++){
                if(str.charAt(j) == '.'){
                    arr[i][j] = 0;
                } else{
                    arr[i][j] = 1;
                }
            }
        }

        n = Integer.parseInt(in.readLine());

        st = new StringTokenizer(in.readLine());
		
		// 왼쪽, 오른쪽에서 던지는 막대
        for(int i=0; i<n; i++){
            h = Integer.parseInt(st.nextToken()) -1;

            if(i % 2 == 0){
                for(int j=0; j<c; j++){
                    if(arr[h][j] == 1){
                        arr[h][j] = 0;
                        break;
                    }
                }
            } else{
                for(int j=c-1; j>=0; j--){
                    if(arr[h][j] == 1){
                        arr[h][j] = 0;
                        break;
                    }
                }
            }
			// 한번 던진 후 클러스터가 발생하는지 체크함
            movingCluster(arr, r, c);
        }

        for(int i=r-1; i>=0; i--){
            for(int j=0; j<c; j++){
                if(arr[i][j] == 0)
                    result.append(".");
                else
                    result.append("x");
            }
            result.append("\n");
        }

        System.out.println(result.toString());
    }

    public static void movingCluster(int[][] arr, int r, int c){
        int[][] visit = new int[r][c];
		// 문제에서 2개 이상이 이동하는 경우가 없기 때문에 그냥 Point 변수로 해도 된다.
        ArrayList<Point> temp = new ArrayList<>();
        int cnt = 1;


		// 그래프 개수 체크 + 클러스터 이동가능 높이 체크
        for(int i=0; i<r; i++){
            for(int j=0; j<c; j++){
                if((arr[i][j] == 1) && (visit[i][j] == 0)){
                    min = 3000;
                    visit[i][j] = cnt;
                    dfs(arr, visit, r, c, i, j, cnt);
                    cnt++;

                    if(min != 0){
                        temp.add(new Point(i, j, min));
                    }
                }
            }
        }

		// 클러스터 이동
        int val;
        for (Point point : temp) {
            val = visit[point.x][point.y];
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    if (visit[i][j] == val){
                        arr[i-point.m][j] = 1;
                        arr[i][j] = 0;
                    }
                }
            }
        }

    }

    public static void dfs(int[][] arr, int[][] visit, int r, int c, int i, int j, int cnt){
        int x, y;
		// 높이가 0이면 이동할 필요가 없으므로 진입 x
        if((i != 0) && (min != 0)) {
			// 현재 점부터 수직아래방향으로만 체크
            for (int t = i - 1; t >= 0; t--) {
				// 값이 같으면 -> 같은 그래프에 있으므로 더이상 볼 필요가 없다
                if(visit[t][j] == visit[i][j])
                    break;

				// 같은 값 체크는 위에서 하기 때문에 빼도 된다
				// 0이 아니고 이동가능 높이가 최소이면 값을 저장하고 break
                if (((visit[t][j] != 0) && (visit[t][j] != visit[i][j])) && (min > i - t -1)) {
                    min = i - t - 1;
                    break;
                }
				
				// 아래방향으로 이동하면서 높이가 0이 된 경우는 시작지점부터 바닥까지 이동할 수 있다는 의미
                if((t == 0) && (visit[t][j] == 0) && (min > i)){
                    min = i;
                }
            }
        } else{
            min = 0;
        }

		// dfs 그래프 탐색
        for(int k=0; k<4; k++){
            x = i + next[k][0];
            y = j + next[k][1];

            if(validCheck(r, c, x, y))
                continue;

            if((arr[x][y] == 1) && (visit[x][y] == 0)){
                visit[x][y] = cnt;
                dfs(arr, visit, r, c, x, y, cnt);
            }
        }
    }

    public static boolean validCheck(int r, int c, int x, int y){
        return (x < 0) || (x >= r) || (y < 0) || (y >= c);
    }
}

class Point{
    int x;
    int y;
    int m;

    public Point(int x, int y, int m) {
        this.x = x;
        this.y = y;
        this.m = m;
    }
}