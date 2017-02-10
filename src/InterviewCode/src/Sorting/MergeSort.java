package Sorting;
import java.util.Arrays;

/*
 * Mergesort -- O(nlogn)
 * 
 * Mergesort consists of two  main parts:
 *  - Splitting the array into equal sized parts
 *  - Sorting these two arrays and merging them back to the original
 *  	(This is just a classic problem of merging two sorted arrays into one in sorted order)
 *  
 *  TopDown
 *  - Recursively 
 *  	- Split the array in halves on both the left and right side until you reach cells of size 1
 *  	- Call Sort and Merge them together
 *  
 *  BottomUp
 *  - Iteratively
 *  	- Start with the front of the array with 1 cell, then 2, 4, 8..powers of 2 until end
 *  	- Sort and Merge them in place
 *  
 */
public class MergeSort {

	public static void main(String[] args) {
		int[] inputArr = {45, 23, 11, 89, 77, 98, 4, 28, 65, 43, -100, -100, -100 };
		// int[] inputArr = {45,23,11,89};//,100,50,75,88};
		System.out.print("Original Array ");
		for (int i : inputArr) {
			System.out.print(i);
			System.out.print(" ");
		}
		System.out.println("");
		
		// TopDown
		// minus 1 b/c length# is 1 > index #
		mergeSortRecursive(0,inputArr.length - 1, inputArr); 
		
		System.out.print("Final Sorted Array TopDown: ");
		for (int i : inputArr) {
			System.out.print(i);
			System.out.print(" ");
		}
		
		// BottomUp
		int[] inputArr2 = {45, 23, 11, 89, 77, 98, 4, 28, 65, 43, -100, -100, -100 };
		mergeSortBottomUp(inputArr2, new int[inputArr2.length]);

		System.out.print("Final Sorted Array BottomUp: ");
		for (int i : inputArr2) {
			System.out.print(i);
			System.out.print(" ");
		}
	}

	/*
	 * Top down recurisve Approach split the array in half merge them (merge two
	 * sorted arrays)
	 */
	public static void mergeSortRecursive(int low, int high, int[] arr) {
		// split the array in half recursively
		// until 1 or low !< high
		// then merge
		if (low < high) {
			// get midpoint of array to split (- & / avoids overflow)
			int mid = (high - low) / 2 + low; 
			// split sort left half
			mergeSortRecursive(low, mid, arr);
			// split sort right half
			mergeSortRecursive(mid + 1, high, arr);
			// merge them
			mergeRecursive(low, mid, high, arr);
		}

	}

	/*
	 * Merge two arrays into a temp in sorted order and copy back to original array
	 */
	public static void mergeRecursive(int left, int mid, int right, int[] arr) {
		// Allocate just enough of cells for what we are changing
		int[] tempArr = new int[right - left + 1]; 
		
		// Get the start and end positions of the 2 arrays to sort
		int k = 0;			// Start of temp to copy out
		int i = left; 		// A[]
		int j = mid + 1;	// B[]

		// While both arrays are less than their last index, sort them
		while (i <= mid && j <= right) {
			if (arr[i] <= arr[j]) {
				// A[i] <= B[j], copy A[i] to temp
				tempArr[k] = arr[i];
				k++;
				i++;
			} else {
				// A[i] > B[j], copy B[j] to temp
				tempArr[k] = arr[j];
				k++;
				j++;
			}
		}

		// Fill in any remaining of the arrays to temp
		while (i <= mid) {
			tempArr[k] = arr[i];
			k++;
			i++;
		}
		while (j <= right) {
			tempArr[k] = arr[j];
			k++;
			j++;
		}

		System.out.print("Temp Array ");
		for (int x : tempArr) {
			System.out.print(x);
			System.out.print(" ");
		}
		System.out.println("");

		// Copy temp back to the sorted array
		for (int a = 0; a < tempArr.length; a++) {
			// copy into arr at start a = 0 + left (start position),
			// temp always start at 0
			arr[a + left] = tempArr[a];
		}

		System.out.print("Merged Array ");
		for (int x : arr) {
			System.out.print(x);
			System.out.print(" ");
		}
		System.out.println("");
	}

	/*
	 * Merge pairs of adjacent arrays 1, then 2, then 4, then 8, etc...
	 */
	public static void mergeSortBottomUp(int[] arr, int[] temp) {
		if (arr == null || temp == null) {
			return;
		}
		// Loop through array by powers of 2 width for groups of indexes to
		// sort, starting at 1
		for (int width = 1; width < arr.length; width *= 2) {
			// Merge the arrays with set sized widths
			for (int i = 0; i < arr.length; i += (2 * width)) {
				int left, mid, right;
				left = i;
				mid = i + width - 1;
				right = i + (2 * width) - 1;
				mergeRecursive(left, Math.min(mid, arr.length - 1), Math.min(right, arr.length - 1), arr);
			}
			System.out.println("After iter: " + Arrays.toString(arr));
		}
	}

	public static void mergeBottomUp() {
		// using the mergeRecursive() from topdown approach
	}
}
