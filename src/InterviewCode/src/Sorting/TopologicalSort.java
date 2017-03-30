package Sorting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

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
 * DFS Implementation -->   https://en.wikipedia.org/wiki/Topological_sorting
 * Time: O(Vertexes) + O(Edges)
 * Space: O(~ V + V + E)  -- For holding its Vertexes and its Edges in map
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
		// gp.addEdge("D", "X", 2); // Cyclic edge for testing

		System.out.println(gp.toString());
		
		// Kahns Indregree algorithm -- Prints "A B C X D"
		TopologicalSort ts = new TopologicalSort();
		List<Vertex> result = ts.topoKahns(gp);
		System.out.println("Kahns Topological sorted order: ");
		for (Vertex s : result) {
			System.out.println(s.getLabel());
		}

		// DFS  -- printing can be different b/c can be many Topo orderings
		// Prints "X A B C D"
		System.out.println("DFS Topological sorted order: ");
		// IF List or ForEach is used, the stack order would not be retained for printing
		Stack<Vertex> result2 = ts.topoDFS(gp);
		while(!result2.isEmpty()) {
			System.out.println(result2.pop().getLabel());
		}
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
		Set<Vertex> independentVertexes = new HashSet<Vertex>();
		for (Vertex x : gp.getAllVertexes()) {
			if (x.getInDegree() == 0) {
				independentVertexes.add(x);
			} else {
				vertexesAndDegrees.put(x, x.getInDegree());
			}
		}

		/*
		 * Print independent vertexes out to verify in beginning
		 * System.out.println("Initial Independent Vertex(es)"); for (Vertex x :
		 * independentVertexes) { System.out.print(x.getLabel() + "\t"); }
		 * System.out.println();
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

	/**
	 * Marking Visitation States for DFS toposort No need for a "Visited" State
	 * b/c once done, it doesn't matter anymore and can be removed
	 *
	 */
	public enum State {
		Unvisited, Visiting
	};

	/**
	 * A cyclic relationship exists if a child (M) of parent (N) visits its
	 * parent (N)
	 * 
	 * DFS reaches and processes children before a parent, so in reverse order
	 * in stack pop items off stack to get correct order
	 * 
	 * @param gp
	 * @return
	 */
	public Stack<Vertex> topoDFS(Graph gp) {
		Stack<Vertex> topoSorted = new Stack<Vertex>();
		if (gp == null) {
			return topoSorted;
		}

		// T = O(V), S = O(~ V + V + E) 
		// Make a Visited Map
		// AND
		// Make a copy for the Adjacent Vertices(E), 
		// for removal once done visiting children of Vertex (removal insures O(E) access)
		Map<Vertex, Map<Vertex, Edge>> adjVertexesMap = new HashMap<Vertex, Map<Vertex, Edge>>();
		Map<Vertex, State> visitedMap = new HashMap<Vertex, State>();
		for (Vertex p : gp.getAllVertexes()) {
			visitedMap.put(p, State.Unvisited);
			adjVertexesMap.put(p, p.getAllAdjacentVertexes());
		}

		// Get a random FIRST node in Graph to process - O(1)
		Stack<Vertex> s = new Stack<Vertex>();
		s.push(visitedMap.keySet().iterator().next());

		// While there are Vertexes unvisited, visit them - O(V) + O(E)
		while (!visitedMap.isEmpty()) {
			if (s.isEmpty()) {
				// All vertexes in stack have been seen
				// Check if there are Unvisited Vertexes left (case: independent
				// Vertexes - no incoming edges)
				Vertex next = visitedMap.keySet().iterator().next();
				if (next != null) {
					s.push(next);
				} else {
					break; // done
				}
			}
			Vertex current = s.peek();
			visitedMap.put(current, State.Visiting); // MARK as Visiting
			Vertex child = null;
			// CHECK FOR CHILDREN O(E)
			if ((child = getAndRemoveUnfinishedChild(visitedMap, adjVertexesMap.get(current).keySet())) != null) {
				// A CHILD EXISTS TO PROCESS
				if (visitedMap.get(child) == State.Visiting) {
					// CHILD HAS cyclic relationship
					System.out.println(
							"Could not create topo sort, edges remaining. Topo sort must use directed acyclic graph (DAG).");
					System.out.println(
							"Cyclic relationship exists between: " + current.getLabel() + " and " + child.getLabel());
					return new Stack<Vertex>();
				}
				// PUSH UNVISITED CHILD TO STACK for processing next
				s.push(child);
			} else {
				// NO CHILDREN UNPROCESSED = DONE
				s.pop();
				visitedMap.remove(current);
				topoSorted.push(current);
			}
		}
		return topoSorted;
	}

	/**
	 * Get me a child that has not been seen, or finished processing Remove that
	 * child from my remaining children to be processed (to ensure O(E) access)
	 * 
	 * @param current
	 * @param visitedMap
	 * @param remainingChildren
	 * @param remainingChildren
	 * @return
	 */
	public static Vertex getAndRemoveUnfinishedChild(Map<Vertex, State> visitedMap,
			Collection<Vertex> remainingChildren) {
		Iterator<Vertex> itr = remainingChildren.iterator();
		Vertex x;
		while (itr.hasNext()) {
			x = itr.next();
			if (visitedMap.get(x) == State.Unvisited || visitedMap.get(x) == State.Visiting) {
				itr.remove();
				return x;
			}
		}
		return null; // all children finished
	}

}
