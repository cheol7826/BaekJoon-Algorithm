/*
Gold 4
백준 9935 : 문자열 폭발
기준 : 2s, 128MB

Time : 720ms
- 스택을 이용하는 문제
- 폭발 대상이 아닌 문자열은 바로 builder에 append
- 폭발 대상 문자열의 첫번째가 나오면 스택에 넣고 다음 인덱스를 가리킴
- 다음 문자가 폭발 대상 문자열의 다음 인덱스에 해당하면 스택에 넣고
이후 첫번째 문자가 다시 나오면 해당 인덱스를 다른 스택에 넣고 기억한 뒤 새로 인덱싱을 시작
- 두개 다 해당하지 않으면 스택에 존재하는 문자열을 builder에 다 추가한 뒤 다시 반복
- 폭발 문자열이 완성되었으면 ( 맨 마지막 문자가 나오면 ) 해당 문자열을 스택에서 지우고 인덱스를 담은 스택에서 한개를 pop
하여 다시 반복함

평균 Time : 364 ~ 516ms
- 스택을 이용하기 보다는 문자열 연산을 이용해서 해결하였음
- 문자열을 builder에 append하고 폭발 문자열의 길이를 넘어가면 substring, delete 등을 이용해서 폭발 문자열 제거
 */

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String str = in.readLine();
        String boom = in.readLine();
        Stack<Character> stack = new Stack<>();
        Stack<Character> temp = new Stack<>();
        Stack<Integer> indexing = new Stack<>();
        StringBuilder result = new StringBuilder();
        char c;
        int idx = 0;

        for(int i=0; i<str.length(); i++){
            c = str.charAt(i);

            if((c == boom.charAt(0)) || (c == boom.charAt(idx))){
                stack.push(c);
                if(c == boom.charAt(0)){
                    if(idx != 0)
                        indexing.push(idx);
                    idx = 1;
                } else{
                    idx++;
                }
            } else{
                if(idx != 0){
                    while(!stack.isEmpty())
                        temp.push(stack.pop());

                    while(!temp.isEmpty())
                        result.append(temp.pop());

                    idx = 0;

                    while(!indexing.isEmpty())
                        indexing.pop();
                }
                result.append(c);
            }

            if(idx == boom.length()){
                while(idx > 0) {
                    stack.pop();
                    idx--;
                }

                if(!indexing.isEmpty()) {
                    idx = indexing.pop();
                }
            }
        }

        while(!stack.isEmpty()) {
            temp.push(stack.pop());
        }

        while(!temp.isEmpty()){
            result.append(temp.pop());
        }



        if(result.toString().equals("")){
            System.out.println("FRULA");
        } else{
            System.out.println(result.toString());
        }

    }
}