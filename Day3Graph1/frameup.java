package Day3Graph1;

import java.io.*;
import java.util.*;

public class frameup {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int height = Integer.parseInt(st.nextToken());
		int width = Integer.parseInt(st.nextToken());
		char[][] grid = new char[height][width];
		int numLets = 0;
		boolean[] letVis = new boolean[26];
		HashMap<Character, Character> corrChars = new HashMap<Character, Character>();
		HashMap<Character, Character> corrCharsRev = new HashMap<Character, Character>();
		char letNum = 65;
		for (int i = 0; i < height; i++) {
			String row = reader.readLine();
			for (int j = 0; j < width; j++) {
				char currChar = row.charAt(j);
				if (currChar != '.') {
					if (!corrChars.containsKey(currChar)) {
						corrChars.put(currChar, letNum);
						corrCharsRev.put(letNum++, currChar);
					}
					grid[i][j] = corrChars.get(currChar);
					if (grid[i][j] != '.' && !letVis[grid[i][j] - 65]) {
						numLets++;
						letVis[grid[i][j] - 65] = true;
					}
				} else {
					grid[i][j] = '.';
				}
			}
		}
		reader.close();
		
		Frame[] frames = new Frame[numLets];
		for (int i = 0; i < numLets; i++) {
			frames[i] = new Frame(numLets);
		}
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (grid[i][j] != '.') {
					int letInd = grid[i][j] - 65;
					if (i < frames[letInd].top) {
						frames[letInd].top = i;
					}
					frames[letInd].bottom = i;
					if (j < frames[letInd].left) {
						frames[letInd].left = j;
					}
					if (j > frames[letInd].right) {
						frames[letInd].right = j;
					}
				}
			}
		}
		
		for (int i = 0; i < numLets; i++) {
			char let = (char) (65 + i);
			int row = frames[i].top;
			int col = 0;
			for (col = frames[i].left; col <= frames[i].right; col++) {
				if (grid[row][col] != let) {
					int otherInd = grid[row][col] - 65;
					frames[i].letAbove[otherInd] = true;
					frames[otherInd].letBelow[i] = true;
				}
			}
			row = frames[i].bottom;
			for (col = frames[i].left; col <= frames[i].right; col++) {
				if (grid[row][col] != let) {
					int otherInd = grid[row][col] - 65;
					frames[i].letAbove[otherInd] = true;
					frames[otherInd].letBelow[i] = true;
				}
			}
			col = frames[i].left;
			for (row = frames[i].top + 1; row < frames[i].bottom; row++) {
				if (grid[row][col] != let) {
					int otherInd = grid[row][col] - 65;
					frames[i].letAbove[otherInd] = true;
					frames[otherInd].letBelow[i] = true;
				}
			}
			col = frames[i].right;
			for (row = frames[i].top + 1; row < frames[i].bottom; row++) {
				if (grid[row][col] != let) {
					int otherInd = grid[row][col] - 65;
					frames[i].letAbove[otherInd] = true;
					frames[otherInd].letBelow[i] = true;
				}
			}
		}
		
		int[] ranges = new int[numLets];
		for (int i = 0; i < numLets; i++) {
			ranges[i] = frames[i].setOrigAbvBlw();
		}
		Integer[] aboveSort = new Integer[numLets];
		for (int i = 0; i < numLets; i++) {
			aboveSort[i] = i;
		}
		Arrays.sort(aboveSort, new AboveComp(frames));
		for (int i = 0; i < numLets; i++) {
			frames[aboveSort[i]].addAbove(frames);
		}
		Integer[] belowSort = new Integer[numLets];
		for (int i = 0; i < numLets; i++) {
			belowSort[i] = i;
		}
		Arrays.sort(belowSort, new BelowComp(frames));
		for (int i = 0; i < numLets; i++) {
			frames[belowSort[i]].addBelow(frames);
		}
		frames[0].addAbove(frames);
		ranges = new int[numLets];
		for (int i = 0; i < numLets; i++) {
			ranges[i] = frames[i].setOrigAbvBlw();
		}
		
		TreeSet<String> possStr = new TreeSet<String>();
		int[] currRan = new int[numLets];
		do {
			for (int i = 0; i < numLets; i++) {
				frames[i].setAbvBlw(currRan[i]);
			}
			Character[] sortLets = new Character[numLets];
			for (int i = 0; i < numLets; i++) {
				sortLets[i] = (char) (65 + i);
			}
			Arrays.sort(sortLets, new LetComp(frames));
			char[] sortLetsReal = new char[numLets];
			for (int i = 0; i < numLets; i++) {
				sortLetsReal[i] = corrCharsRev.get(sortLets[i]);
			}
			possStr.add(new String(sortLetsReal));
		} while (!incRanges(currRan, ranges));
		
		for (String str : possStr) {
			writer.println(str);
		}
		writer.close();
	}
	
	private static class Frame {
		public int top;
		public int bottom;
		public int left;
		public int right;
		public boolean[] letAbove;
		public boolean[] letBelow;
		public int origAbove;
		public int origBelow;
		public int range;
		public int above;
		public int below;
		
		public Frame(int numLets) {
			top = 29;
			left = 29;
			letAbove = new boolean[numLets];
			letBelow = new boolean[numLets];
		}
		
		public void addAbove(Frame[] frames) {
			int numLets = frames.length;
			boolean[] newLetAbove = letAbove.clone();
			for (int i = 0; i < numLets; i++) {
				if (letAbove[i]) {
					boolean[] otherAbove = frames[i].letAbove;
					for (int j = 0; j < numLets; j++) {
						if (otherAbove[j]) {
							newLetAbove[j] = true;
						}
					}
				}
			}
			letAbove = newLetAbove;
		}
		
		public void addBelow(Frame[] frames) {
			int numLets = frames.length;
			boolean[] newLetBelow = letBelow.clone();
			for (int i = 0; i < numLets; i++) {
				if (letBelow[i]) {
					boolean[] otherBelow = frames[i].letBelow;
					for (int j = 0; j < numLets; j++) {
						if (otherBelow[j]) {
							newLetBelow[j] = true;
						}
					}
				}
			}
			letBelow = newLetBelow;
		}
		
		public int setOrigAbvBlw() {
			origAbove = 0;
			origBelow = 0;
			for (int i = 0; i < letAbove.length; i++) {
				if (letAbove[i]) {
					origAbove++;
				}
				if (letBelow[i]) {
					origBelow++;
				}
			}
			range = letAbove.length - origAbove - origBelow - 1;
			return range;
		}
		
		public void setAbvBlw(int val) {
			above = origAbove + val;
			below = origBelow + range - val;
		}
	}
	
	private static boolean incRanges(int[] currRan, int[] ranges) {
		for (int i = currRan.length-1; i >= 0; i--) {
			if (currRan[i] < ranges[i]) {
				currRan[i]++;
				break;
			} else {
				currRan[i] = 0;
				if (i == 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static class LetComp implements Comparator<Character> {
		public Frame[] frames;
		
		public LetComp(Frame[] f) {
			frames = f;
		}
		
		public int compare(Character a, Character b) {
			return frames[a - 65].below - frames[b - 65].below;
		}
	}
	
	private static class AboveComp implements Comparator<Integer> {
		public Frame[] frames;
		
		public AboveComp(Frame[] f) {
			frames = f;
		}
		
		public int compare(Integer a, Integer b) {
			return frames[a].origAbove - frames[b].origAbove;
		}
	}
	
	private static class BelowComp implements Comparator<Integer> {
		public Frame[] frames;
		
		public BelowComp(Frame[] f) {
			frames = f;
		}
		
		public int compare(Integer a, Integer b) {
			return frames[a].origBelow - frames[b].origBelow;
		}
	}
}