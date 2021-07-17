package ShortPath;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.IOException;

public class KCMTravle {
	
	static final int INF = 10000001;
	static int[][] dp;
	static ArrayList<ArrayList<TravleData>> map;
	static PriorityQueue<TravleData> pq;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder answer = new StringBuilder();
		
		int t = Integer.parseInt(br.readLine());
		for(int i = 0; i < t; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int n = Integer.parseInt(st.nextToken());//공항의 수
			int m = Integer.parseInt(st.nextToken());//지원 비용
			int k = Integer.parseInt(st.nextToken());//티켓정보의 수
			
			dp = new int[n + 1][m + 1];
			
			pq = new PriorityQueue<TravleData>(new Comparator<TravleData>() {

				@Override
				public int compare(TravleData t1, TravleData t2) {
					return t1.dist - t2.dist;
				}
			});
			
			map = new ArrayList<ArrayList<TravleData>>();
			for(int j = 0; j <= n; j++) {
				ArrayList<TravleData> minilist = new ArrayList<TravleData>();
				map.add(minilist);
				Arrays.fill(dp[j], INF);
			}
			
			for(int j = 0; j < k; j++) {
				st = new StringTokenizer(br.readLine());
				
				int u = Integer.parseInt(st.nextToken());//출발공항
				int v = Integer.parseInt(st.nextToken());//도착공항
				int c = Integer.parseInt(st.nextToken());//비용
				int d = Integer.parseInt(st.nextToken());//소요시간
				
				ArrayList<TravleData> minilist = map.get(u);
				minilist.add(new TravleData(c, d, v));
				map.set(u, minilist);
			}
			
			dp[i][0] = 0;
			pq.offer(new TravleData(0, 0, 1));
			search(m);
			
			int min = INF;
			for(int j = 0; j <= m; j++) {
				if(dp[n][j] < min) {
					min = dp[n][j];
				}
			}
			
			if(min >= INF) {
				answer.append("Poor KCM");
			}else {
				answer.append(min);
			}
			
			answer.append("\n");
		}
		
		System.out.println(answer);
	}
	
	public static void search(int max_cost) {
		while(!pq.isEmpty()) {
			TravleData td = pq.poll();
			
			int now_cost = td.cost;//현재 위치의 비용
			int now_dist = td.dist;//현재까지의 거리
			int now_dest = td.dest;//현재 있는 노드
			
			ArrayList<TravleData> minilist = map.get(now_dest);
			if(now_cost <= max_cost) {
				for(int i = 0; i < minilist.size(); i++) {
					TravleData td2 = minilist.get(i);
					
					int next_cost = td2.cost;
					int next_dist = td2.dist;
					
					if(now_cost + next_cost > max_cost) continue;//현재 쓴 비용과 다음 경로에 쓸 비용을 더한 값이 최대 비용보다 크다면 탐색하지 않는다
					
					//기존에 계산한 거리값보다 새로 계산한 거리값이 작다면 큐에 담고 값을 바꾼다
					if(dp[td2.dest][now_cost + next_cost] > now_dist + next_dist) {
						pq.offer(new TravleData(now_cost + next_cost, now_dist + next_dist, td2.dest));
						dp[td2.dest][now_cost + next_cost] = now_dist + next_dist;
					}
				}
			}
		}
	}

}

class TravleData{
	int cost;//비용
	int dist;//거리
	int dest;//현재 노드
	
	public TravleData(int c, int d, int v) {
		this.cost = c;
		this.dist = d;
		this.dest = v;
	}
}
