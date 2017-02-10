package Arrays;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * Find a pivot point (P) in an array where it satisfies Left < P < Right
 * Left = All numbers on Left side
 * Right = All numbers on Right side
 * 
 * There can be multiple pivots, return all
 * 
 * Ex. 
 * {10,7,11,15,12} , P = 11
 * {10} , P = 10
 * {1,10,10,12}, P = {1,12}
 * {10,7,11,11,15,12}, P = no pivot
 * 
 * 1) One Pass from Left to Right
 *  O(N) * O(P) -- P = number of pivot candidates
 * 	Keep track of the highest number on Left
 *  if (nums[i] > highestLeft), this is potential pivot, add it to map, and update
 *  else remove all # from map > nums[i], b/c it cannot be pivot
 *  
 * 2) One pass from Front and Back to middle of array (TOO COMPLICATED)
 * 	O(N/2) * O(2P)  + O(~4P)-- P = number of pivot candidates
 *  Keep track of the highestLeft and LowestRight, and their possible pivots in separate maps
 *  if (nums[i] <= highestLeft) --> remove all # from LeftMap > nums[i]
 *  if (nums[j] >= lowestRight) --> remove all @ from RightMap < nums[j]
 *  Check to see if they are possible candidates (be careful of even/odd sized arrays)
 *  Remove any remaining from Left/Right Map that violates the bound rules Left < P < Right
 *  Return the unique set between the two Maps
 *  
 */
public class FindPivot {

	public static void main(String[] args) {

		// No Pivot, Pivot 11 added and need to be removed
		// int[] nums = { 10, 7, 11, 13, 11, 13, 15, 12 };
		// Front and Back 1, 12 is pivot
		// int[] nums= {1,10,10,12};
		// 11, Odd Sized Array
		// int[] nums = { 10, 7, 11, 15, 12 };
		// 11, Even Sized Array
		// int[] nums = { 10, 7, 11, 13, 15, 12 };
		// 11,13,15, Multi-pivots
		// int[] nums = { 10, 7, 11, 13, 15, 27, 18 };
		// 11, 13,Multi-pivots
		// int[] nums = { 10, 7, 11, 13, 15, 14, 27, 18 };
		// 11, Single pivot
		// int[] nums = { 10, 7, 11, 14, 15, 13, 27, 18 };
		// 11,14,15,29,30 Different span pivots
		// int[] nums = { 10, 7, 11, 14, 15, 20, 27, 18, 29, 30 };
		List<int[]> numbers = new ArrayList<int[]>();
		numbers.add(new int[] { 10, 7, 11, 13, 11, 13, 15, 12 });
		numbers.add(new int[] { 1, 10, 10, 12 });
		numbers.add(new int[] { 10, 7, 11, 15, 12 });
		numbers.add(new int[] { 10, 7, 11, 13, 15, 12 });
		numbers.add(new int[] { 10, 7, 11, 13, 15, 27, 18 });
		numbers.add(new int[] { 10, 7, 11, 13, 15, 14, 27, 18 });
		numbers.add(new int[] { 10, 7, 11, 14, 15, 20, 27, 18, 29, 30 });
		for (int[] nums : numbers) {
			// O(N) * O(P) -- P = number of pivot candidates
			System.out.println("One Pass from Left Pivots: ");
			for (int i : findPivot(nums)) {
				System.out.println(i);
			}

			// O(N/2) * O(2P) + O(~4P)-- P = number of pivot candidates
			System.out.println("Front/Back Pass Pivots: ");
			for (int i : findPivotFrontBack(nums)) {
				System.out.println(i);
			}
		}
	}

	public static List<Integer> findPivot(int[] nums) {
		if (nums == null) {
			return null;
		}
		if (nums.length == 1) {
			return new ArrayList<Integer>(nums[0]);
		}

		// Holds Possible Pivot Points
		// O(P) -- If nums[] is sorted asc P==N at end -- sorted desc, P==1
		Map<Integer, Integer> indexToNumberPossiblePivots = new HashMap<>();

		// Highest number on the left side of the array
		int highestLeft = Integer.MIN_VALUE;

		// Loop through and check if num[i] > highestLeft (which is minimum
		// requirement as pivot)
		// Remove all numbers in possible pivot points
		// O(N) * O(P) -- P = number of pivot candidates
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] > highestLeft) {
				// Possible Pivot, Update Highest on Leftside
				indexToNumberPossiblePivots.put(i, nums[i]);
				highestLeft = nums[i];
			} else {
				// nums[i] < highestLeft
				// Anything greater than/= nums[i] cannot be pivot on Left side
				// Remove all greater
				// O(P) -- number of pivot candidates
				Iterator<Integer> itr = indexToNumberPossiblePivots.keySet().iterator();
				while (itr.hasNext()) {
					int index = itr.next();
					if (indexToNumberPossiblePivots.get(index) >= nums[i]) {
						itr.remove();
					}
				}
			}
		}

		// At end, only remaining are possible pivots
		return new ArrayList<Integer>(indexToNumberPossiblePivots.values());
	}

	public static Set<Integer> findPivotFrontBack(int[] nums) {
		if (nums == null) {
			return null;
		}
		if (nums.length == 1) {
			return new HashSet<Integer>(nums[0]);
		}

		// Holds Possible Pivot Points
		// O(P) -- If nums[] is sorted asc P==N at end -- sorted desc, P==1
		Map<Integer, Integer> indexToNumberPossiblePivotsLeft = new HashMap<>();
		Map<Integer, Integer> indexToNumberPossiblePivotsRight = new HashMap<>();

		// Highest number on the left side of the array
		// Lowest number on the right side of the array
		int highestLeft = nums[0];
		int lowestRight = nums[nums.length - 1];
		indexToNumberPossiblePivotsLeft.put(0, highestLeft);
		indexToNumberPossiblePivotsRight.put(nums.length - 1, nums[nums.length - 1]);

		// Loop through and check if num[i] > highestLeft (which is minimum
		// requirement as pivot)
		// Remove all numbers in possible pivot points
		// O(N/2) --> O(N) * O(2*P) -- P = number of pivot candidates
		for (int i = 1, j = nums.length - 2; i <= j; i++, j--) {
			// Anything greater than/= nums[i] cannot be pivot on Left side
			// Remove all greater/=
			// O(P) -- number of pivot candidates
			if (nums[i] <= highestLeft) {
				Iterator<Integer> itr = indexToNumberPossiblePivotsLeft.keySet().iterator();
				while (itr.hasNext()) {
					int index = itr.next();
					if (indexToNumberPossiblePivotsLeft.get(index) >= nums[i]) {
						itr.remove();
					}
				}
			}

			// Anything less than/= nums[j] cannot be pivot on Right side
			// Remove all less than/=
			// O(P) -- number of pivot candidates
			if (nums[j] >= lowestRight) {
				Iterator<Integer> itr = indexToNumberPossiblePivotsRight.keySet().iterator();
				while (itr.hasNext()) {
					int index = itr.next();
					if (indexToNumberPossiblePivotsRight.get(index) <= nums[j]) {
						itr.remove();
					}
				}
			}

			// If they are the same index(odd arrays), treat them as possible
			// pivots
			// If not (even arrays), then make sure nums[i] < nums[j]
			if ((i == j || nums[i] < nums[j])) {
				// num[i] is candidate within bounds (hL < n[i] < lR)
				if (nums[i] > highestLeft) {
					if (nums[i] < lowestRight) {
						indexToNumberPossiblePivotsLeft.put(i, nums[i]);
					}
					// Only update if they are not the same cell
					if (i != j) {
						highestLeft = nums[i];
					}
				}
				// num[j] is candidate within bounds (hL < n[j] < lR)
				if (nums[j] < lowestRight) {
					if (nums[j] > highestLeft) {
						indexToNumberPossiblePivotsRight.put(j, nums[j]);
					}
					// Only update if they are not the same cell
					if (i != j) {
						lowestRight = nums[j];
					}
				}
			} else {
				// Not candidates, see if we need to update the
				// highestLeft/lowestRight
				// Only update if they are not the same cell
				if (nums[i] > highestLeft) {
					highestLeft = nums[i];
				}
				if (nums[j] < lowestRight) {
					lowestRight = nums[j];
				}
			}
		}

		// O(P) - Remove all left over candidates that are not in bounds
		Iterator<Integer> itr = indexToNumberPossiblePivotsLeft.keySet().iterator();
		while (itr.hasNext()) {
			int index = itr.next();
			if (indexToNumberPossiblePivotsLeft.get(index) >= lowestRight) {
				itr.remove();
			}
		}

		// O(P) - Remove all left over candidates that are not in bounds
		itr = indexToNumberPossiblePivotsRight.keySet().iterator();
		while (itr.hasNext()) {
			int index = itr.next();
			if (indexToNumberPossiblePivotsRight.get(index) <= highestLeft) {
				itr.remove();
			}
		}
		// Return the unique possible pivots
		Set<Integer> result = new HashSet<Integer>(indexToNumberPossiblePivotsLeft.values());
		result.addAll(indexToNumberPossiblePivotsRight.values());

		// At end, only remaining are possible pivots
		return result;
	}
}
