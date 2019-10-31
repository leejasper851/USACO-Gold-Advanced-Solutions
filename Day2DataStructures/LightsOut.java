package Day2DataStructures;

import java.io.*;
import java.util.*;

public class LightsOut {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		int numVerts = Integer.parseInt(reader.readLine());
		Vert[] verts = new Vert[numVerts];
		
		for (int i = 0; i < numVerts; i++) {
			StringTokenizer st = new StringTokenizer(reader.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			if (i == 0) {
				verts[0] = new Vert(x, y, true, numVerts);
				continue;
			}
			verts[i] = new Vert(x, y, false, verts[i - 1], numVerts);
			if (i == numVerts-1) {
				verts[numVerts-1].addRight(verts[0]);
			}
		}
		reader.close();
		
		for (int i = 0; i < numVerts; i++) {
			verts[i].calcAngle();
		}
		for (int i = 0; i < numVerts; i++) {
			verts[i].calcPath();
		}
		
		int totalDist = 0;
		for (int i = 0; i < numVerts; i++) {
			totalDist += verts[i].distLeft;
		}
		int[] shortDist = new int[numVerts];
		int[] cwDist = new int[numVerts];
		int[] ccwDist = new int[numVerts];
		int sumDist = 0;
		shortDist[0] = 0;
		cwDist[0] = 0;
		ccwDist[0] = 0;
		for (int i = 1; i < numVerts; i++) {
			sumDist += verts[i].distLeft;
			if (sumDist < totalDist / 2) {
				shortDist[i] = sumDist;
			} else {
				shortDist[i] = totalDist - sumDist;
			}
			cwDist[i] = sumDist;
			ccwDist[i] = totalDist - sumDist;
		}
		
		int[] nextVert = new int[numVerts];
		for (int i = 0; i < numVerts; i++) {
			for (int j = 0; j < numVerts; j++) {
				if (j == i) {
					continue;
				}
				int val = verts[i].calcDiff(i, verts[j]);
				if (val > nextVert[i]) {
					nextVert[i] = val;
				}
			}
			nextVert[i] = (i + nextVert[i]) % numVerts;
		}
		
		int maxDiff = 0;
		for (int i = 1; i < numVerts; i++) {
			int dist1 = cwDist[nextVert[i]] - cwDist[i];
			int dist2 = ccwDist[nextVert[i]] - ccwDist[i];
			int dist = Math.min(Math.abs(dist1), Math.abs(dist2));
			int val = dist + shortDist[nextVert[i]];
			int diff = val - shortDist[i];
			if (diff > maxDiff) {
				maxDiff = diff;
			}
		}
		writer.println(maxDiff);
		writer.close();
	}
	
	private static class Vert {
		public int x;
		public int y;
		public boolean exit;
		public int angle;
		public Vert left;
		public Vert right;
		public int distLeft;
		public int distRight;
		public int[] pathAngle;
		public int[] pathLen;
		
		public Vert(int x, int y, boolean e, int numVerts) {
			this.x = x;
			this.y = y;
			exit = e;
			
			pathAngle = new int[numVerts];
			pathLen = new int[numVerts];
		}
		
		public Vert(int x, int y, boolean e, Vert l, int numVerts) {
			this.x = x;
			this.y = y;
			left = l;
			distLeft = distBtwn(left);
			left.right = this;
			left.distRight = left.distBtwn(this);

			pathAngle = new int[numVerts];
			pathLen = new int[numVerts];
		}
		
		public void addRight(Vert r) {
			right = r;
			distRight = distBtwn(right);
			right.left = this;
			right.distLeft = right.distBtwn(this);
		}
		
		private int distBtwn(Vert other) {
			if (x == other.x) {
				return Math.abs(y - other.y);
			}
			return Math.abs(x - other.x);
		}
		
		public void calcAngle() {
			// 0 - up, 1 - right, 2 - down, 3 - left
			int leftDir = -1;
			if (left.x == x) {
				if (left.y > y) {
					leftDir = 0;
				} else {
					leftDir = 2;
				}
			} else {
				if (left.x > x) {
					leftDir = 1;
				} else {
					leftDir = 3;
				}
			}
			int rightDir = -1;
			if (right.x == x) {
				if (right.y > y) {
					rightDir = 0;
				} else {
					rightDir = 2;
				}
			} else {
				if (right.x > x) {
					rightDir = 1;
				} else {
					rightDir = 3;
				}
			}
			
			if (leftDir == rightDir + 1 || leftDir == 0 && rightDir == 3) {
				angle = 90;
				return;
			}
			if (leftDir == rightDir + 2 || leftDir + 2 == rightDir) {
				angle = 180;
				return;
			}
			angle = 270;
		}
		
		public void calcPath() {
			Vert curr = this;
			for (int i = 0; i < pathAngle.length; i++) {
				pathAngle[i] = curr.angle;
				if (curr.exit) {
					pathAngle[i] = -1;
				}
				pathLen[i] = curr.distRight;
				curr = curr.right;
			}
		}
		
		public int calcDiff(int ind, Vert other) {
			for (int i = 0; i < pathAngle.length; i++) {
				if (pathAngle[i] != other.pathAngle[i]) {
					return i;
				}
				if (pathLen[i] != other.pathLen[i]) {
					return i + 1;
				}
			}
			return -1;
		}
	}
}