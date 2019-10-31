package Day3Graph1;

import java.io.*;
import java.util.*;

public class cruise {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int numPorts = Integer.parseInt(st.nextToken());
		int seqLen = Integer.parseInt(st.nextToken());
		long numSeqs = Integer.parseInt(st.nextToken());
		int[][] ports = new int[numPorts+1][2];
		for (int i = 1; i <= numPorts; i++) {
			st = new StringTokenizer(reader.readLine());
			ports[i][0] = Integer.parseInt(st.nextToken());
			ports[i][1] = Integer.parseInt(st.nextToken());
		}
		int[] seq = new int[seqLen];
		st = new StringTokenizer(reader.readLine());
		for (int i = 0; i < seqLen; i++) {
			seq[i] = st.nextToken().equals("L") ? 0 : 1;
		}
		reader.close();
		
		int[] seqRes = new int[numPorts+1];
		for (int i = 1; i <= numPorts; i++) {
			int currPort = i;
			for (int j = 0; j < seqLen; j++) {
				currPort = ports[currPort][seq[j]];
			}
			seqRes[i] = currPort;
		}
		
		int[] seqRep = new int[numPorts+1];
		for (int i = 1; i <= numPorts; i++) {
			int currPort = i;
			for (int j = 0; j < 500; j++) {
				currPort = seqRes[currPort];
			}
			seqRep[i] = currPort;
		}
		
		int currPort = 1;
		long num500 = numSeqs / 500;
		for (int i = 0; i < num500; i++) {
			currPort = seqRep[currPort];
		}
		long remNum = numSeqs % 500;
		for (int i = 0; i < remNum; i++) {
			currPort = seqRes[currPort];
		}
		writer.println(currPort);
		writer.close();
	}
}