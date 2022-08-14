package uniongraph;

import util.UnionGraphLoader;

import java.util.HashMap;

/**
 * @author Ginny Dang
 *
 * Investigating the connection between any 2 people using Union Find optimized with Path Compression
 */

public class OptimizedUnionGraph extends BasicUnionGraph {
    private HashMap<Integer, Integer> ranks;

    public OptimizedUnionGraph() {
        super();
    }

    public int findSet(int vertex) {
        // Error handling
        if (!vertices.contains(vertex) || !parents.containsKey(vertex)) {
            System.out.println("\n" + vertex);
            throw new IllegalArgumentException("This user does not exist in the network");
        }
        // Find set of the input vertex
        if (parents.get(vertex) != vertex) {
            int parentVertex = findSet(parents.get(vertex));
            parents.put(vertex, parentVertex);
        }
        return parents.get(vertex);
    }

    public void unionSet(int vertex1, int vertex2) {
        int parent1 = findSet(vertex1);
        int parent2 = findSet(vertex2);
        parents.put(parent1, parent2);
        // TODO: make sure this is correct
        if (parent2 != parent1) {
            uniqueParents.add(parent2);
            uniqueParents.remove(parent1);
        }
    }

    // TODO: for testing only
//    private int getNumRanks() {
//        HashSet<Integer> uniqueRanks = new HashSet<Integer>();
//        for (int rank : ranks.values()) {
//            uniqueRanks.add(rank);
//        }
//        return uniqueRanks.size();
//    }

    public static void main(String[] args) {
        /********* Test using Dataset 1 *********/
        // Init the graph
        String filename = "Course5-SocialNetworks/data/small_test_graph.txt";
        System.out.println("DATASET 1: " + filename);
        UnionGraph oug = new OptimizedUnionGraph();
        // Load the graph
        System.out.print("Making a new optimized union graph...");
        System.out.print("DONE. \nLoading the map...");
        long start = System.nanoTime();
        UnionGraphLoader.loadUnionGraph(oug, filename);
//        UnionGraphLoader.loadUnionGraph(ug,"Course5-SocialNetworks/data/facebook_ucsd.txt");
        long end = System.nanoTime();
        long time = (end - start) / 1000;
        System.out.println("DONE.");
        System.out.println("Loading time: " + time);
        System.out.println("Number of vertices: " + oug.vertices.size());
//        for (int parent : oug.parents.keySet()) {
//            System.out.println(parent + " : " + oug.parents.get(parent));
//        }
        System.out.println("\nNumber of unique parents: " + oug.uniqueParents.size());
//        for (int uniqueParent : oug.uniqueParents) {
//            System.out.print(uniqueParent + " " );
//        }
//        System.out.println("\nNumber of ranks: " + ((OptimizedUnionGraph) oug).getNumRanks());

        // Detect the connection between 2 people in the network
        System.out.println("\nTEST 1:");
        int user1 = 2;
        int user2 = 10;
        System.out.print("Is " + user1 + " directly or indirectly connected with " + user2 + "? ");
        start = System.nanoTime();
        int parent1 = oug.findSet(user1);
        int parent2 = oug.findSet(user2);
        if (parent1 == parent2) {
            System.out.print("YES");
        } else {
            System.out.print("NO");
        }
        end = System.nanoTime();
        time = (end - start) / 1000;
        System.out.println("\nRun time: " + time);

        System.out.println("\nTEST 2:");
        user1 = 12;
        user2 = 4;
        System.out.print("Is " + user1 + " directly or indirectly connected with " + user2 + "? ");
        start = System.nanoTime();
        parent1 = oug.findSet(user1);
        parent2 = oug.findSet(user2);
        if (parent1 == parent2) {
            System.out.print("YES");
        } else {
            System.out.print("NO");
        }
        end = System.nanoTime();
        time = (end - start) / 1000;
        System.out.println("\nRun time: " + time);

        System.out.println("\nTEST 3:");
        user1 = 5;
        user2 = 9;
        System.out.print("Is " + user1 + " directly or indirectly connected with " + user2 + "? ");
        start = System.nanoTime();
        parent1 = oug.findSet(user1);
        parent2 = oug.findSet(user2);
        if (parent1 == parent2) {
            System.out.print("YES");
        } else {
            System.out.print("NO");
        }
        end = System.nanoTime();
        time = (end - start) / 1000;
        System.out.println("\nRun time: " + time);

        /********* Test using Dataset 2 *********/
        // Init the graph
        filename = "Course5-SocialNetworks/data/facebook_ucsd.txt";
        System.out.println("\nDATASET 2: " + filename);
        oug = new BasicUnionGraph();
        // Load the graph
        System.out.print("Making a new basic union graph...");
        System.out.print("DONE. \nLoading the map...");
        start = System.nanoTime();
        UnionGraphLoader.loadUnionGraph(oug, filename);
        end = System.nanoTime();
        time = (end - start) / 1000;
        System.out.println("DONE.");
        System.out.println("Loading time: " + time);
        System.out.println("Number of vertices: " + oug.vertices.size());
//        for (int parent : oug.parents.keySet()) {
//            System.out.println(parent + " : " + oug.parents.get(parent));
//        }
        System.out.println("Number of unique parents: " + oug.uniqueParents.size());
//        for (int uniqueParent : oug.uniqueParents) {
//            System.out.print(uniqueParent + " " );
//        }
//        System.out.println("Number of ranks: " + ((OptimizedUnionGraph) oug).getNumRanks());

        // Detect the connection between 2 people in the network
        System.out.println("\nTEST 1:");
        user1 = 2410;
        user2 = 10;
        System.out.print("Is " + user1 + " directly or indirectly connected with " + user2 + "? ");
        start = System.nanoTime();
        parent1 = oug.findSet(user1);
        parent2 = oug.findSet(user2);
        if (parent1 == parent2) {
            System.out.print("YES");
        } else {
            System.out.print("NO");
        }
        end = System.nanoTime();
        time = (end - start) / 1000;
        System.out.println("\nRun time: " + time);

        System.out.println("\nTEST 2:");
        user1 = 3112;
        user2 = 12;
        System.out.print("Is " + user1 + " directly or indirectly connected with " + user2 + "? ");
        start = System.nanoTime();
        parent1 = oug.findSet(user1);
        parent2 = oug.findSet(user2);
        if (parent1 == parent2) {
            System.out.print("YES");
        } else {
            System.out.print("NO");
        }
        end = System.nanoTime();
        time = (end - start) / 1000;
        System.out.println("\nRun time: " + time);

        System.out.println("\nTEST 3:");
        user1 = 1231;
        user2 = 1024;
        System.out.print("Is " + user1 + " directly or indirectly connected with " + user2 + "? ");
        start = System.nanoTime();
        parent1 = oug.findSet(user1);
        parent2 = oug.findSet(user2);
        if (parent1 == parent2) {
            System.out.print("YES");
        } else {
            System.out.print("NO");
        }
        end = System.nanoTime();
        time = (end - start) / 1000;
        System.out.println("\nRun time: " + time);
    }
}
