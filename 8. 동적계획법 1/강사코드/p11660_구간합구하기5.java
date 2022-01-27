package BOJ;

import java.io.*;
import java.util.*;

/**
 * 
 * @author s_cheol.park
 * https://www.acmicpc.net/problem/11660
 * 구간 합 구하기 5 - DP
 * 시간복잡도 O(N*N + M)
 */

public class P_11660_구간합구하기5 {

    public static void main(String[] args) throws NumberFormatException, IOException {
        StringTokenizer st;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        st = new StringTokenizer(br.readLine(), " ");

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int Number [][] = new int [N+1][N+1];
        int D [][] = new int [N+1][N+1];

        // 입력
        for(int i = 1 ; i <= N ; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for(int j = 1 ; j <= N ; j++) {
                Number[i][j] = Integer.parseInt(st.nextToken());    
            }
        }

        // 점화식을 이용한 계산
        // D 와 Number 를 같이 사용해도 무방함
        for(int i = 1 ; i <= N ; i++) {
            for(int j = 1 ; j <= N ; j++) {
                D[i][j] = Number[i][j] + D[i-1][j] + D[i][j-1] - D[i-1][j-1];
            }
        }

        // 정답찾기
        int x1, y1, x2, y2, Answer;
        for(int i = 1 ; i <= M ; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            x1 = Integer.parseInt(st.nextToken());  
            y1 = Integer.parseInt(st.nextToken());  
            x2 = Integer.parseInt(st.nextToken());  
            y2 = Integer.parseInt(st.nextToken());  
            Answer = D[x2][y2] - D[x1 - 1][y2] - D[x2][y1 - 1] + D[x1 - 1][y1 -1];
            bw.write(Answer + "\n");
        }
        bw.flush();
        bw.close();

    }
}
