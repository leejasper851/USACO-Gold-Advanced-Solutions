package Day10MixedProblemSet1;

import java.io.*;
import java.util.*;

public class cbarn {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		int numDoors = Integer.parseInt(reader.readLine());
		int[] doors = new int[numDoors];
		for (int i = 0; i < numDoors; i++) {
			doors[i] = Integer.parseInt(reader.readLine());
		}
		reader.close();
		
		int firstMin = 0;
		int currSum = 0;
		for (int i = 0; i < numDoors; i++) {
			currSum += doors[i];
			currSum--;
			firstMin = Math.min(firstMin, currSum);
		}
		int startDoor = 0;
		if (firstMin < 0) {
			for (int i = 1; i < numDoors; i++) {
				firstMin -= doors[i - 1];
				firstMin++;
				if (firstMin >= 0) {
					startDoor = i;
					break;
				}
			}
		}
		
		int[] ordDoors = new int[numDoors];
		int currInd = 0;
		for (int i = startDoor; i < numDoors; i++) {
			ordDoors[currInd] = doors[i];
			currInd++;
		}
		for (int i = 0; i < startDoor; i++) {
			ordDoors[currInd] = doors[i];
			currInd++;
		}
		
		long energy = 0;
		long nextInd = 0;
		for (long i = 0; i < numDoors; i++) {
			for (int j = 0; j < ordDoors[(int)i]; j++) {
				energy += (nextInd - i) * (nextInd - i);
				nextInd++;
			}
		}
		writer.println(energy);
		writer.close();
	}
}