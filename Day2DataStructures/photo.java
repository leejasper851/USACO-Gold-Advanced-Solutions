package Day2DataStructures;

import java.io.*;
import java.util.*;

public class photo {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		int numCows = Integer.parseInt(reader.readLine());
		int[][] cowInd = new int[5][numCows];
		int[] cowNums = new int[numCows];
		Integer[] sortedCows = new Integer[numCows];
		HashMap<Integer, Integer> cowNumsMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < numCows; i++) {
			StringTokenizer st = new StringTokenizer(reader.readLine());
			int cow = Integer.parseInt(st.nextToken());
			cowNums[i] = cow;
			cowNumsMap.put(cow, i);
			sortedCows[i] = i;
			cowInd[0][i] = i;
		}
		for (int i = 1; i < 5; i++) {
			for (int j = 0; j < numCows; j++) {
				int cow = Integer.parseInt(reader.readLine());
				cowInd[i][cowNumsMap.get(cow)] = j;
			}
		}
		reader.close();
		
		Arrays.sort(sortedCows, new CowIndComp(cowInd));
		for (int i = 0; i < numCows; i++) {
			writer.println(cowNums[sortedCows[i]]);
		}
		writer.close();
	}
	
	private static class CowIndComp implements Comparator<Integer> {
		public int[][] cowInd;
		
		public CowIndComp(int[][] ci) {
			cowInd = ci;
		}
		
		public int compare(Integer a, Integer b) {
			int aBefore = 0;
			int bBefore = 0;
			for (int i = 0; i < 5; i++) {
				if (cowInd[i][a] < cowInd[i][b]) {
					aBefore++;
				} else {
					bBefore++;
				}
			}
			if (aBefore > bBefore) {
				return -1;
			}
			return 1;
		}
	}
}