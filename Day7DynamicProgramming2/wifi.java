package Day7DynamicProgramming2;

import java.io.*;
import java.util.*;

public class wifi {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numCows = Integer.parseInt(st.nextToken());
		int a = Integer.parseInt(st.nextToken());
		int b = Integer.parseInt(st.nextToken());
		int[] cowPos = new int[numCows];
		for (int i = 0; i < numCows; i++) {
			cowPos[i] = Integer.parseInt(reader.readLine());
		}
		Arrays.sort(cowPos);
		reader.close();
		
		double cost = 0;
		cost = a;
		for (int i = 1; i < numCows; i++) {
			double newCost = a;
			double oldCost = (cowPos[i] - cowPos[i - 1]) * b / 2.0;
			cost += Math.min(newCost, oldCost);
		}
		if (cost % 1 == 0) {
			writer.println((int) cost);
		} else {
			writer.println(cost);
		}
		writer.close();
	}
}