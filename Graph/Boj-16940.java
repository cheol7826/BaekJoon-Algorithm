/**
 * Gold 4
 * 백준 16940 : BFS 스페셜 저지
 * 기준 : 2s 512MB
 *
 * Time : 836ms
 * BFS문제
 * 올바른 경로 탐색과정 중 인접 리스트에 노드가 포함되어있는지 검사하는 과정에서 시간초과 발생
 * 원인 : 경로가 포함되어 있는지 확인하는 과정에서 배열을 O(n)으로 탐색해버림
 * --> 45%에서 시간초과 : 특정 노드에 몰려있는 경우 한 노드에 2만개 이상이 몰려도 4초이상이 걸린다
 * 해결 : HashSet을 이용해서 노드들을 담은 다음 빠르게 탐색하였음 (중요)
 */


import java.io.*;
import java.util.*;

public class Main {
    public static ArrayList<ArrayList<Integer>> arr;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(in.readLine());
        arr = new ArrayList<>();

        for (int i = 0; i < n+1; i++) {
            arr.add(new ArrayList<>());
        }

        int x, y;
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(in.readLine());
            x = Integer.parseInt(st.nextToken());
            y = Integer.parseInt(st.nextToken());

            arr.get(x).add(y);
            arr.get(y).add(x);
        }

        int[] d = new int[100001];
        st = new StringTokenizer(in.readLine());
        for (int i = 0; i < n; i++) {
            d[i] = Integer.parseInt(st.nextToken());
        }

        if(d[0] != 1){
            System.out.println(0);
        } else {
            if(bfs(d, n)){
                System.out.println(1);
            } else{
                System.out.println(0);
            }
        }
    }

    public static boolean bfs(int[] d, int n){
        Queue<Integer> q = new LinkedList<>();
        boolean[] visit = new boolean[n+1];
        Set<Integer> set = new HashSet<>();
        q.offer(1);
        visit[1] = true;
        Integer temp;
        int idx = 1;
        while(!q.isEmpty()){
            set.clear();
            temp = q.poll();

            for(Integer k : arr.get(temp)){
                if(visit[k])
                    continue;

                set.add(k);
                visit[k] = true;
            }

            int size = set.size();

            for(int k=idx; k<idx+size; k++){
                if(set.contains(d[k])){
                    q.offer(d[k]);
                } else{
                    return false;
                }
            }

            idx += size;
        }

        return true;
    }
}


