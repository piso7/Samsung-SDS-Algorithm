package p2042;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static long N, M, K;
	static long a, b, c;
	static int S = 1;
	static long[] tree;
	static long leftResult, rightResult;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Long.parseLong(st.nextToken());
		M = Long.parseLong(st.nextToken());
		K = Long.parseLong(st.nextToken());
		
		while(S < N) {
			S *= 2;
		}
		
		tree = new long[2*S];
		
		for(int i = 0; i < N; i++) {
			tree[S + i] = Integer.parseInt(br.readLine());
		}
		
		for(int i = S - 1; i >= 1; i--) {
			tree[i] = tree[2*i] + tree[2*i + 1];
		}
		
//		for(int i = 0; i < 2*S; i++) { //디버깅
//			System.out.print(" " + tree[i] + " ");
//			
//		}
		
		for(int i = 0; i < M + K; i++) {
			st = new StringTokenizer(br.readLine());
			a = Long.parseLong(st.nextToken());
			b = Long.parseLong(st.nextToken());
			c = Long.parseLong(st.nextToken());
			
			if (a == 1) { //b번째 수를 c로 바꾸시오
//				tree[S + b - 1] = c; //아 이거 이렇게 하면 안되네
				long diff = c - tree[(int) (S + b - 1)];
				update(1, S, 1, b, diff);
			}
			else if (a == 2) { // b부터 c까지의 합을 출력하시오
			
//				System.out.println("\n업데이트 후"); //디버깅
//				for(int o = S; o < S + N; o++) { 
//					System.out.print(" " + tree[o] + " ");
//				}
//				System.out.println("");
				
				System.out.println(query(1, S, 1, b, c));
			}
		}
		
	}
	
	static long query(int left, int right, int node, long queryleft, long queryright) {
		if(queryright < left || right < queryleft) {
			return 0;
		}
		else if(queryleft <= left && right <= queryright) {
//			System.out.println(tree[node]);
			return tree[node];
		}
		else {
			int mid = (left + right) / 2;
			long leftResult = query(left, mid, node * 2, queryleft, queryright);
			long rightResult = query(mid + 1, right, node*2 + 1, queryleft, queryright);
			return leftResult + rightResult;
		}
	}
	
	static void update(int left, int right, int node, long target, long diff) {
		if(target < left || right < target) { //연관없음
			return;
		}
		else { //연관있음
			tree[node] += diff;
			if(left != right) {
				int mid = (left + right) / 2;
				update(left, mid, node * 2, target, diff);
				update(mid + 1, right, node*2 + 1, target, diff);
			}
			
		}
	}
}
