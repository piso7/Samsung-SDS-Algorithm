package BOJ;

import java.io.*;
import java.util.*;

/**
 * 
 * @author s_cheol.park
 * https://www.acmicpc.net/problem/7579
 * 앱 - DP
 * O(N * Sum of Cost)
 * 냅색스타일의 문제
 * DP[i][j] = i 번 앱까지 중, j 만큼의 비용을 사용하여 만들수 있는 가장 많은 메모리
 */

public class P_7579_앱 {

    public static void main(String[] args) throws NumberFormatException, IOException {
        StringTokenizer st;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int TotalCost = 0;
        int Memory [] = new int [N+1];
        int Cost [] = new int [N+1];

        // 입력
        st = new StringTokenizer(br.readLine(), " ");
        for(int i = 1 ; i <= N ; i++) {
            Memory[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine(), " ");
        for(int i = 1 ; i <= N ; i++) {
            Cost[i] = Integer.parseInt(st.nextToken());
            TotalCost += Cost[i];
        }
        int DP[][] = new int [N+1][TotalCost+1]; // DP[i][j] = i 번 앱까지 중, j 만큼의 비용을 사용하여 만들수 있는 가장 많은 메모리

        // 점화식을 이용한 계산
        int Answer = TotalCost+1;
        for(int i = 1 ; i <= N ; i++) {
            for(int j = 0 ; j <= TotalCost; j++) {
                DP[i][j] = Math.max(DP[i][j],DP[i-1][j]); // 기초값
                if(j - Cost[i] >= 0) {
                    DP[i][j] = Math.max(DP[i][j], DP[i - 1][j - Cost[i]] + Memory[i]);
                }
            }   
        }

        for(int i = 1 ; i <= TotalCost ; i++) {
            if(DP[N][i] >= M) {
                Answer = i;
                break;
            }
        }

        // 정답찾기
        bw.write(Answer + "\n");
        bw.flush();
        bw.close();

    }
}
