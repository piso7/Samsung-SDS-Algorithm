import java.io.*;
import java.util.*;

/**
 * 
 * @author s_cheol.park
 * https://www.acmicpc.net/problem/11657
 * 타임머신
 * 벨만-포드 알고리즘
 * 
 * 벨만 포드 수행 후 한번 더 전체 간선을 검사했을때, 새롭게 갱신이 되는 간선이 발생한다면 이는 음수싸이클이 있다고 판단할 수 있다.
 * 거리배열(Cost) 의 경우 Long 으로 선언해야지, 가능하다 (최악의 케이스 정점 2개 간선 6천개, 모두 간선의 값이 -10000일 경우)
 * 
 */

public class P_11657_타임머신 {

    static class info {
        int from;
        int to;
        int time;

        public info(int from, int to, int time) {
            this.from = from;
            this.to = to;
            this.time = time;
        }
    }

    static int N, M;
    static info [] List;
    static long [] Cost;
    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws NumberFormatException, IOException {
        StringTokenizer st;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        Cost = new long [N + 1];
        List = new info [M + 1];
        for(int i = 1 ; i<= N ; i++) {
            Cost[i] = INF;
        }

        int A, B, C;
        for(int i = 1 ; i <= M ;i++) {
            st = new StringTokenizer(br.readLine(), " ");
            A = Integer.parseInt(st.nextToken());
            B = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());
            List[i] = new info(A, B, C);
        }

        findShortestPath(1);
        boolean isNegativeCycle = findNegativeCycle();

        if(isNegativeCycle == true) {
            bw.write("-1" + "\n");
            bw.flush();
            bw.close();
            return;
        }

        for(int i = 2 ; i<= N ; i++) {
            if(Cost[i] == INF) {
                bw.write("-1" + "\n");
            } else {
                bw.write(Cost[i] + "\n");
            }
        }

        bw.flush();
        bw.close();
    }

    private static void findShortestPath(int start) {
        Cost[start] = 0;

        for(int i = 1 ; i <= N - 1 ; i++) {
            for(int j = 1 ; j <= M ; j++) {
                info nowEdge = List[j];
                if(Cost[nowEdge.from] != INF) {
                    if(Cost[nowEdge.to] > Cost[nowEdge.from] + nowEdge.time) {
                        Cost[nowEdge.to] = Cost[nowEdge.from] + nowEdge.time;
                    }
                }
            }   
        }
    }

    private static boolean findNegativeCycle() {
        for(int j = 1 ; j <= M ; j++) {
            info nowEdge = List[j];
            if(Cost[nowEdge.from] != INF) {
                if(Cost[nowEdge.to] > Cost[nowEdge.from] + nowEdge.time) {
                    return true;
                }
            }
        }   

        return false;
    }

}
