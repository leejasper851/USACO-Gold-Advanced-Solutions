package Day11MixedProblemSet2;

import java.io.*;
import java.util.*;

public class cbarn2 {
	private static final long MAX_VAL = 10000000000L;
	
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numRooms = Integer.parseInt(st.nextToken());
		int numDoors = Integer.parseInt(st.nextToken());
		int[] rooms = new int[numRooms];
		for (int i = 0; i < numRooms; i++) {
			rooms[i] = Integer.parseInt(reader.readLine());
		}
		
		long minDist = Long.MAX_VALUE;
		for (int i = 0; i < numRooms; i++) {
			long[][] dists = new long[numDoors+1][numRooms+1];
			Arrays.fill(dists[0], MAX_VAL);
			dists[0][numRooms] = 0;
			for (int door = 1; door <= numDoors; door++) {
				for (int room = 0; room <= numRooms; room++) {
					dists[door][room] = MAX_VAL;
					long dist = 0;
					for (int prevRoom = room + 1; prevRoom <= numRooms; prevRoom++) {
						dist += rooms[prevRoom - 1] * (prevRoom - room - 1);
						dists[door][room] = Math.min(dists[door][room], dist + dists[door - 1][prevRoom]);
					}
				}
			}
			minDist = Math.min(minDist, dists[numDoors][0]);
			rotateLeft(rooms);
		}
		writer.println(minDist);
		writer.close();
	}
	
	private static void rotateLeft(int[] rooms) {
		int firstVal = rooms[0];
		for (int i = 0; i < rooms.length - 1; i++) {
			rooms[i] = rooms[i + 1];
		}
		rooms[rooms.length-1] = firstVal;
	}
}