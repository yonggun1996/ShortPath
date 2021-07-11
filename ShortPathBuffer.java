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
			if(o1.weight > o2.weight) {//������ ������ ����ġ�� ������ �켱������ ����
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
		int V = Integer.parseInt(st.nextToken());//������ ����
		int e = Integer.parseInt(st.nextToken());//������ ����
		
		int k = Integer.parseInt(br.readLine());//�����ϴ� ����
		
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
			int u = Integer.parseInt(st.nextToken());//���۳��
			int v = Integer.parseInt(st.nextToken());//�������
			int w = Integer.parseInt(st.nextToken());//����ġ
			
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
			
			if(!visit[nodenum]) {//��忡 �湮���� �ʾҴٸ�
				ArrayList<ShortpathBufferNode> list = map.get(nodenum);
				for(int i = 0; i < list.size(); i++) {
					int idx = list.get(i).finish;
					int now_weight = list.get(i).weight;
					
					if(km[idx] > weight + now_weight) {//���� ��带 ���ļ� ���°� �����ٸ� �Ÿ��� �ٲ۴�
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
	int finish;//�������
	int weight;//����ġ
	
	public ShortpathBufferNode(int v, int w) {
		finish = v;
		weight = w;
	}
}
