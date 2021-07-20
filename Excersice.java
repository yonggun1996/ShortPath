package ShortPath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
�÷��̵� �ͼ� �˰������� �� ����� ���̸� ���Ѵ�
��߳��, �պ���带 ���ϰ� ���� ���Ѵ�(��߳��� ��Ź���� ���� �޶�� �Ѵ�)
v���� �� ������ �� �ּҰ��� ���� �ȴ�
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
