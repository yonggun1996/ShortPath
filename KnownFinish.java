package ShortPath;

//문제풀이에 도움이 된 블로그 : https://gaegosoo.tistory.com/34
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class KnownFinish {
	
	static final int INF = 10000000;
	static ArrayList<ArrayList<KFNode>> map;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder answer = new StringBuilder();
		
		int testcase = Integer.parseInt(br.readLine());
		for(int i = 0; i < testcase; i++) {
			map = new ArrayList<ArrayList<KFNode>>();
			StringTokenizer st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken());//노드 개수
			int m = Integer.parseInt(st.nextToken());//길의 개수
			int t = Integer.parseInt(st.nextToken());//목적지 후보의 개수
			
			for(int j = 0; j <= n; j++) {
				ArrayList<KFNode> minilist = new ArrayList<KFNode>();
				map.add(minilist);
			}
			
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken());//출발지점
			
			//두개의 노드 사이 경로로 갔다는 걸 알려준다
			int g = Integer.parseInt(st.nextToken());
			int h = Integer.parseInt(st.nextToken());
			
			for(int j = 0; j < m; j++) {
				st = new StringTokenizer(br.readLine());
				int nd1 = Integer.parseInt(st.nextToken());
				int nd2 = Integer.parseInt(st.nextToken());
				long km = Integer.parseInt(st.nextToken());
				
				km *= 2;//후각을 감지하지 않은 경로는 짝수로 해둔다
				
				if((nd1 == g && nd2 == h) || (nd1 == h && nd2 == g)) {
					km--;//후각을 감지한 경로는 홀수로 만든다
				}
				
				ArrayList<KFNode> list1 = map.get(nd1);
				list1.add(new KFNode(nd2, km));
				map.set(nd1, list1);
				
				ArrayList<KFNode> list2 = map.get(nd2);
				list2.add(new KFNode(nd1, km));
				map.set(nd2, list2);
			}
			
			ArrayList<Integer> answer_list = new ArrayList<Integer>();
			for(int j = 0; j < t; j++) {
				int x = Integer.parseInt(br.readLine());
				long load = search(s, x);
				
				if(load % 2 == 1) {
					answer_list.add(x);
				}
			}
			
			Collections.sort(answer_list);
			
			for(int j = 0; j < answer_list.size(); j++) {
				answer.append(answer_list.get(j));
				answer.append(" ");
			}
			answer.append("\n");
			
		}
		
		System.out.println(answer.toString());
			
	}
	
	//다익스트라 알고리즘을 찾은 경로
	public static long search(int start, int finish) {
		PriorityQueue<KFNode> pq = new PriorityQueue<KFNode>(new Comparator<KFNode>() {

			@Override
			public int compare(KFNode k1, KFNode k2) {
				return (int) (k1.weight - k2.weight);
			}
		});
		
		pq.offer(new KFNode(start, 0));
		boolean[] visit = new boolean[map.size() + 1];
		long[] dist = new long[map.size() + 1];
		
		Arrays.fill(dist, INF);
		dist[start] = 0;
		
		while(!pq.isEmpty()) {
			KFNode kfnode = pq.poll();
			
			int startNode = kfnode.finish;
			long acc = kfnode.weight;
			
			if(!visit[startNode]) {
				ArrayList<KFNode> list = map.get(startNode);
				
				for(int i = 0; i < list.size(); i++) {
					int finishNode = list.get(i).finish;
					long weight = list.get(i).weight;
					
					pq.offer(new KFNode(finishNode, acc + weight));
					
					dist[finishNode] = Math.min(dist[finishNode], acc + weight);
				}
				
				visit[startNode] = true;
			}
		}
		
		return dist[finish];
	}
	
}

class KFNode{
	int finish;
	long weight;
	
	public KFNode(int finish, long weight) {
		this.finish = finish;
		this.weight = weight;
	}
}
