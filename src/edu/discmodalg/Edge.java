package edu.discmodalg;

public record Edge(Vertex vertex1, Vertex vertex2, int weight) implements Comparable<Edge> {
    @Override
    public int compareTo(Edge o) {
        if (this.weight > o.weight)
            return 1;
        else if (this.weight < o.weight)
            return -1;
        return 0;
    }
}
