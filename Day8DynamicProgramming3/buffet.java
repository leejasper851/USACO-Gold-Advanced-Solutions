package Day8DynamicProgramming3;

import java.io.*;
import java.util.*;

public class buffet {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numPatches = Integer.parseInt(st.nextToken());
		int pathEn = Integer.parseInt(st.nextToken());
		int[] patchEn = new int[numPatches+1];
		ArrayList<ArrayList<Integer>> paths = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i <= numPatches; i++) {
			paths.add(new ArrayList<Integer>());
		}
		for (int i = 1; i <= numPatches; i++) {
			st = new StringTokenizer(reader.readLine());
			patchEn[i] = Integer.parseInt(st.nextToken());
			int numPaths = Integer.parseInt(st.nextToken());
			for (int j = 0; j < numPaths; j++) {
				paths.get(i).add(Integer.parseInt(st.nextToken()));
			}
		}
		reader.close();
		
		Integer[] patches = new Integer[numPatches];
		for (int i = 1; i <= numPatches; i++) {
			patches[i-1] = i;
		}
		Arrays.sort(patches, new EnergyComp(patchEn));

		int maxEn = 0;
		int[] patchMaxEns = new int[numPatches+1];
		for (int i = 0; i < numPatches; i++) {
			int patch = patches[i];
			int[] dist = new int[numPatches+1];
			Arrays.fill(dist, -1);
			dist[patch] = 0;
			ArrayList<Integer> queue = new ArrayList<Integer>();
			queue.add(patch);
			while (queue.size() > 0) {
				int currPatch = queue.remove(0);
				for (int adjPatch : paths.get(currPatch)) {
					if (dist[adjPatch] == -1) {
						dist[adjPatch] = dist[currPatch] + 1;
						queue.add(adjPatch);
					}
				}
			}
			
			int currMaxEn = patchEn[patch];
			for (int j = 1; j <= numPatches; j++) {
				if (dist[j] != -1 && patchEn[j] < patchEn[patch]) {
					int currEn = patchMaxEns[j] + patchEn[patch] - pathEn * dist[j];
					if (currEn > currMaxEn) {
						currMaxEn = currEn;
					}
				}
			}
			patchMaxEns[patch] = currMaxEn;
			if (currMaxEn > maxEn) {
				maxEn = currMaxEn;
			}
		}
		
		writer.println(maxEn);
		writer.close();
	}
	
	private static class EnergyComp implements Comparator<Integer> {
		public int[] patchEn;
		
		public EnergyComp(int[] p) {
			patchEn = p;
		}
		
		public int compare(Integer a, Integer b) {
			return patchEn[a] - patchEn[b];
		}
	}
}