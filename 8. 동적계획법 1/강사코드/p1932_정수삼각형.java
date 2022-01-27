
package BOJ;

import java.io.*;
import java.util.*;

/**
 * 
 * @author s_cheol.park
 * https://www.acmicpc.net/problem/1932
 * 정수 삼각형 
 * DP 로 풀이될수 있다.
 * i 층의 j 번째 숫자를 Number[i][j] 라 하고, i 층의 j 번째 숫자까지의 합을 D[i][j] 라 할때
 * D[i][j] = Number[i][j] + Max (D[i-1][j-1],D[i-1][j]) 의 점화식이 성립된다.
 */

public class P_1932_정수삼각형 {

    public static void main(String[] args) throws NumberFormatException, IOException {
        StringTokenizer st;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int Number [][] = new int [N+1+1][N+1+1];
        int D [][] = new int [N+1+1][N+1+1];
        int Answer = 0;

        // 입력
        for(int i = 1 ; i <= N ; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 1 ; j <= i ; j++) {
                Number[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 점화식을 이용한 계산
        for(int i = 1 ; i <= N ; i++) {
            for (int j = 1 ; j <= i ; j++) {
                D[i][j] = Number[i][j] + Integer.max(D[i-1][j-1],D[i-1][j]);
            }
        }

        // 정답찾기
        for(int i = 1 ; i <= N ; i++) {
            Answer = Integer.max(Answer,D[N][i]);
        }

        System.out.println(Answer);
    }

}
