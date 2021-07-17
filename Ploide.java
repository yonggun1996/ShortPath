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
		
		int n = Integer.parseInt(br.readLine());//������ ����
		int m = Integer.parseInt(br.readLine());//������ ����
		
		table = new int[n + 1][n + 1];
		for(int i = 1; i <= n; i++) {
			Arrays.fill(table[i], INF);
			table[i][i] = 0;
		}
		
		for(int i = 0; i < m; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());//���� ���۵���
			int b = Integer.parseInt(st.nextToken());//���� ��������
			int c = Integer.parseInt(st.nextToken());//������ Ÿ�µ� ��� ���
			
			int min = Math.min(table[a][b], c);
			table[a][b] = min;
		}
		
		for(int i = 1; i <= n; i++) {//��ġ�� ���
			for(int j = 1; j <= n; j++) {//���۳��
				for(int k = 1; k <= n; k++) {//�����
					if(i == j || j == k || i == k) continue;
					
					int min = Math.min(table[j][k], table[j][i] + table[i][k]);
					table[j][k] = min;
				}
			}
		}
		
		StringBuilder answer = new StringBuilder();
		for(int i = 1; i <= n; i++) {
			for(int j = 1; j <= n; j++) {
				if(table[i][j] == INF) {//��ΰ� ������ 0���� ����ؾ� �Ѵ�
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
