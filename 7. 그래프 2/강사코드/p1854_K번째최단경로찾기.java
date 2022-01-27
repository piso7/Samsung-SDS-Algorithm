package BOJ;

import java.io.*;
import java.util.*;

/**
 * 
 * @author s_cheol.park
 * https://www.acmicpc.net/problem/1854
 * K번째 최단경로 찾기
 * 다익스트라 알고리즘
 * 
 * 최단거리 배열을 우선순위 큐를 이용해서 구할수도 있음 (모든 최단거리를 다 들고 있는 방식)
 * 
 */

public class P_1854_K번째최단경로찾기2 {

    static class info implements Comparable<info> {
        int node;
        int distance;

        public info(int node, int distance) {
            this.node = node;
            this.distance = distance;
        }

        @Override
        public int compareTo(info o) {
            return Integer.compare(distance, o.distance);
        }
    }

    static int N, M, K;
    static ArrayList<info> [] Map;
    static PriorityQueue<Integer> [] Distance;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws NumberFormatException, IOException {
        StringTokenizer st;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        Map = new ArrayList [N + 1];
        Distance = new PriorityQueue [N + 1];
        for(int i = 1 ; i <= N ; i++) {
            Map[i] = new ArrayList<>();
            Distance[i] = new PriorityQueue<>(Collections.reverseOrder()); // Peek 시 가장 큰 숫자가 나올수 있게 정렬을 반대로 함
        }

        int a, b, c;
        for(int i = 1 ; i <= M ;i++) {
            st = new StringTokenizer(br.readLine(), " ");
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());

            Map[a].add(new info(b, c));
        }

        findShortestPath(1);

        for(int i = 1 ; i <= N ; i++) {
            if(Distance[i].size() == K) {
                bw.write(Distance[i].peek() + "\n");
            } else {
                bw.write("-1" + "\n");
            }
        }

        bw.flush();
        bw.close();
    }

    private static void findShortestPath(int start) {
        PriorityQueue<info> pq = new PriorityQueue<>();
        Distance[start].add(0);
        pq.add(new info(start,0));

        while(pq.isEmpty() == false) {
            info now = pq.poll();

            if(now.distance > Distance[now.node].peek()) {
                continue;
            }

            for(info next : Map[now.node]) {
                if(Distance[next.node].size() < K) {
                    Distance[next.node].add(now.distance + next.distance);
                    pq.add(new info(next.node, now.distance + next.distance));
                } else if (Distance[next.node].peek() > (now.distance + next.distance)) {
                    Distance[next.node].poll();
                    Distance[next.node].add(now.distance + next.distance);
                    pq.add(new info(next.node, now.distance + next.distance));
                }
            }
        }
    }

}
