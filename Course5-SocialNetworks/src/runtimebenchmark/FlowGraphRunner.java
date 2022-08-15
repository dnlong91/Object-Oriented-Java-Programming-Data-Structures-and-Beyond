package runtimebenchmark;

import flowgraph.BasicFlowGraph;
import flowgraph.OptimizedFlowGraph;
import util.FlowGraphLoader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class FlowGraphRunner {
    public static void main(String[] args) {
        Random rand = new Random();

        /****************** DATASET 1 ******************/
        // Init
        System.out.println("\nTEST 1:");
//		Integer arr1[] = { 5, 6, 14, 1, 3, 8 };
        Integer arr1[] = { 14, 1, 9 };
        HashSet<Integer> init1 = new HashSet<Integer>(Arrays.asList(arr1));
//        System.out.print("Initial nodes: ");
//        for (int node : init1) {
//            System.out.print(node + " ");
//        }
        // Basic Flow Graph
        System.out.print("Making a new basic flow graph...");
        BasicFlowGraph bfg = new BasicFlowGraph(init1, 2, 1);
        System.out.print("DONE. \n");
        // Optimized Flow Graph
        System.out.print("Making a new optimized flow graph...");
        OptimizedFlowGraph ofg = new OptimizedFlowGraph(init1, 2, 1);
        System.out.print("DONE. \n");
        // Load the graphs
        System.out.print("\nLoading the map to the basic flow graph...");
        FlowGraphLoader.loadFlowGraph(bfg,"Course5-SocialNetworks/data/small_test_graph.txt");
        System.out.print("DONE. \n");
        System.out.print("Loading the map to the optimized flow graph...");
        FlowGraphLoader.loadFlowGraph(ofg,"Course5-SocialNetworks/data/small_test_graph.txt");
        System.out.print("DONE. \n");
        // Update changes
        long start = System.nanoTime();
        bfg.updateGenerations();
        long end = System.nanoTime();
        long time = (end - start) / 1_000_000;
        System.out.println("\nupdateGenerations() method of basic flow graph run time: " + time);
        start = System.nanoTime();
        ofg.updateChanges();
        end = System.nanoTime();
        time = (end - start) / 1_000_000;
        System.out.println("updateChanges() method of optimized flow graph run time: " + time + "\n");
        // Print Effect Table
        System.out.println("Effect table of basic flow graph:");
        start = System.nanoTime();
//        bfg.printEffectTable();
        end = System.nanoTime();
        time = (end - start) / 1_000_000;
        System.out.println("printEffectTable() method of basic flow graph run time: " + time);
        System.out.println("\nEffect table of optimized flow graph:");
        start = System.nanoTime();
//        ofg.printEffectTable();
        end = System.nanoTime();
        time = (end - start) / 1_000_000;
        System.out.println("printEffectTable() method of optimized flow graph run time: " + time);

        /****************** DATASET 2 ******************/
        // Init
        System.out.println("\nTEST 2:");
        int maxNumNodes = 100;
        Integer arr2[] = new Integer[maxNumNodes];
        for (int i = 0; i < maxNumNodes; i++) {
            arr2[i] = rand.nextInt(14947);
        }
        HashSet<Integer> init2 = new HashSet<Integer>(Arrays.asList(arr2));
//        System.out.print("Initial nodes: ");
//        for (int node : init2) {
//            System.out.print(node + " ");
//        }
        // Basic Flow Graph
        System.out.print("Making a new basic flow graph...");
        bfg = new BasicFlowGraph(init2, 2, 1);
        System.out.print("DONE. \n");
        // Optimized Flow Graph
        System.out.print("Making a new optimized flow graph...");
        ofg = new OptimizedFlowGraph(init2, 2, 1);
        System.out.print("DONE. \n");
        // Load the graphs
        System.out.print("\nLoading the map to the basic flow graph...");
        FlowGraphLoader.loadFlowGraph(bfg,"Course5-SocialNetworks/data/facebook_ucsd.txt");
        System.out.print("DONE. \n");
        System.out.print("Loading the map to the optimized flow graph...");
        FlowGraphLoader.loadFlowGraph(ofg,"Course5-SocialNetworks/data/facebook_ucsd.txt");
        System.out.print("DONE. \n");
        // Update changes
        start = System.nanoTime();
        bfg.updateGenerations();
        end = System.nanoTime();
        time = (end - start) / 1_000_000;
        System.out.println("\nupdateGenerations() method of basic flow graph run time: " + time);
        start = System.nanoTime();
        ofg.updateChanges();
        end = System.nanoTime();
        time = (end - start) / 1_000_000;
        System.out.println("updateChanges() method of optimized flow graph run time: " + time + "\n");
        // Print Effect Table
//        System.out.println("Effect table of basic flow graph:");
//        bfg.printEffectTable();
//        System.out.println("\nEffect table of optimized flow graph:");
//        ofg.printEffectTable();
    }
}
