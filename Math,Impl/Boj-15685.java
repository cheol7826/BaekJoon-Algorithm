/*
Gold 4
백준 15685 : 드래곤 커브
기준 : 1s, 512MB

Time : 152ms
- 구현, 시뮬레이션 문제
- 시작 좌표와 방향, 세대가 주어지면 함수 재귀호출을 통해서 드래곤 커브를 배열에 체크
- x, y 가 아닌 y, x로 탐색해야 함
- 0세대를 만들어서 함수에 전달한 후 end를 기준으로 90도 시계방향 ( 두 점의 차이를 y, x 라고 할 때
--> end y좌표에서 x를 빼고, x좌표에서 y를 더함 )
- 큐를 이용해서 원래 좌표와 시계방향으로 이동한 좌표를 같이 저장하고 다음 세대로 전달
- 마지막으로 100 * 100배열을 탐색하면서 정사각형 꼭지점이 전부 1이 되어있는지만 확인
평균 Time : 134 ~ 144ms
 */

import java.io.*;
import java.util.*;

public class Main {
    public static int[][] next = {{0, 1}, {-1, 0}, {0, -1}, {1, 0}};
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int[][] arr = new int[101][101];
        int x, y, d, g;
        int count = 0;
        int n = Integer.parseInt(in.readLine());
        Point startp, endp;
        Queue<Point> q;
        for(int i=0; i<n; i++){
            q = new LinkedList<>();
            st = new StringTokenizer(in.readLine());
            x = Integer.parseInt(st.nextToken());
            y = Integer.parseInt(st.nextToken());
            d = Integer.parseInt(st.nextToken());
            g = Integer.parseInt(st.nextToken());

            startp = new Point(y, x);
            endp = new Point(y + next[d][0], x + next[d][1]);
            arr[y][x] = 1;
            arr[endp.y][endp.x] = 1;
            q.add(startp);
            q.add(endp);

            makeCurve(arr, q, startp, endp, g, 0);
        }

        for(int i=0; i<100; i++){
            for(int j=0; j<100; j++){
                if((arr[i][j] != 0) && (arr[i+1][j] != 0) && (arr[i][j+1] != 0) && (arr[i+1][j+1] != 0))
                    count++;
            }
        }

        System.out.println(count);
    }

    public static void makeCurve(int[][] arr, Queue<Point> q, Point start, Point end, int g, int depth){
        if(depth == g)
            return;

        ArrayList<Point> list = new ArrayList<>();
        Point temp;
        Point res;
        Point nextend = start;
        int x, y;
        while(!q.isEmpty()){
            temp = q.poll();

            if((temp.x == end.x) && (temp.y == end.y)) {
                list.add(temp);
                continue;
            }

            x = end.x - temp.x;
            y = end.y - temp.y;
            arr[end.y-x][end.x+y] = 1;
            res = new Point(end.y - x, end.x + y);
            if((temp.x == start.x) && (temp.y == start.y))
                nextend = res;
            list.add(temp);
            list.add(res);
        }

        q.addAll(list);
        makeCurve(arr, q, start, nextend, g, depth+1);

    }
}

class Point{
    int x;
    int y;

    public Point(int y, int x) {
        this.x = x;
        this.y = y;
    }
}