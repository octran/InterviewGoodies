package StacksAndQueues;

import java.util.*;

/*
 * Answer to HackerRank -- If a string is symbol balanced
 * 
 * T = O(n) S = O(1) 
 * Using stack AND If/Else Condition OR stack AND Map
 */
public class SymbolMatch {

	/**
	 * Using Stack + If/ELSE
	 * push() corresponding closing symbol when an open symbol is seen
	 * pop() and check if closing symbol on stack matches with character in string
	 * @param expression
	 * @return
	 */
	public static boolean isBalanced(String expression) {
		// null /empty strings are balanced
		if (expression == null || expression.length() == 0) {
			return true;
		}
		// Odd length cannot be balanced
		if (expression.length() % 2 == 1) {
			return false;
		}
		// Time = O(n)
		// Space = O(1)
		Stack<Character> symbols = new Stack<Character>();
		for (int i = 0; i < expression.length(); i++) {
			char c = expression.charAt(i);
			// IF Open symbol, ADD its CLOSING symbol to stack
			if (c == '(') {
				symbols.push(')');
			} else if (c == '[') {
				symbols.push(']');
			} else if (c == '{') {
				symbols.push('}');
			} else {
				// Close symbol or other, check if it matches what should be there
				if (symbols.pop() != c) {
					return false;
				}
			}
		}

		// At end stack must also be empty to be balanced
		return symbols.isEmpty();
	}
	
	/**
	 * Using STACK + MAP to hold open and close symbol map
	 * push() - matching closing symbol to stack when see open symbol
	 * pop() - pop and check for match with character
	 * @param expression
	 * @return
	 */
	public static boolean isBalancedMAP(String expression) {
		// null /empty strings are balanced
		if (expression == null || expression.length() == 0) {
			return true;
		}
		// Odd length cannot be balanced
		if (expression.length() % 2 == 1) {
			return false;
		}
		// maybe not ideal to keep creating map inside of method
		// Time = O(n)
		// Space = O(c)-- constant amount of items stored
		Map<Character, Character> openCloseSymbolMap = new HashMap<Character, Character>();
		openCloseSymbolMap.put('(', ')');
		openCloseSymbolMap.put('[', ']');
		openCloseSymbolMap.put('{', '}');

		Stack<Character> symbols = new Stack<Character>();
		for (int i = 0; i < expression.length(); i++) {
			char c = expression.charAt(i);
			if (openCloseSymbolMap.containsKey(c)) {
				// Open symbol, add its CLOSING symbol to stack
				symbols.push(openCloseSymbolMap.get(c));
			} else {
				// Other symbol, pop stack and check closing symbol
				if (symbols.isEmpty() || symbols.pop() != c) {
					// Mapped Closing symbol does not match c,
					// or Symbols is empty when not suppose to be
					return false;
				}
			}
		}

		// At end stack must also be empty to be balanced
		return symbols.isEmpty();
	}


	public static void main(String[] args) {
		String a = "{[()]}";
		String b = "{[(])}";
		String c = "{{[[(())]]}}";

		String d = "{{{{[[(())]]}}}]";
		/*
		System.out.println((isBalanced(null)) ? "YES" : "NO"); // YES
		System.out.println((isBalanced("[")) ? "YES" : "NO"); // NO
		System.out.println((isBalanced("]")) ? "YES" : "NO"); // NO
		System.out.println((isBalanced(d)) ? "YES" : "NO"); // NO

		System.out.println((isBalanced(a)) ? "YES" : "NO"); // YES
		System.out.println((isBalanced(b)) ? "YES" : "NO"); // NO
		System.out.println((isBalanced(c)) ? "YES" : "NO"); // YES
		*/
		System.out.println(assertion(isBalanced(null), true));
		System.out.println(assertion(isBalanced("["), false));
		System.out.println(assertion(isBalanced("]"), false));
		System.out.println(assertion(isBalanced(d), false));
		System.out.println(assertion(isBalanced(a), true));
		System.out.println(assertion(isBalanced(b), false));
		System.out.println(assertion(isBalanced(c), true));
	}
	
	private static String assertion(boolean answer, boolean expected) {
		return answer == expected ? "PASS" : "FAIL -- Unexpected result of " + answer;
	}
}
