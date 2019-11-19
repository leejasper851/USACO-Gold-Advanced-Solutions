package Day9DynamicProgramming4;

import java.io.*;
import java.util.*;

public class hopscotch2 {
	private static final int MOD_NUM = 1000000007;
	
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numRows = Integer.parseInt(st.nextToken());
		int numCols = Integer.parseInt(st.nextToken());
		int maxNum = Integer.parseInt(st.nextToken());
		int[][] grid = new int[numRows][numCols];
		for (int i = 0; i < numRows; i++) {
			st = new StringTokenizer(reader.readLine());
			for (int j = 0; j < numCols; j++) {
				grid[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		reader.close();
		
		int[][] numCounts = new int[numCols][maxNum+1];
		int[] totalCounts = new int[numCols];
		int[][] counts = new int[2][numCols];
		int prevInd = 0;
		int currInd = 1;
		counts[prevInd][0] = 1;
		for (int row = 1; row < numRows; row++) {
			counts[currInd][0] = 0;
			for (int col = 1; col < numCols; col++) {
				setNumCounts(row, col, grid, numCounts, totalCounts, counts, prevInd, currInd);
			}
			prevInd = currInd;
			currInd = (prevInd == 0) ? 1 : 0;
		}
		int diff = totalCounts[numCols-1] - numCounts[numCols-1][grid[numRows-1][numCols-1]];
		while (diff < 0) {
			diff += MOD_NUM;
		}
		writer.println(diff % MOD_NUM);
		writer.close();
	}
	
	private static void setNumCounts(int row, int col, int[][] grid, int[][] numCounts, int[] totalCounts, int[][] counts, int prevInd, int currInd) {
		for (int c = 0; c < col; c++) {
			numCounts[col][grid[row-1][c]] += counts[prevInd][c];
			numCounts[col][grid[row-1][c]] %= MOD_NUM;
			totalCounts[col] += counts[prevInd][c];
			totalCounts[col] %= MOD_NUM;
		}
		counts[currInd][col] = totalCounts[col] - numCounts[col][grid[row][col]];
		counts[currInd][col] %= MOD_NUM;
	}
}