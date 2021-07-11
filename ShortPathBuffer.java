package ShortPath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ShortPathBuffer {
	
	static PriorityQueue<ShortpathBufferNode> pq = new PriorityQueue<ShortpathBufferNode>(new Comparator<ShortpathBufferNode>() {

		@Override
		public int compare(ShortpathBufferNode o1, ShortpathBufferNode o2) {
			if(o1.weight > o2.weight) {//기존에 들어오는 가중치가 작은게 우선순위가 먼저
				return 1;
			}else {
				return -1;
			}
			
		}
	}); 
	
	static final int INF = 1000000;
	static ArrayList<ArrayList<ShortpathBufferNode>> map = new ArrayList<ArrayList<ShortpathBufferNode>>();
	static int[] km;
	static boolean[] visit;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int V = Integer.parseInt(st.nextToken());//정점의 개수
		int e = Integer.parseInt(st.nextToken());//간선의 개수
		
		int k = Integer.parseInt(br.readLine());//시작하는 정점
		
		km = new int[V + 1];
		visit = new boolean[V + 1];
		Arrays.fill(km, INF);
		km[k] = 0;
		
		for(int i = 0; i < V + 1; i++) {
			ArrayList<ShortpathBufferNode> list = new ArrayList<ShortpathBufferNode>();
			map.add(list);
		}
		
		for(int i = 0; i < e; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());//시작노드
			int v = Integer.parseInt(st.nextToken());//도착노드
			int w = Integer.parseInt(st.nextToken());//가중치
			
			ArrayList<ShortpathBufferNode> list = map.get(u);
			list.add(new ShortpathBufferNode(v, w));
			map.set(u, list);
		}
		
		pq.add(new ShortpathBufferNode(k, 0));
		search(k);
		
		StringBuilder answer = new StringBuilder();
		for(int i = 1; i < km.length; i++) {
			if(km[i] == INF) {
				//System.out.println("INF");
				answer.append("INF\n");
			}else {
				//System.out.println(km[i]);
				answer.append(km[i]);
				answer.append("\n");
			}
		}
		
		System.out.println(answer.toString());
	}
	
	public static void search(int k) {
		while(!pq.isEmpty()) {
			ShortpathBufferNode nd = pq.poll();
			int nodenum = nd.finish;
			int weight = nd.weight;
			
			if(!visit[nodenum]) {//노드에 방문하지 않았다면
				ArrayList<ShortpathBufferNode> list = map.get(nodenum);
				for(int i = 0; i < list.size(); i++) {
					int idx = list.get(i).finish;
					int now_weight = list.get(i).weight;
					
					if(km[idx] > weight + now_weight) {//이전 노드를 거쳐서 오는게 빠르다면 거리를 바꾼다
						km[idx] = weight + now_weight;
						pq.add(new ShortpathBufferNode(idx, km[idx]));
					}
					
				}
				
				visit[nodenum] = true;
			}
		}
	}

}

class ShortpathBufferNode{
	int finish;//도착노드
	int weight;//가중치
	
	public ShortpathBufferNode(int v, int w) {
		finish = v;
		weight = w;
	}
}
