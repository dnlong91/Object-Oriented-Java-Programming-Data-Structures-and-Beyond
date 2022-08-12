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
		vertices = new HashMap<>();
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

	private void computeBetweenness() {
		for (Node vertex : vertices.values()) {
			// Compute # of all shortest paths from vertex to each other node
			for (int otherId : vertices.keySet()) {
				if (otherId == vertex.getId()) {
					vertices.get(otherId).setNumPaths(1);
					vertices.get(otherId).setDistance(0);
				} else {
					vertices.get(otherId).setNumPaths(0);
					vertices.get(otherId).setDistance(Integer.MAX_VALUE);
				}
			}
			HashSet<Node> parents = new HashSet<>();
			HashMap<Node, Set<Node>> parentMap = new HashMap<>();  // Map a child to one or more parents
			bfs(vertex, parents, parentMap);
			// Now, all nodes have their numPaths and distance properties updated based on the current vertex
			// Distribute flow to edges along these paths
			// Determine the leaf nodes
			List<Node> leaves = new ArrayList<>();
			for (Node node : vertices.values()) {
				if (!parents.contains(node)) {
					leaves.add(node);
				}
			}
			// Compute edge scores
			
		}
	}

	private void bfs(Node source, HashSet<Node> parents, HashMap<Node, Set<Node>> parentMap) {
		HashSet<Node> visited = new HashSet<>();
		Queue<Node> toExplore = new LinkedList<>();

		visited.add(source);
		toExplore.add(source);

		while (!toExplore.isEmpty()) {
			Node curr = toExplore.remove();
			Set<Node> neighbors = curr.getNeighbors();
			for (Node neighbor : neighbors) {
				if (!visited.contains(neighbor)) {
					visited.add(neighbor);
					if (!parentMap.containsKey(neighbor)) {
						parentMap.put(neighbor, new HashSet<>());
					}
					parentMap.get(neighbor).add(curr);
					parentMap.put(neighbor, parentMap.get(neighbor));
					parents.add(curr);
					toExplore.add(neighbor);
				}
				if (neighbor.getDistance() > curr.getDistance() + 1) {
					neighbor.setDistance(curr.getDistance() + 1);
					neighbor.setNumPaths(curr.getNumPaths());
				} else if (neighbor.getDistance() == curr.getDistance() + 1) {
					neighbor.setNumPaths(neighbor.getNumPaths() + curr.getNumPaths());
				}
			}
		}
	}

	private void removeHighestBetweenness() {

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
