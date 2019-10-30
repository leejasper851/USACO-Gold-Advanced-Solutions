package Day1Warmup;

import java.io.*;
import java.util.*;

public class poker {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		int numRanks = Integer.parseInt(reader.readLine());
		int[] vals = new int[numRanks];
		for (int i = 0; i < numRanks; i++) {
			vals[i] = Integer.parseInt(reader.readLine());
		}
		reader.close();
		
		long numStraights = vals[0];
		for (int i = 1; i < numRanks; i++) {
			if (vals[i] > vals[i - 1]) {
				numStraights += vals[i] - vals[i - 1];
			}
		}
		writer.println(numStraights);
		writer.close();
	}
}