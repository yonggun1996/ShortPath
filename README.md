# 최단거리 찾기 알고리즘

### 개요

예를 들어 각각의 목적지가 있고, 경로에 따른 가중치가 있다고 가정합시다. 아래와 같은 이미지로 말이죠 이 때 최단 경로들을 구하는 알고리즘들은 무엇이 있을까요?

![최단경로 이미지](https://user-images.githubusercontent.com/68115246/126433989-5689242f-0e27-48d7-8028-8fdf44b9da8a.png)

### 다익스트라 알고리즘

다익스트라 알고리즘은 특정 경로에서 특정 경로까지 가는 최단 경로를 구할 때 필요한 알고리즘 입니다.

1. 우선순위 큐를 생성합니다. 이는 해당 경로에서 가장 가까운 경로가 우선순위가 먼저인 큐를 생성합니다.
2. 1번 노드부터 각 노드까지의 거리를 최신화 하기 위해 배열을 생성합니다.
3. 행선지와 거리를 속성으로 한 클래스를 선언하고 이 클래스를 담는 우선순위 큐에 1번 노드의 각각의 행선지와 거리를 담습니다.

![최단경로2](https://user-images.githubusercontent.com/68115246/126434282-376cb52d-46b0-41e8-a50a-009402f9dbd2.png)

4. 우선순위 큐에서 꺼낸 노드를 확인합니다. 꺼낸 노드에서 또 다른 행선지의 거리와 꺼낸 거리를 더해 기존에 기록했던 배열값보다 작으면 값이 바뀝니다.

그림으로 설명하자면 1번 노드와 연결되어있는 모든 노드를 담고 거리를 배열에 담았습니다.

![최단경로3](https://user-images.githubusercontent.com/68115246/126434547-5e441d4d-b618-4838-9117-ada4d322176f.png)

이때 2번노드로 향하는 데이터를 꺼냅니다. 일단 1번노드는 이미 확인했기 때문에 큐에 담지 않았습니다. 이어서 2번 노드에서 3번노드가 연결이 됩니다. 
큐에서 꺼낸 거리 3과 2에서 3으로 향하는 거리 4와 6을 비교합니다. 4가 더 작기 때문에 값을 바꿔줍니다.

![최단경로4](https://user-images.githubusercontent.com/68115246/126434761-3be7e491-5dd9-46f8-895b-98830de6302c.png)

또한 2에서 5로 연결하는 경로도 있습니다. 1에서 2로 가는 경로 3 + 2에서 5로 가는 경로 2를 더하면 5이고 기존에 있던 7보다 작기 때문에 값을 바꿔줍니다.

![최단경로5](https://user-images.githubusercontent.com/68115246/126434993-5bb26555-e676-4240-9725-84e82c0e6373.png)

이렇게 계속해서 3번과 4번을 지속적으로 수행하면서, 큐가 다 비어있다면 실행을 멈춥니다.
그렇게 해서 다음과 같이 배열이 바뀝니다.

![최단경로6](https://user-images.githubusercontent.com/68115246/126435262-aff5178c-eb03-41a7-92b3-9d880c738b11.png)

이를 코드로 풀자면 다음과 같습니다.(백준 알고리즘 1753번 문제)

```java
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
```

### 플로이드 와샬 알고리즘

이 알고리즘은 모든 정점에서 모든 정점까지 정점을 알아야 할 때 구하는 알고리즘 입니다.

그림과 같은 그래프가 있다고 가정합니다.(양방향 그래프 입니다.)

![최단경로7](https://user-images.githubusercontent.com/68115246/126439563-928785fe-7b3e-4062-9ec5-4801adba4336.png)


1. 우선 각 노드에서 노드까지의 거리를 2차원 배열에 담습니다.

![최단경로8](https://user-images.githubusercontent.com/68115246/126439620-959b03e9-f98a-440b-9a2f-55f6ab6ff661.png)

2. 코드로 표현을 하면 3중 for문을 사용합니다. 먼저 1번을 경유 지점으로 정하고 각각 출발지점과 도착지점을 잡습니다.
아래 그림을 토대로 설명하자면 노란색 부분이 고쳐야할 대상 입니다.

![최단경로9](https://user-images.githubusercontent.com/68115246/126439671-26033d7d-5fa8-41c4-b4cb-ca759f2af491.png)

먼저 2행 3열의 데이터를 봅니다. 기존에 있던 2행 3열의 데이터롸 2행 1열을 향한 후 1행 3열을 향하는 데이터를 더한 값을 비교를 합니다.
전자는 3, 후자는 (3 + INF)가 됩니다. 3이 작기 때문에 값이 변동되지 않습니다.

다음 2행 4열을 봅니다. 기존에 있던 2행 4열의 데이터 7과 2행 1열 데이터와 1행 4열의 데이터를 더한 값을 비교합니다.
전자는 7, 후자는 (3 + 1) = 4가 됩니다. 4가 더 작기 때문에 값을 변경합니다.

![최단경로10](https://user-images.githubusercontent.com/68115246/126439947-26d1a15b-093b-4972-b304-fa3878247aed.png)

다음 3행 2열을 확인합니다. 3행 2열 데이터와 3행 1열과 1행 2열의 데이터를 더한 값을 비교합니다.
전자는 3, 후자는 (INF + 3) 입니다. 값이 변경되지 않습니다.

다음은 3행 4열을 확인합니다. 3행 4열의 데이터 5와 3행 1열의 값과 3행 4열의 값을 더한 값과 비교합니다.
전자는 5, 후자는 (INF + 1) 입니다. 값이 변동되지 않습니다.

다음은 4행 2열을 확인합니다. 4행 2열의 데이터와 4행 1열의 데이터와 1행 2열의 데이터를 더한 값과 비교합니다.
전자는 7, 후자는 (1 + 3) 입니다. 값이 4로 바뀝니다.

![최단경로11](https://user-images.githubusercontent.com/68115246/126440350-89de4318-7bbd-4fae-aff8-e1c87a3c1f75.png)

다음은 4행 3열을 확인합니다. 4행 3열의 데이터 5와 4행 1열의 데이터와 1행 3열의 데이터를 더한 값을 비교합니다.
전자는 5 후자는 (1 + INF) 입니다. 값이 바뀌지 않습니다.

이런식으로 4번 더 실행하게 되면서 최종적으로 값이 이렇게 됩니다.

![최단경로12](https://user-images.githubusercontent.com/68115246/126440816-74d436d0-13bf-4365-a0cd-536a685c3b81.png)

플로이드 와샬 소스코드 입니다.(백준 알고리즘 11404번 문제에 따른 소스코드)
```java
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
		
		int n = Integer.parseInt(br.readLine());//도시의 개수
		int m = Integer.parseInt(br.readLine());//버스의 개수
		
		table = new int[n + 1][n + 1];
		for(int i = 1; i <= n; i++) {
			Arrays.fill(table[i], INF);
			table[i][i] = 0;
		}
		
		for(int i = 0; i < m; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());//버스 시작도시
			int b = Integer.parseInt(st.nextToken());//버스 도착도시
			int c = Integer.parseInt(st.nextToken());//버스를 타는데 드는 비용
			
			int min = Math.min(table[a][b], c);
			table[a][b] = min;
		}
		
		for(int i = 1; i <= n; i++) {//거치는 노드
			for(int j = 1; j <= n; j++) {//시작노드
				for(int k = 1; k <= n; k++) {//끝노드
					if(i == j || j == k || i == k) continue;
					
					int min = Math.min(table[j][k], table[j][i] + table[i][k]);
					table[j][k] = min;
				}
			}
		}
		
		StringBuilder answer = new StringBuilder();
		for(int i = 1; i <= n; i++) {
			for(int j = 1; j <= n; j++) {
				if(table[i][j] == INF) {//경로가 없으면 0으로 출력해야 한다
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
```
