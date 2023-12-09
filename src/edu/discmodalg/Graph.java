package edu.discmodalg;

import java.util.List;

public interface Graph {
    void addVertex(Vertex vertex);
    void addEdge(Vertex v1, Vertex v2, int weight);
    boolean containsVertex(Vertex vertex);
    boolean containsEdge(Vertex v1, Vertex v2);
    void removeVertex(Vertex vertex);
    void removeEdge(Vertex v1, Vertex v2);
    int numberOfVertices();
    int numberOfEdges();
    List<Edge> getAdjacentEdges(Vertex vertex);
    List<Vertex> getAdjacentVertices(Vertex vertex);
    double getEdgeWeight(Vertex v1, Vertex v2);
    void setEdgeWeight(Vertex v1, Vertex v2, int weight);
    List<Vertex> getVertices();
    List<Edge> getEdges();
    void printGraph();
    Graph clone();
}
