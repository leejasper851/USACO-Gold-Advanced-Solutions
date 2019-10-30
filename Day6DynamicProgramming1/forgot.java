package Day6DynamicProgramming1;

import java.io.*;
import java.util.*;

public class forgot {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		StringTokenizer st = new StringTokenizer(reader.readLine());
		int len = Integer.parseInt(st.nextToken());
		int numWords = Integer.parseInt(st.nextToken());
		char[] passwd = new char[len];
		String line = reader.readLine();
		for (int i = 0; i < len; i++) {
			passwd[i] = line.charAt(i);
		}
		String[] words = new String[numWords];
		for (int i = 0; i < numWords; i++) {
			words[i] = reader.readLine();
		}
		reader.close();
		
		boolean[] reached = new boolean[len];
		int[] wordAt = new int[len];
		String[] prefix = new String[len];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < numWords; j++) {
				String word = words[j];
				if (wordMatch(word, passwd, i, reached)) {
					String currPre = getPrefix(word, i, prefix);
					if (!reached[i]) {
						wordAt[i] = j;
						reached[i] = true;
						prefix[i] = currPre;
						continue;
					}
					if (currPre.compareTo(prefix[i]) < 0) {
						wordAt[i] = j;
						reached[i] = true;
						prefix[i] = currPre;
					}
				}
			}
		}
		writer.println(prefix[len-1]);
		writer.close();
	}
	
	private static boolean wordMatch(String word, char[] passwd, int endInd, boolean[] reached) {
		if (endInd - word.length() + 1 < 0) {
			return false;
		}
		if (endInd - word.length() >= 0 && !reached[endInd - word.length()]) {
			return false;
		}
		for (int i = 0; i < word.length(); i++) {
			int passInd = endInd - word.length() + 1 + i;
			if (passwd[passInd] != '?' && word.charAt(i) != passwd[passInd]) {
				return false;
			}
		}
		return true;
	}
	
	private static String getPrefix(String word, int endInd, String[] prefix) {
		if (endInd - word.length() < 0) {
			return word;
		}
		return prefix[endInd - word.length()] + word;
	}
}