package ShortPath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Timemarchine {
	
	static ArrayList<TimeMarchineData> map = new ArrayList<TimeMarchineData>();
	static long[] dist;
	static final int INF = 100000000;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		dist = new long[n + 1];
		Arrays.fill(dist, INF);
		
		for(int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			map.add(new TimeMarchineData(a, b, c));
		}
		
		if(search(n, 1)) {
			System.out.println(-1);
		}else {
			for(int i = 2; i < dist.length; i++) {
				if(dist[i] >= INF) {
					System.out.println(-1);
				}else {
					System.out.println(dist[i]);
				}
			}
		}
		
	}
	
	public static boolean search(int nodecount, int start) {
		boolean flag = false;
		dist[start] = 0;
		
		for(int i = 0; i <= nodecount; i++) {
			for(int j = 0; j < map.size(); j++) {
				int a = map.get(j).start;
				int b = map.get(j).dest;
				int c = map.get(j).dist;
				
				if(dist[a] != INF && dist[b] > dist[a] + c) {
					System.out.println("dist[" + b + "] 갱신 : " + dist[b] + "에서 " + (dist[a] + c) + "로");
					dist[b] = dist[a] + c;
					
					//노드의 개수만큼 계속 반복하면 음의 무한대 값이 될 수 있기 때문에 flag를 true로 초기화
					if(i == nodecount) {
						flag = true;
					}
				}
			}
		}
		
		return flag;
	}

}

class TimeMarchineData{
	int start;
	int dest;//도착지점
	int dist;//도착지점까지 거리
	
	public TimeMarchineData(int a, int b, int c) {
		start = a;
		dest = b;
		dist = c;
	}
}
