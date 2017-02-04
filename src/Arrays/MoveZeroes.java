/*
 * Given an array of numbers
 * Move all the Zeroes to the end
 * 	1) No order to the non zero numbers
 *  2) Keep relative Order to the non zero numbers
 *  
 *  Ex. {0,1,0,3,12,0};
 *  
 *  No order	12 1 3 0 0 0  
 *  keep order  1 3 12 0 0 0 
 */
public class MoveZeroes {

	public static void main(String[] args) {
		// int [] inputArr = new int[] {1, 2, 0, 3, 0, 1, 2};
		// int [] inputArr = new int[] {0, 2, 0, 3, 0, 1, 0};
		// int [] inputArr = new int[] {0, 0, 0, 0, 1, 0, 0};
		int[] inputArr = new int[] { 0, 1, 0, 3, 12, 0 };
		System.out.println("No order");
		moveZeroesNoOrder(inputArr);
		for (int i : inputArr) {
			System.out.print(i);
			System.out.print(" ");
		}
		System.out.println(" ");

		int[] inputArr2 = new int[] { 0, 1, 0, 3, 12, 0 };
		System.out.println("keep order ");
		moveZeroesKeepOrder(inputArr2);
		for (int i : inputArr2) {
			System.out.print(i);
			System.out.print(" ");
		}
	}

	public static void moveZeroesKeepOrder(int[] nums) {
		// O(n)
		if (nums == null || nums.length == 1) {
			return;
		} // not processing null or one element

		// start last non 0 element at 0, b/c we will process it anyways ex.
		// {2,1,0}, first operation 2 = 2 anyways
		int lastNonZero = 0;
		// If the current element is not 0, then we need to
		// append it just in front of last non 0 element we found.
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] != 0) {
				nums[lastNonZero++] = nums[i];
			}
		}
		// After we have finished processing new elements,
		// all the non-zero elements are already at beginning of array.
		// We just need to fill remaining array with 0's.
		for (int i = lastNonZero; i < nums.length; i++) {
			nums[i] = 0;
		}
	}

	public static void moveZeroesNoOrder(int[] nums) {
		// O(n)
		// Move Zeroes to right end of array
		// For array [1, 2, 0, 3, 0, 1, 2], the program can output [1, 2, 3, 1,
		// 2, 0, 0]

		// (REDUNDANT) From Back of array, find the first non-zero j-th position
		// (first swappable position)
		// THIS is optional, b/c the 3rd case in while loop,
		// we just decrement j until we find swappable position

		// Iterate through array from front and back until they meet
		// Cases
		// Array[i] != 0 ; skip ; i++ only
		// Array[i] == 0 && Array[j] != 0; swap with Array[j]; i++,j--
		// Array[i] == 0 && Array[j] == 0; j-th position NOT swappable, j-- only
		// no need to return array b/c altered in place

		if (nums == null || nums.length == 1) {
			return;
		} // not processing null or one element

		int front = 0; // ptr to front of array
		int back = nums.length - 1; // ptr to back of array

		while (front < back) {
			if (nums[front] != 0) {
				front++;
			} else if (nums[front] == 0 && nums[back] != 0) {
				// swap
				nums[front] = nums[back];
				nums[back] = 0;
				front++;
				back--;
			} else { // if (nums[i] == 0 && nums[j] == 0) default case already
				back--;
			}
		}
	}

}
