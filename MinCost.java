package com.company;

import java.io.*;
import java.util.*;

public class MinCost {

    static final int INF = 1000000000;//방문하지 않은 지역의 거리
    static HashMap<Integer, ArrayList<CityData>> map = new HashMap<>();

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int bus = Integer.parseInt(br.readLine());

        for(int i = 1; i <= n; i++){
            map.put(i, new ArrayList<CityData>());
        }

        for(int i = 0; i < bus; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int city1 = Integer.parseInt(st.nextToken());//도시1
            int city2 = Integer.parseInt(st.nextToken());//도시2
            int dist = Integer.parseInt(st.nextToken());//거리

            ArrayList<CityData> list1 = map.get(city1);//리스트에 담고 map에 담아 그래프 생성하는 과정
            list1.add(new CityData(city2, dist));
            map.put(city1, list1);
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        int answer = search_shortdist(start, end);//다익스트라 알고리즘을 이용한 메서드로 이동
        System.out.println(answer);
    }

    public static int search_shortdist(int start, int end){
        //거리가 우선인 우선순위 큐 생성
        PriorityQueue<CityData> pq = new PriorityQueue<>(new Comparator<CityData>(){
            @Override
            public int compare(CityData cd1, CityData cd2){
                return cd1.dist - cd2.dist;
            }
        });

        int[] dist_arr = new int[map.size() + 1];//시작지점의 거리를 담는 배열
        Arrays.fill(dist_arr, INF);

        pq.offer(new CityData(start,0));//start 지역의 거리는 0임을 우선순위 큐에 담는다.

        while(!pq.isEmpty()){
            CityData citydata = pq.poll();
            int city = citydata.city;
            int dist = citydata.dist;

            if(dist_arr[city] > dist){//기존에 거리보다 작으면 배열 갱신/ 우선순위 큐에 담는다.
                dist_arr[city] = dist;
                ArrayList<CityData> list = map.get(city);
                for(int i = 0; i < list.size(); i++){
                    CityData next = list.get(i);
                    pq.offer(new CityData(next.city, next.dist + dist));
                }
            }
        }

        return dist_arr[end];//도착지점까지의 최단거리를 반환
    }

}

class CityData{
    int city;//출발도시
    int dist;//누적된 비용

    public CityData(int city, int dist){
        this.city = city;
        this.dist = dist;
    }
}
