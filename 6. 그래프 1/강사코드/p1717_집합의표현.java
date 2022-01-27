
/**
 * 
 * @author s_cheol.park
 * https://www.acmicpc.net/problem/1717
 * 집합의 표현
 * Union-Find / 서로소 집합
 * 
 * find 시에 Return 값을 최종 배열에 넣어주는 방식으로 경로압축을 해야 
 * find 의 속도가 O(N) 에 근접하지 않고 O(1) 에 근접하게 된다.
 */

package BOJ;

import java.io.*;
import java.util.*;

public class P_1717_집합의표현 {

    static int N, M;
    static int [] Group;

    public static void main(String[] args) throws NumberFormatException, IOException {
        StringTokenizer st;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        Group = new int [N + 1];
        for(int i = 0 ; i<= N ; i++) {
            Group[i] = i;
        }

        int Q, a, b;
        for(int i = 1 ; i <= M ;i++) {
            st = new StringTokenizer(br.readLine(), " ");
            Q = Integer.parseInt(st.nextToken());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());

            if(Q == 0) {
                union (a, b);
            }

            if (Q == 1) {
                if(find(a) == find(b)) {
                    bw.write("YES" + "\n");
                } else {
                    bw.write("NO" + "\n");
                }
            }
        }

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
