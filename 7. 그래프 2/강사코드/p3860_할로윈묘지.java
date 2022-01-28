
package BOJ;

import java.io.*;
import java.util.*;

/**
 * 
 * @author s_cheol.park
 * https://www.acmicpc.net/problem/3860
 * 할로윈묘지
 * 
 * 벨만포드 알고리즘
 * 
 */

public class P_3860_할로윈묘지 {
    static class edge{
        int from;
        int to;
        int cost;

        public edge(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }

    static int W, H;
    static int [][] Map;
    static ArrayList<edge> EdgeList;
    static int GraveCount;
    static final int GraveFlag = -1;
    static int GhostCount;
    static final int GhostFlag = -2;
    static int [] DirectionX = {0, 1, -1, 0, 0};
    static int [] DirectionY = {0, 0, 0, 1, -1};
    static int [] Cost;
    static boolean isNegativeCycle;
    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws NumberFormatException, IOException {

        StringTokenizer st;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        while (true) {
            st = new StringTokenizer(br.readLine(), " ");
            W = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());
            if(W == 0 && H == 0) {
                break;
            }

            Map = new int [W + 1 + 1][H + 1 + 1];
            EdgeList = new ArrayList<>();
            Cost = new int [W * H + 1];

            // 문제에서는 (0,0) ~ (W-1, H-1) 로 묘지를 정의하나,
            // 코딩의 편의성을 고려하여 (1, 1) ~ (W, H) 로 정의함.
            // Map 배열에서 갈수 없는 곳 (묘비가 세워진곳) 을 -1 로 정의
            GraveCount = Integer.parseInt(br.readLine());
            int x1, y1, x2, y2, cost;
            for (int i = 1 ; i <= GraveCount ; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                x1 = Integer.parseInt(st.nextToken());
                y1 = Integer.parseInt(st.nextToken());
                Map[x1 + 1][y1 + 1] = GraveFlag;
            }

            // 유령동굴이 있는곳은 간선을 바로 만들어주면서 -2 처리 (추가 간선을 만들지 않겠다. 그러나 도달은 해야한다.)
            GhostCount = Integer.parseInt(br.readLine());
            for (int i = 1 ; i <= GhostCount ; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                x1 = Integer.parseInt(st.nextToken());
                y1 = Integer.parseInt(st.nextToken());
                x2 = Integer.parseInt(st.nextToken());
                y2 = Integer.parseInt(st.nextToken());
                cost = Integer.parseInt(st.nextToken());
                Map[x1 + 1][y1 + 1] = GhostFlag;
                EdgeList.add(new edge(pointToNumber(x1 + 1, y1 + 1), pointToNumber(x2 + 1, y2 + 1), cost));
            }

            // 각 지점 별로 4방으로 간선을 생성할때 끝 벽의 예외로직을 따로 처리하지 않기 위해서 -1로 막아놓음
            for (int i = 0 ; i <= W + 1 ; i++) {
                Map[i][0] = GraveFlag;
                Map[i][H + 1] = GraveFlag;
            }
            for (int i = 0 ; i <= H + 1 ; i++) {
                Map[0][i] = GraveFlag;
                Map[W + 1][i] = GraveFlag;
            }

            for (int i = 1 ; i <= W ; i++) {
                for (int j = 1 ; j <= H ; j++) {
                    Cost[pointToNumber(i,j)] = INF;
                    makeEdge(i, j);
                }
            }
            Cost[pointToNumber(1,1)] = 0;

            // 벨만 포드 알고리즘
            for (int i = 1 ; i <= W * H - 1; i++) {
                for (edge now : EdgeList) {
                    if(Cost[now.from] != INF) {
                        Cost[now.to] = Math.min(Cost[now.to], Cost[now.from] + now.cost);
                    }
                }
            }

            // 음수 싸이클이 발생하는지 점검한다.
            isNegativeCycle = false;
            cycleCheck : for (edge now : EdgeList) {
                if(Cost[now.from] != INF && Cost[now.to] > Cost[now.from] + now.cost) {
                    isNegativeCycle = true;
                    break cycleCheck;
                }   
            }

            if(isNegativeCycle == true) {
                bw.write("Never" + "\n");
            } else if (Cost[pointToNumber(W, H)] == INF){
                bw.write("Impossible" + "\n");
            } else {
                bw.write(Cost[pointToNumber(W, H)] + "\n");
            }
        }
        bw.flush();
        bw.close();
    }

    private static void makeEdge(int x, int y) {

        // 묘지, 귀신구멍, 도착점에서는 간선을 생성하지 않는다.
        if(Map[x][y] == GraveFlag || Map[x][y] == GhostFlag || (x == W && y == H)) {
            return;
        }

        // 묘지로 가는 간선은 생성하지 않는다.
        for(int i = 1 ; i <= 4 ; i++) {
            if(Map[x + DirectionX[i]][y + DirectionY[i]] != GraveFlag) {
                EdgeList.add(new edge(pointToNumber(x, y), pointToNumber(x + DirectionX[i], y + DirectionY[i]), 1));
            }
        }

    }

    // x,y 좌표를 Integer 로 변경하는 함수
    private static int pointToNumber(int x, int y) {
        return x + (y - 1) * W;
    }

}
