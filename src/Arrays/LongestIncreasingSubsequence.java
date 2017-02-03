import java.util.Arrays;

/*
 * Given an array of numbers. Find the largest increasing subsequence
 *  {3,4,-1,0,6,2,3};  // answer = 4 {-1,0,2,3}
 *  
 */
public class LongestIncreasingSubsequence {

	public static void main(String[] args) {
		int[] nums = new int[] {3,4,-1,0,6,2,3};  // answer = 4 {-1,0,2,3}
		System.out.println(longestIncreasingSubsequence(nums));
	}
	
	
	public static int longestIncreasingSubsequence(int[] nums) {
		// O(n^2) -- fill (n) + loop n^2 + get max (n)
		// Loop through nums starting  @ j = 0; i = 1
			// compare all numbers from j -> i
				// RESET j = 0 when j == i and i++ to search for next increasing sequence
				// if a[j] < a[i] ; there is an increasing sequence here, 
					// a[i] = MAX(a[j] + 1, a[i])
					// Max because there might be a longer sequence we have already recorded, so dont override it
				
		int[] result = new int[nums.length];
		Arrays.fill(result, 1);			// start with 1; which is the largest increasing sequence with number at a[x]
		int max = Integer.MIN_VALUE;	// start max with some very low value
		
		/* WHILE LOOP VERSION
		int j = 0, i = 1;
		while(i < nums.length) {
			while(j < i) {
				if(nums[j] < nums[i]) {
					// increasing sequence found, check if its the max so far
					result[i] = Math.max(result[i], result[j] + 1);
				}
				j++;	// move j to check if next j number is also increasing
			}
			j = 0; 		// Reset to search again
			i++;		// Search for next increasing sequence through i
		}*/
		
		// FOR LOOP VERSION -- Much cleaner
		for(int i = 1; i < nums.length; i++) {
			for(int j = 0; j < i; j++) {
				if(nums[j] < nums[i]) {
					result[i] = Math.max(result[i], result[j] + 1);
				}
			}
		}
		
		// Search for max
		for(int x : result) {
			max = x > max ? x : max;
		}
		
		return max;
	}

}
