package AppliedAlgorithms;

import java.util.*;

/**
 * Answer to HackerRank -- Count how many ways to climb stairs taking 1, 2, 3 steps
 * 
 * O(n)
 * Iterative - maintain 3 variables
 * 
 * Recurisve + Memoization
 * 
 * @author ot
 *
 */
public class ClimbStairs {

	public static void main(String[] args) {
		int s = 7;
		for (int i = 0; i <= s; i++) {
			// MEMOED VERSION
			System.out.println("Memoed: " + countWaysToClimbStairsMemoed(i));
			// ITERATIVE VERSION
			System.out.println("Iterative: " +countWaysToClimbStairsIterative(i));
		}
	}
	
	/** 
	 * Iterative way using 3 variables to hold prior values (Fibonacci style)
	 * @param stairs
	 * @return
	 */
	public static int countWaysToClimbStairsIterative(int stairs) {
		if(stairs < 0) {
			return 0;
		}
		if(stairs <= 1) {
			return 1;
		}
		if(stairs == 2) {
			return 2;
		}
		if(stairs == 3) {
			return 4;
		}
		int firstVal = 1, secondVal = 2, thirdVal = 4;
		int ways = 0;
		for(int i = 4; i <= stairs; i++) {
			ways = firstVal + secondVal + thirdVal;
			firstVal = secondVal;
			secondVal = thirdVal;
			thirdVal = ways;		
		}
		
		return ways;
	}

	/**
	 * Recursive memoization problem
	 * @param stairs
	 * @return
	 */
	public static int countWaysToClimbStairsMemoed(int stairs) {
		Map<Integer, Integer> memoedWays = new HashMap<Integer, Integer>();
		return countWaysToClimbStairsHelper(stairs, memoedWays);
	}

	public static int countWaysToClimbStairsHelper(int stairs, Map<Integer, Integer> memoedWays) {
		// Base Case: 0 or 1 stair = 1 way,  < 0 stairs = 0 ways
		if (stairs < 0) {
			return 0;
		}
		if (stairs <= 1) { // assume 0 stairs there is one way to move
			return 1;
		}
		int ways = 0;
		if (!memoedWays.containsKey(stairs)) {
			ways += countWaysToClimbStairsHelper(stairs - 1, memoedWays)
					+ countWaysToClimbStairsHelper(stairs - 2, memoedWays)
					+ countWaysToClimbStairsHelper(stairs - 3, memoedWays);
			memoedWays.put(stairs, ways);
		}
		return memoedWays.get(stairs);
	}
}
