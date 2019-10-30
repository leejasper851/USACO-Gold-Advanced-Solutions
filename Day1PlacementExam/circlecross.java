package Day1PlacementExam;

import java.io.*;
import java.util.*;

public class circlecross {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		int numCows = Integer.parseInt(reader.readLine());
		int[] crossPoints = new int[numCows * 2 + 1];
		for (int i = 1; i < crossPoints.length; i++) {
			crossPoints[i] = Integer.parseInt(reader.readLine());
		}
		reader.close();
		
		long crossPairs = 0;
		int[] startedBit = new int[crossPoints.length];
		int[] startPoints = new int[numCows+1];
		boolean[] seen = new boolean[numCows+1];
		for (int i = 1; i < crossPoints.length; i++) {
			int currCow = crossPoints[i];
			if (!seen[currCow]) {
				seen[currCow] = true;
				update(startedBit, i, 1);
				startPoints[currCow] = i;
			} else {
				update(startedBit, startPoints[currCow], -1);
				crossPairs += query(startedBit, i) - query(startedBit, startPoints[currCow]);
			}
		}
		writer.println(crossPairs);
		writer.close();
	}
	
	private static void update(int[] bit, int ind, int val) {
		for (; ind < bit.length; ind += ind & -ind) {
			bit[ind] += val;
		}
	}
	
	private static int query(int[] bit, int ind) {
		int sum = 0;
		for (; ind > 0; ind -= ind & -ind) {
			sum += bit[ind];
		}
		return sum;
	}
}