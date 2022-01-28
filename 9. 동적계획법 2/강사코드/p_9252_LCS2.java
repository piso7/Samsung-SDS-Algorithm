package BOJ;

import java.io.*;

/**
 * 
 * @author s_cheol.park
 * https://www.acmicpc.net/problem/9252
 * 공통 부분 문자열 - DP
 * O(문자열1번의 길이 * 문자열2번의 길이)
 * DP[i][j] = 1번문자열을 i 까지 쓰고, 2번문자열을 j 까지 쓸때의 LCS(공통부분문자열) 의 최대 길이
 * Direction = 실제 LCS 를 찾아가기 위한 배열 (DP 갱신시에 어디서 온지를 정의한다.)
 */

public class P_9252_LCS2 {

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String A = br.readLine();
        String B = br.readLine();   
        int lengthA = A.length();   
        int lengthB = B.length();
        int DP [][] = new int [lengthA+1][lengthB+1];
        int Direction [][] = new int [lengthA+1][lengthB+1]; 

        final int fromA = 1; 
        final int fromB = 2; 
        final int equal = 3; 

        // 점화식을 이용한 LCS 길이 계산
        for(int i = 1 ; i <= lengthA ; i++) {
            for(int j = 1 ; j <= lengthB; j++) {
                if(DP[i-1][j] >= DP[i][j-1]) {
                    Direction[i][j] = fromA;
                    DP[i][j] = DP[i - 1][j];
                } else {
                    Direction[i][j] = fromB;
                    DP[i][j] = DP[i][j-1];
                }
                if(A.charAt(i - 1) == B.charAt(j - 1)) {
                    if (DP[i][j] < DP[i - 1][j - 1] + 1) {
                        DP[i][j] = DP[i - 1][j - 1] + 1;
                        Direction[i][j] = equal;
                    }
                } 
            }   
        }

        // LCS 역추적 , While 문으로 만들어도 무방함
        StringBuffer LCS = new StringBuffer();
        for(int i = lengthA, j = lengthB ; i >= 1 && j >= 1 ; ) {
            if(Direction[i][j] == fromA) {
                i--;
            } 
            if(Direction[i][j] == fromB) {
                j--;
            } 
            if(Direction[i][j] == equal) {
                LCS.append(A.charAt(i - 1));
                i--;
                j--;
            } 
        }

        bw.write(DP[lengthA][lengthB] + "\n");
        bw.write(LCS.reverse() + "\n");

        bw.flush();
        bw.close();

    }
}
