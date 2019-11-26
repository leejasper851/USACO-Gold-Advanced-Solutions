package Day11MixedProblemSet2;

import java.io.*;
import java.util.*;

public class allow {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numVals = Integer.parseInt(st.nextToken());
		long allowance = Integer.parseInt(st.nextToken());
		int numWeeks = 0;
		TreeMap<Long, Long> vals = new TreeMap<>(new RevComp());
		for (int i = 0; i < numVals; i++) {
			st = new StringTokenizer(reader.readLine());
			long val = Long.parseLong(st.nextToken());
			long numCoins = Long.parseLong(st.nextToken());
			if (val >= allowance) {
				numWeeks += numCoins;
				continue;
			}
			vals.put(val, numCoins);
		}
		reader.close();
		
		while (true) {
			long prevSum = 0;
			long prevLessSum = 0;
			TreeMap<Long, Long> prevSumVals = new TreeMap<>(new RevComp());
			TreeMap<Long, Long> prevLessSumVals = new TreeMap<>(new RevComp());
			ArrayList<Long> toRemove = new ArrayList<Long>();
			for (long val : vals.keySet()) {
				if (vals.get(val) == 0) {
					toRemove.add(val);
					continue;
				}
				long reachVal = allowance - prevLessSum;
				if (val * vals.get(val) >= reachVal) {
					if (reachVal % val == 0) {
						prevSum = allowance;
						prevSumVals = prevLessSumVals;
						prevSumVals.put(val, reachVal / val);
						break;
					}
					long numReach = reachVal / val;
					prevSum = prevLessSum + val * (numReach + 1);
					prevSumVals = (TreeMap<Long, Long>) prevLessSumVals.clone();
					prevSumVals.put(val, numReach + 1);
					prevLessSum = prevSum - val;
					if (numReach > 0) {
						prevLessSumVals.put(val, numReach);
					}
				} else {
					prevLessSum += val * vals.get(val);
					prevLessSumVals.put(val, vals.get(val));
				}
			}
			for (long val : toRemove) {
				vals.remove(val);
			}
			if (prevSum == 0) {
				break;
			}
			
			long numTimes = Long.MAX_VALUE;
			for (long val : prevSumVals.keySet()) {
				numTimes = Math.min(numTimes, vals.get(val) / prevSumVals.get(val));
			}
			for (long val : prevSumVals.keySet()) {
				vals.put(val, vals.get(val) - numTimes * prevSumVals.get(val));
			}
			numWeeks += numTimes;
		}
		writer.println(numWeeks);
		writer.close();
	}
	
	private static class RevComp implements Comparator<Long> {
		public RevComp() {
		}
		
		public int compare(Long a, Long b) {
			if (b > a) {
				return 1;
			}
			if (a > b) {
				return -1;
			}
			return 0;
		}
	}
}