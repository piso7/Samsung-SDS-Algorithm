package p2342;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static int[] Direction;
	static int[][][] DP;
	static int INF;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		
//		N = st.countTokens();
//		N--;
		
		Direction = new int[100000 + 1];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		int now;
		for(int i = 1;;i++) {
			now = Integer.parseInt(st.nextToken());
			if(now == 0) {
				N = i - 1;
				break;
			}
			
			Direction[i] = now;
			
		}
		
		//Direction = new int[N + 1];
		
		
		INF = Integer.MAX_VALUE;
		DP = new int[N + 1][5][5];
		
//		for(int i = 1; i <= N; i++) {
//			Direction[i] = Integer.parseInt(st.nextToken());
////			System.out.print(Direction[i] + " ");
//		}
		
		for(int i = 1; i <= N; i++) {
			for(int j = 0; j <= 4; j++) {
				for(int k = 0; k <= 4; k++) {
					DP[i][j][k] = INF;
				}
			}
		}
		
		//첫발을 오른발로 가느냐 왼발로 가느냐
		DP[1][0][Direction[1]] = 2;
		DP[1][Direction[1]][0] = 2;
		
		int next;
		for(int i = 1; i <= N - 1; i++) {
			for(int j = 0; j <= 4; j++) {
				for(int k = 0; k <= 4; k++) {
					
					if(DP[i][j][k] != INF) {
						
						next = Direction[i + 1]; //다음으로 갈 곳
						
						if(k != next) {
							DP[i+1][next][k] = Math.min(DP[i+1][next][k], DP[i][j][k] + cost(j ,next));
						}
						
						if(j != next) {
							DP[i+1][j][next] = Math.min(DP[i+1][j][next], DP[i][j][k] + cost(k ,next));
						}
							
						}
					}
				}
			}
		
		
		int Answer = INF;
		for(int j = 0; j <= 4; j++) {
			for(int k = 0; k <= 4; k++) {
				Answer = Math.min(Answer, DP[N][j][k]);
			}
		}
		
		System.out.println(Answer);
		
	}
	
	static int cost(int from, int to) {
    //함수 내 if-else 순서 주의!!
		if(from == to)
			return 1;
		else if(from == 0)
			return 2;
		else if(Math.abs(from - to) == 2)
			return 4;
		else
			return 3;
	}

}
