package Day1PlacementExam;

import java.io.*;
import java.util.*;

public class FruitFeast {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int maxFull = Integer.parseInt(st.nextToken());
		int AFull = Integer.parseInt(st.nextToken());
		int BFull = Integer.parseInt(st.nextToken());
		reader.close();
		
		int[] full = new int[maxFull+1];
		full[0] = 2;
		boolean[] reachA = new boolean[maxFull+1];
		boolean[] reachB = new boolean[maxFull+1];
		
		for (int currFull = 1; currFull <= maxFull; currFull++) {
			if (currFull >= AFull && full[currFull - AFull] > 0) {
				full[currFull] = full[currFull - AFull];
			}
			if (currFull >= BFull && full[currFull - BFull] > full[currFull]) {
				full[currFull] = full[currFull - BFull];
			}
			if (full[currFull] == 0) {
				if (currFull >= AFull) {
					reachA[currFull - AFull] = true;
				}
				if (currFull >= BFull) {
					reachB[currFull - BFull] = true;
				}
			}
			if (full[currFull] == 2 && full[currFull / 2] == 0) {
				full[currFull / 2] = 1;
				fixDivide(currFull / 2, full, AFull, BFull, reachA, reachB);
			}
		}
		
		for (int currFull = maxFull; currFull >= 0; currFull--) {
			if (full[currFull] > 0) {
				writer.println(currFull);
				break;
			}
		}
		writer.close();
	}
	
	private static void fixDivide(int currFull, int[] full, int AFull, int BFull, boolean[] reachA, boolean[] reachB) {
		full[currFull] = 1;
		if (reachA[currFull]) {
			reachA[currFull] = false;
			fixDivide(currFull + AFull, full, AFull, BFull, reachA, reachB);
		}
		if (reachB[currFull]) {
			reachB[currFull] = false;
			fixDivide(currFull + BFull, full, AFull, BFull, reachA, reachB);
		}
	}
}