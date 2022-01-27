package BOJ;

import java.io.*;
import java.util.*;

/**
 * 
 * @author s_cheol.park
 * https://www.acmicpc.net/problem/3176
 * 도로 네트워크
 * LCA (최소공통조상)
 * 
 * 도시와 도로의 갯수를 보고 Tree 형태임을 알수 있어야한다.
 * Tree 형태에서 두개 정점 (A 와 B) 사이의 경로는  A to LCA to B 가 되고,
 * Parents 배열의 형태로, 사용하는 간선들의 최대값과 최소값을 저장하고 있고 두개 정점에서 LCA 를 향해 올라가면서 최대값과 최소값을 갱신해나가면 구할수 있다.
 */

public class P_3176_도로네트워크 {

    static class info {
        int node;
        int distance;

        public info(int node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    static int N, K;
    static int LogN; // 2의 몇제곱까지 계산할지를 저장한 변수 (N 이 10만이니 17로 선언하고 사용해도 무방)
    static int [] Depth;
    static int [][] Parents;
    static int [][] MinDistance;
    static int [][] MaxDistance;
    static ArrayList<info> [] Map;

    static int MinAnswer, MaxAnswer;

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
        MinDistance = new int [LogN + 1][N + 1];
        MaxDistance = new int [LogN + 1][N + 1];
        for(int i = 0 ; i<= N ; i++) {
            Map[i] = new ArrayList<>();
        }

        int a, b, c, d, e;
        for(int i = 1 ; i <= N - 1 ;i++) {
            st = new StringTokenizer(br.readLine(), " ");
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());
            Map[a].add(new info(b, c));
            Map[b].add(new info(a, c));
        }

        doBfs(1);
        makeSparseTable();

        K = Integer.parseInt(br.readLine());
        for(int i = 1 ; i <= K ;i++) {
            st = new StringTokenizer(br.readLine(), " ");
            d = Integer.parseInt(st.nextToken());
            e = Integer.parseInt(st.nextToken());
            getLCA(d, e);
            bw.write(MinAnswer + " " + MaxAnswer + "\n");
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
                MinDistance[i][j] = Math.min(MinDistance[i-1][j], MinDistance[i-1][Parents[i-1][j]]);
                MaxDistance[i][j] = Math.max(MaxDistance[i-1][j], MaxDistance[i-1][Parents[i-1][j]]);
            }
        }   
    }

    private static void doBfs(int start) {
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        Depth[start] = 1;
        dq.add(start);

        while(dq.isEmpty() == false) {
            int now = dq.poll();
            for(info next : Map[now]) {
                if(Depth[next.node] == 0) {
                    Depth[next.node] = Depth[now] + 1;
                    Parents[0][next.node] = now;
                    MinDistance[0][next.node] = next.distance;
                    MaxDistance[0][next.node] = next.distance;
                    dq.add(next.node);
                }
            }   
        }
    }

    private static int getLCA(int a, int b) {
        // a 가 더 깊이 있음을 가정한 로직
        if(Depth[a] < Depth[b]) {
            return getLCA(b, a);
        }

        MinAnswer = 1000001;
        MaxAnswer = 0;

        // 높이 맞추기
        for(int i = 0 ; i <= LogN ; i++) {
            if(((Depth[a] - Depth[b]) & (1 << i)) >= 1) {
                MinAnswer = Math.min(MinAnswer, MinDistance[i][a]);
                MaxAnswer = Math.max(MaxAnswer, MaxDistance[i][a]);
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
                MinAnswer = Math.min(MinAnswer, Math.min(MinDistance[i][a], MinDistance[i][b]));
                MaxAnswer = Math.max(MaxAnswer, Math.max(MaxDistance[i][a], MaxDistance[i][b]));
                a = Parents[i][a];
                b = Parents[i][b];
            }
        }

        MinAnswer = Math.min(MinAnswer, Math.min(MinDistance[0][a], MinDistance[0][b]));
        MaxAnswer = Math.max(MaxAnswer, Math.max(MaxDistance[0][a], MaxDistance[0][b]));

        return Parents[0][a];
    }

}
