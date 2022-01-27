package BOJ;

import java.io.*;
import java.util.*;

/**
 * 
 * @author s_cheol.park
 * https://www.acmicpc.net/problem/2098
 * 외판원 순회
 * DP + 비트마스킹.
 * 
 * 외판원 순회는 CS 분야에서 중요한 문제 중 하나이다. 
 * 
 * 도시가 N 개일 경우 단순한 전체 탐색을 이용할 경우 시간복잡도가 N! 가 나오고 풀이가 불가능하다.
 * DP + 비트마스킹 + 일반탐색(DFS) 기법을 사용하면, 시간복잡도 N^2*2^N 의 풀이가 가능하다.
 * 
 * DP[Cur][Visit] = Cur 도시까지 Visit 에 기록된 도시들을 방문하고 가는 최소 비용
 * 이때, Visit 의 경우 N 개 도시들의 방문기록을 나타내야하고 일반적으로는 N 크기의 배열을 사용하나, 방문여부는 1 or 0 으로 표현될수 있다는 아이디어에 착안하여 
 * 비트마스킹 기법을 사용하여, integer 하나로 표현한다.
 * 예를들어 4개의 도시 1,2,3,4 가 있고 이중 2와 3만 방문한 경우에는 0110 (2) = 6 으로 같이 표현한다.
 *                              1과 4만 방문했을 경우에는 1001 (2) = 9 로 표현한다.
 * 위처럼 Integer 를 2진수로 표현해서 기록한다.                              
 */

public class P_2098_외판원순회 {

    static int N;
    static int [][] DP;
    static int [][] W ;
    static int VisitAll; 
    static final int Inf = Integer.MAX_VALUE;
    static int Answer;

    public static void main(String[] args) throws NumberFormatException, IOException {
        StringTokenizer st;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        VisitAll = (1 << N) - 1; // 모든 도시를 방문한 경우, 도시가 4개라면 1111(2) = 15가 기록된다, 즉  10000(2) 에서 1을 뺀 결과와 동일하다.
        W = new int [N+1][N+1];
        DP = new int [N+1][VisitAll + 1];

        // 입력
        for(int i = 1 ; i <= N ; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for(int j = 1 ; j <= N ; j++) {
                W[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 초기값 셋팅
        Answer = Inf;
        for(int i = 1 ; i <= N ; i++) {
            for(int j = 0 ; j <= VisitAll ; j++) {
                DP[i][j] = Inf;
            }
        }

        DP[1][1] = 0; // 1번정점을 1번 정점만 방문한 상태로 갈수 있는 최소비용
        getDP(1, 1);

        bw.write(Answer + "\n");
        bw.flush();
        bw.close();
    }

    private static void getDP(int now, int visited) {
        // 모든 도시를 방문한 경우
        // 이제 최초 출발점으로 돌아가는 코드만 있으면 된다.
        if(visited == VisitAll) {
            if(W [now][1] == 0) {
                return; // now 에서 출발점으로 갈수 없는 경우
            }
            Answer = Math.min(Answer, DP[now][visited] + W[now][1]);
        }

        // 아직 방문할 도시가 남은 경우, 1~N 까지 모든 정점을 탐색해본다.
        for(int i = 1 ; i <= N ; i++) {
            int next = (1<<(i-1));
            int nextVisited =  visited | next;  // 다음 방문할 도시의 비트연산자
            if(nextVisited == visited) { // 다음 방문할 도시를 이미 방문한 경우는 Continue
                continue; 
            }
            if(W[now][i] == 0) { // 다음 도시를 갈수있는 길이 없어도 Continue
                continue;
            }
            // 다음 도시를 계산한다.
            if(DP[i][nextVisited] > DP[now][visited] + W[now][i]) {
                DP[i][nextVisited] = DP[now][visited] + W[now][i];
                getDP(i, nextVisited);
            }
        }
    }
}
