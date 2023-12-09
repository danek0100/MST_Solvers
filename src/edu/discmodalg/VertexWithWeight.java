package edu.discmodalg;

public class VertexWithWeight implements Comparable<VertexWithWeight> {
    private final Vertex vertex;
    private final int weight;

    public VertexWithWeight(Vertex vertex, int weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public int compareTo(VertexWithWeight other) {
        return Integer.compare(this.weight, other.weight);
    }
}
