package uniongraph;

import java.util.HashMap;
import java.util.HashSet;

public abstract class UnionGraph {
    HashSet<Integer> vertices;
    HashMap<Integer, Integer> parents; // Map a child to its parent
    HashSet<Integer> uniqueParents; // All nodes that actually has at least 1 child that is not itself

    public UnionGraph() {
        vertices = new HashSet<Integer>();
        parents = new HashMap<Integer, Integer>();
        uniqueParents = new HashSet<Integer>();
    }

    public void addVertex(int vertex) {
        vertices.add(vertex);
        parents.put(vertex, vertex);
    }

    public abstract int findSet(int vertex);

    public abstract void unionSet(int vertex1, int vertex2);
}
