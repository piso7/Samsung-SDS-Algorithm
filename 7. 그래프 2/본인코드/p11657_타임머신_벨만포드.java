package p11657_타임머신;

import java.io.*;
import java.util.*;

public class Main {
	static int N, M, X;
	static int u, v, w;
	static int start, end;
	static info [] Map;
	static long[] Dist;
	static final int INF = Integer.MAX_VALUE;
	
	static class info implements Comparable<info>{
		int from;
		int to;
		int distance;
		
		public info(int from, int to, int distance) {
			super();
			this.from = from;
			this.to = to;
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
		
		Map = new info[M + 1]; //간선리스트
		Dist = new long[N + 1];
		
		for(int i = 1; i <= N; i++) {
			Dist[i] = INF;
		}
		
		for(int i = 1; i <= M; i++) {
			st = new StringTokenizer(br.readLine());
			u = Integer.parseInt(st.nextToken());
			v = Integer.parseInt(st.nextToken());
			w = Integer.parseInt(st.nextToken());
			
			Map[i] = new info(u, v, w);
		}
		
		if(BellmanFord(1)) {
			bw.write("-1");
		}
		else {
			for(int i = 2; i <= N; i++) {
				if(Dist[i] != INF) {
					bw.write(Dist[i] + "\n");
				}
				else {
					bw.write("-1" + "\n");
				}
			}
		}
		
		bw.flush();
		bw.close();
	}
	
	static boolean BellmanFord(int start) {
		Dist[start] = 0;
		
		for(int i = 1; i <= N; i++) { //N번 순회
			for(int j = 1; j <= M; j++) { //모든 간선을 순회합니다
				info now = Map[j];
				if(Dist[now.from] != INF) {
					if(Dist[now.to] > Dist[now.from] + now.distance) {
						Dist[now.to] = Dist[now.from] + now.distance;
						
						if(i == N) return true; //음수 순환 존재
					}
				}
				
			}
		}
		
		return false;
	}


}
