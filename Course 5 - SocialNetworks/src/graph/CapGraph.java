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
	private Map<Integer, HashSet<Integer>> vertices;
	
	public CapGraph() {
		vertices = new HashMap<Integer, HashSet<Integer>>();
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
	
	public HashSet<Integer> getNeighbors(int vertex) {
		// Error handling
		if (!vertices.containsKey(vertex)) {
			throw new IllegalArgumentException("center point does not exist in the map");
		}
		return vertices.get(vertex);
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
		Graph egonet = new CapGraph();
		HashSet<Integer> neighbors = vertices.get(center);
		// Add all nodes and edges connected to center to the egonet
		for (int neighbor : neighbors) {
			egonet.addVertex(neighbor);
			egonet.addEdge(center, neighbor);
			System.out.println("new vertex added to the egonet");
		}
		// Amongst all existing neighbor nodes in the egonet,
		// connect those that have connection in the original graph
		for (int neighbor : neighbors) {
			HashSet<Integer> nextNeighbors = vertices.get(neighbor);
			for (int nextNeighbor : nextNeighbors) {
				if (neighbors.contains(nextNeighbor)) {
					egonet.addEdge(neighbor, nextNeighbor);
					System.out.println("connected 2 neighbors of the center of the egonet");
				}
			}
		}
		return egonet;
	}
	
	private CapGraph copyGraph() {
		CapGraph copy = new CapGraph();
		for (int vertex : vertices.keySet()) {
			copy.addVertex(vertex);
			for (int neighbor : vertices.get(vertex)) {
				copy.addEdge(vertex, neighbor);
			}
		}
		return copy;
	}
	
	private CapGraph transpose() {
		CapGraph transpose = new CapGraph();
		for (int vertex : vertices.keySet()) {
			transpose.addVertex(vertex);
		}
		for (int vertex : vertices.keySet()) {
			for (int neighbor : vertices.get(vertex)) {
				transpose.addEdge(neighbor, vertex);
			}
		}
		return transpose;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getSCCs()
	 */
	@Override
	public List<Graph> getSCCs() {
		List<Graph> sccs = new ArrayList<Graph>();
		// Get the stack of vertices
		Stack<Integer> graphVertices = new Stack<Integer>();
		for (int vertex : vertices.keySet()) {
			graphVertices.push(vertex);
		}
		// Get the original graph
		CapGraph origin = copyGraph();
		// Step 1: Keep track of the order in which vertices finish
		Stack<Integer> finished1 = dfs(origin, graphVertices);
		// Step 2: Compute the transpose of the original graph
		CapGraph transpose = transpose();
		// Step 3: Explore in the reverse order of finish time from dfs
		Stack<Integer> finished2 = dfs(transpose, finished1);
		// Get all SCCS
		for (int vertex : finished2) {
			// TODO
		}
		return sccs;
	}

	private Stack<Integer> dfs(CapGraph graph, Stack<Integer> graphVertices) {
		Set<Integer> visited = new HashSet<Integer>();
		Stack<Integer> finished = new Stack<Integer>();
		while (!graphVertices.isEmpty()) {
			int vertex = graphVertices.pop();
			if (!visited.contains(vertex)) {
				dfsVisit(graph, vertex, visited, finished);
			}
		}
		return finished;
	}

	private void dfsVisit(CapGraph graph, int vertex, Set<Integer> visited, Stack<Integer> finished) {
		visited.add(vertex);
		Set<Integer> neighbors = graph.getNeighbors(vertex);
		for (int neighbor : neighbors) {
			if (!visited.contains(neighbor)) {
				dfsVisit(graph, neighbor, visited, finished);
			}
		}
		finished.push(vertex);
	}

	/* (non-Javadoc)
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		// TODO Auto-generated method stub
		return null;
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