package ShortPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class ShortPath {
	
	static PriorityQueue<NodeData> pq = new PriorityQueue<NodeData>(new Comparator<NodeData>() {

		@Override
		public int compare(NodeData o1, NodeData o2) {
			if(o1.weight > o2.weight) {//기존에 들어오는 가중치가 작은게 우선순위가 먼저
				return 1;
			}else {
				return -1;
			}
			
		}
	}); 
	
	static final int INF = 1000000;
	static ArrayList<ArrayList<NodeData>> map = new ArrayList<ArrayList<NodeData>>();
	static int[] km;
	static boolean[] visit;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int V = in.nextInt();//정점의 개수
		int e = in.nextInt();//간선의 개수
		int k = in.nextInt();//시작하는 정점
		
		km = new int[V + 1];
		visit = new boolean[V + 1];
		Arrays.fill(km, INF);
		km[k] = 0;
		
		for(int i = 0; i < V + 1; i++) {
			ArrayList<NodeData> list = new ArrayList<NodeData>();
			map.add(list);
		}
		
		for(int i = 0; i < e; i++) {
			int u = in.nextInt();//시작노드
			int v = in.nextInt();//도착노드
			int w = in.nextInt();//가중치
			
			ArrayList<NodeData> list = map.get(u);
			list.add(new NodeData(v, w));
			map.set(u, list);
		}
		
		pq.add(new NodeData(k, 0));
		search(k);
		
		for(int i = 1; i < km.length; i++) {
			if(km[i] == INF) {
				System.out.println("INF");
			}else {
				System.out.println(km[i]);
			}
		}
	}
	
	public static void search(int k) {
		while(!pq.isEmpty()) {
			NodeData nd = pq.poll();
			int nodenum = nd.finish;
			int weight = nd.weight;
			
			if(!visit[nodenum]) {//노드에 방문하지 않았다면
				ArrayList<NodeData> list = map.get(nodenum);
				for(int i = 0; i < list.size(); i++) {
					int idx = list.get(i).finish;
					int now_weight = list.get(i).weight;
					
					if(km[idx] > weight + now_weight) {//이전 노드를 거쳐서 오는게 빠르다면 거리를 바꾼다
						km[idx] = weight + now_weight;
						pq.add(new NodeData(idx, km[idx]));
					}
					
				}
				
				visit[nodenum] = true;
			}
		}
	}

}

class NodeData{
	int finish;//도착노드
	int weight;//가중치
	
	public NodeData(int v, int w) {
		finish = v;
		weight = w;
	}
}
