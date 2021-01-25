/*
Platinum 3
백준 2873 : 롤러코스터
기준 : 1s, 256MB

Time : 816ms
- 짝수 X 짝수 배열을 제외한 나머지 경우는 모든 칸을 방문할 수 있고 짝수 X 짝수는 한칸(짝, 홀 or 홀, 짝)을 제외한 나머지 전부를 방문할 수 있다.
- 일반탐색, 특수탐색의 두가지 경우로 나누어서 체크
( 짝 * 짝을 제외한 나머지는 전부 일반탐색, 짝*짝은 일반 + 특수탐색)
- 일반탐색
홀수행 : 진행방향의 왼쪽부터 시계방향
짝수행 : 진행방향의 오른쪽부터 반시계방향
으로 방문여부 체크

- 특수탐색
방문할 수 없는 부분을 제외하고 탐색 ( 해당 행의 -1 부분에 도착하면 동작 +1되면 일반탐색으로 전환 )
홀수행 : 무조건 진행방향의 오른쪽부터 반시계방향
짝수행 : 무조건 진행방향의 왼쪽부터 시계방향
으로 방문여부 체크

최대값을 구해야하기 때문에 짝,홀 or 홀, 짝중 가장 작은값을 방문 불가칸으로 지정
*/

import java.util.*;
import java.io.*;

public class Main{
	public static int[][] next = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
	public static char[] cnext = {'U', 'R', 'D', 'L'};
	public static BufferedWriter out;
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		out = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;
		int[][] arr = new int[1000][1000];
		int[][] visit = new int[1000][1000];
		st = new StringTokenizer(in.readLine());
		int r = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());
		int min = 100000;
		int reverse = 0;
		int tx = 0, ty = 0;
		int[] point = new int[2];
		int way = 1;
		
		for(int i=0; i<r; i++) {
			st = new StringTokenizer(in.readLine());
			for(int j=0; j<c; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
				if(((i % 2 == 1) && (j % 2 == 0)) || ((i % 2 == 0) && (j % 2 == 1))) {
					if(min > arr[i][j]) {
						min = arr[i][j];
						tx = i;
						ty = j;
					}
				}
			}
		}
		
		visit[0][0] = 1;
		visit[r-1][c-1] = 1;
		point[0] = 0;
		point[1] = 0; 
		if((r % 2 == 0) && (c % 2 == 0)) {
			visit[tx][ty] = 1;
			for(int i=0; i<r*c; i++) {
				if((point[0] == tx-1) || (point[0] == tx)) {
					way = specialMove(visit, r, c, point, way);
				} else {
					way = normalMove(visit, r, c, point, reverse, way);
				}
				
				if(way == -1)
					break;
			}
		}
		else {
			if(r % 2 == 1) {
				reverse = 0;
			} else {
				reverse = 1;
			}
			
			for(int i=0; i<r*c; i++) {
				way = normalMove(visit, r, c, point, reverse, way);
				if(way == -1)
					break;
			}
		}
		out.write("\n");
		out.flush();
		
	}
	
	public static int normalMove(int[][] visit, int r, int c, int[] point, int reverse, int way) throws IOException {
		int x, y, n;
		
		for(int i=0; i<4; i++) {
			if(point[reverse] % 2 == 0) {
				if(reverse == 0)
					n = (5 - i) % 4;
				else
					n = (6 - i) % 4;
			} else {
				if(reverse == 0)
					n = (3 + i) % 4;
				else
					n = i % 4;
			}
			
			x = point[0] + next[n][0];
			y = point[1] + next[n][1];
			
			if((x < 0) || (x >= r) || (y < 0) || (y >= c))
				continue;
			
			if(visit[x][y] == 0) {
				visit[x][y] = 1;
				point[0] = x;
				point[1] = y;
				out.write(cnext[n]);
				return n;
			}
		}
		if(point[0] == r-1) {
			out.write(cnext[1]);
		} else {
			out.write(cnext[2]);
		}
		return -1;
	}
	
	public static int specialMove(int[][] visit, int r, int c, int[] point, int way) throws IOException{
		int x, y, n;
		
		for(int i=0; i<4; i++) {
			if(point[0] % 2 == 0) {
				n = (way + 5 - i) % 4;
			} else {
				n = (way + 3 + i) % 4;
			}
			
			x = point[0] + next[n][0];
			y = point[1] + next[n][1];
			
			if((x < 0) || (x >= r) || (y < 0) || (y >= c))
				continue;
			
			if(visit[x][y] == 0) {
				visit[x][y] = 1;
				point[0] = x;
				point[1] = y;
				out.write(cnext[n]);
				return n;
			}
		}
		if(point[0] == r-1) {
			out.write(cnext[1]);
		} else {
			out.write(cnext[2]);
		}
		return -1;
	}
}
