package Day2DataStructures;

import java.io.*;
import java.util.*;

public class art2 {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		int len = Integer.parseInt(reader.readLine());
		int[] topCols = new int[len];
		for (int i = 0; i < len; i++) {
			topCols[i] = Integer.parseInt(reader.readLine());
		}
		reader.close();
		
		int[] lastInds = new int[len+1];
		boolean[] seen1 = new boolean[len+1];
		for (int i = len-1; i >= 0; i--) {
			int col = topCols[i];
			if (!seen1[col]) {
				lastInds[col] = i;
				seen1[col] = true;
			}
		}
		
		int currLayers = 0;
		int maxLayers = 0;
		boolean impossible = false;
		boolean[] seen2 = new boolean[len+1];
		int topCol = 0;
		for (int i = 0; i < len; i++) {
			int col = topCols[i];
			if (col > 0) {
				if (!seen2[col]) {
					currLayers++;
					seen2[col] = true;
					topCol = col;
				}
				if (currLayers > maxLayers) {
					maxLayers = currLayers;
				}
				if (lastInds[col] == i) {
					currLayers--;
					if (topCol > 0 && topCol != col) {
						impossible = true;
						break;
					}
					topCol = 0;
				}
			} else {
				if (currLayers > 0) {
					impossible = true;
					break;
				}
			}
		}
		
		if (impossible) {
			writer.println(-1);
		} else {
			writer.println(maxLayers);
		}
		writer.close();
	}
}