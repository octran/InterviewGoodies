import java.util.Arrays;

/*
 * Given an n*n board, place a Chess Knight(horse) on all positions of n*n if possible 
 * Starting with the top leftmost corner [0][0] 
 * Print out the solution on the board
 * 	1. Print all solutions
 *  2. Print one solution
 * 
 * BACKTRACKING SOLUTION -- Exponential time
 * 		(Explore all available solutions without always starting from beginning)
 * There are up to 8 predetermined, L shaped moves the knight can make on the board from any position
 * 		Check if it is "safe" to place the knight on one of the locations (if no knight there already) 
 * 		Place the move and check all different directions from there (recursion)
 * 			If at the end of the call stack a solution is found, print it out
 * 		Once you return in the call stack from this move, 
 * 			you BACKTRACK by removing the knight piece and checking/placing another of the 8 moves
 * 
 * If you are only interested in one solution to cut down the runtime, 
 * 		you need to return true when a solution is found and not check any other move
 * 
 * 
 * 
 * The Solutions below illustrate 
 *  A Redundant Code Version (8 - IF cases for each move if its more intuitive)
 *  A NonRedundant code version (for loop over 8 predefined moves using a position array)
 *  	-- shown only for getting one solution
 */
public class KnightsTour {
	public static int solutionsCount = 0;

	public static void main(String[] args) {
		// int max = 15;
		int n = 5;

		// FIND ALL SOLUTIONS
		// for(int n = 1; n <= max; n++) {
		// This is for timing the solutions of 1 -> n board
		int[][] knightsMatrix = new int[n][n];
		for (int[] row : knightsMatrix) {
			Arrays.fill(row, -1);
		}
		knightsMatrix[0][0] = 0; // starting knight position
		long start = System.currentTimeMillis();
		KnightsTourBacktrackingExponentialTimeAllSolutions(knightsMatrix, n);
		long end = System.currentTimeMillis();
		System.out.println("# Solutions: " + solutionsCount + " for n = " + n + " took " + (end - start) + "ms");
		solutionsCount = 0; // reset the solutions count
		// }

		// FIND ONE SOLUTION
		// for(int n = 1; n <= max; n++) {
		// This is for timing the solutions of 1 -> n board
		for (int[] row : knightsMatrix) {
			Arrays.fill(row, -1);
		}
		knightsMatrix[0][0] = 0;
		start = System.currentTimeMillis();
		KnightsTourBacktrackingExponentialTimeOneSolution(knightsMatrix, n);
		end = System.currentTimeMillis();
		System.out.println("# Solutions: " + solutionsCount + " for n = " + n + " took " + (end - start) + "ms");
		solutionsCount = 0; // reset the solutions count
		// }

		// FIND ONE SOLUTION -- Non redundant code
		// for(int n = 1; n <= max; n++) {
		// This is for timing the solutions of 1 -> n board
		for (int[] row : knightsMatrix) {
			Arrays.fill(row, -1);
		}
		knightsMatrix[0][0] = 0;
		start = System.currentTimeMillis();
		KnightsTourBacktrackingExponentialTimeOneSolutionNonRedundant(knightsMatrix, n);
		end = System.currentTimeMillis();
		System.out.println("# Solutions: " + solutionsCount + " for n = " + n + " took " + (end - start) + "ms");
		solutionsCount = 0; // reset the solutions count
		// }
	}

	public static void KnightsTourBacktrackingExponentialTimeAllSolutions(int[][] knightsMatrix, int n) {
		KnightsTourHelperExponentialTimeAllSolutionsREDUNDANTCODE(0, 0, knightsMatrix, n, 1);
	}

	public static void KnightsTourBacktrackingExponentialTimeOneSolution(int[][] knightsMatrix, int n) {
		KnightsTourHelperExponentialTimeOneSolutionREDUNDANTCODE(0, 0, knightsMatrix, n, 1);
	}

	public static void KnightsTourBacktrackingExponentialTimeOneSolutionNonRedundant(int[][] knightsMatrix, int n) {
		// Defines the next L shaped move of Knight, 8 in total possible
		int xMove[] = { 2, 1, -1, -2, -2, -1, 1, 2 }; // Row X position
		int yMove[] = { 1, 2, 2, 1, -1, -2, -2, -1 }; // Col Y position
		KnightsTourHelperExponentialTimeOneSolutionNONREDUNDANTCode(0, 0, knightsMatrix, n, 1, xMove, yMove);
	}

	public static boolean KnightsTourHelperExponentialTimeOneSolutionNONREDUNDANTCode(int row, int col,
			int[][] knightsMatrix, int n, int move, int[] xMove, int[] yMove) {
		int rows = n;
		int cols = n;

		// Check if the knights board is finished AND Print solution
		// WHEN WE have placed the last piece n*n - 1, if move == n*n, then we
		// are finished
		if (move == n * n) {
			System.out.println("Solution Found: ");
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					System.out.print(knightsMatrix[i][j] + " ");
				}
				System.out.println("");
			}
			solutionsCount++;
			return true;
		}

		// Check all 8 different moves
		// Check if it is "safe" to place the Knight at position(i,j)
		// If it is, place it and recurse with that position and move + 1
		// After recurs, backtrack and remove the knight at that position = -1
		// Return true if there was a solution to stop
		int nextMoveX = 0;
		int nextMoveY = 0;
		for (int i = 0; i < 8; i++) {
			nextMoveX = xMove[i] + row; // Get the next move from the move
										// matrix
			nextMoveY = yMove[i] + col;
			if (isSafeToPlaceKnight(nextMoveX, nextMoveY, knightsMatrix, n)) {
				knightsMatrix[nextMoveX][nextMoveY] = move;
				if (KnightsTourHelperExponentialTimeOneSolutionNONREDUNDANTCode(nextMoveX, nextMoveY, knightsMatrix, n,
						move + 1, xMove, yMove)) {
					return true; // Return one solution
				} else {
					knightsMatrix[nextMoveX][nextMoveY] = -1; // Back Track
				}
			}
		}

		return false; // THERE was no solution found
	}

	public static boolean KnightsTourHelperExponentialTimeOneSolutionREDUNDANTCODE(int row, int col,
			int[][] knightsMatrix, int n, int move) {
		int rows = n;
		int cols = n;

		// Check if the knights board is finished AND Print solution
		// WHEN WE have placed the last piece n*n - 1, if move == n*n, then we
		// are finished
		if (move == n * n) {
			System.out.println("Solution Found: ");
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					System.out.print(knightsMatrix[i][j] + " ");
				}
				System.out.println("");
			}
			solutionsCount++;
			return true;
		}

		// Check all 8 different combinations (notice IF, not IF/ELSE , to
		// explore all 8 moves
		// Check if it is "safe" to place the Knight at position(i,j)
		// If it is, place it and recurse with that position and move + 1
		// After recurs, backtrack and remove the knight at that position = -1
		// Return true if there was a solution to stop
		if (row > 0 && col < n - 2 && knightsMatrix[row - 1][col + 2] == -1) {
			// Place the Knight
			knightsMatrix[row - 1][col + 2] = move;
			// Recurse to next row to explore possible solutions
			if (KnightsTourHelperExponentialTimeOneSolutionREDUNDANTCODE(row - 1, col + 2, knightsMatrix, n,
					move + 1)) {
				return true;
			} else {
				// BackTrack
				knightsMatrix[row - 1][col + 2] = -1;
			}
		}
		if (row > 0 && col > 1 && knightsMatrix[row - 1][col - 2] == -1) {
			knightsMatrix[row - 1][col - 2] = move;
			if (KnightsTourHelperExponentialTimeOneSolutionREDUNDANTCODE(row - 1, col - 2, knightsMatrix, n,
					move + 1)) {
				return true;
			} else {
				knightsMatrix[row - 1][col - 2] = -1;
			}
		}
		if (row > 1 && col > 1 && knightsMatrix[row - 2][col - 1] == -1) {
			knightsMatrix[row - 2][col - 1] = move;
			if (KnightsTourHelperExponentialTimeOneSolutionREDUNDANTCODE(row - 2, col - 1, knightsMatrix, n,
					move + 1)) {
				return true;
			} else {
				knightsMatrix[row - 2][col - 1] = -1;
			}
		}
		if (row > 1 && col < n - 1 && knightsMatrix[row - 2][col + 1] == -1) {
			knightsMatrix[row - 2][col + 1] = move;
			if (KnightsTourHelperExponentialTimeOneSolutionREDUNDANTCODE(row - 2, col + 1, knightsMatrix, n,
					move + 1)) {
				return true;
			} else {
				knightsMatrix[row - 2][col + 1] = -1;
			}
		}
		if (row < n - 1 && col < n - 2 && knightsMatrix[row + 1][col + 2] == -1) {
			knightsMatrix[row + 1][col + 2] = move;
			if (KnightsTourHelperExponentialTimeOneSolutionREDUNDANTCODE(row + 1, col + 2, knightsMatrix, n,
					move + 1)) {
				return true;
			} else {
				knightsMatrix[row + 1][col + 2] = -1;
			}
		}
		if (row < n - 1 && col > 1 && knightsMatrix[row + 1][col - 2] == -1) {
			knightsMatrix[row + 1][col - 2] = move;
			if (KnightsTourHelperExponentialTimeOneSolutionREDUNDANTCODE(row + 1, col - 2, knightsMatrix, n,
					move + 1)) {
				return true;
			} else {
				knightsMatrix[row + 1][col - 2] = -1;
			}
		}
		if (row < n - 2 && col < n - 1 && knightsMatrix[row + 2][col + 1] == -1) {
			knightsMatrix[row + 2][col + 1] = move;
			if (KnightsTourHelperExponentialTimeOneSolutionREDUNDANTCODE(row + 2, col + 1, knightsMatrix, n,
					move + 1)) {
				return true;
			} else {
				knightsMatrix[row + 2][col + 1] = -1;
			}
		}
		if (row < n - 2 && col > 0 && knightsMatrix[row + 2][col - 1] == -1) {
			knightsMatrix[row + 2][col - 1] = move;
			if (KnightsTourHelperExponentialTimeOneSolutionREDUNDANTCODE(row + 2, col - 1, knightsMatrix, n,
					move + 1)) {
				return true;
			} else {
				knightsMatrix[row + 2][col - 1] = -1;
			}
		}

		return false; // THERE was no solution found
	}

	public static void KnightsTourHelperExponentialTimeAllSolutionsREDUNDANTCODE(int row, int col,
			int[][] knightsMatrix, int n, int move) {
		int rows = n;
		int cols = n;

		// Check if the knights board is finished AND Print solution
		if (move == n * n) {
			// WHEN WE have placed the last piece n*n - 1, if move == n*n, then
			// we are finished
			System.out.println("Solution Found: ");
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					System.out.print(knightsMatrix[i][j] + " ");
				}
				System.out.println("");
			}
			solutionsCount++;
		}

		// Check all 8 different combinations (notice IF, not IF/ELSE
		// Check if it is "safe" to place the Knight at position(i,j)
		// If it is, place it and recurse with that position and move + 1
		// After recurs, backtrack and remove the knight at that position =-1

		// Check up to the 8 possible combinations for a knights move
		if (row > 0 && col < n - 2 && knightsMatrix[row - 1][col + 2] == -1) {
			// Place the Knight
			knightsMatrix[row - 1][col + 2] = move;
			// Recurse to next row to explore possible solutions
			KnightsTourHelperExponentialTimeAllSolutionsREDUNDANTCODE(row - 1, col + 2, knightsMatrix, n, move + 1);
			// BackTrack
			knightsMatrix[row - 1][col + 2] = -1;
		}
		if (row > 0 && col > 1 && knightsMatrix[row - 1][col - 2] == -1) {
			knightsMatrix[row - 1][col - 2] = move;
			KnightsTourHelperExponentialTimeAllSolutionsREDUNDANTCODE(row - 1, col - 2, knightsMatrix, n, move + 1);
			knightsMatrix[row - 1][col - 2] = -1;
		}
		if (row > 1 && col > 1 && knightsMatrix[row - 2][col - 1] == -1) {
			knightsMatrix[row - 2][col - 1] = move;
			KnightsTourHelperExponentialTimeAllSolutionsREDUNDANTCODE(row - 2, col - 1, knightsMatrix, n, move + 1);
			knightsMatrix[row - 2][col - 1] = -1;
		}
		if (row > 1 && col < n - 1 && knightsMatrix[row - 2][col + 1] == -1) {
			knightsMatrix[row - 2][col + 1] = move;
			KnightsTourHelperExponentialTimeAllSolutionsREDUNDANTCODE(row - 2, col + 1, knightsMatrix, n, move + 1);
			knightsMatrix[row - 2][col + 1] = -1;
		}
		if (row < n - 1 && col < n - 2 && knightsMatrix[row + 1][col + 2] == -1) {
			knightsMatrix[row + 1][col + 2] = move;
			KnightsTourHelperExponentialTimeAllSolutionsREDUNDANTCODE(row + 1, col + 2, knightsMatrix, n, move + 1);
			knightsMatrix[row + 1][col + 2] = -1;
		}
		if (row < n - 1 && col > 1 && knightsMatrix[row + 1][col - 2] == -1) {
			knightsMatrix[row + 1][col - 2] = move;
			KnightsTourHelperExponentialTimeAllSolutionsREDUNDANTCODE(row + 1, col - 2, knightsMatrix, n, move + 1);
			knightsMatrix[row + 1][col - 2] = -1;
		}
		if (row < n - 2 && col < n - 1 && knightsMatrix[row + 2][col + 1] == -1) {
			knightsMatrix[row + 2][col + 1] = move;
			KnightsTourHelperExponentialTimeAllSolutionsREDUNDANTCODE(row + 2, col + 1, knightsMatrix, n, move + 1);
			knightsMatrix[row + 2][col + 1] = -1;
		}
		if (row < n - 2 && col > 0 && knightsMatrix[row + 2][col - 1] == -1) {
			knightsMatrix[row + 2][col - 1] = move;
			KnightsTourHelperExponentialTimeAllSolutionsREDUNDANTCODE(row + 2, col - 1, knightsMatrix, n, move + 1);
			knightsMatrix[row + 2][col - 1] = -1;
		}
	}

	/*
	 * DON'T DO THIS!!! :) If this check is executed at each call stack , you
	 * will add on O(n*n) complexity and end up waiting forever to get any
	 * results
	 * 
	 * DO USE --- moveCount (n*n -1) is the very last move we place onto the
	 * board if starting square[0][0] = 0 if move == n*n, then we have finished
	 * successfully ex. n = 8 , the last move is 8*8-1 = 63, if move == 64, we
	 * are done
	 */
	public static boolean isKnightsTourComplete(int[][] knightsMatrix) {
		// All cells contain a visited
		for (int[] row : knightsMatrix) {
			for (int i = 0; i < row.length; i++) {
				if (row[i] == -1) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean isSafeToPlaceKnight(int row, int col, int[][] knightsMatrix, int n) {
		// empty (-1) and in bounds of grid is safe, else not safe
		return row >= 0 && row < n && col >= 0 && col < n && knightsMatrix[row][col] == -1;
	}

}
