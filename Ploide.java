package ShortPath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Ploide {
	
	static int[][] table;
	static final int INF = 10000001;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());//도시의 개수
		int m = Integer.parseInt(br.readLine());//버스의 개수
		
		table = new int[n + 1][n + 1];
		for(int i = 1; i <= n; i++) {
			Arrays.fill(table[i], INF);
			table[i][i] = 0;
		}
		
		for(int i = 0; i < m; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());//버스 시작도시
			int b = Integer.parseInt(st.nextToken());//버스 도착도시
			int c = Integer.parseInt(st.nextToken());//버스를 타는데 드는 비용
			
			int min = Math.min(table[a][b], c);
			table[a][b] = min;
		}
		
		for(int i = 1; i <= n; i++) {//거치는 노드
			for(int j = 1; j <= n; j++) {//시작노드
				for(int k = 1; k <= n; k++) {//끝노드
					if(i == j || j == k || i == k) continue;
					
					int min = Math.min(table[j][k], table[j][i] + table[i][k]);
					table[j][k] = min;
				}
			}
		}
		
		StringBuilder answer = new StringBuilder();
		for(int i = 1; i <= n; i++) {
			for(int j = 1; j <= n; j++) {
				if(table[i][j] == INF) {//경로가 없으면 0으로 출력해야 한다
					table[i][j] = 0;
				}
				answer.append(table[i][j]);
				answer.append(" ");
			}
			answer.append("\n");
		}
		
		System.out.println(answer);
	}

}
