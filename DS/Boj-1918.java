/*
Gold 4
백준 1918 : 후위 표기식
기준 : 2s, 128MB

Time : 128ms
- 스택을 이용하는 문제
- 알파벳이 나오면 그대로 쓰고 연산자나 괄호에 대해서만 스택에 삽입
- 맨 처음 연산자의 경우 스택에 그냥 삽입
- 두번째 연산자 부터 스택에 가장 위에있는 연산자와 비교해서 수행
- ( 의 경우 스택에 상관없이 바로 삽입하고 )가 나오면 (가 나올때까지 스택에서 pop
- 그외는 우선순위에 맞춰서 스택연산
--> 높은 우선순위가 나온 경우 스택에 그냥 삽입
--> 낮은 우선순위가 나온 경우 스택에 있는 연산자를 pop 후 그 다음 연산자까지 pop한 뒤 낮은 우선순위를 push
( 스택에 있는 연산자가 더 높은 우선순위 -> 그 다음연산자는 + - 에 해당하는 연산자로 아래(동등한 연산자) 기능을 같이 수행 )
--> 동등한 연산자는 맨 위 연산자만 pop하고 동등한 연산자를 push
평균 Time : 124 ~ 132ms
 */

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String str = in.readLine();
        char c, temp;
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder();

        int idx = 0;

        while(idx < str.length()){
            c = str.charAt(idx);
            if(isAlpha(c)){
                result.append(c);
            } else{
                if(stack.isEmpty()){
                    stack.push(c);
                    idx++;
                    continue;
                }

                if(c == '('){
                    stack.push(c);
                } else if(c == ')'){
                    while(!stack.isEmpty()){
                        temp = stack.pop();
                        if(temp == '(')
                            break;

                        result.append(temp);
                    }
                } else{
                    temp = stack.pop();
                    if(temp == '(') {
                        stack.push(temp);
                    } else{
                        if(calcWeight(temp) < calcWeight(c)){
                            stack.push(temp);
                        } else{
                            result.append(temp);
                            if ((calcWeight(temp) != calcWeight(c)) && !stack.isEmpty()) {
                                    result.append(stack.pop());
                            }

                        }
                    }
                    stack.push(c);
                }
            }
            idx++;
        }

        while(!stack.isEmpty())
            result.append(stack.pop());

        System.out.println(result.toString());
    }

    public static int calcWeight(char c){
        if((c == '+') || (c == '-'))
            return 1;
        else
            return 2;
    }

    public static boolean isAlpha(char c){
        return (c >= 'A') && (c <= 'Z');
    }

}