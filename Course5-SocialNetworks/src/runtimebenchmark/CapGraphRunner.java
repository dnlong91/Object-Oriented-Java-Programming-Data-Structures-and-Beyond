package runtimebenchmark;

import graph.CapGraph;
import graph.Graph;
import util.GraphLoader;

import java.util.*;

public class CapGraphRunner {
    public static void main(String[] args) {
        Random rand = new Random();

        /****************** DATASET 1 ******************/
        /*********** Load graph ***********/
        System.out.print("Making a new graph...");
        CapGraph cg = new CapGraph();
        System.out.print("DONE. \nLoading the map...");
        long start = System.nanoTime();
        GraphLoader.loadGraph(cg, "Course5-SocialNetworks/data/small_test_graph.txt");
        long end = System.nanoTime();
        System.out.println("DONE.");
        long time = (end - start) / 1000;
        System.out.println("Loading time: " + time);
        /*********** Get Egonet ***********/
        int numVertices = cg.getNumVertices();
        int center = rand.nextInt(numVertices);
        System.out.println("\nCenter: " + center);
        start = System.nanoTime();
        Graph egonet = cg.getEgonet(center);
        end = System.nanoTime();
        time = (end - start) / 1000;
        HashMap<Integer, HashSet<Integer>> exportedEgonet = egonet.exportGraph();
        for (int vertex : exportedEgonet.keySet()) {
            System.out.print(vertex + " : ");
            String neighborStr = "";
            for (int neighbor : exportedEgonet.get(vertex)) {
                neighborStr += neighbor + " ";
            }
            System.out.print(neighborStr + "\n");
        }
        System.out.println("Getting egonet time: " + time + "\n");
        /*********** Get all Strongly Connected Components ***********/
        start = System.nanoTime();
        List<Graph> sccs = cg.getSCCs();
        end = System.nanoTime();
        time = (end - start) / 1000;
        System.out.println("Number of strongly connected components: " + sccs.size());
        System.out.println("Getting strongly connected components time: " + time + "\n");
    }
}
