package p1922_네트워크연결;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
	static int N, M;
	static int n1, n2, w;
	static node[] list;
	static int[] group;
	static int sum = 0;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		list = new node[M + 1]; //간선리스트
		group = new int[N + 1]; //서로소 집합 배열
		
		for(int i = 1; i <= M; i++) { //입력받음
			st = new StringTokenizer(br.readLine());
			n1 = Integer.parseInt(st.nextToken());
			n2 = Integer.parseInt(st.nextToken());
			w = Integer.parseInt(st.nextToken());
			
			list[i] = new node(n1, n2, w);
			
//			list[i].n1 = n1;
//			list[i].n2 = n2;
//			list[i].weight = w;
		}
		
		for(int i = 1; i <= N; i++) {
			group[i] = i;
		}
		
		Arrays.sort(list, 1, M + 1); //가중치 오름차순 정렬
		
		int ConnectedNode = 0; //간선을 N-1개 연결할경우 최소신장트리 완성
		for(int i = 1; i <= M; i ++) {
			if(find(list[i].n1) != find(list[i].n2)) {
				union(list[i].n1, list[i].n2);
				sum += list[i].weight;
				ConnectedNode++;
			}
			
			if (ConnectedNode == N - 1)
				break;
		}
		
		System.out.println(sum);
		
	}
	
	static class node implements Comparable<node>{
		int n1;
		int n2;
		int weight;
		
		public node(int n1, int n2, int weight) {
			super();
			this.n1 = n1;
			this.n2 = n2;
			this.weight = weight;
		}

		@Override
		public int compareTo(node o) {
			// TODO Auto-generated method stub
			return Integer.compare(this.weight, o.weight);
		}
	}
	
	static void union(int a, int b){
		int parenta = find(a);
		int parentb = find(b);
		group[parenta] = parentb;
	}
	
	static int find(int i) {
		if (group[i] == i) 
			return i;
		else
			return group[i] = find(group[i]);
	}
	

}
