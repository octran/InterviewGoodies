package Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Basic Graph Implementation
 * -- adjacency list style using Map for holding vertices that hold edges
 */
public class Graph {

	private Map<String, Vertex> allVertexes;
	private List<Edge> allEdges;
	boolean isDirected = false;

	public Graph(boolean isDirected) {
		this.isDirected = isDirected;
		allVertexes = new HashMap<String, Vertex>();
		allEdges = new ArrayList<Edge>();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Edge e : allEdges) {
			sb.append("From: " + e.getFromVertex().label + " To: " + e.getToVertex().label + " Weight: "
					+ e.getEdgeWeight() + "\n");
		}
		return sb.toString();
	}

	public boolean addEdge(String from, String to, int weight) {
		try {
			Vertex x;
			Vertex y;
			// Get or Create new Vertexes X and Y if not exist
			if (allVertexes.containsKey(from)) {
				x = allVertexes.get(from);
			} else {
				x = new Vertex(from);
				allVertexes.put(from, x);
			}
			if (allVertexes.containsKey(to)) {
				y = allVertexes.get(to);			
			} else {
				y = new Vertex(to);
				allVertexes.put(to, y);
			}
			// Add the Edge for the Vertexes
			Edge e = new Edge(x, y, weight, this.isDirected);
			allEdges.add(e);
			x.addAdjacentVertex(e, y);
			if (!this.isDirected) {
				// add to the other Vertex list also if no direction
				y.addAdjacentVertex(e, x);
			}
			return true;
		} catch (Exception e) {
			System.out.println("Could not add edge: " + from + " " + to + " " + weight);
			return false;
		}
	}
	
	public boolean removeEdge(String from, String to, int weight) {
		try {
			Vertex x;
			Vertex y;
			if (allVertexes.containsKey(from)) {
				x = allVertexes.get(from);
			} else {
				throw new Exception();
			}
			if (allVertexes.containsKey(to)) {
				y = allVertexes.get(to);
			} else {
				throw new Exception();
			}
			// Remove the Edge
			Edge eX = x.getAdjacentVertexEdge(y);
			Edge eY = y.getAdjacentVertexEdge(x);
			allEdges.remove(eX);
			allEdges.remove(eY);
			x.removeAdjacentVertex(eX, y);
			if (!this.isDirected) {
				// remove the other edge also
				y.addAdjacentVertex(eY, x);
			}
			
			return true;
		} catch (Exception e) {
			System.out.println("Could not remove edge: " + from + " " + to + " " + weight);
			return false;
		}
	}

	public boolean addVertex(Vertex x) {
		if (allVertexes.containsKey(x)) {
			return false;
		}
		allVertexes.put(x.label, x);
		for (Edge e : x.getEdges()) {
			allEdges.add(e);
		}
		return true;
	}

	public boolean removeVertex(Vertex x) {
		if (!allVertexes.containsKey(x)) {
			return false;
		}
		allVertexes.remove(x.label);
		for (Edge e : x.getEdges()) {
			allEdges.remove(e);
		}
		return true;
	}

	public List<Edge> getAllEdges() {
		return allEdges;
	}

	public List<Vertex> getAllVertexes() {
		return  new ArrayList<Vertex>(allVertexes.values());
	}

	/*
	 * Edge connects Vertex A to Vertex B and holds weights
	 */
	public class Edge {
		private boolean isDirected = false;
		private Vertex fromVertex;
		private Vertex toVertex;
		private int edgeWeight;

		public Edge(Vertex from, Vertex to, int weight, boolean isDirected) {
			this.isDirected = isDirected;
			fromVertex = from;
			toVertex = to;
			edgeWeight = weight;
		}

		public Vertex getFromVertex() {
			return fromVertex;
		}

		public Vertex getToVertex() {
			return toVertex;
		}

		public int getEdgeWeight() {
			return edgeWeight;
		}

		public boolean isDirected() {
			return isDirected;
		}
	}

	/*
	 * Vertex holds its list of Edges and its map of adjacent neighbors
	 */
	public class Vertex {
		private String label;
		private List<Edge> edges;
		private Map<Vertex, Edge> adjacentVertex;
		private int inDegree;

		public Vertex(String label) {
			this.label = label;
			edges = new ArrayList<Edge>();
			adjacentVertex = new HashMap<Vertex, Edge>();
		}

		public String getLabel() {
			return label;
		}
		
		public int getInDegree() {
			return inDegree;
		}

		public void addAdjacentVertex(Edge e, Vertex x) {
			if (!edges.contains(e)) {
				edges.add(e);
			}
			if (!adjacentVertex.containsKey(x)) {
				adjacentVertex.put(x, e);
			}
			x.inDegree++; // x has an additional incoming edge
		}

		public void removeAdjacentVertex(Edge e, Vertex x) {
			if (edges.contains(e)) {
				edges.remove(e);
			}
			if (adjacentVertex.containsKey(x)) {
				adjacentVertex.remove(x);
			}
			x.inDegree--; // x has one less incoming edge
		}

		public boolean isAdjacentTo(Vertex x) {
			return adjacentVertex.containsKey(x);
		}

		public Edge getAdjacentVertexEdge(Vertex x) {
			return adjacentVertex.get(x);
		}
		
		public Map<Vertex, Edge> getAllAdjacentVertexes() {
			return adjacentVertex;
		}

		public int getDegrees() {
			return edges.size();
		}

		public List<Edge> getEdges() {
			return edges;
		}

		public boolean equals(Object other) {
			if (!(other instanceof Vertex)) {
				return false;
			}
			Vertex x = (Vertex) other;
			return x.getLabel() == this.label;
		}
	}
}
