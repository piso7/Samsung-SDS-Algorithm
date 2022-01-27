package BOJ;

import java.io.*;
import java.util.*;

/**
 * 
 * @author s_cheol.park
 * https://www.acmicpc.net/problem/11438
 * LCA2
 * LCA (최소공통조상)
 * 
 * N 의 크기가 10만 M 의 크기도 10만으로, Sparse Table 을 사용하여,
 * LCA 를 구하는 시간복잡도를 logN 으로 만들어서 전체 문제의 시간복잡도를 MlogN 으로 만들어야한다.
 * 
 */

public class P_11438_LCA2 {

    static int N, M;
    static int LogN; // 2의 몇제곱까지 계산할지를 저장한 변수 (N 이 10만이니 17로 선언하고 사용해도 무방)
    static int [] Depth;
    static int [][] Parents;
    static ArrayList<Integer> [] Map;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws NumberFormatException, IOException {
        StringTokenizer st;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        N = Integer.parseInt(br.readLine());
        Map = new ArrayList [N + 1];
        Depth = new int [N + 1];
        getLogN();
        Parents = new int [LogN + 1][N + 1];
        for(int i = 0 ; i<= N ; i++) {
            Map[i] = new ArrayList<>();
        }

        int a, b;
        for(int i = 1 ; i <= N - 1 ;i++) {
            st = new StringTokenizer(br.readLine(), " ");
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());

            Map[a].add(b);
            Map[b].add(a);
        }
        // DFS 로 구현할 경우 재귀 방식으로 구현하면, 재귀 호출을 최대 10만번(N) 함으로써 Stack Overflow 가 발생할수 있음
        // 재귀 방식의 DFS 가 아닌 Stack 자료구조를 이용한 DFS 로 설계 해야함.
        doBfs(1); 
        makeSparseTable();

        M = Integer.parseInt(br.readLine());
        for(int i = 1 ; i <= M ;i++) {
            st = new StringTokenizer(br.readLine(), " ");
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            bw.write(getLCA(a, b) + "\n");

        }
        bw.flush();
        bw.close();
    }

    public static void getLogN() {
        LogN = 0;
        for (int k = 1; k < N; k *= 2) {
            LogN++;
        }
    }

    private static void makeSparseTable() {
        for(int i = 1 ; i <= LogN ;i++) {
            for(int j = 1 ; j <= N ; j++) {
                Parents[i][j] = Parents[i-1][Parents[i-1][j]];
            }
        }   
    }

    private static void doBfs(int start) {
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        Depth[start] = 1;
        dq.add(start);

        while(dq.isEmpty() == false) {
            int now = dq.poll();
            for(int next : Map[now]) {
                if(Depth[next] == 0) { // 탐색하지 않은 지점만 탐색한다.
                    Depth[next] = Depth[now] + 1;
                    Parents[0][next] = now;
                    dq.add(next);
                }
            }   
        }
    }

    private static int getLCA(int a, int b) {
        // a 가 더 깊이 있음을 가정한 로직
        if(Depth[a] < Depth[b]) {
            return getLCA(b, a);
        }

        // 높이 맞추기
        for(int i = 0 ; i <= LogN ; i++) {
            if(((Depth[a] - Depth[b]) & (1 << i)) >= 1) {
                a = Parents[i][a];
            }
        }

        // 높이 맞췄으면 같은지 검사
        if(a == b) {
            return a;
        }

        // 공통조상이 아닐때 까지 부모를 따라 올라간다. 
        // 최종적으로는 LCA 바로 밑칸까지만 올라간다.
        for(int i = LogN ; i >= 0 ; i--) {
            if(Parents[i][a] != Parents[i][b]) {
                a = Parents[i][a];
                b = Parents[i][b];
            }
        }

        return Parents[0][a];
    }

}
