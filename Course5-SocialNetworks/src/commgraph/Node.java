package commgraph;

import java.util.*;

public class Node {
    private int id;
    private HashSet<Edge> edges; // all edges starting from this node
    private int numPaths; // the number of the shortest paths from a source node to this node
    private int distance; // the length of the shortest path from the source to the current one

    public Node(int id) {
        this.id = id;
        this.edges = new HashSet<Edge>();
        this.numPaths = 0;
        this.distance = Integer.MAX_VALUE;
    }

    public void addEdge(Edge newEdge) {
        edges.add(newEdge);
    }

    public int getId() {
        return this.id;
    }

    public Set<Edge> getEdges() {
        return this.edges;
    }

    public int getNumPaths() {
        return this.numPaths;
    }

    public void setNumPaths(int newNumPaths) {
        this.numPaths = newNumPaths;
    }

    public void setDistance(int newDistance) {
        this.distance = newDistance;
    }
}
