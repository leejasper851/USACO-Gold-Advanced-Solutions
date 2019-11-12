package Day7DynamicProgramming2;

import java.io.*;
import java.util.*;

public class ontherun {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numClumps = Integer.parseInt(st.nextToken()) + 1;
		int startPos = Integer.parseInt(st.nextToken());
		int[] clumps = new int[numClumps];
		for (int i = 0; i < numClumps - 1; i++) {
			clumps[i] = Integer.parseInt(reader.readLine());
		}
		clumps[numClumps-1] = startPos;
		Arrays.sort(clumps);
		reader.close();
		
		for (int i = 0; i < numClumps; i++) {
			if (clumps[i] == startPos) {
				startPos = i;
				break;
			}
		}
		int[][][] stales = new int[numClumps][numClumps][2];
		for (int i = 0; i < numClumps; i++) {
			for (int j = 0; j < numClumps; j++) {
				Arrays.fill(stales[i][j], -1);
			}
		}
		
		writer.println(stale(startPos, startPos, 0, stales, clumps));
		writer.close();
	}
	
	private static int stale(int left, int right, int dir, int[][][] stales, int[] clumps) {
		int numClumps = clumps.length;
		if (left == 0 && right == numClumps-1) {
			stales[left][right][dir] = 0;
			return 0;
		}
		if (stales[left][right][dir] != -1) {
			return stales[left][right][dir];
		}
		int minStales = Integer.MAX_VALUE;
		int currInd = (dir == 0) ? left : right;
		
		if (left != 0) {
			int newStales = stale(left - 1, right, 0, stales, clumps) + Math.abs(clumps[currInd] - clumps[left - 1]) * (numClumps - (right-left+1));
			if (newStales < minStales) {
				minStales = newStales;
			}
		}
		if (right != numClumps-1) {
			int newStales = stale(left, right + 1, 1, stales, clumps) + Math.abs(clumps[currInd] - clumps[right + 1]) * (numClumps - (right-left+1));
			if (newStales < minStales) {
				minStales = newStales;
			}
		}
		stales[left][right][dir] = minStales;
		return minStales;
	}
}