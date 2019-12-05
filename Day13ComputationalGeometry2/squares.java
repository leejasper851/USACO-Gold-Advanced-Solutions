package Day13ComputationalGeometry2;

import java.io.*;
import java.util.*;

public class squares {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numPasts = Integer.parseInt(st.nextToken());
		int pastSize = Integer.parseInt(st.nextToken());
		TreeSet<Past> pasts = new TreeSet<>();
		for (int i = 0; i < numPasts; i++) {
			st = new StringTokenizer(reader.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			pasts.add(new Past(x, y, pastSize));
		}
		reader.close();
		
		int area = 0;
		int numOverlapped = 0;
		for (Past past : pasts) {
			int roundX = Math.abs(past.x) - Math.abs(past.x) % pastSize;
			if (past.x < 0) {
				roundX *= -1;
				if (past.x % 6 != 0) {
					roundX -= pastSize;
				}
			}
			
			Past lower = pasts.ceiling(new Past(roundX - pastSize, past.y - pastSize, pastSize));
			Past higher = pasts.floor(new Past(roundX - 1, past.y + pastSize, pastSize));
			Set<Past> rangeSet = new TreeSet<Past>();
			if (lower != null && higher != null && higher.compareTo(lower) >= 0) {
				rangeSet = pasts.subSet(lower, true, higher, true);
			}
			for (Past rangePast : rangeSet) {
				if (past.compareTo(rangePast) == 0) {
					continue;
				}
				if (past.overlap(rangePast) > 0) {
					numOverlapped++;
					if (numOverlapped > 2) {
						break;
					}
					area = past.overlap(rangePast);
				}
			}
			if (numOverlapped > 2) {
				break;
			}
			
			lower = pasts.ceiling(new Past(roundX, past.y - pastSize, pastSize));
			higher = pasts.floor(new Past(roundX + pastSize - 1, past.y + pastSize, pastSize));
			if (lower != null && higher != null && higher.compareTo(lower) >= 0) {
				rangeSet = pasts.subSet(lower, true, higher, true);
			} else {
				rangeSet = new TreeSet<>();
			}
			for (Past rangePast : rangeSet) {
				if (past.compareTo(rangePast) == 0) {
					continue;
				}
				if (past.overlap(rangePast) > 0) {
					numOverlapped++;
					if (numOverlapped > 2) {
						break;
					}
					area = past.overlap(rangePast);
				}
			}
			if (numOverlapped > 2) {
				break;
			}
			
			lower = pasts.ceiling(new Past(roundX + pastSize, past.y - pastSize, pastSize));
			higher = pasts.floor(new Past(roundX + pastSize * 2 - 1, past.y + pastSize, pastSize));
			if (lower != null && higher != null && higher.compareTo(lower) >= 0) {
				rangeSet = pasts.subSet(lower, true, higher, true);
			} else {
				rangeSet = new TreeSet<>();
			}
			for (Past rangePast : rangeSet) {
				if (past.compareTo(rangePast) == 0) {
					continue;
				}
				if (past.overlap(rangePast) > 0) {
					numOverlapped++;
					if (numOverlapped > 2) {
						break;
					}
					area = past.overlap(rangePast);
				}
			}
			if (numOverlapped > 2) {
				break;
			}
		}
		
		if (numOverlapped == 0) {
			writer.println(0);
		} else if (numOverlapped > 2) {
			writer.println(-1);
		} else {
			writer.println(area);
		}
		writer.close();
	}
	
	private static class Past implements Comparable<Past> {
		public int x;
		public int y;
		public int pastSize;
		
		public Past(int x, int y, int ps) {
			this.x = x;
			this.y = y;
			pastSize = ps;
		}
		
		public int overlap(Past other) {
			if (Math.abs(x - other.x) >= pastSize || Math.abs(y - other.y) >= pastSize) {
				return 0;
			}
			int xOver = pastSize - Math.abs(x - other.x);
			int yOver = pastSize - Math.abs(y - other.y);
			return xOver * yOver;
		}
		
		public int compareTo(Past other) {
			int roundX = Math.abs(x) - Math.abs(x) % pastSize;
			if (x < 0) {
				roundX *= -1;
				if (x % 6 != 0) {
					roundX -= pastSize;
				}
			}
			int otherX = Math.abs(other.x) - Math.abs(other.x) % pastSize;
			if (other.x < 0) {
				otherX *= -1;
				if (other.x % 6 != 0) {
					otherX -= pastSize;
				}
			}
			if (roundX == otherX) {
				if (y == other.y) {
					return x - other.x;
				}
				return y - other.y;
			}
			return roundX - otherX;
		}
	}
}