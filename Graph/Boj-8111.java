/*
Platinum 5
백준 8111 : 0과 1
기준 : 1s, 128MB

Time : 160ms ( java 8 : 116ms )
- BFS를 이용하는 그래프 문제
- 처음에는 수학적 접근으로 각 자릿수 ( 1, 10, 100, .. ) 에 해당하는 나머지들을 구해서 접근하려 했으나
--> 이 경우 조합을 찾는 데 O(n!)이 걸린다.
- DFS방식으로 접근했는데 이 경우에도 자릿수가 많아지는 경우 TLE가 발생하였다.
- BFS방식으로 나머지가 0일때 리턴하고 그 외 방문하지 않은 나머지의 경우 큐에 넣어서 bfs를 진행
평균 Time : 84 ~ 120ms
 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedWriter out;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        out = new BufferedWriter(new OutputStreamWriter(System.out));
        int[] visit;
        int T = Integer.parseInt(in.readLine());
        int n;

        for (int i = 0; i < T; i++) {
            n = Integer.parseInt(in.readLine());

            visit = new int[20001];
            bfs(visit, n);
        }

        out.flush();
    }

    public static void bfs(int[] visit, int n) throws IOException{
        Number temp;
        int num;
        Queue<Number> q = new LinkedList<>();
        q.add(new Number(1, "1"));
        visit[1] = 1;

        while(!q.isEmpty()){
            temp = q.poll();
            num = temp.num;

            if(num == 0){
                out.write(temp.output + "\n");
                return;
            }

            if(visit[(num * 10) % n] == 0){
                visit[(num*10) % n] = 1;
                q.add(new Number(((num *10) % n), (temp.output + "0")));
            }

            if(visit[(num * 10 + 1) % n] == 0){
                visit[(num*10+1) % n] = 1;
                q.add(new Number(((num *10+1) % n), (temp.output + "1")));
            }
        }

        out.write("BRAK\n");
    }
}

class Number{
    int num;
    String output;

    Number(int num, String output){
        this.num = num;
        this.output = output;
    }
}