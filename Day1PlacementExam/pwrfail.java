package Day1PlacementExam;

import java.io.*;
import java.util.*;

public class pwrfail {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numPoles = Integer.parseInt(st.nextToken());
		int numWires = Integer.parseInt(st.nextToken());
		double maxLen = Double.parseDouble(reader.readLine());
		int[][] poleCoords = new int[numPoles+1][2];
		for (int i = 1; i <= numPoles; i++) {
			st = new StringTokenizer(reader.readLine());
			poleCoords[i][0] = Integer.parseInt(st.nextToken());
			poleCoords[i][1] = Integer.parseInt(st.nextToken());
		}
		boolean[][] adjMat = new boolean[numPoles+1][numPoles+1];
		for (int i = 0; i < numWires; i++) {
			st = new StringTokenizer(reader.readLine());
			int pole1 = Integer.parseInt(st.nextToken());
			int pole2 = Integer.parseInt(st.nextToken());
			adjMat[pole1][pole2] = true;
			adjMat[pole2][pole1] = true;
		}
		reader.close();
		
		double[] poleDist = new double[numPoles+1];
		for (int i = 0; i <= numPoles; i++) {
			poleDist[i] = Double.MAX_VALUE;
		}
		poleDist[1] = 0;
		boolean[] poleVis = new boolean[numPoles+1];
		poleVis[1] = true;
		int[] polePar = new int[numPoles+1];
		int nodesVis = 0;
		int currNode = 1;
		while (nodesVis < numPoles && currNode != numPoles) {
			nodesVis++;
			poleVis[currNode] = true;
			
			for (int i = 1; i <= numPoles; i++) {
				double dist = distBtwn(currNode, i, poleCoords, adjMat);
				if (dist <= maxLen) {
					if (poleDist[currNode] + dist < poleDist[i]) {
						poleDist[i] = poleDist[currNode] + dist;
						polePar[i] = currNode;
					}
				}
			}
			
			double minVal = Double.MAX_VALUE;
			int minPole = Integer.MAX_VALUE;
			for (int i = 1; i <= numPoles; i++) {
				if (poleDist[i] < minVal && !poleVis[i]) {
					minVal = poleDist[i];
					minPole = i;
				}
			}
			if (minPole == Integer.MAX_VALUE) {
				break;
			}
			currNode = minPole;
		}
		
		if (currNode != numPoles) {
			System.out.println(-1);
		} else {
			double totalDist = 0;
			currNode = numPoles;
			while (currNode != 1) {
				totalDist += distBtwn(currNode, polePar[currNode], poleCoords, adjMat);
				currNode = polePar[currNode];
			}
			writer.println((int) (totalDist * 1000));
		}
		writer.close();
	}
	
	private static double distBtwn(int pole1, int pole2, int[][] poleCoords, boolean[][] adjMat) {
		if (adjMat[pole1][pole2]) {
			return 0;
		}
		return Math.sqrt(Math.pow(poleCoords[pole1][0] - poleCoords[pole2][0], 2) + Math.pow(poleCoords[pole1][1] - poleCoords[pole2][1], 2));
	}
}