package Day4Graphs2;

import java.io.*;
import java.util.*;

public class slides {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numPlats = Integer.parseInt(st.nextToken());
		int numSlides = Integer.parseInt(st.nextToken());
		int[] pos = new int[numPlats+1];
		for (int i = 1; i <= numPlats; i++) {
			pos[i] = Integer.parseInt(reader.readLine());
		}
		int[] outDiff = new int[numPlats+1];
		for (int i = 0; i < numSlides; i++) {
			st = new StringTokenizer(reader.readLine());
			int fromPlat = Integer.parseInt(st.nextToken());
			int toPlat = Integer.parseInt(st.nextToken());
			outDiff[fromPlat]++;
			outDiff[toPlat]--;
		}
		reader.close();
		
		ArrayList<Integer> starts = new ArrayList<Integer>();
		ArrayList<Integer> ends = new ArrayList<Integer>();
		for (int i = 1; i <= numPlats; i++) {
			if (outDiff[i] > 0) {
				for (int j = 0; j < outDiff[i]; j++) {
					ends.add(pos[i]);
				}
			} else {
				for (int j = 0; j < Math.abs(outDiff[i]); j++) {
					starts.add(pos[i]);
				}
			}
		}
		Collections.sort(starts);
		Collections.sort(ends);
		
		int dist = 0;
		while (!starts.isEmpty()) {
			dist += Math.abs(starts.remove(0) - ends.remove(0));
		}
		writer.println(dist);
		writer.close();
	}
}