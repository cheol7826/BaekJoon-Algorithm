/*
Gold 1
백준 9328 : 열쇠
기준 : 1s, 256MB

Time : 352ms (java 8 = 208ms)
- BFS를 이용하는 그래프 문제
- 비트마스킹을 이용해서 문과 문에 해당하는 키 존재 여부를 확인
( 배열 탐색, 비트마스킹 상황에 맞게 쓰도록 연습하기 ! )
- 키에 도착했을 때 해당 문으로 이동하는 과정 구현에서 어려움이 있었다. ( List를 이용해서 키를 매칭하여 큐에 바로 add )
- 방문한 문은 리스트에서 지워 재방문하지 않도록 해야 한다 ( 중복 계산 가능성 )
- 그 외 나머지는 일반 그래프 bfs와 비슷했다.

평균 Time : 150 ~ 180ms
 */
import java.io.*;
import java.util.*;

public class Main {
    public static int count;
    public static int[][] next = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        char[][] arr = new char[102][102];
        int[][] visit;
        int T = Integer.parseInt(in.readLine());
        ArrayList<Points> door;
        int h,w;
        String str = "";
        int key;

        for(int t=0; t<T; t++){
            count = 0;
            key = 0;
            st = new StringTokenizer(in.readLine());
            h = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());
            visit = new int[h+2][w+2];
            door = new ArrayList<>();
            for(int i=0; i<h+2; i++){
                if((i != 0) && (i != h+1))
                    str = in.readLine();
                for(int j=0; j<w+2; j++){
                    if((i == 0) || (i == h+1) || (j == 0) || (j == w+1))
                        arr[i][j] = '.';
                    else
                        arr[i][j] = str.charAt(j-1);

                    if(arr[i][j] == '*')
                        visit[i][j] = 1;
                }
            }

            str = in.readLine();
            if(str.charAt(0) != '0'){
                for(int k=0; k<str.length(); k++){
                    key |= 1 << (str.charAt(k) - 'a');
                }
            }

            bfs(arr, door, visit, h+2, w+2, key);
            out.write(count + "\n");
        }
        out.flush();
    }

    public static void bfs(char[][] arr, ArrayList<Points> door, int[][] visit, int h, int w, int key){
        Points temp;
        int x, y;
        Queue<Points> q = new LinkedList<>();
        q.add(new Points(0, 0));
        visit[0][0] = 1;

        while(!q.isEmpty()){
            temp = q.poll();
            door.remove(temp);

            for(int i=0; i<4; i++){
                x = temp.x + next[i][0];
                y = temp.y + next[i][1];

                if(validIndex(x, y, h, w))
                    continue;

                if(visit[x][y] == 0){
                    visit[x][y] = 1;
                    if(arr[x][y] == '$'){
                        count++;
                        q.add(new Points(x, y));
                    } else {
                        if (isUpper(arr[x][y])) {
                            if (isKey(key, (char) (arr[x][y] + 32))) {
                                q.add(new Points(x, y));
                            } else
                                door.add(new Points(x, y));
                        } else {
                            if (arr[x][y] != '.') {
                                q.add(new Points(x, y));
                                key = setKey(key, arr[x][y]);
                                for (int v = 0; v < door.size(); v++) {
                                    if (isKey(key, (char) (arr[door.get(v).x][door.get(v).y] + 32))) {
                                        q.add(new Points(door.get(v).x, door.get(v).y));
                                    }
                                }
                            } else {
                                q.add(new Points(x, y));
                            }
                        }
                    }
                }
            }

        }
    }

    public static int setKey(int key, char c){
        int val = 1 << (c - 'a');
        if((key & val) != val){
            key |= val;
        }

        return key;
    }

    public static boolean validIndex(int x, int y, int h, int w){
        return (x < 0) || (x >= h) || (y < 0) || (y >= w);
    }

    public static boolean isUpper(char c){
        return (c >= 'A') && (c <= 'Z');
    }

    public static boolean isKey(int key, char c){
        int val = 1 << (c - 'a');
        return (key & val) == val;
    }
}

class Points{
    int x;
    int y;

    Points(int x, int y){
        this.x = x;
        this.y = y;
    }
}