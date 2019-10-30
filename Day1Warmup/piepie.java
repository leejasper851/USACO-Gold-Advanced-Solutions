package Day1Warmup;

import java.io.*;
import java.util.*;

public class piepie {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numPies = Integer.parseInt(st.nextToken());
		int range = Integer.parseInt(st.nextToken());
		int[] bessVals = new int[numPies * 2+1];
		int[] elsVals = new int[numPies * 2+1];
		int[] dist = new int[numPies * 2+1];
		LinkedList<Integer> queue = new LinkedList<Integer>();
		ArrayList<Integer> bessSort = new ArrayList<Integer>();
		ArrayList<Integer> elsSort = new ArrayList<Integer>();
		for (int i = 1; i <= numPies; i++) {
			st = new StringTokenizer(reader.readLine());
			bessVals[i] = -Integer.parseInt(st.nextToken());
			elsVals[i] = -Integer.parseInt(st.nextToken());
			dist[i] = -1;
			if (elsVals[i] == 0) {
				queue.add(i);
				dist[i] = 1;
			} else {
				bessSort.add(i);
			}
		}
		for (int j = numPies + 1; j <= numPies * 2; j++) {
			st = new StringTokenizer(reader.readLine());
			bessVals[j] = -Integer.parseInt(st.nextToken());
			elsVals[j] = -Integer.parseInt(st.nextToken());
			dist[j] = -1;
			if (bessVals[j] == 0) {
				queue.add(j);
				dist[j] = 1;
			} else {
				elsSort.add(j);
			}
		}
		reader.close();
		
		boolean[] bessUsed = new boolean[bessSort.size()];
		boolean[] elsUsed = new boolean[elsSort.size()];
		MyCompBess myCompBess = new MyCompBess(elsVals);
		MyCompEls myCompEls = new MyCompEls(bessVals);
		Collections.sort(bessSort, new MyCompBess(elsVals));
		Collections.sort(elsSort, new MyCompEls(bessVals));
		
		while (!queue.isEmpty()) {
			int i = queue.remove();
			if (i <= numPies) {
				int startInd = -Collections.binarySearch(elsSort, i, myCompEls) - 1;
				for (int j = startInd; j < elsSort.size(); j++) {
					int ind = elsSort.get(j);
					int val = bessVals[ind];
					if (val > bessVals[i] + range) {
						break;
					}
					if (elsUsed[j]) {
						continue;
					}
					dist[ind] = dist[i] + 1;
					queue.add(ind);
					elsUsed[j] = true;
				}
			} else {
				int startInd = -Collections.binarySearch(bessSort, i, myCompBess) - 1;
				for (int j = startInd; j < bessSort.size(); j++) {
					int ind = bessSort.get(j);
					int val = elsVals[ind];
					if (val > elsVals[i] + range) {
						break;
					}
					if (bessUsed[j]) {
						continue;
					}
					dist[ind] = dist[i] + 1;
					queue.add(ind);
					bessUsed[j] = true;
				}
			}
		}
		
		for (int i = 1; i <= numPies; i++) {
			writer.println(dist[i]);
		}
		writer.close();
	}
	
	private static class MyCompBess implements Comparator<Integer> {
		public int[] elsVals;
		
		public MyCompBess(int[] e) {
			elsVals = e;
		}
		
		public int compare(Integer a, Integer b) {
			return (elsVals[a] == elsVals[b]) ? 1 : (elsVals[a] - elsVals[b]);
		}
	}
	
	private static class MyCompEls implements Comparator<Integer> {
		public int[] bessVals;
		
		public MyCompEls(int[] b) {
			bessVals = b;
		}
		
		public int compare(Integer a, Integer b) {
			return (bessVals[a] == bessVals[b]) ? 1 : (bessVals[a] - bessVals[b]);
		}
	}
}