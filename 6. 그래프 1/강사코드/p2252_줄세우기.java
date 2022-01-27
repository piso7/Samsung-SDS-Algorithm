
/**
 * 
 * @author s_cheol.park
 * https://www.acmicpc.net/problem/2252
 * 줄세우기
 * 위상정렬로 풀이 가능하다.
 * 
 * 문제의 조건에서, 
 * 학생들을 정점, 학생들 간의 관계를 간선으로 그래프 형태로 바꾸면 
 * 위상 정렬 이후 탐색 순서대로 출력하면 문제 해결 가능
 */

package BOJ;

import java.io.*;
import java.util.*;

public class P_2252_줄세우기 {

    static int N, M;
    static ArrayList<Integer> [] Map;
    static boolean [] Visit;
    static int [] InDegree;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws NumberFormatException, IOException {

        StringTokenizer st;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        Map = new ArrayList [N + 1];
        Visit = new boolean [N + 1];
        InDegree = new int [N + 1];
        // 그래프 초기화
        for(int i = 1 ; i <= N ; i++) {
            Map[i] = new ArrayList<>();
        }
        // 진입차수가 0이 되어 탐색순서가 도달한 정점을 담는 큐
        ArrayDeque<Integer> dq = new ArrayDeque<Integer>();
        // 그래프 정의
        // 먼저 출력되어야 하는 학생 > 나중에 출력되어야 하는 학생 순서로 그래프를 구성함
        // 나중에 출력되어야 하는 학생들의 경우엔 진입차수 1 증가시킴
        int from, to;
        for(int i = 1 ; i <= M ; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            from = Integer.parseInt(st.nextToken());
            to = Integer.parseInt(st.nextToken());
            Map[from].add(to);
            InDegree[to]++;
        }

        // 최초 탐색을 할 학생을 찾는다.
        for(int i = 1 ; i <= N ; i++) {
            if(InDegree[i] == 0) {
                dq.addLast(i);
            }
        }

        int seq = 0; // 마지막 공백을 출력하지 않기 위한 변수
        // Queue 가 빌때까지 수행된다.
        while(dq.isEmpty() == false) {
            int now = dq.pollLast(); // 현재 탐색 위치
            seq++;
            // 바로 출력
            if(seq == N) {
                bw.write(String.valueOf(now));
            } else {
                bw.write(String.valueOf(now) + " ");
            }

            // 인접한 노드들을 검사한다.
            // 이때 진입차수가 0 보다 큰 정점들만 탐색한다 (0이면 이미 큐에 있음)
            // 진입차수를 하나씩 내려주고, 0이 되었다면 큐에 넣어준다.
            for(int next : Map[now]) {
                if(InDegree[next] > 0) {
                    InDegree[next]--;
                    if(InDegree[next] == 0) {
                        dq.addLast(next);
                    }
                }
            }
        }
        bw.flush();
        bw.close();
    }

}
