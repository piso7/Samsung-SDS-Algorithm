package BOJ;

import java.io.*;
import java.util.*;

/**
 * 
 * @author s_cheol.park
 * https://www.acmicpc.net/problem/2342
 * Dance Dance Revolution
 * 
 * DP [i][j][k] = i번까지 발판을 밟았을때의 최소 비용 (왼발은 j에 오른발은 k에 위치)
 * 
 * 현재 밟은 발판에서 다음 밟을 발판에 얼마의 비용으로 갈수 있는지를 정의한다.
 */

public class P_2342_DanceDanceRevolution {

    static int N;
    static int [][][] DP;
    static int [] Direction;
    static int Answer;
    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws NumberFormatException, IOException {
        StringTokenizer st;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        Direction = new int [100000+1];
        Answer = INF;
        // 입력
        int now;
        st = new StringTokenizer(br.readLine(), " ");
        input : for(int i = 1; ;i++) {
            now = Integer.parseInt(st.nextToken());
            if(now == 0) {
                N = i - 1;
                break input;
            }
            Direction[i] = now;
        }
        DP = new int [N+1][5][5];

        for(int i = 1 ; i <= N ; i++) {
            for(int j = 0 ; j <= 4 ; j++) {
                for(int k = 0 ; k <= 4 ; k++) {
                    DP[i][j][k] = INF;
                }   
            }
        }

        // 초기값
        // 첫번째 방향을 왼발로 밟느냐, 오른발로 밟느냐
        DP[1][0][Direction[1]] = 2;
        DP[1][Direction[1]][0] = 2;

        int next;
        for(int i = 1 ; i <= N - 1 ; i++) {
            for(int j = 0 ; j <= 4 ; j++) {
                for(int k = 0 ; k <= 4 ; k++) {
                    if(DP[i][j][k] != INF) { // 여기까지 밟는 방법이 있을 경우에만 계산한다.
                        next = Direction[i + 1];
                        if(k != next) { // 왼발로 밟기 (같은 위치에 두 발이 모두 있으면 안되기 때문에)
                            DP[i+1][next][k] = Math.min(DP[i+1][next][k], DP[i][j][k] + getCost(j, next));
                        }
                        if(j != next) { // 오른발로 밟기
                            DP[i+1][j][next] = Math.min(DP[i+1][j][next], DP[i][j][k] + getCost(k, next));
                        }
                    }
                }   
            }
        }

        for(int j = 0 ; j <= 4 ; j++) {
            for(int k = 0 ; k <= 4 ; k++) {
                Answer = Math.min(Answer, DP[N][j][k]);
            }   
        }

        bw.write(Answer + "\n");
        bw.flush();
        bw.close();
    }

    static int getCost (int from, int to){
        if(from == to) {
            return 1;
        } else if(from == 0) {
            return 2;
        } else if (Math.abs(from - to) == 2) {
            return 4;
        } else { 
            return 3;
        }
    }

}
