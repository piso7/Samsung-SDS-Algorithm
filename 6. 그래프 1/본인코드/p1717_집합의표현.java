package p1717_집합의표현;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int n, m;
	static int op, a, b;
	static int[] group;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		group = new int[n + 1];
		
		for(int i = 0; i < n + 1; i++) { //초기화
			group[i] = i;
		}
		
		while(m --> 0) {
			st = new StringTokenizer(br.readLine());
			op = Integer.parseInt(st.nextToken());
			a = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
			
			if(op == 0) {
				union(a, b);
			}
			else if(op == 1){
				if(find(a) == find(b)) {
					System.out.println("YES");
				} else {
					System.out.println("NO");
				}
			}
		}
		
	}
	
	static void union(int a, int b) {
		int aroot = find(a);
		int broot = find(b);
		
		group[aroot] = broot;
		
	
	}
	
	static int find(int a) {
		if(group[a] == a) return a;
		else {
			return group[a] = find(group[a]);
		}
		
	}
	

}
