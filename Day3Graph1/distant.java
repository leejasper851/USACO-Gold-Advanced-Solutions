package Day3Graph1;

import java.io.*;
import java.util.*;

public class distant {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int sideLen = Integer.parseInt(st.nextToken());
		int sameTime = Integer.parseInt(st.nextToken());
		int diffTime = Integer.parseInt(st.nextToken());
		boolean[][] grid = new boolean[sideLen][sideLen];
		for (int i = 0; i < sideLen; i++) {
			String row = reader.readLine();
			for (int j = 0; j < sideLen; j++) {
				grid[i][j] = (row.charAt(j) == '(');
			}
		}
		reader.close();
		
		int numNodes = sideLen * sideLen;
		Node[] nodes = new Node[numNodes];
		int[][] adjMat = new int[numNodes][numNodes];
		int idInd = 0;
		for (int i = 0; i < sideLen; i++) {
			for (int j = 0; j < sideLen; j++) {
				nodes[idInd] = new Node(idInd, Integer.MAX_VALUE);
				if (i > 0) {
					if (grid[i][j] == grid[i - 1][j]) {
						adjMat[idInd][idInd - sideLen] = sameTime;
						adjMat[idInd - sideLen][idInd] = sameTime;
					} else {
						adjMat[idInd][idInd - sideLen] = diffTime;
						adjMat[idInd - sideLen][idInd] = diffTime;
					}
				}
				if (j > 0) {
					if (grid[i][j] == grid[i][j - 1]) {
						adjMat[idInd][idInd - 1] = sameTime;
						adjMat[idInd - 1][idInd] = sameTime;
					} else {
						adjMat[idInd][idInd - 1] = diffTime;
						adjMat[idInd - 1][idInd] = diffTime;
					}
				}
				idInd++;
			}
		}
		
		int maxDist = 0;
		for (int root = 0; root < numNodes; root++) {
			int[] dist = new int[numNodes];
			for (int i = 0; i < numNodes; i++) {
				dist[i] = Integer.MAX_VALUE;
			}
			boolean[] visited = new boolean[numNodes];
			dist[root] = 0;
			visited[root] = true;
			nodes[root].dist = 0;
			PriorityQueue<Node> heap = new PriorityQueue<Node>();
			for (int i = 0; i < numNodes; i++) {
				if (i == root) {
					continue;
				}
				heap.add(nodes[i]);
			}
			
			Node currNode = nodes[root];
			while (true) {
				int currId = currNode.id;
				visited[currId] = true;
				if (currNode.dist > maxDist) {
					maxDist = currNode.dist;
				}
				if (heap.isEmpty()) {
					break;
				}
				
				if (currId >= sideLen && !visited[currId - sideLen]) {
					int otherId = currId - sideLen;
					int newVal = dist[currId] + adjMat[currId][otherId];
					if (newVal < dist[otherId]) {
						dist[otherId] = newVal;
						heap.remove(nodes[otherId]);
						nodes[otherId].dist = newVal;
						heap.add(nodes[otherId]);
					}
				}
				if (currId % sideLen > 0 && !visited[currId - 1]) {
					int otherId = currId - 1;
					int newVal = dist[currId] + adjMat[currId][otherId];
					if (newVal < dist[otherId]) {
						dist[otherId] = newVal;
						heap.remove(nodes[otherId]);
						nodes[otherId].dist = newVal;
						heap.add(nodes[otherId]);
					}
				}
				if (currId < numNodes - sideLen && !visited[currId + sideLen]) {
					int otherId = currId + sideLen;
					int newVal = dist[currId] + adjMat[currId][otherId];
					if (newVal < dist[otherId]) {
						dist[otherId] = newVal;
						heap.remove(nodes[otherId]);
						nodes[otherId].dist = newVal;
						heap.add(nodes[otherId]);
					}
				}
				if (currId % sideLen < sideLen-1 && !visited[currId + 1]) {
					int otherId = currId + 1;
					int newVal = dist[currId] + adjMat[currId][otherId];
					if (newVal < dist[otherId]) {
						dist[otherId] = newVal;
						heap.remove(nodes[otherId]);
						nodes[otherId].dist = newVal;
						heap.add(nodes[otherId]);
					}
				}
				currNode = heap.remove();
			}
			
			for (int i = 0; i < numNodes; i++) {
				nodes[i].dist = Integer.MAX_VALUE;
			}
		}
		
		writer.println(maxDist);
		writer.close();
	}
	
	private static class Node implements Comparable<Node> {
		public int id;
		public int dist;
		
		public Node(int id, int d) {
			this.id = id;
			dist = d;
		}
		
		public int compareTo(Node other) {
			if (id == other.id) {
				return 0;
			}
			return dist - other.dist;
		}
	}
}