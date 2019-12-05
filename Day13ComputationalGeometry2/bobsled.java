package Day13ComputationalGeometry2;

import java.io.*;
import java.util.*;

public class bobsled {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int len = Integer.parseInt(st.nextToken());
		int numTurns = Integer.parseInt(st.nextToken());
		PriorityQueue<Turn> turns = new PriorityQueue<>();
		for (int i = 0; i < numTurns; i++) {
			st = new StringTokenizer(reader.readLine());
			int pos = Integer.parseInt(st.nextToken());
			int lim = Integer.parseInt(st.nextToken());
			turns.add(new Turn(pos, lim));
		}
		reader.close();
		
		int[] turnPos = new int[numTurns + 1];
		int[] turnLims = new int[numTurns + 1];
		turnPos[0] = 0;
		turnLims[0] = 1;
		for (int i = 1; i <= numTurns; i++) {
			turnPos[i] = turns.element().pos;
			turnLims[i] = turns.remove().lim;
		}
		
		for (int i = 1; i <= numTurns; i++) {
			if (turnLims[i] - turnLims[i - 1] > turnPos[i] - turnPos[i - 1]) {
				turnLims[i] = turnLims[i - 1] + (turnPos[i] - turnPos[i - 1]);
			}
		}
		for (int i = numTurns - 1; i >= 0; i--) {
			if (turnLims[i] - turnLims[i + 1] > turnPos[i + 1] - turnPos[i]) {
				turnLims[i] = turnLims[i + 1] + (turnPos[i + 1] - turnPos[i]);
			}
		}
		
		int highVal = 1;
		for (int i = 1; i <= numTurns; i++) {
			highVal = Math.max(highVal, highestVal(turnPos[i - 1], turnLims[i - 1], turnPos[i], turnLims[i]));
		}
		highVal = Math.max(highVal, turnLims[numTurns] + (len - turnPos[numTurns]));
		writer.println(highVal);
		writer.close();
	}
	
	private static int highestVal(int pos1, int lim1, int pos2, int lim2) {
		int freeDist = (pos2 - pos1) - Math.abs(lim2 - lim1);
		int extraHeight = freeDist / 2;
		return Math.max(lim1, lim2) + extraHeight;
	}
	
	private static class Turn implements Comparable<Turn> {
		public int pos;
		public int lim;
		
		public Turn(int p, int l) {
			pos = p;
			lim = l;
		}
		
		public int compareTo(Turn other) {
			return pos - other.pos;
		}
	}
}