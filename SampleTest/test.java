package SampleTest;

import java.io.*;
import java.util.*;

public class test {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		int numTimes = Integer.parseInt(reader.readLine());
		reader.close();
		
		for (int i = 0; i < numTimes; i++) {
			writer.println("This is a test!");
		}
		writer.close();
	}
}