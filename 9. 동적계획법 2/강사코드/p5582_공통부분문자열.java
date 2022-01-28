package BOJ;

import java.io.*;

/**
 * 
 * @author s_cheol.park
 * https://www.acmicpc.net/problem/5582
 * 공통 부분 문자열 - DP
 * O(문자열1번의 길이 * 문자열2번의 길이)
 * DP[i][j] = 1번문자열을 i 까지 쓰고, 2번문자열을 j 까지 쓸때의 LCS(공통부분문자열) 의 최대 길이
 */

public class P_5582_공통부분문자열 {

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String A = br.readLine();
        String B = br.readLine();   
        int lengthA = A.length();   
        int lengthB = B.length();
        int DP [][] = new int [lengthA+1][lengthB+1];
        int Answer = 0;
        // 점화식을 이용한 LCS 길이 계산
        for(int i = 1 ; i <= lengthA ; i++) {
            for(int j = 1 ; j <= lengthB; j++) {
                if(A.charAt(i - 1) == B.charAt(j - 1)) {
                    DP[i][j] = Math.max(DP[i][j], DP[i - 1][j - 1] + 1);
                    Answer = Math.max(Answer, DP[i][j]);
                } 
            }   
        }

        bw.write(Answer + "\n");

        bw.flush();
        bw.close();

    }
}
