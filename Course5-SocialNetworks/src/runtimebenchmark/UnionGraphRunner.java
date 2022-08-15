package runtimebenchmark;

import uniongraph.BasicUnionGraph;
import uniongraph.OptimizedUnionGraph;
import uniongraph.UnionGraph;
import util.UnionGraphLoader;

import java.util.HashSet;
import java.util.Random;

public class UnionGraphRunner {
    public static void main(String[] args) {
        Random rand = new Random();
        String[] filenames = {  "Course5-SocialNetworks/data/small_test_graph.txt",
                                "Course5-SocialNetworks/data/facebook_ucsd.txt",
                                "Course5-SocialNetworks/data/facebook_2000.txt",
                                "Course5-SocialNetworks/data/facebook_1000.txt"  };
        for (int i = 0; i < filenames.length; i++) {
            // Init
            String filename = filenames[i];
            System.out.println("\nDATASET " + (i + 1) + ": " + filename);
            // Make the graphs
            //// Basic Union Graph
            System.out.print("Making a new basic union graph...");
            UnionGraph bug = new BasicUnionGraph();
            System.out.print("DONE. \n");
            //// Optimized Union Graph
            System.out.print("Making a new optimized union graph...");
            UnionGraph oug = new OptimizedUnionGraph();
            System.out.print("DONE. \n");
            // Load the graphs
            //// Basic Union Graph
            System.out.print("\nLoading the map to the basic union graph...");
            long start = System.nanoTime();
            UnionGraphLoader.loadUnionGraph(bug, filename);
            long end = System.nanoTime();
            long time = (end - start) / 1000;
            System.out.print("DONE. \n");
            System.out.println("- Loading time of the basic union graph: " + time);
            System.out.println("- Number of vertices in the basic union graph: " + bug.getNumVertices());
            System.out.println("- Number of unique parents in the basic union graph: " + bug.getNumUniqueParents());
            //// Optimized Union Graph
            System.out.print("\nLoading the map to the optimized union graph...");
            start = System.nanoTime();
            UnionGraphLoader.loadUnionGraph(oug, filename);
            end = System.nanoTime();
            time = (end - start) / 1000;
            System.out.print("DONE. \n");
            System.out.println("- Loading time of the optimized union graph: " + time);
            System.out.println("- Number of vertices in the optimized union graph: " + oug.getNumVertices());
            System.out.println("- Number of unique parents in the optimized union graph: " + oug.getNumUniqueParents());
            // Detect the connection between 2 people in the network
            for (int j = 0; j < 4; j++) {
                System.out.println("\nTEST " + (j + 1) + ":");
                int numVertices = 0;
                HashSet<Integer> vertices = new HashSet<>();
                if (j % 2 == 0) {
                    numVertices = bug.getNumVertices();
                    vertices = bug.getVertices();
                } else {
                    numVertices = oug.getNumVertices();
                    vertices = oug.getVertices();
                }
                int user1 = rand.nextInt(numVertices);
                while (!vertices.contains(user1)) {
                    user1 = rand.nextInt(numVertices);
                }
                int user2 = rand.nextInt(numVertices);
                while (!vertices.contains(user2)) {
                    user2 = rand.nextInt(numVertices);
                }
                // Basic Union Graph
                System.out.println("Result of the basic union graph:");
                System.out.print("- Is " + user1 + " directly or indirectly connected with " + user2 + "? ");
                start = System.nanoTime();
                int parent1 = bug.findSet(user1);
                int parent2 = bug.findSet(user2);
                if (parent1 == parent2) {
                    System.out.print("YES");
                } else {
                    System.out.print("NO");
                }
                end = System.nanoTime();
                time = (end - start) / 1000;
                System.out.println("\n- Run time of the basic union graph: " + time);
                // Optimized Union Graph
                System.out.println("Result of the optimized union graph:");
                System.out.print("- Is " + user1 + " directly or indirectly connected with " + user2 + "? ");
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
                System.out.println("\n- Run time of the optimized union graph: " + time);
            }
        }
    }
}
