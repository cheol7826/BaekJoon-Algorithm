/*
Platinum 5
백준 6549 : 히스토그램에서 가장 큰 직사각형
기준 : 1s, 256MB

Time : 648ms
- 스택 문제
- 입력 순서대로 연산 후 스택에 넣으면서 최대 높이를 계산한다
- 스택에 존재하는 값보다 큰 값이 들어온 경우 : top에 새로 추가하며 count를 1로 초기화한다.
- 작은 값이 들어오는 경우 : 스택의 값이 작거나 같은 수가 나올때 까지 pop한다
--> 이 때 스택에는 무조건 오름차순으로 저장되기 때문에 역순으로 count를 세고
현재 위치한 높이에서 (count + 그 위치에서의 count) 를 곱하여 넓이를 계산한다
--> 그 위치의 count를 더해서 곱하는 이유는 이전에 특정 높이보다 큰값이 있던 경우 해당 높이 count에 포함되어 있기 때문

- 반복 후 작거나 같은 값이 나온경우
작은 값 : 작은 값은 그대로 push하고 입력받은 높이의 count+1 하여 저장한다. ( 입력받은 값 포함, 작은 값 포함 안함 )
같은 값 : 같은 값은 입력받은 높이의 count + 1을 같은값에 이어서 더한다. 

--> 처음에는 pop해서 사라진 부분에 대한 갯수를 고려하지 못해서 막혔다.
- count를 스택에 같이 저장함으로써 중간중간 pop된 직사각형들의 개수를 기억하면서 구현하였다.

평균 Time : 500 ~ 630ms
- 세그먼트 트리, 분할 정복 등의 다른 여러가지 방법들이 존재하는 것 같다.
 */

import java.io.*;
import java.util.*;

public class Main {
    public static long max;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder result = new StringBuilder();
        Stack<Value> stack;
        int n, cnt;
        long val, calc;
        Value temp;

        while(true){
            stack = new Stack<>();
            st = new StringTokenizer(in.readLine());
            max = 0;
            n = Integer.parseInt(st.nextToken());

            if(n == 0)
                break;

            for(int i=0; i<n; i++) {
                cnt = 0;
                val = Integer.parseInt(st.nextToken());
                
                // 제일 처음 사각형 입력
                if(i == 0){
                    stack.push(new Value(val, val, 1));
                    continue;
                }

                // 두번째 부터 스택확인 연산 필요
                while(!stack.isEmpty()){
                    // 값 확인을 위해 스택에서 하나를 pop
                    temp = stack.pop();

                    // 크거나 같은 값이 들어온 경우 ( 작은값에서 반복한 뒤 나오는 경우도 포함 )
                    if(val >= temp.height){
                        // 큰 값의 경우 temp는 그대로 push하고
                        if(val != temp.height) {
                            stack.push(temp);
                            
                        // cnt는 처음 큰값을 받으면 0이고 작은값에서 탐색하다 온 경우는 0이 아님    
                            stack.push(new Value(val, val * (cnt + 1), cnt + 1));
                        } else{
                            // 같은 값의 경우는 그 값에 더해서 push
                            stack.push(new Value(val, temp.total + val * (cnt+1), temp.count + cnt + 1));
                        }
                        break;
                    }
                    // 작은 값이 들어온 경우
                    else {
                        // 스택 값이 더 큰것이므로 높이를 계산한 뒤 pop한다.
                        calc = temp.total + temp.height * cnt;

                        if(max < calc)
                            max = calc;

                        // 확인하기 위한 값이 스택의 마지막이였으면 맨 처음 if문으로 갈 수 없기 때문에
                        // 여기서 추가한다
                        if(stack.empty()){
                            stack.push(new Value(val, val * (temp.count + cnt+1), temp.count + cnt+1));
                            break;
                        }
                        
                        cnt += temp.count;
                    }
                }

            }

            // 맨 마지막까지 한 뒤 스택에 값이 남은 경우 
            // 높이들을 계산
            cnt = 0;
            while(!stack.isEmpty()){
                temp = stack.pop();

                if(max < temp.total + temp.height * cnt)
                    max = temp.total + temp.height * cnt;

                cnt += temp.count;

            }


            result.append(max);
            result.append("\n");
        }

        System.out.println(result.toString());
    }
}

class Value{
    long height;
    long total;
    long count;

    public Value(long height, long total, long count) {
        this.height = height;
        this.total = total;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Value{" +
                "height=" + height +
                ", total=" + total +
                ", count=" + count +
                '}';
    }
}