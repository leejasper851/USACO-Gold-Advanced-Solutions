package Day5Graphs3;

import java.io.*;
import java.util.*;

public class jobhunt {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int moneyLim = Integer.parseInt(st.nextToken());
		int numPaths = Integer.parseInt(st.nextToken());
		int numCities = Integer.parseInt(st.nextToken());
		int numRoutes = Integer.parseInt(st.nextToken());
		int startCity = Integer.parseInt(st.nextToken());
		ArrayList<Edge> edges = new ArrayList<Edge>();
		for (int i = 0; i < numPaths; i++) {
			st = new StringTokenizer(reader.readLine());
			int fromCity = Integer.parseInt(st.nextToken());
			int toCity = Integer.parseInt(st.nextToken());
			edges.add(new Edge(fromCity, toCity, -moneyLim));
		}
		for (int i = 0; i < numRoutes; i++) {
			st = new StringTokenizer(reader.readLine());
			int fromCity = Integer.parseInt(st.nextToken());
			int toCity = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			edges.add(new Edge(fromCity, toCity, cost - moneyLim));
		}
		reader.close();
		
		int[] dist = new int[numCities+1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[startCity] = 0;
		for (int i = 0; i < numCities - 1; i++) {
			for (int j = 0; j < edges.size(); j++) {
				Edge edge = edges.get(j);
				if (dist[edge.fromCity] + edge.weight < dist[edge.toCity]) {
					dist[edge.toCity] = dist[edge.fromCity] + edge.weight;
				}
			}
		}
		
		boolean noLim = false;
		for (int i = 0; i < edges.size(); i++) {
			Edge edge = edges.get(i);
			if (dist[edge.fromCity] + edge.weight < dist[edge.toCity]) {
				noLim = true;
				writer.println(-1);
				break;
			}
		}
		
		if (!noLim) {
			int minDist = Integer.MAX_VALUE;
			for (int i = 1; i <= numCities; i++) {
				if (dist[i] < minDist) {
					minDist = dist[i];
				}
			}
			writer.println(moneyLim - minDist);
		}
		writer.close();
	}
	
	private static class Edge {
		public int fromCity;
		public int toCity;
		public int weight;
		
		public Edge(int f, int t, int w) {
			fromCity = f;
			toCity = t;
			weight = w;
		}
	}
}