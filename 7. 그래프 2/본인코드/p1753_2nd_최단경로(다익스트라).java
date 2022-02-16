package p1753_2nd_최단경로_다익스트라;

import java.io.*;
import java.util.*;

public class Main {
	static int V, E;
	static int u, v, w;
	static int start;
	static int INF = Integer.MAX_VALUE;
	static int[] Dist;
	static ArrayList<info>[] Map;
	
	static class info implements Comparable<info>{
		int vertex;
		int distance;
		
		public info(int vertex, int distance) {
			super();
			this.vertex = vertex;
			this.distance = distance;
		}

		@Override
		public int compareTo(info o) {
			// TODO Auto-generated method stub
			return Integer.compare(this.distance, o.distance);
		}
	}
	
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		
		Map = new ArrayList[V + 1];
		Dist = new int[V + 1];
		
		for(int i = 1; i <= V; i++) {
			Map[i] = new ArrayList<>();
			Dist[i] = INF;
		}
		
		start = Integer.parseInt(br.readLine());
		
		for(int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			u = Integer.parseInt(st.nextToken());
			v = Integer.parseInt(st.nextToken());
			w = Integer.parseInt(st.nextToken());
			
			Map[u].add(new info(v, w));
		}
		
		FindShortestPath(start);
		
		for(int i = 1; i <= V; i++) {
			if(Dist[i] == INF) {
				bw.write("INF" + "\n");
			} else {
				bw.write(Dist[i] + "\n");
			}
		}
		
		bw.flush();
		bw.close();
	}
	
	static void FindShortestPath(int start) {
		PriorityQueue<info> pq = new PriorityQueue<>();
		Dist[start] = 0;
		
		pq.add(new info(start, 0));
		
		while(!pq.isEmpty()) {
			info now = pq.poll();
			
			if(now.distance > Dist[now.vertex]) continue;
			
			for(info next : Map[now.vertex]) {
				if(Dist[next.vertex] > Dist[now.vertex] + next.distance) {
					Dist[next.vertex] = Dist[now.vertex] + next.distance;
					pq.add(new info(next.vertex, Dist[next.vertex]));
				}
			}
		}
		
	}

}
