package Day15FinalExam;

import java.io.*;
import java.util.*;

public class pogocow {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		int numTargs = Integer.parseInt(reader.readLine());
		int[][] targs = new int[numTargs][2];
		for (int i = 0; i < numTargs; i++) {
			StringTokenizer st = new StringTokenizer(reader.readLine());
			targs[i][0] = Integer.parseInt(st.nextToken());
			targs[i][1] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(targs, new PosComp());
		reader.close();
		
		int maxPoints = 0;
		int[][] maxTargs = new int[numTargs][numTargs];
		for (int i = numTargs-1; i >= 0; i--) {
			for (int j = i; j < numTargs; j++) {
				int points = targs[i][1];
				int currMax = 0;
				for (int k = j; k < numTargs; k++) {
					if (targs[k][0] - targs[j][0] >= targs[j][0] - targs[i][0] || k == j) {
						currMax = Math.max(currMax, maxTargs[j][k]);
					}
				}
				points += currMax;
				maxTargs[i][j] = points;
				maxPoints = Math.max(maxPoints, points);
			}
		}
		for (int i = 0; i < numTargs; i++) {
			for (int j = i - 1; j >= 0; j--) {
				int points = targs[i][1];
				int currMax = 0;
				for (int k = j; k >= 0; k--) {
					if (targs[j][0] - targs[k][0] >= targs[i][0] - targs[j][0] || k == j) {
						currMax = Math.max(currMax, maxTargs[j][k]);
					}
				}
				points += currMax;
				maxTargs[i][j] = points;
				maxPoints = Math.max(maxPoints, points);
			}
		}
		writer.println(maxPoints);
		writer.close();
	}
	
	private static class PosComp implements Comparator<int[]> {
		public PosComp() {
		}
		
		public int compare(int[] a, int[] b) {
			return a[0] - b[0];
		}
	}
}