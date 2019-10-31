package Day2DataStructures;

import java.io.*;
import java.util.*;

public class ttravel {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		
		int numQuers = Integer.parseInt(reader.readLine());
		ArrayList<Stack<Integer>> stacks = new ArrayList<Stack<Integer>>();
		stacks.add(new Stack<Integer>());
		for (int i = 1; i <= numQuers; i++) {
			StringTokenizer st = new StringTokenizer(reader.readLine());
			String command = st.nextToken();
			if (command.equals("a")) {
				int id = Integer.parseInt(st.nextToken());
				Stack<Integer> stack = (Stack<Integer>) stacks.get(i - 1).clone();
				stack.push(id);
				writer.println(id);
				stacks.add(stack);
			} else if (command.equals("s")) {
				Stack<Integer> stack = (Stack<Integer>) stacks.get(i - 1).clone();
				stack.pop();
				if (stack.empty()) {
					writer.println(-1);
				} else {
					writer.println(stack.peek());
				}
				stacks.add(stack);
			} else {
				int quer = Integer.parseInt(st.nextToken());
				Stack<Integer> stack = (Stack<Integer>) stacks.get(quer - 1).clone();
				if (stack.empty()) {
					writer.println(-1);
				} else {
					writer.println(stack.peek());
				}
				stacks.add(stack);
			}
		}
		reader.close();
		writer.close();
	}
}