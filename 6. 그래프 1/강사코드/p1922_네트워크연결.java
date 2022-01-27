
package BOJ;

import java.io.*;
import java.util.*;

/**
 * 
 * @author s_cheol.park
 * https://www.acmicpc.net/problem/1922
 * 네트워크 연결
 * 최소신장트리로 풀이가 가능하다.
 * (크루스칼 알고리즘 or 프림 알고리즘)
 * 
 * 본 코드는 크루스칼 알고리즘으로 풀이함
 * (간선정렬 + 서로소집합)
 */

public class P_1922_네트워크연결 {

    static class info implements Comparable<info>{
        int node1;
        int node2;
        int distance;

        public info(int node1, int node2, int distance) {
            this.node1 = node1;
            this.node2 = node2;
            this.distance = distance;
        }

        @Override
        public int compareTo(info o) {
            return Integer.compare(distance, o.distance);
        }
    }

    static int N, M;
    static info [] List;
    static int [] Group;
    static int Answer;

    public static void main(String[] args) throws NumberFormatException, IOException {
        StringTokenizer st;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());
        List = new info [M + 1]; // 간선리스트
        Group = new int [N + 1]; // 서로소집합에 사용할 배열
        Answer = 0;
        int n1, n2, cost;
        for(int i = 1 ; i <= M ; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            n1 = Integer.parseInt(st.nextToken());
            n2 = Integer.parseInt(st.nextToken());
            cost = Integer.parseInt(st.nextToken());
            List[i] = new info(n1, n2, cost);
        }

        for(int i = 1 ; i <= N ; i++) {
            Group[i] = i;
        }

        Arrays.sort(List, 1, M+1); // 간선을 비용순으로 정렬한다.

        int connectCount = 0; // 간선을 N-1 개 연결하면 최소신장트리 완성
        for(int i = 1 ; i <= M ; i++) {
            // 현재 선택된 간선의 두개 정점이 연결된 상태가 아니라면 연결해준다.
            if(find(List[i].node1) != find(List[i].node2)) {
                union(List[i].node1, List[i].node2);
                Answer += List[i].distance;
                connectCount++;
            }

            if(connectCount == N - 1) {
                break;
            }
        }
        bw.write(Answer + "\n");
        bw.flush();
        bw.close();
    }
    private static void union(int a, int b) {
        int aGroup = find(a);
        int bGroup = find(b);

        Group[aGroup] = bGroup;
    }

    private static int find(int i) {
        if(Group[i] == i) {
            return i;
        } else {
            return Group[i] = find(Group[i]);
        }
    }
}
