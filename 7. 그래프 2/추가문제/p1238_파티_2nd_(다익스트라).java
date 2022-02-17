package p1238_파티;

//2번째 풀기 => 그래프 뒤집기

import java.io.*;
import java.util.*;

public class Main {
	static int N, M, X;
	static int u, v, w;
	static int start, end;
	static ArrayList<info> [] Map, Map_reverse;
	static int[] Dist, Dist_reverse;
	static int[] Answer;
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
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());
		
		Map = new ArrayList[N + 1];
		Map_reverse = new ArrayList[N + 1];
		Dist = new int[N + 1];
		Dist_reverse = new int[N + 1];
		
		Answer = new int[N + 1];
		
		for(int i = 1; i <= N; i++) {
			Map[i] = new ArrayList<>();
			Map_reverse[i] = new ArrayList<>();
			Dist[i] = INF;
			Dist_reverse[i] = INF;
		}
		
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			u = Integer.parseInt(st.nextToken());
			v = Integer.parseInt(st.nextToken());
			w = Integer.parseInt(st.nextToken());
			
			Map[u].add(new info(v, w));
			Map_reverse[v].add(new info(u, w));
		}
		
		//1. X 에서 전체
		FindShortestPath(X);
		Answer = Dist.clone();
		
		Dist = Dist_reverse;
		Map = Map_reverse;
		
		//2. 전체에서 X까지
		FindShortestPath(X);
		for(int i = 1; i <= N; i++) {
			Answer[i] += Dist[i];
		}
		
		
		int max = 0;
		for(int i = 1; i <= N; i++) {
			max = Math.max(max, Answer[i]);
		}
		
		bw.write(String.valueOf(max));
		
		bw.flush();
		bw.close();
	}
	
	static void init() {
		
		for(int i = 1; i <= N; i++) {
			Dist[i] = INF;
		}
		
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
