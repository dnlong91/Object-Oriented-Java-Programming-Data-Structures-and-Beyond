/**
 * @author UCSD MOOC development team and Ginny Dang
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;

import java.util.*;
import java.util.function.Consumer;
import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team
 * @author Ginny Dang
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	private HashMap<GeographicPoint, MapNode> vertices; // For efficient look-up
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph() {
		vertices = new HashMap<GeographicPoint, MapNode>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices() {
		return vertices.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices() {
		Set<GeographicPoint> vertexPoints = new HashSet<GeographicPoint>();
		for (GeographicPoint vertex : vertices.keySet()) {
			vertexPoints.add(vertex);
		}
		return vertexPoints;
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges() {
		int numEdges = 0;
		for (MapNode node : vertices.values()) {
			int currNumEdges = node.getNumEdges();
			numEdges += currNumEdges;
		}
		return numEdges;
	}
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location) {
		// Edge case handling
		if (vertices.containsKey(location) || location == null) {
			return false;
		}
		
		// Normal cases
		MapNode vertex = new MapNode(location);
		vertices.put(location, vertex);
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName, String roadType, double length) throws IllegalArgumentException {
		// Exception Handling
		if (!vertices.containsKey(from) || !vertices.containsKey(to) || 
				roadName == null || roadType == null ||
				length < 0) {
			throw new IllegalArgumentException("Illegal Input");
		}
		
		// Normal cases
		MapNode startVertex = vertices.get(from);
		MapNode endVertex = vertices.get(to);
		MapEdge newEdge = new MapEdge(startVertex, endVertex, roadName, roadType, length);
		startVertex.addEdge(newEdge);
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {
		// Setup - check validity of inputs
		if (start == null || goal == null) {
			throw new NullPointerException("Cannot find route from or to null node");
		}
		
		MapNode startNode = vertices.get(start);
		MapNode goalNode = vertices.get(goal);
		
		if (startNode == null || goalNode == null) {
			System.out.println("Start or goal node is null! No path exists.");
			return new LinkedList<GeographicPoint>();
		}
		
		// BFS
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		HashSet<MapNode> visited = new HashSet<MapNode>();
		Queue<MapNode> toExplore = new LinkedList<MapNode>();
		boolean found = false;
		
		visited.add(startNode);
		toExplore.add(startNode);
		
		while (!toExplore.isEmpty()) {
			MapNode currNode = toExplore.remove();
			// Hook for visualization
			nodeSearched.accept(currNode.getPoint());
			// The goalNode is found here
			if (currNode.equals(goalNode)) {
				found = true;
				break;
			}
			// The goalNode has not been found, keep exploring neighbor nodes
			Set<MapNode> neighbors = currNode.getNeighbors();
			for (MapNode neighbor : neighbors) {
				if (!visited.contains(neighbor)) {
					visited.add(neighbor);
					parentMap.put(neighbor, currNode);
					toExplore.add(neighbor);
				}
			}
		}
		
		// The goalNode has not been found, after exploring all nodes
		if (found == false) {
			System.out.println("No path found from " + start + " to " + goal);
			return null;
		}
		
		// Reconstruct the parent path
		List<GeographicPoint> path = constructPath(startNode, goalNode, parentMap);
		return path;
	}

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {
		// Setup - check validity of inputs
		if (start == null || goal == null) {
			throw new NullPointerException("Cannot find route from or to null node");
		}
				
		MapNode startNode = vertices.get(start);
		MapNode goalNode = vertices.get(goal);
				
		if (startNode == null || goalNode == null) {
			System.out.println("Start or goal node is null! No path exists.");
			return new LinkedList<GeographicPoint>();
		}
		
		// Dijkstra
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		HashSet<MapNode> visited = new HashSet<MapNode>();
		PriorityQueue<MapNode> toExplore = new PriorityQueue<MapNode>();
		int count = 0; // count visited
		
		// Initialize distance for all nodes
		for (MapNode node : vertices.values()) {
			node.setDistance(Double.POSITIVE_INFINITY);
		}
		startNode.setDistance(0);
		
		toExplore.add(startNode);
		
		while (!toExplore.isEmpty()) {
			MapNode currNode = toExplore.remove();
			// Hook for visualization
			nodeSearched.accept(currNode.getPoint());
			count++;
			System.out.println("DIJKSTRA visiting" + currNode);
			// The goalNode is found here
			if (currNode.equals(goalNode)) {
				break;
			}
			// The goalNode has not been found, keep exploring neighbor nodes
			if (!visited.contains(currNode)) {
				visited.add(currNode);
				Set<MapEdge> edges = currNode.getEdges();
				for (MapEdge edge : edges) {
					MapNode neighborNode = edge.getEndNode();
					if (!visited.contains(neighborNode)) {
						// Distance from startNode to neighborNode
						double currDist = edge.getLength() + currNode.getDistance();
//						System.out.print(currDist);
						if (currDist < neighborNode.getDistance()) {
							parentMap.put(neighborNode, currNode);
							neighborNode.setDistance(currDist);
							toExplore.add(neighborNode);
						}
					}
				}
			}
		}
		
		// The goalNode has not been found, after exploring all nodes
		if (goalNode.getDistance() == Double.POSITIVE_INFINITY) {
			System.out.println("No path found from " + start + " to " + goal);
			return null;
		}
		
		// Reconstruct the parent path
		List<GeographicPoint> path = constructPath(startNode, goalNode, parentMap);
		System.out.println("Nodes visited in search: " + count);
		return path;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {
		// Setup - check validity of inputs
		if (start == null || goal == null) {
			throw new NullPointerException("Cannot find route from or to null node");
		}
						
		MapNode startNode = vertices.get(start);
		MapNode goalNode = vertices.get(goal);
						
		if (startNode == null || goalNode == null) {
			System.out.println("Start or goal node is null! No path exists.");
			return new LinkedList<GeographicPoint>();
		}
		
		// A-Star
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		HashSet<MapNode> visited = new HashSet<MapNode>();
		PriorityQueue<MapNode> toExplore = new PriorityQueue<MapNode>();
		int count = 0; // count visited
		
		// Initialize predicted distance and actual distance for all nodes
		for (MapNode node : vertices.values()) {
			node.setDistance(Double.POSITIVE_INFINITY);
			node.setActualDistance(Double.POSITIVE_INFINITY);
		}
		startNode.setDistance(0);
		startNode.setActualDistance(0);
		
		toExplore.add(startNode);
		
		while (!toExplore.isEmpty()) {
			MapNode currNode = toExplore.remove();
			// Hook for visualization
			nodeSearched.accept(currNode.getPoint());
			count++;
			System.out.println("A* visiting" + currNode);
			// The goalNode is found here
			if (currNode.equals(goalNode)) {
				break;
			}
			// The goalNode has not been found, keep exploring neighbor nodes
			if (!visited.contains(currNode)) {
				visited.add(currNode);
				Set<MapEdge> edges = currNode.getEdges();
				for (MapEdge edge : edges) {
					MapNode neighborNode = edge.getEndNode();
					if (!visited.contains(neighborNode)) {
						// Actual distance from startNode to neighborNode
						double currActualDist = edge.getLength() + currNode.getActualDistance();
//						System.out.print(currActualDist);
						// Predicted distance from neighborNode to goalNode
						double currPredDist = currActualDist + (neighborNode.getPoint()).distance(goalNode.getPoint());
//						System.out.print(currPredDist);
						if (currPredDist < neighborNode.getDistance()) {
							parentMap.put(neighborNode, currNode);
							neighborNode.setActualDistance(currActualDist);
							neighborNode.setDistance(currPredDist);
							toExplore.add(neighborNode);
						}
					}
				}
			}
		}
		
		// The goalNode has not been found, after exploring all nodes
		if (goalNode.getActualDistance() == Double.POSITIVE_INFINITY) {
			System.out.println("No path found from " + start + " to " + goal);
			return null;
		}
		
		// Reconstruct the parent path
		List<GeographicPoint> path = constructPath(startNode, goalNode, parentMap);
		System.out.println("Nodes visited in search: " + count);
		return path;
	}
	
	private List<GeographicPoint> constructPath(MapNode startNode, MapNode goalNode, HashMap<MapNode, MapNode> parentMap) {
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		MapNode curr = goalNode;
		while (curr != startNode) {
			path.addFirst(curr.getPoint());
			curr = parentMap.get(curr);
		}
		path.addFirst(startNode.getPoint());
		return path;
	}
	
	public static void main(String[] args) {
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		
		// You can use this method for testing.  
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		/* Use this code in Week 3 End of Week Quiz */
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		System.out.println("\n\nQuiz test result");
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
	}
}