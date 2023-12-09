package edu.discmodalg;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class AdjacencyListGraph implements Graph {
    private final Map<Vertex, Set<Edge>> adjacencyList;

    public AdjacencyListGraph() {
        this.adjacencyList = new HashMap<>();
    }

    @Override
    public void addVertex(Vertex vertex) {
        adjacencyList.putIfAbsent(vertex, new HashSet<>());
    }

    @Override
    public void addEdge(Vertex v1, Vertex v2, int weight) {
        adjacencyList.get(v1).add(new Edge(v1, v2, weight));
        // Для неориентированного графа, добавляем ребро и в обратном направлении
        adjacencyList.get(v2).add(new Edge(v2, v1, weight));
    }

    @Override
    public boolean containsVertex(Vertex vertex) {
        return adjacencyList.containsKey(vertex);
    }

    @Override
    public boolean containsEdge(Vertex v1, Vertex v2) {
        return adjacencyList.get(v1).stream().anyMatch(e -> e.vertex2().equals(v2));
    }

    @Override
    public void removeVertex(Vertex vertex) {
        adjacencyList.values().forEach(edges -> edges.removeIf(e -> e.vertex2().equals(vertex)));
        adjacencyList.remove(vertex);
    }

    @Override
    public void removeEdge(Vertex v1, Vertex v2) {
        adjacencyList.get(v1).removeIf(e -> e.vertex2().equals(v2));
        adjacencyList.get(v2).removeIf(e -> e.vertex1().equals(v1)); // Для неориентированного графа
    }

    @Override
    public int numberOfVertices() {
        return adjacencyList.size();
    }

    @Override
    public int numberOfEdges() {
        return adjacencyList.values().stream().mapToInt(Set::size).sum() / 2; // Для неориентированного графа
    }

    @Override
    public List<Edge> getAdjacentEdges(Vertex vertex) {
        return new ArrayList<>(adjacencyList.get(vertex));
    }

    @Override
    public List<Vertex> getAdjacentVertices(Vertex vertex) {
        List<Vertex> adjacentVertices = new ArrayList<>();
        for (Edge edge : adjacencyList.get(vertex)) {
            adjacentVertices.add(edge.vertex2());
        }
        return adjacentVertices;
    }

    @Override
    public double getEdgeWeight(Vertex v1, Vertex v2) {
        return adjacencyList.get(v1).stream()
                .filter(e -> e.vertex2().equals(v2))
                .findFirst()
                .map(Edge::weight)
                .orElse(Integer.MAX_VALUE);
    }

    @Override
    public void setEdgeWeight(Vertex v1, Vertex v2, int weight) {
        adjacencyList.get(v1).stream()
                .filter(e -> e.vertex2().equals(v2))
                .findFirst()
                .ifPresent(e -> {
                    adjacencyList.get(v1).remove(e);
                    adjacencyList.get(v1).add(new Edge(v1, v2, weight));
                });
    }

    @Override
    public List<Vertex> getVertices() {
        return new ArrayList<>(adjacencyList.keySet());
    }

    @Override
    public List<Edge> getEdges() {
        List<Edge> allEdges = new ArrayList<>();
        adjacencyList.values().forEach(allEdges::addAll);
        return allEdges;
    }

    @Override
    public void printGraph() {
        for (Vertex vertex : adjacencyList.keySet()) {
            System.out.print("Vertex " + vertex.name() + " is connected to: ");
            for (Edge edge : adjacencyList.get(vertex)) {
                System.out.print(edge.vertex2().name() + " (" + edge.weight() + "), ");
            }
            System.out.println();
        }
    }

    @Override
    public Graph clone() {
        AdjacencyListGraph clone = new AdjacencyListGraph();
        for (Vertex vertex : adjacencyList.keySet()) {
            clone.addVertex(vertex);
            for (Edge edge : adjacencyList.get(vertex)) {
                clone.addEdge(edge.vertex1(), edge.vertex2(), edge.weight());
            }
        }
        return clone;
    }
}

