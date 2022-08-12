package commgraph;

import java.util.*;

/**
 * @author Ginny Dang
 *
 * Detecting communities within a social network
 */

public class CommGraph {
	private HashMap<Integer, Node> vertices;

	public CommGraph() {
		vertices = new HashMap<Integer, Node>();
	}

	public boolean addVertex(int id) {
		// Edge case handling
		if (vertices.containsKey(id)) {
			return false;
		}
		// Normal cases
		Node vertex = new Node(id);
		vertices.put(id, vertex);
		return true;
	}

	public void addEdge(int from, int to) {
		// Exception Handling
		if (!vertices.containsKey(from) || !vertices.containsKey(to)) {
			throw new IllegalArgumentException("Illegal Input");
		}
		// Normal cases
		Node startVertex = vertices.get(from);
		Node endVertex = vertices.get(to);
		Edge newEdge = new Edge(startVertex, endVertex);
		startVertex.addEdge(newEdge);
	}

	public void computeBetweenness() {
		for (int vertex : vertices.keySet()) {
			for (int other : vertices.keySet()) {
				if (other == vertex) {
					vertices.get(other).setNumPaths(1);
					vertices.get(other).setDistance(0);
					continue;
				}
				bfs(vertex, other);
			}
		}
	}

	private int bfs(int start, int end) {
		Node source = vertices.get(start);
		Node dest = vertices.get(end);
		HashMap<Node, Node> parentMap = new HashMap<Node, Node>();
		HashSet<Node> visited = new HashSet<Node>();
		Queue<Node> toExplore = new LinkedList<Node>();

		// Initialize numPaths value and distance value for all nodes
		for (Node node : vertices.values()) {
			node.setNumPaths(0);
			node.setDistance(Integer.MAX_VALUE);
		}
		source.setNumPaths(1); // TODO: check if this is redundant
		source.setDistance(0); // TODO: check if this is redundant

		visited.add(source);
		toExplore.add(dest);


		return 0;
	}

	public void removeHighestBetweenness() {

	}

//	pubilc HashSet<Integer> detectCommunities(int num) {
//		HashSet<Integer> communities = new HashSet<Integer>();
//		int detected = 0;
//		while (detected < num) {
//
//		}
//		return communities;
//	}

	public static void main(String[] args) {

	}
}
