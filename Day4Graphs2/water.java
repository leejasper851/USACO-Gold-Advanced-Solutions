package Day4Graphs2;

import java.io.*;
import java.util.*;

public class water {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		int numPasts = Integer.parseInt(reader.readLine());
		int[] wellCost = new int[numPasts];
		for (int i = 0; i < numPasts; i++) {
			wellCost[i] = Integer.parseInt(reader.readLine());
		}
		int[][] adjMat = new int[numPasts][numPasts];
		for (int i = 0; i < numPasts; i++) {
			StringTokenizer st = new StringTokenizer(reader.readLine());
			for (int j = 0; j < numPasts; j++) {
				adjMat[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		reader.close();
		
		int minCost = Integer.MAX_VALUE;
		for (int i = 0; i < numPasts; i++) {
			int cost = 0;
			int[] dist = new int[numPasts];
			for (int j = 0; j < numPasts; j++) {
				dist[j] = Integer.MAX_VALUE;
			}
			boolean[] visited = new boolean[numPasts];
			ArrayList<Integer> groupCost = new ArrayList<Integer>();
			int[] wellGroup = new int[numPasts];
			int currNode = i;
			dist[currNode] = 0;
			cost += wellCost[currNode];
			groupCost.add(wellCost[currNode]);
			wellGroup[currNode] = 0;
			
			while (true) {
				visited[currNode] = true;
				int minDist = Integer.MAX_VALUE;
				int minPast = -1;
				for (int j = 0; j < numPasts; j++) {
					if (!visited[j]) {
						if (adjMat[currNode][j] < dist[j]) {
							dist[j] = adjMat[currNode][j];
							wellGroup[j] = wellGroup[currNode];
						}
						int wellDiff = wellCost[j] - groupCost.get(wellGroup[j]);
						int costDiff = Math.min(dist[j] + Math.min(wellDiff, 0), wellCost[j]);
						if (costDiff < minDist) {
							minDist = costDiff;
							minPast = j;
						}
					}
				}
				if (minDist == Integer.MAX_VALUE) {
					break;
				}
				
				currNode = minPast;
				int wellDiff = wellCost[minPast] - groupCost.get(wellGroup[minPast]);
				if (wellCost[minPast] < dist[minPast] + Math.min(wellDiff, 0)) {
					dist[minPast] = wellCost[minPast];
					groupCost.add(wellCost[minPast]);
					wellGroup[minPast] = groupCost.size()-1;
				} else {
					if (wellDiff < 0) {
						dist[minPast] += wellDiff;
						groupCost.set(wellGroup[minPast], wellCost[minPast]);
					}
				}
				cost += dist[minPast];
			}
			
			if (cost < minCost) {
				minCost = cost;
			}
		}
		writer.println(minCost);
		writer.close();
	}
}