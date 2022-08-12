package commgraph;

public class Edge {
    private Node start;
    private Node end;
    private int betweenesses;
    private double score;
    public Edge(Node start, Node end) {
        this.start = start;
        this.end = end;
    }


}
