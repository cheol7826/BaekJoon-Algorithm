/*
Gold 4
백준 16197 : 두 동전
기준 : 2s, 512MB

Time : 240ms
- 브루트 포스 + 그래프 탐색 문제
- 한 칸 이동할 때 두 동전이 같이 이동하므로 큐 2개를 사용하였음
- bfs를 이용해서 그래프 탐색 ==> 좌표, 이동횟수를 같이 체크해서 이동횟수가 10번을 넘어가면 탐색 X
- 모든 경우의 수를 탐색하여 동전이 한 개만 떨어지는 경우 체크 + 동전은 겹칠 수 없음을 체크해야 한다.

평균 Time : 132 ~ 260ms
- 200ms 이하로 나온 경우는 큐를 1개만 사용하였다.
 */

import java.io.*;
import java.util.*;

public class Main {
    public static Queue<Point> q1;
    public static Queue<Point> q2;
    public static int count;
    public static int[][] next = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        char[][] arr = new char[20][20];
        StringTokenizer st = new StringTokenizer(in.readLine());
        q1 = new LinkedList<>();
        q2 = new LinkedList<>();
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        count = -1;
        Point temp;
        boolean flag = false;
        String str;
        for(int i=0; i<n; i++){
            str = in.readLine();
            for(int j=0; j<m; j++){
                arr[i][j] = str.charAt(j);
                if(arr[i][j] == 'o'){
                    temp = new Point(i, j, 0);

                    if(flag){
                        q2.add(temp);
                    } else{
                        flag = true;
                        q1.add(temp);
                    }

                    arr[i][j] = '.';
                }
            }
        }

        bfs(arr, n, m);
        System.out.println(count);
    }

    public static void bfs(char[][] arr, int n, int m){
        Point t1;
        Point t2;
        int x1, x2, y1, y2;

        while(!q1.isEmpty()){
            t1 = q1.poll();
            t2 = q2.poll();

            if(t1.cnt == 10)
                continue;

            for(int i=0; i<4; i++){
                x1 = t1.x + next[i][0];
                x2 = t2.x + next[i][0];
                y1 = t1.y + next[i][1];
                y2 = t2.y + next[i][1];

                if(validCheck(x1, y1, n, m) && (validCheck(x2, y2, n, m))){
                    continue;
                } else if(validCheck(x1, y1, n ,m) || (validCheck(x2, y2, n , m))) {
                    count = t1.cnt+1;
                    return;
                }

                if((arr[x1][y1] == '#') && (arr[x2][y2] == '#'))
                    continue;
                else if((arr[x1][y1] == '#') && (x2 == t1.x) && (y2 == t1.y)){
                    continue;
                }
                else if((arr[x2][y2] == '#') && (x1 == t2.x) && (y1 == t2.y)){
                    continue;
                }

                if(arr[x1][y1] == '#'){
                    x1 = t1.x;
                    y1 = t1.y;
                }

                if(arr[x2][y2] == '#'){
                    x2 = t2.x;
                    y2 = t2.y;
                }

                q1.add(new Point(x1, y1, t1.cnt+1));
                q2.add(new Point(x2, y2, t2.cnt+1));
            }
        }
    }

    public static boolean validCheck(int x, int y, int n, int m){
        return (x < 0) || (x >= n) || (y < 0) || (y >= m);
    }
}

class Point{
    int x;
    int y;
    int cnt;

    public Point(int x, int y, int cnt) {
        this.x = x;
        this.y = y;
        this.cnt = cnt;
    }
}
