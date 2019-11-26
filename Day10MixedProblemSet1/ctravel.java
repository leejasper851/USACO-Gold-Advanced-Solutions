package Day10MixedProblemSet1;

import java.io.*;
import java.util.*;

public class ctravel {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numRows = Integer.parseInt(st.nextToken());
		int numCols = Integer.parseInt(st.nextToken());
		int timeAfter = Integer.parseInt(st.nextToken());
		boolean[][] grid = new boolean[numRows][numCols];
		for (int i = 0; i < numRows; i++) {
			String line = reader.readLine();
			for (int j = 0; j < numCols; j++) {
				grid[i][j] = (line.charAt(j) == '*');
			}
		}
		st = new StringTokenizer(reader.readLine());
		int fromRow = Integer.parseInt(st.nextToken())-1;
		int fromCol = Integer.parseInt(st.nextToken())-1;
		int toRow = Integer.parseInt(st.nextToken())-1;
		int toCol = Integer.parseInt(st.nextToken())-1;
		reader.close();
		
		int[][][] ways = new int[timeAfter+1][numRows][numCols];
		ways[0][fromRow][fromCol] = 1;
		for (int time = 1; time <= timeAfter; time++) {
			for (int row = 0; row < numRows; row++) {
				for (int col = 0; col < numCols; col++) {
					if (grid[row][col]) {
						continue;
					}
					int[][] adj = {{0,1}, {0,-1}, {-1,0}, {1,0}};
					for (int i = 0; i < 4; i++) {
						if (col + adj[i][0] >= 0 && col + adj[i][0] < numCols && row + adj[i][1] >= 0 && row + adj[i][1] < numRows) {
							ways[time][row][col] += ways[time - 1][row + adj[i][1]][col + adj[i][0]];
						}
					}
				}
			}
		}
		writer.println(ways[timeAfter][toRow][toCol]);
		writer.close();
	}
}