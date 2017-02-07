
/*
 * Find the largest contiguous sum  in an array
 * 	1) Print out only largest Sum
 *  2) Print out the start and end indexes with largest Sum
 *    
 * 
 * Ex. {-2, -3, 4, -1, -2, 1, 5, -3}
 * Should return 7 for indices 2 -> 6 =  {4, -1, -2, 1, 5}
 */
public class LargestContiguousSumSubarray {

	public static void main(String[] args) {
		//int [] inputArr = new int[] {-2, -3, 4, -1, -2, 1, 5, -3};
		//int [] inputArr = new int[] {-2, -3, -1};	// all negs will not work with Kandanes
		//int [] inputArr = new int[] {-2, -3, -1, 4};
		int [] inputArr = new int[] {4, -2, 6, -3, -1};
        for(int i:inputArr){
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.println();
		System.out.println("Largest C-Sum Kandanes: " + largestContiguousSumKandanes(inputArr));
		System.out.println();
		System.out.println("Largest C-Sum All Negs: " + largestContiguousSumWorksForAllNegatives(inputArr));
	}
	
	public static int largestContiguousSumKandanes(int[] nums) {
		// Kandanes algorithm
			// Find all the positive sums, does not handle all negative nums b/c assuming 0
		// Keep track of max_so_far (maximum amount found) and max_ending_here (array position)
			// if max_ending_here < 0, keep it 0
		
		if(nums == null) { return 0; }
		if(nums.length == 1){ return nums[0]; }
		
		int max_so_far = 0;
		int max_ending_here = 0;
		int startIndex = 0;
		int endIndex = 0;
		int s = 0; 
		
		for(int i = 0; i < nums.length; i++) {
			max_ending_here = max_ending_here + nums[i]; // Get total ending here
			
			// max_ending_here = Math.max(max_ending_here, 0);
			// Changed to IF to get start/endIndex
			if(max_ending_here < 0) {
				max_ending_here = 0;
				// Move s one position to check for next largest sum
				s = i + 1;
			}
			// max_so_far = Math.max(max_ending_here, max_so_far);	
			// Changed to IF to  get start/endIndex
			if(max_so_far < max_ending_here) {	
				max_so_far = max_ending_here;
				// Capture the start/end of largest sum found so far
				startIndex = s;					
				endIndex = i;					
			}
		}	
		
		System.out.println("Start: " + startIndex + "End: " + endIndex);
		return max_so_far;
	}
	
	
	public static int largestContiguousSumWorksForAllNegatives(int[] nums) {
		if(nums == null) { return 0; }
		if(nums.length == 1){ return nums[0]; }
		
		int max_so_far = nums[0]; // start the max as the first number in array
		int max_ending_here = nums[0];
		
		int startIndex = 0;
		int endIndex = 0;
		int s = 0; 
		
		// Loop and check if the next index is larger than max_ending_here 
			// or max_ending_here + index num is larger
		// Reassign max_so_far if new max found
		for(int i = 1; i < nums.length; i++) { // start at 1(next position to check)
			
			// max_ending_here = Math.max(nums[i], max_ending_here + nums[i]);
			// Changed to IF to  get start/endIndex
			if(max_ending_here + nums[i] < nums[i]) {
				max_ending_here = nums[i];
				s = i; 
				// Move s to i b/c looking for a new largest sum starting at i
			} else if(max_ending_here + nums[i] >= nums[i]) {
				max_ending_here = max_ending_here + nums[i] ;
				// Dont move s b/c part of largest sum
			}
			// max_so_far = Math.max(max_ending_here, max_so_far);	
			// Changed to IF to  get start/endIndex
			if(max_so_far < max_ending_here) {	
				max_so_far = max_ending_here;
				// Capture the start/end of largest sum found so far
				startIndex = s;
				endIndex = i;
			}
		}
			
		System.out.println("Start: " + startIndex + "End: " + endIndex);
		return max_so_far;
	}

}
