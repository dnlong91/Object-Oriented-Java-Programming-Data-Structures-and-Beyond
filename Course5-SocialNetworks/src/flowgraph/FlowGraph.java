package flowgraph;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Ginny Dang
 *
 * Investigating the information flow within a social network
 *
 */

public abstract class FlowGraph {
    HashSet<Integer> currGeneration;
    int a;
    int b;
    HashMap<Integer, HashSet<Integer>> vertices;
    HashMap<Integer, HashSet<Integer>> affects;

    public FlowGraph(HashSet<Integer> init, int rewardA, int rewardB) {
        currGeneration = new HashSet<Integer>();
        currGeneration.addAll(init);
        a = rewardA;
        b = rewardB;
        vertices = new HashMap<Integer, HashSet<Integer>>();
        affects = new HashMap<Integer, HashSet<Integer>>();
    }
    public void addVertex(int num) {
        // Only add vertex if it has not been in the graph yet
        if (!vertices.containsKey(num)) {
            vertices.put(num, new HashSet<Integer>());
            affects.put(num, new HashSet<Integer>());
        }
    }
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

    public abstract void printEffectTable();
}
