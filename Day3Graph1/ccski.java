/*
This program doesn't run on time when graded by AlphaStar Academy,
but it does when graded by USACO's servers.
*/

package Day3Graph1;

import java.io.*;
import java.util.*;

public class ccski {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("ccski.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("ccski.out")));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numRows = Integer.parseInt(st.nextToken());
		int numCols = Integer.parseInt(st.nextToken());
		int numNodes = numRows * numCols;
		Node[] nodes = new Node[numNodes];
		for (int i = 0; i < numRows; i++) {
			st = new StringTokenizer(reader.readLine());
			for (int j = 0; j < numCols; j++) {
				int id = i * numCols + j;
				int val = Integer.parseInt(st.nextToken());
				nodes[id] = new Node(id, val, i, j, numRows, numCols);
			}
		}
		int firstWp = -1;
		int numWps = 0;
		for (int i = 0; i < numRows; i++) {
			st = new StringTokenizer(reader.readLine());
			for (int j = 0; j < numCols; j++) {
				if (st.nextToken().equals("1")) {
					int id = i * numCols + j;
					nodes[id].wp = true;
					if (firstWp == -1) {
						firstWp = id;
					}
					numWps++;
				}
			}
		}
		reader.close();
		
		Node currNode = nodes[firstWp];
		nodes[firstWp].diff = 0;
		TreeSet<Node> heap = new TreeSet<Node>();
		int maxDiff = -1;
		int wpsVis = 0;
		boolean[] inHeap = new boolean[numNodes];
		while (true) {
			currNode.visited = true;
			if (currNode.diff > maxDiff) {
				maxDiff = currNode.diff;
			}
			if (currNode.wp) {
				wpsVis++;
				if (wpsVis == numWps) {
					break;
				}
			}
			for (int neighbor : currNode.neighbors) {
				Node neighborNode = nodes[neighbor];
				if (neighborNode.visited) {
					continue;
				}
				int newVal = Math.abs(currNode.val - neighborNode.val);
				if (newVal < neighborNode.diff) {
					if (!inHeap[neighbor]) {
						inHeap[neighbor] = true;
					} else {
						heap.remove(neighborNode);
					}
					neighborNode.diff = newVal;
					heap.add(neighborNode);
				}
			}
			currNode = heap.pollFirst();
		}
		writer.println(maxDiff);
		writer.close();
	}
	
	private static class Node implements Comparable<Node> {
		public int id;
		public int val;
		public boolean wp;
		public int diff;
		public boolean visited;
		public ArrayList<Integer> neighbors;
		
		public Node(int id, int v, int row, int col, int numRows, int numCols) {
			this.id = id;
			val = v;
			wp = false;
			diff = Integer.MAX_VALUE;
			visited = false;
			
			neighbors = new ArrayList<Integer>();
			if (row > 0) {
				neighbors.add(id - numCols);
			}
			if (row < numRows-1) {
				neighbors.add(id + numCols);
			}
			if (col > 0) {
				neighbors.add(id - 1);
			}
			if (col < numCols-1) {
				neighbors.add(id + 1);
			}
		}
		
		public int compareTo(Node other) {
			if (id == other.id) {
				return 0;
			}
			if (diff == other.diff) {
				return 1;
			}
			return diff - other.diff;
		}
	}
}