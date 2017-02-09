package AppliedAlgorithms;
import java.util.ArrayList;
import java.util.List;

/*
 * Given NxM matrix of land elevation. 0 = water, >0 = land, find the sizes of each pond.
 * Ponds can be connected diagonally, horizontally, and vertically
 * Ex. 
 * { 0, 2, 1, 0 }
 * { 0, 1, 0, 1 }
 * { 1, 1, 0,1 }
 * { 0, 1, 0, 4 }
 * Should return 3 ponds sizes 2,1,4 in any order
 * 
 * DFS - O(N x M) 
 * 	Using DFS to explore all possible paths at arr[i][j] and a visited[][], we can visit each water cell once
 * 		- At each cell, DFS the entire array to count its size, mark it as visited
 */
public class PondSizesDFS {

	public static void main(String[] args) {
		// int[][] landElevation = { { 0, 2, 1, 0 }, { 0, 1, 0, 1 }, { 1, 1, 0,1
		// }, { 0, 1, 0, 4 } };
		int[][] landElevation = { { 0, 2, 1, 0, 0, 0 }, { 0, 1, 0, 1, 0, 0 }, { 1, 1, 0, 1, 0, 0 },
				{ 0, 1, 0, 4, 0, 0 } };
		
		//int[][] landElevation = { { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 } }; // all water
		//int[][] landElevation = { { 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1 } }; // no water

		int rows = landElevation.length;
		int cols = landElevation[0].length;

		for (int i : findPondSizes(landElevation, rows, cols)) {
			System.out.println(i);
		}
	}

	public static List<Integer> findPondSizes(int[][] landElevation, int rows, int cols) {
		// Iterate through EACH cell(NxM)
		// Do DFS on all 8 moves from one cell
		// to find all paths containing water from there, + 1 for each water
		// Mark the water cell as visited so we don't visit the
		// water again from somewhere else

		// MOVES: up, upleft, upright, left, right, down, downleft, downright
		int[] rowMoves = { -1, -1, -1, 0, 0, 1, 1, 1 };
		int[] colMoves = { 0, -1, 1, -1, 1, 0, -1, 1 };
		int[][] visited = new int[rows][cols]; // 0 unvisited, 1 = visited
		List<Integer> pondSizes = new ArrayList<Integer>();
		
		// O(N x M) -- Using the isWaterandUnvisited() and visited[][] we only
		// visit each cell once for DFS
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (isWaterAndUnvisited(landElevation, i, j, rows, cols, visited)) {
					int waterTotalSize = findPondSizesDFS(landElevation, i, j, colMoves, rowMoves, rows, cols, visited,
							1);
					pondSizes.add(waterTotalSize);
				}
			}
		}
		return pondSizes;
	}

	public static int findPondSizesDFS(int[][] landElevation, int row, int col, int[] colMoves, int[] rowMoves,
			int totalRows, int totalCols, int[][] visited, int currentPondSize) {

		// Mark this water cell as visited
		visited[row][col] = 1;

		// Search through all the 8 moves from this cell
		// Adding to current size of pond each time
		// return currentPondSize when no more moves available
		for (int x = 0; x < colMoves.length; x++) {
			if (isWaterAndUnvisited(landElevation, row + rowMoves[x], col + colMoves[x], totalRows, totalCols,
					visited)) {
				// If it is water, keep recursing, add one to pond size
				currentPondSize = findPondSizesDFS(landElevation, row + rowMoves[x], col + colMoves[x], colMoves,
						rowMoves, totalRows, totalCols, visited, currentPondSize + 1);
			}
		}

		return currentPondSize;
	}

	public static boolean isWaterAndUnvisited(int[][] landElevation, int row, int col, int totalRows, int totalCols,
			int[][] visited) {
		// In bounds of matrix and is water == 0 and is unvisited == 0
		return row >= 0 && col >= 0 && row < totalRows && col < totalCols && landElevation[row][col] == 0
				&& visited[row][col] == 0;
	}
}
