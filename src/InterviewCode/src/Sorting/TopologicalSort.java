package Sorting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import Utils.Graph;
import Utils.Graph.Edge;
import Utils.Graph.Vertex;

/*
 * Topological Sort for a directed graph
 * 	-- Useful for checking resource dependencies, project planning etc...
 * 
 * Kahn's algorithm --> https://en.wikipedia.org/wiki/Topological_sorting
 * Time: O(Vertexes) + O(Edges)
 * 
 * 
 * DFS Implementation --> TBD
 */
public class TopologicalSort {

	public static void main(String[] args) {

		Graph gp = new Graph(true); // Must be Directed Acyclic Graph for TOPO
									// sort
		gp.addEdge("A", "B", 3);
		gp.addEdge("B", "C", 4);
		gp.addEdge("A", "C", 2);
		gp.addEdge("C", "D", 1);
		
		gp.addEdge("B", "D", 2);
		gp.addEdge("X", "D", 2);
		//gp.addEdge("D", "X", 2); // Cyclic edge for testing

		System.out.println(gp.toString());

		TopologicalSort ts = new TopologicalSort();
		List<Vertex> result = ts.topoKahns(gp);
		System.out.println("Kahns Topological sorted order: ");
		for (Vertex s : result) {
			System.out.println(s.getLabel());
		}
		
		// DFS Implementation to come
	}

	public List<Vertex> topoKahns(Graph gp) {
		List<Vertex> topoSorted = new ArrayList<Vertex>();
		if (gp == null) {
			return topoSorted;
		}

		// Store the Vertex in-degrees(# of incoming edges) for non-zero
		// Keep track of the independent Vertexes
		// vertexesAndDegrees should be empty at end
		// Time: O(Vertexes)
		Map<Vertex, Integer> vertexesAndDegrees = new HashMap<Vertex, Integer>();
		Set<Vertex> independentVertexes  = new HashSet<Vertex>();
		for (Vertex x : gp.getAllVertexes()) {
			if (x.getInDegree() == 0) {
				independentVertexes.add(x);			
			} else {
				vertexesAndDegrees.put(x, x.getInDegree());
			}
		}

		/* Print independent vertexes out to verify in beginning
		System.out.println("Initial Independent Vertex(es)");
		for (Vertex x : independentVertexes) {
			System.out.print(x.getLabel() + "\t");
		}
		System.out.println();
		*/

		// Loop and Visit each independent vertex and its adjacent neighbors
		// Time: O(Vertexes) + O(Edges)
		while (!independentVertexes.isEmpty()) {
			// Get the next independent Vertex
			Vertex visited = independentVertexes.iterator().next();

			// Add Vertex to the sorted list
			topoSorted.add(visited);

			// Remove the Vertex b/c visited
			independentVertexes.remove(visited);

			// Visit all its neighbors and remove the dependent edge
			for (Vertex m : visited.getAllAdjacentVertexes().keySet()) {
				int currentInDegree = vertexesAndDegrees.get(m);
				if (currentInDegree - 1 <= 0) {
					// No more edges, Remove the Vertex and add to independent
					// Set
					vertexesAndDegrees.remove(m);
					independentVertexes.add(m);
				} else {
					// "Remove" or reduce the edge-in degree
					vertexesAndDegrees.put(m, currentInDegree - 1);
				}
			}
		}

		// At the end now, so the graph(map) should be "empty" of any edges
		// Time: O(1)
		if (!vertexesAndDegrees.isEmpty()) {
			System.out.println(
					"Could not create topo sort, edges remaining. Topo sort must use directed acyclic graph (DAG).");
			for (Vertex x : vertexesAndDegrees.keySet()) {
				System.out.println(x.getLabel() + " Indegree remaining: " + vertexesAndDegrees.get(x));
			}
			return new ArrayList<Vertex>();
		}

		return topoSorted;
	}

	/* 
	 * Method not needed since we can add to IndependentVertexes Set at time of 
	 * 
	 
	public Set<Vertex> getAndRemoveIndependentVertexes(Map<Vertex, Integer> allVertexesAndDegrees) {
		// Time: O(Vertexes)
		if (allVertexesAndDegrees == null) {
			return null;
		}
		Set<Vertex> independentVertexes = new HashSet<Vertex>();
		Iterator<Vertex> it = allVertexesAndDegrees.keySet().iterator();
		Vertex x;
		while (it.hasNext()) {
			x = it.next();
			if (allVertexesAndDegrees.get(x) == 0) {
				independentVertexes.add(x);
				it.remove(); // remove the item using iterator to avoid
								// concurrent modification exception
			}
		}
		return independentVertexes;
	}
	*/

}
