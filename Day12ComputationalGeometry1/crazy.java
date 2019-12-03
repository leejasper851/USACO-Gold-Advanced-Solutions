package Day12ComputationalGeometry1;

import java.io.*;
import java.util.*;

public class crazy {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numFences = Integer.parseInt(st.nextToken());
		int numCows = Integer.parseInt(st.nextToken());
		TreeMap<Point, Point> fences1 = new TreeMap<>(new PointComp());
		TreeMap<Point, Point> fences2 = new TreeMap<>(new PointComp());
		for (int i = 0; i < numFences; i++) {
			st = new StringTokenizer(reader.readLine());
			int ax = Integer.parseInt(st.nextToken());
			int ay = Integer.parseInt(st.nextToken());
			int bx = Integer.parseInt(st.nextToken());
			int by = Integer.parseInt(st.nextToken());
			if (!fences1.containsKey(new Point(ax, ay))) {
				fences1.put(new Point(ax, ay), new Point(bx, by));
			} else {
				fences2.put(new Point(ax, ay), new Point(bx, by));
			}
			if (!fences1.containsKey(new Point(bx, by))) {
				fences1.put(new Point(bx, by), new Point(ax, ay));
			} else {
				fences2.put(new Point(bx, by), new Point(ax, ay));
			}
		}
		Point[] cows = new Point[numCows];
		for (int i = 0; i < numCows; i++) {
			st = new StringTokenizer(reader.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			cows[i] = new Point(x, y);
		}
		reader.close();
		
		ArrayList<ArrayList<Point>> comms = new ArrayList<>();
		ArrayList<Integer> tops = new ArrayList<>();
		while (!fences1.isEmpty()) {
			ArrayList<Point> comm = new ArrayList<>();
			Point firstPoint = fences1.keySet().iterator().next();
			comm.add(firstPoint);
			int top = firstPoint.y;
			Point prevPoint = firstPoint;
			Point currPoint = fences1.get(firstPoint);
			fences1.remove(firstPoint);
			fences2.remove(firstPoint);
			while (!currPoint.equals(firstPoint)) {
				comm.add(currPoint);
				top = Math.max(top, currPoint.y);
				Point point1 = fences1.get(currPoint);
				Point point2 = fences2.get(currPoint);
				if (point1.equals(prevPoint)) {
					prevPoint = currPoint;
					currPoint = point2;
				} else {
					prevPoint = currPoint;
					currPoint = point1;
				}
				fences1.remove(prevPoint);
				fences2.remove(prevPoint);
			}
			comms.add(comm);
			tops.add(top);
		}
		
		int[] commCows = new int[comms.size() + 1];
		for (int i = 0; i < numCows; i++) {
			int comm = -1;
			for (int j = 0; j < comms.size(); j++) {
				if (inside(cows[i], comms.get(j))) {
					if (comm == -1 || tops.get(j) < tops.get(comm)) {
						comm = j;
					}
				}
			}
			if (comm == -1) {
				commCows[commCows.length-1]++;
			} else {
				commCows[comm]++;
			}
		}
		
		int maxComm = 0;
		for (int commCow : commCows) {
			maxComm = Math.max(maxComm, commCow);
		}
		writer.println(maxComm);
		writer.close();
	}
	
	private static class Point {
		public int x;
		public int y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public boolean equals(Object otherObj) {
			Point other = (Point) otherObj;
			return (x == other.x && y == other.y);
		}
		
		public Point clone() {
			return new Point(x, y);
		}
	}
	
	private static class Line {
		public Point a;
		public Point b;
		
		public Line(Point a, Point b) {
			this.a = a;
			this.b = b;
		}
	}
	
	private static int ccw(Point a, Point b, Point c) {
		long dx1 = b.x - a.x;
		long dy1 = b.y - a.y;
		long dx2 = c.x - a.x;
		long dy2 = c.y - a.y;
		if (dx1 * dy2 > dy1 * dx2) {
			return 1;
		}
		if (dx1 * dy2 < dy1 * dx2) {
			return -1;
		}
		if ((dx1 * dx2 < 0) || (dy1 * dy2 < 0)) {
			return -1;
		}
		if ((dx1 * dx1 + dy1 * dy1) < (dx2 * dx2 + dy2 * dy2)) {
			return 1;
		}
		return 0;
	}
	
	private static boolean intersect(Line a, Line b) {
		return ((ccw(a.a, a.b, b.a) * ccw(a.a, a.b, b.b) <= 0) &&
			(ccw(b.a, b.b, a.a) * ccw(b.a, b.b, a.b) <= 0));
	}
	
	private static boolean inside(Point p, ArrayList<Point> poly) {
		Line extend = new Line(p.clone(), p.clone());
		extend.b.x = 1000001;
		extend.b.y += 1;
		poly.add(0, poly.get(poly.size()-1));
		int count = 0;
		for (int i = 1; i < poly.size(); i++) {
			Point polyP = poly.get(i);
			Line polyL = new Line(polyP, poly.get(i - 1));
			if (intersect(polyL, extend)) {
				count++;
			}
		}
		poly.remove(0);
		return (count % 2 == 1);
	}
	
	private static class PointComp implements Comparator<Point> {
		public PointComp() {
		}
		
		public int compare(Point a, Point b) {
			if (a.y == b.y) {
				return a.x - b.x;
			}
			return a.y - b.y;
		}
	}
}