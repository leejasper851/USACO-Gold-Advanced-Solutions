package Day2DataStructures;

import java.io.*;
import java.util.*;

public class snowboots {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numTiles = Integer.parseInt(st.nextToken());
		int numBoots = Integer.parseInt(st.nextToken());
		int[] tileDepth = new int[numTiles];
		ArrayList<Integer> tileSort = new ArrayList<Integer>();
		st = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numTiles; i++) {
			tileDepth[i] = Integer.parseInt(st.nextToken());
			tileSort.add(i);
		}
		int[] bootDepth = new int[numBoots];
		int[] bootStep = new int[numBoots];
		Integer[] bootSort = new Integer[numBoots];
		for (int i = 0; i < numBoots; i++) {
			st = new StringTokenizer(reader.readLine());
			bootDepth[i] = Integer.parseInt(st.nextToken());
			bootStep[i] = Integer.parseInt(st.nextToken());
			bootSort[i] = i;
		}
		reader.close();
		
		Collections.sort(tileSort, new TileComp(tileDepth));
		Arrays.sort(bootSort, new BootComp(bootDepth));
		
		int[] prev = new int[numTiles];
		int[] next = new int[numTiles];
		for (int i = 0; i < numTiles; i++) {
			prev[i] = i - 1;
			next[i] = i + 1;
		}
		
		boolean[] works = new boolean[numBoots];
		int maxStep = 0;
		for (int i = numBoots-1; i >= 0; i--) {
			int boot = bootSort[i];
			while (!tileSort.isEmpty() && tileDepth[tileSort.get(tileSort.size()-1)] > bootDepth[boot]) {
				int currTile = tileSort.remove(tileSort.size()-1);
				next[prev[currTile]] = next[currTile];
				prev[next[currTile]] = prev[currTile];
				int step = next[currTile] - prev[currTile];
				if (step > maxStep) {
					maxStep = step;
				}
			}
			if (bootStep[boot] >= maxStep) {
				works[boot] = true;
			}
		}
		
		for (int i = 0; i < numBoots; i++) {
			writer.println(works[i] ? 1 : 0);
		}
		writer.close();
	}
	
	private static class TileComp implements Comparator<Integer> {
		public int[] tileDepth;
		
		public TileComp(int[] td) {
			tileDepth = td;
		}
		
		public int compare(Integer a, Integer b) {
			return tileDepth[a] - tileDepth[b];
		}
	}
	
	private static class BootComp implements Comparator<Integer> {
		public int[] bootDepth;
		
		public BootComp(int[] bd) {
			bootDepth = bd;
		}
		
		public int compare(Integer a, Integer b) {
			return bootDepth[a] - bootDepth[b];
		}
	}
}