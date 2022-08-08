/**
 * 
 */
package graph;

import java.util.*;
import util.GraphLoader;

/**
 * @author Ginny Dang
 * 
 * For the warm up assignment, you must implement your Graph in a class
 * named CapGraph. Here is the stub file.
 *
 */
public class CapGraph implements Graph {
	private HashMap<Integer, HashSet<Integer>> vertices;
	
	// Create the original CapGraph
	public CapGraph() {
		vertices = new HashMap<Integer, HashSet<Integer>>();
	}
	
	// Create the transposed CapGraph
	public CapGraph Transpose() {
		CapGraph transpose = new CapGraph();
		// Add all vertices of the original graph to the transposed graph
		for (int vertex : vertices.keySet()) {
			transpose.vertices.put(vertex, new HashSet<Integer>());
		}
		// Add reversed edges to the transposed graph
		for (int vertex : vertices.keySet()) {
			for (int neighbor : vertices.get(vertex)) {
				transpose.vertices.get(neighbor).add(vertex);
			}
		}
		return transpose;
	}
	
	/* (non-Javadoc)
	 * @see graph.Graph#addVertex(int)
	 */
	@Override
	public void addVertex(int num) {
		// Only add vertex if it has not been in the graph yet
		if (!vertices.containsKey(num)) {
			vertices.put(num, new HashSet<Integer>());
		}
	}

	/* (non-Javadoc)
	 * @see graph.Graph#addEdge(int, int)
	 */
	@Override
	public void addEdge(int from, int to) {
		// Error handling
		if (!vertices.containsKey(from) || !vertices.containsKey(to)) {
			throw new IllegalArgumentException("start point or end point does not exist in the map");
		}
		// Add new edge
		HashSet<Integer> fromNeighbors = vertices.get(from);
		fromNeighbors.add(to);
		vertices.put(from, fromNeighbors);
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getEgonet(int)
	 */
	@Override
	public Graph getEgonet(int center) {
		// Error handling
		if (!vertices.containsKey(center)) {
			throw new IllegalArgumentException("center point does not exist in the map");
		}
		// Find the egonet of the center
		CapGraph egonet = new CapGraph();
		egonet.vertices.put(center, new HashSet<Integer>());
		HashSet<Integer> neighbors = vertices.get(center);
		// Add all nodes and edges connected to center to the egonet
		for (int neighbor : neighbors) {
			egonet.vertices.put(neighbor, new HashSet<Integer>());
			egonet.vertices.get(center).add(neighbor);
		}
		// Amongst all existing neighbor nodes in the egonet,
		// connect those that have connection in the original graph
		for (int neighbor : neighbors) {
			HashSet<Integer> nextNeighbors = vertices.get(neighbor);
			for (int nextNeighbor : nextNeighbors) {
				if (neighbors.contains(nextNeighbor)) {
					egonet.vertices.get(neighbor).add(nextNeighbor);
				}
			}
		}
		return egonet;
	}
	
	/* DFS 1 keeps track of the order in which vertices finish
	 * @param vertex - the vertex from which we explore
	 * @param visited - all visited vertices up until this point
	 * @param finished - all vertices that have no non-visited neighbors
	 */
	private void fillOrder(int vertex, HashSet<Integer> visited, Stack<Integer> finished) {
		visited.add(vertex);
		for (int neighbor : vertices.get(vertex)) {
			if (!visited.contains(neighbor)) {
				fillOrder(neighbor, visited, finished);
			}
		}
		finished.push(vertex);
	}
	
	/* DFS 2 explores the transposed graph in the reverse order of finish time
	 * @param vertex -  the vertex from which we explore
	 * @param visited - all visited vertices up until this point
	 */
	private CapGraph dfsUtil(int vertex, HashSet<Integer> visited, CapGraph newSCC) {
		newSCC.addVertex(vertex);
		visited.add(vertex);
		for (int next : vertices.get(vertex)) {
			if (!visited.contains(next)) {
				dfsUtil(next, visited, newSCC);
			}
		}
		return newSCC;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getSCCs()
	 */
	@Override
	public List<Graph> getSCCs() {
		List<Graph> sccs = new ArrayList<Graph>();
		// DFS 1
		Stack<Integer> finished = new Stack<Integer>();
		HashSet<Integer> visited = new HashSet<Integer>();
		for (int vertex : vertices.keySet()) {
			if (!visited.contains(vertex)) {
				fillOrder(vertex, visited, finished);
			}
		}
		// Transpose the graph
		CapGraph transpose = Transpose();
		// DFS 2
		visited.clear();
		while (!finished.isEmpty()) {
			int top = finished.pop();
			if (!visited.contains(top)) {
				CapGraph scc = transpose.dfsUtil(top, visited, new CapGraph());
				sccs.add(scc);
			}
		}
		return sccs;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		return this.vertices;
	}
	
	public static void main(String[] args) {
		System.out.print("Making a new graph...");
		Graph firstGraph = new CapGraph();
		System.out.print("DONE. \nLoading the map...");
//		GraphLoader.loadGraph(firstGraph, "data/facebook_1000.txt");
//		GraphLoader.loadGraph(firstGraph, "data/facebook_2000.txt");
//		GraphLoader.loadGraph(firstGraph, "data/facebook_ucsd.txt");
		GraphLoader.loadGraph(firstGraph, "data/small_test_graph.txt");
//		GraphLoader.loadGraph(firstGraph, "data/twitter_combined.txt");
//		GraphLoader.loadGraph(firstGraph, "data/twitter_higgs.txt");
		System.out.println("DONE.");
	}
}