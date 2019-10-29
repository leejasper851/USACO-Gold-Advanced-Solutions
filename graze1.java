import java.io.*;
import java.util.*;

public class graze1 {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numCows = Integer.parseInt(st.nextToken());
		int pastLen = Integer.parseInt(st.nextToken());
		int[] cows = new int[numCows+1];
		for (int i = 1; i <= numCows; i++) {
			cows[i] = Integer.parseInt(reader.readLine());
		}
		reader.close();
		
		pastLen++;
		int dist = (pastLen - numCows) / (numCows-1);
		dist++;
		int[] prevMinCost = new int[pastLen];
		int[] minCost = new int[pastLen];
		for (int i = 0; i < pastLen; i++) {
			prevMinCost[i] = Integer.MAX_VALUE;
			minCost[i] = Integer.MAX_VALUE;
		}
		Arrays.fill(prevMinCost, Integer.MAX_VALUE);
		Arrays.fill(minCost, Integer.MAX_VALUE);
		minCost[0] = cows[1];
		int llow = 0;
		int rlow = 0;
		int lhigh = pastLen-1 - (numCows-1) * (dist + 1);
		int rhigh = pastLen-1 - (numCows-1) * dist;
		int low = 0;
		int high = 0;
		for (int i = 1; i <= numCows; i++) {
			for (int j = low; j <= high; j++) {
				if (i == 1 && j == 0) {
					continue;
				}
				int minPrev = Integer.MAX_VALUE;
				if (j - dist - 1 >= 0 && prevMinCost[j - dist - 1] < minPrev) {
					minPrev = prevMinCost[j - dist - 1];
				}
				if (j - dist >= 0 && prevMinCost[j - dist] < minPrev) {
					minPrev = prevMinCost[j - dist];
				}
				if (minPrev != Integer.MAX_VALUE) {
					minCost[j] = Math.abs(cows[i] - j) + minPrev;
				}
			}
			prevMinCost = minCost.clone();
			Arrays.fill(minCost, Integer.MAX_VALUE);
			llow += dist;
			rlow += dist + 1;
			lhigh += dist + 1;
			rhigh += dist;
			low = Math.max(llow, lhigh);
			high = Math.min(rlow, rhigh);
		}
		
		writer.println(prevMinCost[pastLen-1]);
		writer.close();
	}
}