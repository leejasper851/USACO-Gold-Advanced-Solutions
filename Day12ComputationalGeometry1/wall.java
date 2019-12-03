package Day12ComputationalGeometry1;

import java.io.*;
import java.util.*;

public class wall {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int height = Integer.parseInt(st.nextToken());
		int numHolds = Integer.parseInt(st.nextToken());
		ArrayList<Hold> holds = new ArrayList<>();
		for (int i = 0; i < numHolds; i++) {
			st = new StringTokenizer(reader.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			holds.add(new Hold(x, y));
		}
		reader.close();
		
		TreeSet<Integer> holdsSort = new TreeSet<>(new HoldComp(holds));
		ArrayList<Integer>[] adjs = new ArrayList[numHolds];
		for (int i = 0; i < numHolds; i++) {
			holdsSort.add(i);
			adjs[i] = new ArrayList<>();
		}
		for (int i = 0; i < numHolds; i++) {
			int y = holds.get(i).y;
			holds.add(new Hold(0, y - 1000));
			holds.add(new Hold(30000, y + 1000));
			Set<Integer> rangeSet = holdsSort.subSet(holdsSort.ceiling(holds.size()-2), true, holdsSort.floor(holds.size()-1), true);
			holds.remove(holds.size()-1);
			holds.remove(holds.size()-1);
			
			for (int rangeHold : rangeSet) {
				if (rangeHold == i) {
					continue;
				}
				if (holds.get(i).inRange(holds.get(rangeHold))) {
					adjs[i].add(rangeHold);
				}
			}
		}
		
		boolean[] visited = new boolean[numHolds];
		int[] moves = new int[numHolds];
		ArrayList<Integer> queue = new ArrayList<>();
		for (int i = 0; i < numHolds; i++) {
			if (holds.get(i).y <= 1000) {
				queue.add(i);
				visited[i] = true;
			}
		}
		boolean finished = false;
		while (!queue.isEmpty()) {
			int currHold = queue.remove(0);
			for (int adjHold : adjs[currHold]) {
				if (!visited[adjHold]) {
					if (holds.get(adjHold).y >= height - 1000) {
						writer.println(moves[currHold] + 2);
						finished = true;
						break;
					}
					queue.add(adjHold);
					visited[adjHold] = true;
					moves[adjHold] = moves[currHold] + 1;
				}
			}
			if (finished) {
				break;
			}
		}
		writer.close();
	}
	
	private static class Hold implements Comparable<Hold> {
		public int x;
		public int y;
		
		public Hold(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public boolean inRange(Hold other) {
			int xDiff = x - other.x;
			int yDiff = y - other.y;
			double dist = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
			return (dist <= 1000);
		}
		
		public int compareTo(Hold other) {
			if (y == other.y) {
				return x - other.x;
			}
			return y - other.y;
		}
	}
	
	private static class HoldComp implements Comparator<Integer> {
		public ArrayList<Hold> holds;
		
		public HoldComp(ArrayList<Hold> h) {
			holds = h;
		}
		
		public int compare(Integer a, Integer b) {
			return holds.get(a).compareTo(holds.get(b));
		}
	}
}