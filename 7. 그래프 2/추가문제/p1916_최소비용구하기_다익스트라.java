package p1916_최소비용구하기_다익스트라;

import java.io.*;
import java.util.*;

public class Main {
	static int N, M;
	static int u, v, w;
	static int start, end;
	static ArrayList<info> [] Map;
	static int[] Dist;
	static final int INF = Integer.MAX_VALUE;
	
	static class info implements Comparable<info>{
		int node;
		int distance;
		
		public info(int node, int distance) {
			super();
			this.node = node;
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
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		
		Map = new ArrayList[N + 1];
		Dist = new int[N + 1];
		
		for(int i = 1; i <= N; i++) {
			Map[i] = new ArrayList<>();
			Dist[i] = INF;
		}
		
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			u = Integer.parseInt(st.nextToken());
			v = Integer.parseInt(st.nextToken());
			w = Integer.parseInt(st.nextToken());
			
			Map[u].add(new info(v, w));
			
		}
		
		st = new StringTokenizer(br.readLine());
		start = Integer.parseInt(st.nextToken());
		end = Integer.parseInt(st.nextToken());
		
		FindShortestPath(start);
		
		bw.write(String.valueOf(Dist[end]));
		
		bw.flush();
		bw.close();
	}
	
	static void FindShortestPath(int start) {
		PriorityQueue<info> pq = new PriorityQueue<>();
		Dist[start] = 0;
		pq.add(new info(start, 0));
		
		while(!pq.isEmpty()) {
			info now = pq.poll();
			
			if(now.distance > Dist[now.node])
				continue;
			
			for(info next : Map[now.node]) {
				if(Dist[next.node] > Dist[now.node] + next.distance) {
					Dist[next.node] = Dist[now.node] + next.distance;
					pq.add(new info(next.node, Dist[next.node]));
				}
			}
		}
	}

}
