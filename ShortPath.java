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
			if(o1.weight > o2.weight) {//������ ������ ����ġ�� ������ �켱������ ����
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
		int V = in.nextInt();//������ ����
		int e = in.nextInt();//������ ����
		int k = in.nextInt();//�����ϴ� ����
		
		km = new int[V + 1];
		visit = new boolean[V + 1];
		Arrays.fill(km, INF);
		km[k] = 0;
		
		for(int i = 0; i < V + 1; i++) {
			ArrayList<NodeData> list = new ArrayList<NodeData>();
			map.add(list);
		}
		
		for(int i = 0; i < e; i++) {
			int u = in.nextInt();//���۳��
			int v = in.nextInt();//�������
			int w = in.nextInt();//����ġ
			
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
			
			if(!visit[nodenum]) {//��忡 �湮���� �ʾҴٸ�
				ArrayList<NodeData> list = map.get(nodenum);
				for(int i = 0; i < list.size(); i++) {
					int idx = list.get(i).finish;
					int now_weight = list.get(i).weight;
					
					if(km[idx] > weight + now_weight) {//���� ��带 ���ļ� ���°� �����ٸ� �Ÿ��� �ٲ۴�
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
	int finish;//�������
	int weight;//����ġ
	
	public NodeData(int v, int w) {
		finish = v;
		weight = w;
	}
}
