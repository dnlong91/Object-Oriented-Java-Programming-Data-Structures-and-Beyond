package uniongraph;

import util.UnionGraphLoader;

import java.util.*;

/**
 * @author Ginny Dang
 *
 * Investigating the connection between any 2 people using Union Find
 */

public class UnionGraph {
    private HashSet<Integer> vertices;
    private HashMap<Integer, Integer> parents; // Map a child to its parent
    private HashSet<Integer> uniqueParents; // All nodes that actually has at least 1 child that is not itself

    public UnionGraph() {
        vertices = new HashSet<Integer>();
        parents = new HashMap<Integer, Integer>();
        uniqueParents = new HashSet<Integer>();
    }

    public void addVertex(int vertex) {
        vertices.add(vertex);
        parents.put(vertex, vertex);
    }

    public int findSet(int vertex) {
        // Error handling
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
        // TODO: make sure this is correct
        if (parent2 != parent1) {
            uniqueParents.add(parent2);
        }
    }

    public static void main(String[] args) {
        // Init the graph
        String filename = "Course5-SocialNetworks/data/small_test_graph.txt";
		System.out.println("DATASET 1: " + filename);
		UnionGraph ug = new UnionGraph();
		// Load the graph
		System.out.print("Making a new union graph...");
		System.out.print("DONE. \nLoading the map...");
        long start = System.nanoTime();
		UnionGraphLoader.loadUnionGraph(ug, filename);
//        UnionGraphLoader.loadUnionGraph(ug,"Course5-SocialNetworks/data/facebook_ucsd.txt");
        long end = System.nanoTime();
        long time = (end - start) / 1_000_000;
		System.out.println("DONE.");
        System.out.println("Loading time: " + time);
        System.out.println("Number of vertices: " + ug.vertices.size());
        for (int parent : ug.parents.keySet()) {
            System.out.println(parent + " : " + ug.parents.get(parent));
        }
        System.out.println("Number of unique parents: " + ug.uniqueParents.size());
        for (int uniqueParent : ug.uniqueParents) {
            System.out.print(uniqueParent + " " );
        }

        // Detect the connection between 2 people in the network
        System.out.println("\nTEST 1:");
        int user1 = 2;
        int user2 = 10;
        System.out.print("Is " + user1 + " directly or indirectly connected with " + user2 + "? ");
        start = System.nanoTime();
        int parent1 = ug.findSet(user1);
        int parent2 = ug.findSet(user2);
        if (parent1 == parent2) {
            System.out.print("YES");
        } else {
            System.out.print("NO");
        }
        end = System.nanoTime();
        time = (end - start) / 1_000_000;
        System.out.println("\nRun time: " + time);

        System.out.println("\nTEST 2:");
        user1 = 12;
        user2 = 4;
        System.out.print("Is " + user1 + " directly or indirectly connected with " + user2 + "? ");
        start = System.nanoTime();
        parent1 = ug.findSet(user1);
        parent2 = ug.findSet(user2);
        if (parent1 == parent2) {
            System.out.print("YES");
        } else {
            System.out.print("NO");
        }
        end = System.nanoTime();
        time = (end - start) / 1_000_000;
        System.out.println("\nRun time: " + time);

        System.out.println("\nTEST 3:");
        user1 = 5;
        user2 = 9;
        System.out.print("Is " + user1 + " directly or indirectly connected with " + user2 + "? ");
        start = System.nanoTime();
        parent1 = ug.findSet(user1);
        parent2 = ug.findSet(user2);
        if (parent1 == parent2) {
            System.out.print("YES");
        } else {
            System.out.print("NO");
        }
        end = System.nanoTime();
        time = (end - start) / 1_000_000;
        System.out.println("\nRun time: " + time);
    }
}
