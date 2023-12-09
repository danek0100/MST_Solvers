package edu.discmodalg;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EdgeListGraph implements Graph {
    private List<Vertex> vertices;
    private List<Edge> edges;

    public EdgeListGraph() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    @Override
    public void addVertex(Vertex vertex) {
        if (!containsVertex(vertex)) {
            vertices.add(vertex);
        }
    }

    @Override
    public void addEdge(Vertex v1, Vertex v2, int weight) {
        if (!containsEdge(v1, v2)) {
            edges.add(new Edge(v1, v2, weight));
        }
    }

    @Override
    public boolean containsVertex(Vertex vertex) {
        return vertices.contains(vertex);
    }

    @Override
    public boolean containsEdge(Vertex v1, Vertex v2) {
        return edges.stream().anyMatch(e -> e.vertex1().equals(v1) && e.vertex2().equals(v2) ||
                e.vertex1().equals(v2) && e.vertex2().equals(v1));
    }

    @Override
    public void removeVertex(Vertex vertex) {
        vertices.remove(vertex);
        edges.removeIf(e -> e.vertex1().equals(vertex) || e.vertex2().equals(vertex));
    }

    @Override
    public void removeEdge(Vertex v1, Vertex v2) {
        edges.removeIf(e -> e.vertex1().equals(v1) && e.vertex2().equals(v2) ||
                e.vertex1().equals(v2) && e.vertex2().equals(v1));
    }

    @Override
    public int numberOfVertices() {
        return vertices.size();
    }

    @Override
    public int numberOfEdges() {
        return edges.size();
    }

    @Override
    public List<Edge> getAdjacentEdges(Vertex vertex) {
        return edges.stream()
                .filter(e -> e.vertex1().equals(vertex) || e.vertex2().equals(vertex))
                .collect(Collectors.toList());
    }

    @Override
    public List<Vertex> getAdjacentVertices(Vertex vertex) {
        return edges.stream()
                .filter(e -> e.vertex1().equals(vertex) || e.vertex2().equals(vertex))
                .map(e -> e.vertex1().equals(vertex) ? e.vertex2() : e.vertex1())
                .collect(Collectors.toList());
    }

    @Override
    public double getEdgeWeight(Vertex v1, Vertex v2) {
        return edges.stream()
                .filter(e -> e.vertex1().equals(v1) && e.vertex2().equals(v2) ||
                        e.vertex1().equals(v2) && e.vertex2().equals(v1))
                .findFirst()
                .map(Edge::weight)
                .orElse(Integer.MAX_VALUE); // или выбросить исключение
    }

    @Override
    public void setEdgeWeight(Vertex v1, Vertex v2, int weight) {
        edges.stream()
                .filter(e -> e.vertex1().equals(v1) && e.vertex2().equals(v2) ||
                        e.vertex1().equals(v2) && e.vertex2().equals(v1))
                .findFirst()
                .ifPresent(e -> {
                    edges.remove(e);
                    edges.add(new Edge(v1, v2, weight));
                });
    }

    @Override
    public List<Vertex> getVertices() {
        return new ArrayList<>(vertices);
    }

    @Override
    public List<Edge> getEdges() {
        return new ArrayList<>(edges);
    }

    @Override
    public void printGraph() {
        for (Edge edge : edges) {
            System.out.println(edge);
        }
    }

    @Override
    public Graph clone() {
        EdgeListGraph copy = new EdgeListGraph();
        for (Vertex vertex : this.vertices) {
            copy.addVertex(vertex);
        }
        for (Edge edge : this.edges) {
            copy.addEdge(edge.vertex1(), edge.vertex2(), edge.weight());
        }
        return copy;
    }
}

