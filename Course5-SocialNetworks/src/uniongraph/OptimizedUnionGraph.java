package uniongraph;

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
}
