package p2252_줄세우기;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

//위상정렬
public class Main {
	static int N, M;
	static int from, to;
	static int Indegree[];
	static ArrayList<Integer>[] Map;
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); //N명의 학생 (정점 수)
		M = Integer.parseInt(st.nextToken()); //M번의 비교
		
		Indegree = new int[N + 1];
		Map = new ArrayList[N + 1];
		
		//그래프초기화
		for(int i = 1; i <= N; i++) {
			Map[i] = new ArrayList<>();
		}
		
		//진입차수가 0이 되어 탐색순서가 돌아온 정점을 담는 큐
		ArrayDeque<Integer> dq = new ArrayDeque<>();
		
		// 처음 들어온 학생 < 나중 들어온 학생 순서로 그래프 구상
		// 나중에 들어온 학생은 진입차수 + 1
		
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			
			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());
			Map[from].add(to);
			Indegree[to]++;
		}
		
		//그래프 탐색 - 최초로 탐색할 학생 검색
		for(int i = 1; i <= N; i++) {
			if(Indegree[i] == 0)
				dq.addLast(i);
		}
		
		while(dq.isEmpty() == false) {
			int now = dq.poll();
			
			System.out.print(now + " ");
			
			//인접한 노드를 검사하는데
			//해당 노드의 진입차수가 0일 경우 이미 큐에 존재하므로
			//0보다 큰 경우에 대해서만 탐색
			for(int next: Map[now]) {
				if(Indegree[next] > 0) {
					Indegree[next]--;
					if(Indegree[next] == 0) {
						dq.addLast(next);
					}
				}
			}
		}
		
		
	}

}
