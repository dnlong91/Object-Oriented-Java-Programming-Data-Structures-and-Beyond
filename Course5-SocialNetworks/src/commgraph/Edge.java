package commgraph;

public class Edge {
    private Node start;
    private Node end;
    private double score;

    public Edge(Node start, Node end) {
        this.start = start;
        this.end = end;
    }

    public Node getEndNode() {
        return this.end;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
