package p1753_최단경로_다익스트라;

import java.io.*;
import java.util.*;

public class Main {
	static int V, E;
	static int u, v, w;
	static int[] Distance;
	static final int INF = Integer.MAX_VALUE;
	static ArrayList<node>[] Map;
	
	static class node implements Comparable<node>{
		int n1;
		int weight;
		
		public node(int n1, int weight) {
			super();
			this.n1 = n1;
			this.weight = weight;
		}

		@Override
		public int compareTo(node o) {
			return Integer.compare(weight, o.weight);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		
		
		Map = new ArrayList[V + 1];
		Distance = new int[V + 1];
		
		for(int i = 1; i <= V; i++) {
			Map[i] = new ArrayList<>();
			Distance[i] = INF;
		}
		
		int start = Integer.parseInt(br.readLine());
		
		for(int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			u = Integer.parseInt(st.nextToken());
			v = Integer.parseInt(st.nextToken());
			w = Integer.parseInt(st.nextToken());
			
			Map[u].add(new node(v, w));
			
		}
		
		ShortestPathFinder(start);
		
//		for(int i = 1; i <= V; i++) {
//			if(Distance[i] != INF) {
//				System.out.println(Distance[i]);
//			} else {
//				System.out.println("INF");
//			}
//		}
		
		for(int i = 1; i <= V; i++) {
			if(Distance[i] != INF) {
				bw.write(Distance[i] + "\n");
			} else {
				bw.write("INF" + "\n");
			}
		}
		
		bw.flush();
		bw.close();
		
	}
	
	static void ShortestPathFinder(int start) {
		PriorityQueue<node> pq = new PriorityQueue<>();
		Distance[start] = 0;
		pq.add(new node(start, 0));
		
		while(pq.isEmpty() == false) {
			node now = pq.poll();
			
			if(now.weight > Distance[now.n1]) {//갱신할 필요 없음
				continue;
			}
				
			
			for(node next : Map[now.n1]) {
				if(Distance[next.n1] > Distance[now.n1] + next.weight) {
					Distance[next.n1] = Distance[now.n1] + next.weight;
					pq.add(new node(next.n1, Distance[next.n1]));
				}
				
			}
		}
	}

}
