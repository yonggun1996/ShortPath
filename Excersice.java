package ShortPath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
플로이드 와샬 알고리즘으로 각 노드의 길이를 구한다
출발노드, 왕복노드를 정하고 값을 더한다(출발노드와 도탁노드는 값이 달라야 한다)
v까지 다 더했을 때 최소값이 답이 된다
 */
public class Excersice {
	
	static final int INF = 10000000;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int v = Integer.parseInt(st.nextToken());
		int e = Integer.parseInt(st.nextToken());
		
		int[][] table = new int[v + 1][v + 1];
		
		for(int i = 0; i <= v; i++) {
			Arrays.fill(table[i], INF);
			table[i][i] = 0;
		}
		
		for(int i = 0; i < e; i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			table[a][b] = c;
		}
		
		for(int i = 1; i <= v; i++) {
			for(int j = 1; j <= v; j++) {
				for(int k = 1; k <= v; k++) {
					if(i == j || i == k || j == k) continue;
					
					table[j][k] = Math.min(table[j][k], table[j][i] + table[i][k]);
				}
			}
		}
		
		int answer = INF;
		
		for(int i = 1; i <= v; i++) {
			for(int j = i + 1; j <= v; j++) {
				answer = Math.min(answer, table[i][j] + table[j][i]);
			}
		}
		
		if(answer >= INF) {
			System.out.println(-1);
		}else {
			System.out.println(answer);
		}
	}

}
