package uniongraph;

/**
 * @author Ginny Dang
 *
 * Investigating the connection between any 2 people using Union Find
 */

public class BasicUnionGraph extends UnionGraph{
    public BasicUnionGraph() {
        super();
    }

    public int findSet(int vertex) {
        // Error handling
        if (!vertices.contains(vertex) || !parents.containsKey(vertex)) {
            throw new IllegalArgumentException("User " + vertex + " does not exist in the network");
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
}
