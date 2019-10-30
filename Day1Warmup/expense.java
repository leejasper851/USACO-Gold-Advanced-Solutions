package Day1Warmup;

import java.io.*;
import java.util.*;

public class expense {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numDays = Integer.parseInt(st.nextToken());
		int numMonths = Integer.parseInt(st.nextToken());
		int[] expenses = new int[numDays];
		for (int i = 0; i < numDays; i++) {
			expenses[i] = Integer.parseInt(reader.readLine());
		}
		reader.close();
		
		long maxLim = 0;
		for (int i = 0; i < expenses.length; i++) {
			maxLim += expenses[i];
		}
		long minLim = 0;
		long currLim = maxLim / 2;
		while (true) {
			currLim = (maxLim + minLim) / 2;
			if (valid(currLim, expenses, numMonths)) {
				maxLim = currLim - 1;
			} else {
				minLim = currLim + 1;
			}
			if (maxLim < minLim) {
				if (valid(maxLim, expenses, numMonths)) {
					writer.println(maxLim);
				} else {
					writer.println(minLim);
				}
				break;
			}
		}
		writer.close();
	}
	
	private static boolean valid(long currLim, int[] expenses, int numMonths) {
		long currAmt = 0;
		int currMonths = 0;
		for (int i = 0; i < expenses.length;) {
			if (currAmt + expenses[i] > currLim) {
				currAmt = 0;
				currMonths++;
				if (currMonths >= numMonths) {
					return false;
				}
				continue;
			}
			currAmt += expenses[i];
			i++;
		}
		return true;
	}
}