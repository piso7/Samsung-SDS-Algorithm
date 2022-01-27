package BOJ;

import java.io.*;
import java.util.*;

/**
 * 
 * @author s_cheol.park
 * https://www.acmicpc.net/problem/3830
 * 교수님은 기다리지 않는다.
 * 
 * union-find 의 응용
 * A 와 B 가 연결관계가 있고, B 와 C의 연결관계가 있고, A 와 D 의 연결관계가 있으면 A B C D 끼리는 모두 비교할수 있음.
 * 
 * WeightDiff[i] = 내 Root (Parent) 보다 무거운 정도.
 * 사용예) WeightDiff[2] = 3 이고 Parent[2] = 4 라면 2번 노드는 4번노드 보다 상대적으로 3만큼 더 무겁다.
 * 
 * 즉, 최상위 Parent 의 WeightDiff 값은 0이 된다.
 * 
 * 단순히 탐색 (DFS / BFS 로 풀이하려면 풀이가 쉽지 않음)
 * 
 */

public class P_3830_교수님은기다리지않는다 {

    static int N, M;
    static long [] WeightDiff;
    static int [] Parent;
    static long Answer;

    public static void main(String[] args) throws NumberFormatException, IOException {
        StringTokenizer st;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        while(true) {
            st = new StringTokenizer(br.readLine()," ");
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            if(N == 0 && M == 0) {
                break;
            }

            WeightDiff = new long [N + 1];
            Parent = new int [N + 1];
            for (int i = 1 ; i <= N ; i++) {
                Parent[i] = i;
            }
            String q;
            int a, b, diff;
            for(int i = 1 ; i <= M ; i++) {
                st = new StringTokenizer(br.readLine()," ");
                q = st.nextToken();
                a = Integer.parseInt(st.nextToken());
                b = Integer.parseInt(st.nextToken());

                if(q.equals("!")) {
                    diff = Integer.parseInt(st.nextToken());
                    union(a, b, diff);
                } else {
                    if(find(a) == find(b)) {
                        Answer = WeightDiff[b] - WeightDiff[a];
                        bw.write(Answer + "\n");
                    } else {
                        bw.write("UNKNOWN" + "\n");
                    }
                }
            }
        }

        bw.flush();
        bw.close();
    }
    private static void union(int a, int b, int diff) {
        int parentA = find(a);
        int parentB = find(b);

        if(parentA == parentB) {
            return;
        }

        WeightDiff[parentB] = WeightDiff[a] - WeightDiff[b] + diff;
        Parent[parentB] = parentA;
    }

    private static int find(int i) {
        if(Parent[i] == i) {
            return i;
        } else {
            int parentIndex = find(Parent[i]);
            WeightDiff[i] += WeightDiff[Parent[i]];
            return Parent[i] = parentIndex;
        }
    }
}
