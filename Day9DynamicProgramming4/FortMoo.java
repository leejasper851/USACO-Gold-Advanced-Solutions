package Day9DynamicProgramming4;

import java.io.*;
import java.util.*;

public class FortMoo {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numRows = Integer.parseInt(st.nextToken());
		int numCols = Integer.parseInt(st.nextToken());
		int[][] grid = new int[numRows+1][numCols+1];
		for (int i = 1; i <= numRows; i++) {
			String row = reader.readLine();
			for (int j = 1; j <= numCols; j++) {
				grid[i][j] = grid[i-1][j] + grid[i][j-1] - grid[i-1][j-1];
				grid[i][j] += (row.charAt(j-1) == 'X') ? 1 : 0;
			}
		}
		reader.close();
		
		int maxArea = 0;
		for (int top = 1; top <= numRows; top++) {
			for (int bot = top; bot <= numRows; bot++) {
				int left = -1;
				for (int right = 1; right <= numCols; right++) {
					boolean rightClear = edgeClear(right, top, bot, grid);
					if (rightClear) {
						maxArea = Math.max(maxArea, bot - top + 1);
					}
					if (rightClear && left != -1) {
						maxArea = Math.max(maxArea, (bot-top+1) * (right-left+1));
					}
					if (!edgeClear(right, top, top, grid) || !edgeClear(right, bot, bot, grid)) {
						left = -1;
					}
					if (rightClear && left == -1) {
						left = right;
					}
				}
			}
		}
		writer.println(maxArea);
		writer.close();
	}
	
	private static boolean edgeClear(int col, int top, int bot, int[][] grid) {
		return grid[bot][col] - grid[top-1][col] - grid[bot][col-1] + grid[top-1][col-1] == 0;
	}
}