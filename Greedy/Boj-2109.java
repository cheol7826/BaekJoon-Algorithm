/*
Gold 4
백준 2109 : 순회강연
기준 : 2s, 128MB

Time : 308ms
- 그리디 알고리즘 문제
* 시간복잡도 판단을 잘못해서 오래 걸린 문제
N=10000 --> O(N^2) 이 가능함

강연료 순으로 정렬한 뒤 P일보다 작은부분을 모두 체크해야 한다. ( 2일 이내는 1일차, 2일차에 가능 )
-> N부터 1까지 역순으로 체크한 뒤 중간에 들어갈 자리가 있으면 넣은 후 break
-> 이후 각 날짜에 들어있는 강연료들을 더해서 합을 구함

 */
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        Data[] arr = new Data[10001];
        int[] cnt = new int[10001];

        int n = Integer.parseInt(in.readLine());
        int d, p;

        for(int i=0; i<n; i++){
            st = new StringTokenizer(in.readLine());
            p = Integer.parseInt(st.nextToken());
            d = Integer.parseInt(st.nextToken());
            arr[i] = new Data(d, p);
        }

        Arrays.sort(arr, 0, n);

        for(int i=0; i<n; i++){
            for(int j=arr[i].d; j>0; j--){
                if(cnt[j] < arr[i].p){
                    cnt[j] = arr[i].p;
                    break;
                }
            }
        }

        int sum = 0;

        for(int i=0; i<10001; i++){
            sum += cnt[i];
        }

        System.out.println(sum);
    }
}

class Data implements Comparable<Data>{
    int d;
    int p;

    public Data(int d, int p) {
        this.d = d;
        this.p = p;
    }

    @Override
    public int compareTo(Data o) {
        if(p > o.p){
            return -1;
        } else if(p < o.p){
            return 1;
        } else{
            if(d > o.d){
                return 1;
            } else{
                return -1;
            }
        }
    }
}


