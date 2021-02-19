/*
Gold 1
백준 3015 : 오아시스 결합
기준 : 1s, 256MB

Time : 488ms ( java 8 : 456ms )
- 자료구조 (스택) 문제
- 입력마다 스택의 값과 비교하면서 가능한 경우를 카운팅하는 방식
- Value라는 클래스를 이용 ( height = 키, count = 같은 키에 해당하는 사람의 수 )
- 1. 스택의 값보다 낮은 값이 들어오면 결과값을 1 증가시키고 스택에 push
- 2. 같은 값이 들어오면 ( 스택에는 중복되는 값이 없도록 하였다 ) 그 때 count를 기억하고 스택에서 큰 값이 나올 때 까지 pop
--> 보통 같은 값의 다음 값이나, 스택이 비는 경우 2가지로 볼 수 있다.
- 3. 높은 값이 들어오면 count는 기억하지 않고 낮은 값이 나올 때 까지 pop ( 중간에 같은 경우가 나올 수 있다 )
--> pop을 하면서 같지 않은 경우에는 1을, 같은 경우에는 그 때의 count를 더해서 누적한 뒤 결과 값에 증가시킨다
- 2, 3 진행 중 낮은 값이 나오면 1로 진행된다

평균 Time : 304 ~ 450ms ( java 8 기준 )
- 스택 라이브러리를 이용하지 않고 배열을 이용해서 푼 경우 300ms 대의 시간이 걸린 듯하다
- 라이브러리와, 클래스를 이용해서 시간이 좀 더 걸린 듯 하다.
 */

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Stack<Value> stack = new Stack<>();
        int n = Integer.parseInt(in.readLine());
        int k;
        long cnt;
        long result = 0;
        Value temp;

        for(int i=0; i<n; i++){
            cnt = 0;
            k = Integer.parseInt(in.readLine());

            if(i == 0){
                stack.push(new Value(k, 1));
                continue;
            }
            temp = stack.pop();

            if(temp.height > k){
                stack.push(temp);
                stack.push(new Value(k, 1));
                result++;
            } else{
                while((temp.height <= k)){
                    result += temp.count;

                    if(temp.height == k)
                        cnt = temp.count;

                    if(!stack.isEmpty())
                        temp = stack.pop();
                    else
                        break;
                }

                if(temp.height > k){
                    stack.push(temp);
                    result++;
                }

                stack.push(new Value(k, cnt+1));
            }

        }

        System.out.println(result);

    }
}

class Value{
    int height;
    long count;

    public Value(int height, long count) {
        this.height = height;
        this.count = count;
    }
}
