/*
This program doesn't run on time when graded by AlphaStar Academy,
but it does when graded by USACO's servers
*/

package Day3Graph1;

import java.io.*;
import java.util.*;

public class gpsduel {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numNodes = Integer.parseInt(st.nextToken());
		int numEdges = Integer.parseInt(st.nextToken());
		ArrayList<ArrayList<Edge>> backAdjListA = new ArrayList<ArrayList<Edge>>();
		ArrayList<ArrayList<Edge>> backAdjListB = new ArrayList<ArrayList<Edge>>();
		ArrayList<ArrayList<Integer>> forwAdjList = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < numNodes; i++) {
			backAdjListA.add(new ArrayList<Edge>());
			backAdjListB.add(new ArrayList<Edge>());
			forwAdjList.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < numEdges; i++) {
			st = new StringTokenizer(reader.readLine());
			int fromNode = Integer.parseInt(st.nextToken())-1;
			int toNode = Integer.parseInt(st.nextToken())-1;
			backAdjListA.get(toNode).add(new Edge(fromNode, Integer.parseInt(st.nextToken())));
			backAdjListB.get(toNode).add(new Edge(fromNode, Integer.parseInt(st.nextToken())));
			forwAdjList.get(fromNode).add(toNode);
		}
		reader.close();
		
		for (int i = 0; i < numNodes; i++) {
			Collections.sort(backAdjListA.get(i));
			Collections.sort(backAdjListB.get(i));
		}
		
		int[] dist = new int[numNodes];
		for (int i = 0; i < numNodes; i++) {
			dist[i] = Integer.MAX_VALUE;
		}
		boolean[] vis = new boolean[numNodes];
		int[] parA = new int[numNodes];
		TreeSet<Integer> heap = new TreeSet<Integer>(new DistComp(dist));
		int currNode = numNodes-1;
		dist[currNode] = 0;
		parA[currNode] = currNode;
		while (true) {
			vis[currNode] = true;
			for (int i = 0; i < backAdjListA.get(currNode).size(); i++) {
				int adjNode = backAdjListA.get(currNode).get(i).end;
				int adjDist = backAdjListA.get(currNode).get(i).dist;
				if (vis[adjNode]) {
					continue;
				}
				if (dist[currNode] + adjDist < dist[adjNode]) {
					heap.remove(adjNode);
					dist[adjNode] = dist[currNode] + adjDist;
					((DistComp) heap.comparator()).dist = dist;
					heap.add(adjNode);
					parA[adjNode] = currNode;
				}
			}
			if (heap.isEmpty()) {
				break;
			}
			currNode = heap.pollFirst();
		}
		
		dist = new int[numNodes];
		for (int i = 0; i < numNodes; i++) {
			dist[i] = Integer.MAX_VALUE;
		}
		vis = new boolean[numNodes];
		int[] parB = new int[numNodes];
		heap = new TreeSet<Integer>(new DistComp(dist));
		currNode = numNodes-1;
		dist[currNode] = 0;
		parB[currNode] = currNode;
		while (true) {
			vis[currNode] = true;
			for (int i = 0; i < backAdjListB.get(currNode).size(); i++) {
				int adjNode = backAdjListB.get(currNode).get(i).end;
				int adjDist = backAdjListB.get(currNode).get(i).dist;
				if (vis[adjNode]) {
					continue;
				}
				if (dist[currNode] + adjDist < dist[adjNode]) {
					heap.remove(adjNode);
					dist[adjNode] = dist[currNode] + adjDist;
					((DistComp) heap.comparator()).dist = dist;
					heap.add(adjNode);
					parB[adjNode] = currNode;
				}
			}
			if (heap.isEmpty()) {
				break;
			}
			currNode = heap.pollFirst();
		}
		
		dist = new int[numNodes];
		for (int i = 0; i < numNodes; i++) {
			dist[i] = Integer.MAX_VALUE;
		}
		vis = new boolean[numNodes];
		int[] par = new int[numNodes];
		heap = new TreeSet<Integer>(new DistComp(dist));
		currNode = 0;
		dist[currNode] = 0;
		while (true) {
			vis[currNode] = true;
			if (currNode == numNodes-1) {
				break;
			}
			for (int adjNode : forwAdjList.get(currNode)) {
				if (vis[adjNode]) {
					continue;
				}
				int distBtwn = 0;
				if (parA[currNode] != adjNode) {
					distBtwn++;
				}
				if (parB[currNode] != adjNode) {
					distBtwn++;
				}
				if (dist[currNode] + distBtwn < dist[adjNode]) {
					heap.remove(adjNode);
					dist[adjNode] = dist[currNode] + distBtwn;
					((DistComp) heap.comparator()).dist = dist;
					heap.add(adjNode);
					par[adjNode] = currNode;
				}
			}
			currNode = heap.pollFirst();
		}
		int pathDist = 0;
		while (true) {
			if (parA[par[currNode]] != currNode) {
				pathDist++;
			}
			if (parB[par[currNode]] != currNode) {
				pathDist++;
			}
			currNode = par[currNode];
			if (currNode == 0) {
				break;
			}
		}
		writer.println(pathDist);
		writer.close();
	}
	
	private static class Edge implements Comparable<Edge> {
		public int end;
		public int dist;
		
		public Edge(int e, int d) {
			end = e;
			dist = d;
		}
		
		public int compareTo(Edge other) {
			return end - other.end;
		}
	}
	
	private static class DistComp implements Comparator<Integer> {
		public int[] dist;
		
		public DistComp(int[] d) {
			dist = d;
		}
		
		public int compare(Integer a, Integer b) {
			if (a == b) {
				return 0;
			}
			if (dist[a] == dist[b]) {
				return 1;
			}
			return dist[a] - dist[b];
		}
	}
}