import java.io.*;
import java.util.*;

/**
 * 
 * @author s_cheol.park
 * https://www.acmicpc.net/problem/11266
 * 단절점
 * 
 * 단절점 알고리즘
 * DFS 의 응용
 * 
 * DFS 의 탐색순서를 이용하여,
 * 내 다음에 탐색될수 있는 정점들이 내 탐색순위보다 더 빠른 탐색순위의 정점으로 갈수 있는 경로가 있을 경우 해당 정점은 단절점이 아니다. (즉, 우회경로가 있다)
 * 이를 이용하여, 내 다음에 탐색될수 있는 정점들의 모든 탐색순위를 찾아서 가장 작은값이 나의 탐색순위보다가 큰 경우는 단절점이 된다.
 * 
 */

public class P_11266_단절점 {

    static int V, E;
    static ArrayList<Integer> [] Map;
    static int [] SearchOrder;
    static boolean [] isCutVertex;
    static int Order;
    static int Answer;
    static StringBuffer AnswerList;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws NumberFormatException, IOException {
        StringTokenizer st;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        st = new StringTokenizer(br.readLine(), " ");
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        SearchOrder = new int [V + 1];
        isCutVertex = new boolean [V + 1];
        Map = new ArrayList [V + 1];
        for(int i = 1 ; i<= V ; i++) {
            Map[i] = new ArrayList<>();
        }

        int a, b;
        for(int i = 1 ; i <= E ;i++) {
            st = new StringTokenizer(br.readLine(), " ");
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());

            Map[a].add(b);
            Map[b].add(a);
        }

        Order = 0;
        for(int i = 1 ; i<= V ; i++) {
            if(SearchOrder[i] == 0) {
                dfs(i,true);
            }
        }

        Answer = 0;
        AnswerList = new StringBuffer();

        for(int i = 1 ; i<= V ; i++) {
            if(isCutVertex[i] == true) {
                Answer++;
                AnswerList.append(i + " ");
            }
        }

        bw.write(Answer + "\n");
        if(Answer > 0) {
            bw.write(AnswerList + "\n");
        }
        bw.flush();
        bw.close();
    }

    private static int dfs(int now, boolean isRoot) {
        Order++; // 방문순서
        SearchOrder[now] = Order;
        int rtn = Order; // 지금 정점이후에 도달훌수 있는 모든 정점들의 탐색순서 중 가장 작은값
        int child = 0; // 자식의 숫자 Root 일 경우 단절점 판단을 위함

        for (int next : Map[now]) {
            if (SearchOrder[next] == 0) {
                child++;

                // 자식 정점 중 방문 순서가 가장 빠른 값.
                // 이때, 특정 자식 정점이 여러 개의 정점을 타고 타고 올라가서 1번 정점까지 갈 수도 있다는 점에 유의해야 함.
                int low = dfs(next, false); // 현재 정점의 다음에 방문할 모든 정점에 대해서 도달할수 있는 최소의 Order 순서 (우회로가 있나 찾아보는 것임)

                // Root 가 아니고, 내 다음에 방문할 정점의 순서가 모두 나보다 클 경우에 지금위치는 단절점이다.
                if (isRoot == false && low >= SearchOrder[now]) {
                    isCutVertex[now] = true;
                }
                rtn = Math.min(rtn, low);
            } else { // 자식정점이 이미 방문한 경우
                rtn = Math.min(rtn, SearchOrder[next]);
            }
        }

        // Root 의 경우 내 Order 보다 작은게 나올수 없기 때문에 자식노드의 숫자로 판단한다.
        if (isRoot == true && child >= 2) {
            isCutVertex[now] = true;
        }

        return rtn;
    }

}
