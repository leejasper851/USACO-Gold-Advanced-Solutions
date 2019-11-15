package Day8DynamicProgramming3;

import java.io.*;
import java.util.*;

public class barnpainting {
	private static long MOD_NUM = 1000000007;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numBarns = Integer.parseInt(st.nextToken());
		int numPainted = Integer.parseInt(st.nextToken());
		ArrayList<Integer>[] paths = new ArrayList[numBarns+1];
		for (int i = 1; i <= numBarns; i++) {
			paths[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < numBarns - 1; i++) {
			st = new StringTokenizer(reader.readLine());
			int aBarn = Integer.parseInt(st.nextToken());
			int bBarn = Integer.parseInt(st.nextToken());
			paths[aBarn].add(bBarn);
			paths[bBarn].add(aBarn);
		}
		int[] painted = new int[numBarns+1];
		for (int i = 0; i < numPainted; i++) {
			st = new StringTokenizer(reader.readLine());
			int barn = Integer.parseInt(st.nextToken());
			int col = Integer.parseInt(st.nextToken());
			painted[barn] = col;
		}
		reader.close();
		
		long[][] colCounts = new long[numBarns+1][4];
		for (int i = 1; i <= numBarns; i++) {
			Arrays.fill(colCounts[i], -1);
		}
		writer.println(colCount(1, -1, 0, paths, painted, colCounts));
		writer.close();
	}
	
	private static long colCount(int barn, int prevCol, int prev, ArrayList<Integer>[] paths, int[] painted, long[][] colCounts) {
		if (prevCol != -1 && colCounts[barn][prevCol] != -1) {
			return colCounts[barn][prevCol];
		}
		if (prevCol == painted[barn]) {
			colCounts[barn][prevCol] = 0;
			return 0;
		}
		if (paths[barn].size() == 1 && prevCol != -1) {
			if (painted[barn] != 0) {
				colCounts[barn][prevCol] = 1;
				return 1;
			}
			colCounts[barn][prevCol] = 2;
			return 2;
		}
		
		long[] counts = new long[4];
		Arrays.fill(counts, 1);
		for (int adjBarn : paths[barn]) {
			if (adjBarn != prev) {
				for (int col = 1; col <= 3; col++) {
					if (col == prevCol || (painted[barn] != 0 && col != painted[barn])) {
						counts[col] = 0;
						continue;
					}
					counts[col] *= colCount(adjBarn, col, barn, paths, painted, colCounts);
					counts[col] %= MOD_NUM;
				}
			}
		}
		
		long count = (counts[1] + counts[2] + counts[3]) % MOD_NUM;
		if (prevCol != -1) {
			colCounts[barn][prevCol] = count;
		}
		return count;
	}
}