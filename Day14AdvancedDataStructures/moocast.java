package Day14AdvancedDataStructures;

import java.io.*;
import java.util.*;

public class moocast {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		int numCows = Integer.parseInt(reader.readLine());
		int[][] cowCoords = new int[numCows][2];
		for (int i = 0; i < numCows; i++) {
			StringTokenizer st = new StringTokenizer(reader.readLine());
			cowCoords[i][0] = Integer.parseInt(st.nextToken());
			cowCoords[i][1] = Integer.parseInt(st.nextToken());
		}
		reader.close();
		
		double[] dist = new double[numCows];
		Arrays.fill(dist, Double.MAX_VALUE);
		boolean[] visited = new boolean[numCows];
		int currCow = 0;
		double maxDist = 0;
		while (true) {
			visited[currCow] = true;
			double minDist = Double.MAX_VALUE;
			int minCow = -1;
			for (int i = 0; i < numCows; i++) {
				if (visited[i]) {
					continue;
				}
				double currDist = distBtwn(cowCoords[currCow], cowCoords[i]);
				if (currDist < dist[i]) {
					dist[i] = currDist;
				}
				if (dist[i] < minDist) {
					minDist = dist[i];
					minCow = i;
				}
			}
			if (minCow == -1) {
				break;
			}
			maxDist = Math.max(maxDist, minDist);
			currCow = minCow;
		}
		writer.println(Math.round(maxDist * maxDist));
		writer.close();
	}
	
	private static double distBtwn(int[] coord1, int[] coord2) {
		return Math.sqrt(Math.pow(coord1[0] - coord2[0], 2) + Math.pow(coord1[1] - coord2[1], 2));
	}
}