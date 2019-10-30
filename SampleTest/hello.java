package SampleTest;

import java.io.*;
import java.util.*;

public class hello {
	public static void main(String[] args) throws IOException {
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		writer.println("Hello World!");
		writer.close();
	}
}