package Day2DataStructures;

import java.io.*;
import java.util.*;

public class fpot {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numDrops = Integer.parseInt(st.nextToken());
		int range = Integer.parseInt(st.nextToken());
		ArrayList<int[]> points = new ArrayList<int[]>();
		for (int i = 0; i < numDrops; i++) {
			st = new StringTokenizer(reader.readLine());
			int[] point = new int[2];
			point[0] = Integer.parseInt(st.nextToken());
			point[1] = Integer.parseInt(st.nextToken());
			points.add(point);
		}
		reader.close();
		
		Collections.sort(points, new PointComp());
		TreeMap<Integer, Integer> currPoints = new TreeMap<Integer, Integer>();
		int leastRange = Integer.MAX_VALUE;
		int left = 0;
		int right = 0;
		currPoints.put(points.get(0)[1], 1);
		while (true) {
			if (currPoints.lastKey() - currPoints.firstKey() >= range) {
				if (points.get(right)[0] - points.get(left)[0] < leastRange) {
					leastRange = points.get(right)[0] - points.get(left)[0];
				}
				int leftY = points.get(left)[1];
				if (currPoints.get(leftY) > 1) {
					currPoints.put(leftY, currPoints.get(leftY) - 1);
				} else {
					currPoints.remove(leftY);
				}
				left++;
			} else {
				if (right == numDrops-1) {
					break;
				}
				int rightY = points.get(right + 1)[1];
				if (currPoints.containsKey(rightY)) {
					currPoints.put(rightY, currPoints.get(rightY) + 1);
				} else {
					currPoints.put(rightY, 1);
				}
				right++;
			}
		}
		
		if (leastRange == Integer.MAX_VALUE) {
			writer.println(-1);
		} else {
			writer.println(leastRange);
		}
		writer.close();
	}
	
	private static class PointComp implements Comparator<int[]> {
		public int compare(int[] a, int[] b) {
			return a[0] - b[0];
		}
	}
}