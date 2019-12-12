/*
This program doesn't run on time when graded by AlphaStar Academy,
but it does when graded by USACO's servers.
*/

package Day5Graphs3;

import java.io.*;
import java.util.*;

public class dream {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("dream.in"));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("dream.out")));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numRows = Integer.parseInt(st.nextToken());
		int numCols = Integer.parseInt(st.nextToken());
		Tile[][] grid = new Tile[numRows+2][numCols+2];
		for (int i = 0; i < numRows+2; i++) {
			grid[i][0] = new Tile0(i, 0);
			grid[i][numCols+1] = new Tile0(i, numCols+1);
		}
		for (int i = 1; i <= numCols; i++) {
			grid[0][i] = new Tile0(0, i);
			grid[numRows+1][i] = new Tile0(numRows+1, i);
		}
		for (int i = 1; i <= numRows; i++) {
			st = new StringTokenizer(reader.readLine());
			for (int j = 1; j <= numCols; j++) {
				int type = Integer.parseInt(st.nextToken());
				if (type == 0) {
					grid[i][j] = new Tile0(i, j);
				} else if (type == 1) {
					grid[i][j] = new Tile1(i, j);
				} else if (type == 2) {
					grid[i][j] = new Tile2(i, j);
				} else if (type == 3) {
					grid[i][j] = new Tile3(i, j);
				} else {
					grid[i][j] = new Tile4(i, j);
				}
			}
		}
		reader.close();
		
		ArrayList<Tile> oldQueue = new ArrayList<Tile>();
		ArrayList<Tile> queue = new ArrayList<Tile>();
		grid[1][1].update(false, false);
		oldQueue.add(grid[1][1]);
		int move = 1;
		while (true) {
			while (!oldQueue.isEmpty()) {
				oldQueue.remove(0).updateAround(grid, queue);
			}
			if (queue.isEmpty()) {
				writer.println(-1);
				break;
			}
			if (((Tile1) grid[numRows][numCols]).reach) {
				writer.println(move);
				break;
			}
			oldQueue = new ArrayList<Tile>(queue);
			queue.clear();
			move++;
		}
		writer.close();
	}
	
	private static abstract class Tile {
		public int type;
		public int row;
		public int col;
		
		public Tile(int t, int r, int c) {
			type = t;
			row = r;
			col = c;
		}
		
		public abstract boolean update(boolean orange, boolean hor);
		public abstract void updateAround(Tile[][] grid, ArrayList<Tile> queue);
	}
	
	private static class Tile0 extends Tile {
		public Tile0(int row, int col) {
			super(0, row, col);
		}
		
		public boolean update(boolean ora, boolean hor) {
			return false;
		}
		
		public void updateAround(Tile[][] grid, ArrayList<Tile> queue) {
		}
	}
	
	private static class Tile1 extends Tile {
		public boolean reach;
		public boolean oReach;
		
		public Tile1(int row, int col) {
			super(1, row, col);
		}
		
		public boolean update(boolean ora, boolean hor) {
			if (!reach) {
				reach = true;
				if (ora) {
					oReach = true;
				}
				return true;
			}
			if (ora && !oReach) {
				oReach = true;
				return true;
			}
			return false;
		}
		
		public void updateAround(Tile[][] grid, ArrayList<Tile> queue) {
			int[][] adjs = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
			for (int i = 0; i < adjs.length; i++) {
				boolean hor = (adjs[i][1] == 0);
				if (grid[row + adjs[i][0]][col + adjs[i][1]].update(false, hor)) {
					queue.add(grid[row + adjs[i][0]][col + adjs[i][1]]);
				}
				if (oReach) {
					if (grid[row + adjs[i][0]][col + adjs[i][1]].update(true, hor)) {
						queue.add(grid[row + adjs[i][0]][col + adjs[i][1]]);
					}
				}
			}
		}
	}
	
	private static class Tile2 extends Tile {
		public boolean reach;
		
		public Tile2(int row, int col) {
			super(2, row, col);
		}
		
		public boolean update(boolean orange, boolean hor) {
			if (!reach) {
				reach = true;
				return true;
			}
			return false;
		}
		
		public void updateAround(Tile[][] grid, ArrayList<Tile> queue) {
			int[][] adjs = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
			for (int i = 0; i < adjs.length; i++) {
				boolean hor = (adjs[i][1] == 0);
				if (grid[row + adjs[i][0]][col + adjs[i][1]].update(true, hor)) {
					queue.add(grid[row + adjs[i][0]][col + adjs[i][1]]);
				}
			}
		}
	}
	
	private static class Tile3 extends Tile {
		public boolean reach;
		
		public Tile3(int row, int col) {
			super(3, row, col);
		}
		
		public boolean update(boolean orange, boolean hor) {
			if (orange && !reach) {
				reach = true;
				return true;
			}
			return false;
		}
		
		public void updateAround(Tile[][] grid, ArrayList<Tile> queue) {
			int[][] adjs = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
			for (int i = 0; i < adjs.length; i++) {
				boolean hor = (adjs[i][1] == 0);
				if (grid[row + adjs[i][0]][col + adjs[i][1]].update(true, hor)) {
					queue.add(grid[row + adjs[i][0]][col + adjs[i][1]]);
				}
			}
		}
	}
	
	private static class Tile4 extends Tile {
		public boolean horReach;
		public boolean verReach;
		
		public Tile4(int row, int col) {
			super(4, row, col);
		}
		
		public boolean update(boolean orange, boolean hor) {
			if (hor && !horReach) {
				horReach = true;
				return true;
			}
			if (!hor && !verReach) {
				verReach = true;
				return true;
			}
			return false;
		}
		
		public void updateAround(Tile[][] grid, ArrayList<Tile> queue) {
			int[][] adjs = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
			boolean horImp = (grid[row-1][col].type == 0) || (grid[row-1][col].type == 3) || (grid[row+1][col].type == 0) || (grid[row+1][col].type == 3);
			boolean verImp = (grid[row][col+1].type == 0) || (grid[row][col+1].type == 3) || (grid[row][col-1].type == 0) || (grid[row][col-1].type == 3);
			if (horImp && !verReach) {
				verReach = true;
			}
			if (verImp && !horReach) {
				horReach = true;
			}
			for (int i = 0; i < adjs.length; i++) {
				boolean hor = (adjs[i][1] == 0);
				if (hor && horReach) {
					if (grid[row + adjs[i][0]][col + adjs[i][1]].update(false, hor)) {
						queue.add(grid[row + adjs[i][0]][col + adjs[i][1]]);
					}
				}
				if (!hor && verReach) {
					if (grid[row + adjs[i][0]][col + adjs[i][1]].update(false, hor)) {
						queue.add(grid[row + adjs[i][0]][col + adjs[i][1]]);
					}
				}
			}
		}
	}
}