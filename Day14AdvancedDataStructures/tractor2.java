/*
This program doesn't run on time when graded by AlphaStar Academy,
but it does when graded by USACO's servers.
*/

package Day14AdvancedDataStructures;

import java.io.*;
import java.util.*;

public class tractor2 {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("tractor.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("tractor.out")));
		
		int size = Integer.parseInt(reader.readLine());
		int[] cellNums = new int[size * size];
		int low = 1000000;
		int high = 0;
		for (int i = 0; i < size; i++) {
			StringTokenizer st = new StringTokenizer(reader.readLine());
			for (int j = 0; j < size; j++) {
				int elev = Integer.parseInt(st.nextToken());
				cellNums[i * size + j] = elev;
				low = Math.min(low, elev);
				high = Math.max(high, elev);
			}
		}
		reader.close();
		
		Cell[] cells = new Cell[size * size];
		for (int i = 0; i < size * size; i++) {
			cells[i] = new Cell(i, cellNums, size);
		}
		
		int goal = (size * size) - (size * size) / 2;
		while (true) {
			if (low == high) {
				writer.println(low);
				break;
			}
			if (high - low == 1) {
				if (costEnough(low, cells, goal)) {
					writer.println(low);
				} else {
					writer.println(high);
				}
				break;
			}
			int mid = (low + high) / 2;
			if (costEnough(mid, cells, goal)) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}
		writer.close();
	}
	
	private static boolean costEnough(int cost, Cell[] cells, int goal) {
		boolean[] visited = new boolean[cells.length];
		int totalCount = 0;
		for (int i = 0; i < cells.length; i++) {
			if (!visited[i]) {
				int count = 0;
				ArrayList<Integer> queue = new ArrayList<>();
				queue.add(i);
				visited[i] = true;
				while (!queue.isEmpty()) {
					int currCell = queue.remove(queue.size()-1);
					count++;
					for (int j = 0; j < cells[currCell].adjCells.size(); j++) {
						int adjCell = cells[currCell].adjCells.get(j);
						if (!visited[adjCell] && cells[currCell].adjDiffs.get(j) <= cost) {
							queue.add(adjCell);
							visited[adjCell] = true;
						}
					}
				}
				if (count >= goal) {
					return true;
				}
				totalCount += count;
				if (cells.length - totalCount < goal) {
					return false;
				}
			}
		}
		return false;
	}
	
	private static class Cell {
		public ArrayList<Integer> adjCells;
		public ArrayList<Integer> adjDiffs;
		
		public Cell(int cellNum, int[] cellNums, int size) {
			adjCells = new ArrayList<>();
			adjDiffs = new ArrayList<>();
			if (cellNum - size >= 0) {
				adjCells.add(cellNum - size);
				adjDiffs.add(Math.abs(cellNums[cellNum] - cellNums[cellNum - size]));
			}
			if (cellNum + size < cellNums.length) {
				adjCells.add(cellNum + size);
				adjDiffs.add(Math.abs(cellNums[cellNum] - cellNums[cellNum + size]));
			}
			if (cellNum % size != 0) {
				adjCells.add(cellNum - 1);
				adjDiffs.add(Math.abs(cellNums[cellNum] - cellNums[cellNum - 1]));
			}
			if (cellNum % size != size - 1) {
				adjCells.add(cellNum + 1);
				adjDiffs.add(Math.abs(cellNums[cellNum] - cellNums[cellNum + 1]));
			}
		}
	}
}