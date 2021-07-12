package ShortPath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class SpecialShortPath {
	
	static final int INF = 10000000;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(st.nextToken());
		int e = Integer.parseInt(st.nextToken());
		
		int[][] table = new int[n + 1][n + 1];
		
		for(int i = 1; i <= n; i++) {
			int[] arr = table[i];
			Arrays.fill(arr, INF);
			table[i] = arr;
			
			table[i][i] = 0;
		}
		
		for(int i = 0; i < e; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			table[a][b] = c;
			table[b][a] = c;
		}
		
		st = new StringTokenizer(br.readLine());
		int stop1 = Integer.parseInt(st.nextToken());
		int stop2 = Integer.parseInt(st.nextToken());
		
		for(int i = 1; i <= n; i++) {//거쳐갈 노드
			for(int j = 1; j <= n; j++) {//시작노드
				for(int k = 1; k <= n; k++) {//도착노드
					if(j == k) continue;
					
					table[j][k] = Math.min(table[j][k], table[j][i] + table[i][k]);
				}
			}
		}
		
		System.out.println(table[1][stop1]);
		System.out.println(table[stop1][stop2]);
		System.out.println(table[stop2][n]);
		
		System.out.println(table[1][stop2]);
		System.out.println(table[stop2][stop1]);
		System.out.println(table[stop1][n]);
		
		int answer = Math.min(table[1][stop1] + table[stop1][stop2] + table[stop2][n], 
				table[1][stop2] + table[stop2][stop1] + table[stop1][n]);
		
		if(answer >= INF) {
			System.out.println(-1);
		}else {
			System.out.println(answer);
		}
	}

}
