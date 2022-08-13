package uniongraph;

import util.UnionGraphLoader;

import java.util.*;

/**
 * @author Ginny Dang
 *
 * Investigating the connection between any 2 people using Union Find
 */

public class UnionGraph {
//    private HashMap<Integer, HashSet<Integer>> vertices;
    private HashSet<Integer> vertices;
    private HashMap<Integer, Integer> parents;

    public UnionGraph() {
//        vertices = new HashMap<Integer, HashSet<Integer>>();
        vertices = new HashSet<Integer>();
        parents = new HashMap<Integer, Integer>();
    }

    public void addVertex(int vertex) {
//        vertices.put(vertex, new HashSet<Integer>());
        vertices.add(vertex);
        parents.put(vertex, vertex);
    }

//    public void addEdge(int from, int to) {
//        // Error handling
//        if (!vertices.containsKey(from) || !vertices.containsKey(to)) {
//            throw new IllegalArgumentException("Either one of the users does not exist in the network");
//        }
//        // Add new edge
//        HashSet<Integer> fromNeighbors = vertices.get(from);
//        fromNeighbors.add(to);
//        vertices.put(from, fromNeighbors);
//        // Union from and to
//        unionSet(from, to);
//    }

    public int getNumVertices() {
        return vertices.size();
    }

    public int findSet(int vertex) {
        // Error handling
//        if (!vertices.containsKey(vertex) || !parents.containsKey(vertex)) {
        if (!vertices.contains(vertex) || !parents.containsKey(vertex)) {
            System.out.println("\n" + vertex);
            throw new IllegalArgumentException("This user does not exist in the network");
        }
        // Find set of the input vertex
        while (vertex != parents.get(vertex)) {
            vertex = parents.get(vertex);
        }
        return vertex;
    }

    public void unionSet(int vertex1, int vertex2) {
        int parent1 = findSet(vertex1);
        int parent2 = findSet(vertex2);
        parents.put(parent1, parent2);
    }

    public static void main(String[] args) {
        // Init the graph
		System.out.println("TEST 1:");
		UnionGraph ug = new UnionGraph();
		// Load the graph
		System.out.print("Making a new union graph...");
		System.out.print("DONE. \nLoading the map...");
//		UnionGraphLoader.loadUnionGraph(ug,"Course5-SocialNetworks/data/small_test_graph.txt");
        UnionGraphLoader.loadUnionGraph(ug,"Course5-SocialNetworks/data/facebook_ucsd.txt");
		System.out.println("DONE.");
        System.out.println("Number of vertices: " + ug.getNumVertices());
        // Detect the connection between 2 people in the network
        int user1 = 2410;
        int user2 = 10;
        System.out.print("Is " + user1 + " directly or indirectly connected with " + user2 + "? ");
        int parent1 = ug.findSet(user1);
        int parent2 = ug.findSet(user2);
        if (parent1 == parent2) {
            System.out.print("YES");
        } else {
            System.out.print("NO");
        }
    }
}
