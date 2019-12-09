package Day15FinalExam;

import java.io.*;
import java.util.*;

public class piggyback {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int bessEn = Integer.parseInt(st.nextToken());
		int elsEn = Integer.parseInt(st.nextToken());
		int bothEn = Integer.parseInt(st.nextToken());
		int numFields = Integer.parseInt(st.nextToken());
		int numConns = Integer.parseInt(st.nextToken());
		ArrayList<Integer>[] conns = new ArrayList[numFields+1];
		for (int i = 1; i <= numFields; i++) {
			conns[i] = new ArrayList<>();
		}
		for (int i = 0; i < numConns; i++) {
			st = new StringTokenizer(reader.readLine());
			int field1 = Integer.parseInt(st.nextToken());
			int field2 = Integer.parseInt(st.nextToken());
			conns[field1].add(field2);
			conns[field2].add(field1);
		}
		reader.close();
		
		int[] bessEns = findEns(conns, numFields, 1, bessEn);
		int[] elsEns = findEns(conns, numFields, 2, elsEn);
		int[] bothEns = findEns(conns, numFields, numFields, bothEn);
		
		int minEn = bessEns[numFields] + elsEns[numFields];
		for (int i = 1; i < numFields; i++) {
			int currEn = bessEns[i] + elsEns[i] + bothEns[i];
			minEn = Math.min(minEn, currEn);
		}
		writer.println(minEn);
		writer.close();
	}
	
	private static int[] findEns(ArrayList<Integer>[] conns, int numFields, int startField, int connEn) {
		int[] ens = new int[numFields+1];
		Arrays.fill(ens, Integer.MAX_VALUE);
		boolean[] visited = new boolean[numFields+1];
		ArrayList<Integer> queue = new ArrayList<>();
		queue.add(startField);
		visited[startField] = true;
		ens[startField] = 0;
		while (!queue.isEmpty()) {
			int currField = queue.remove(0);
			for (int adjField : conns[currField]) {
				if (visited[adjField]) {
					continue;
				}
				queue.add(adjField);
				visited[adjField] = true;
				ens[adjField] = ens[currField] + connEn;
			}
		}
		return ens;
	}
}