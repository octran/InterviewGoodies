import java.util.Arrays;


/* N Queens Problem
 * Given n x n board, place a Queen on each row where they cannot be attacked by any other queen
 * 
 * 
 * BACKTRACKING SOLUTION
 * FIND ALL SOLUTIONS
 * 		Place a Queen and explore all safe possible solutions on the next row (recurse)
 * 			If end of call stack a solution is found, print
 * 			When coming back up , BACKTRACK by removing the queen on that space, and move to the next space in row
 * 
 * Print out the solutions -- commented out in recursing method , uncomment to see solution
 * 
 * 
 * Can be very slow if iterating larger than 13
 * 
 */
public class NQueens {

	public static void main(String[] args) {

		int max = 15;
		for(int n = 1; n <= max; n++) {		// For timing 1 --> max nxn boards
			char[][] queensMatrix = new char[n+1][n+1];
			for(char[] row : queensMatrix) {
				Arrays.fill(row, '_');
			}
			long start = System.currentTimeMillis();
			NQueensBacktrackingExponentialTime(queensMatrix, n);
			long end = System.currentTimeMillis();
			System.out.println("# Solutions: " + solutionsCount + " for n = " + n + " took " + (end-start) + "ms"); 
			solutionsCount = 0; // reset the solutions count
		}
	}
	
	public static int solutionsCount = 0;
	
	
	public static void NQueensBacktrackingExponentialTime(char[][] queensMatrix, int n) {
		NQueensHelperExponentialTime(0,0,queensMatrix, n);
	}
	
	public static void NQueensHelperExponentialTime(int row, int col, char[][] queensMatrix, int n) {
		int rows = n;
		int cols = n;
		// If we reached the n-th row, or overshot the array b/c 0 based, that means all queens have been placed.
		// Print solution
		if(row == rows) {
			/* -- Uncomment to print out the solutions
			System.out.println("Solution Found: ");	
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < cols; j++) {
					System.out.print(queensMatrix[i][j] + " ");
				}
				System.out.println("");				
			}	
			*/
			solutionsCount++;;
			return;
		}
		
		//  Loop through all the row positions  
			// Check if it is "safe" to place the Queen at position(i,j)
			// If it is, place it and recurse down to he next row, starting at first column (i + 1, 0)
			// After recursion, backtrack and remove the queen to explore more column positions in this row (i)
		for(int i = 0; i < cols; i++) { 
			if(isSafeToPlaceQueen(row,  i, queensMatrix, n)) {
			
				// Place the Queen
				queensMatrix[row][i] = 'Q';
				
				// Recurse to next row to explore possible solutions
				NQueensHelperExponentialTime(row + 1 , 0, queensMatrix, n);
			
				// Remove Queen to backtrack
				queensMatrix[row][i] = '_';
			}		
		}
	}
	
	public static boolean isSafeToPlaceQueen(int row, int col, char[][]queensMatrix, int n) {
		int cols = n;
		// Check Row -- Queen will be the only one placed on this row so don't have to handle this case
		// Check Column -- Start from 0 up to the row we are processing only (b/c others not filled yet)
		// Check Diagonal \ and /
		
		 // return false if two queens share the same column
	    for (int i = 0; i < row; i++) {
	        if (queensMatrix[i][col] == 'Q') {
	            return false;
	        }
	    }
	 
	    // return false if two queens share the same \ diagonal
	    for (int i = row , j = col; i >= 0 && j >= 0; i--, j--) {
	        if (queensMatrix[i][j] == 'Q') {
	            return false;
	        }
	    }
	 
	    // return false if two queens share the same / diagonal
	    for (int i = row, j = col; i >= 0 && j <= cols; i--, j++) {
	        if (queensMatrix[i][j] == 'Q') {
	            return false; 
	            }
	    }
	    
	    return true;
	}

}
