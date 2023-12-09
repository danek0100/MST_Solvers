package edu.discmodalg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyMatrixGraph implements Graph {
    private int[][] adjacencyMatrix;
    private final Map<Vertex, Integer> vertexIndices;
    private final List<Vertex> vertices;

    public AdjacencyMatrixGraph(int numVertices) {
        this.adjacencyMatrix = new int[numVertices][numVertices];
        this.vertexIndices = new HashMap<>();
        this.vertices = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (i == j) {
                    adjacencyMatrix[i][j] = 0;
                } else {
                    adjacencyMatrix[i][j] = Integer.MAX_VALUE;
                }
            }
        }
    }

    @Override
    public void addVertex(Vertex vertex) {
        if (!containsVertex(vertex)) {
            vertexIndices.put(vertex, vertices.size());
            vertices.add(vertex);
        }
    }

    @Override
    public void addEdge(Vertex v1, Vertex v2, int weight) {
        if (containsVertex(v1) && containsVertex(v2)) {
            int i = vertexIndices.get(v1);
            int j = vertexIndices.get(v2);
            adjacencyMatrix[i][j] = weight;
            adjacencyMatrix[j][i] = weight; // Для неориентированного графа
        }
    }

    @Override
    public boolean containsVertex(Vertex vertex) {
        return vertexIndices.containsKey(vertex);
    }

    @Override
    public boolean containsEdge(Vertex v1, Vertex v2) {
        if (containsVertex(v1) && containsVertex(v2)) {
            int i = vertexIndices.get(v1);
            int j = vertexIndices.get(v2);
            // Проверяем, что вес ребра не равен 0 или Double.POSITIVE_INFINITY (по умолчанию для несвязанных вершин)
            return adjacencyMatrix[i][j] != 0 && adjacencyMatrix[i][j] != Double.POSITIVE_INFINITY;
        }
        return false;
    }


    @Override
    public void removeVertex(Vertex vertex) {
        if (!containsVertex(vertex)) {
            return; // Вершина не существует в графе
        }

        int indexToRemove = vertexIndices.get(vertex);
        int newSize = vertices.size() - 1;

        int[][] newMatrix = new int[newSize][newSize];
        for (int i = 0, j = 0; i < adjacencyMatrix.length; i++) {
            if (i == indexToRemove) continue; // Пропускаем удаляемую вершину

            for (int k = 0, u = 0; k < adjacencyMatrix[i].length; k++) {
                if (k == indexToRemove) continue; // Пропускаем удаляемую вершину

                newMatrix[j][u] = adjacencyMatrix[i][k];
                u++;
            }
            j++;
        }

        adjacencyMatrix = newMatrix;
        vertices.remove(vertex);
        vertexIndices.remove(vertex);

        // Обновляем индексы оставшихся вершин
        for (int i = 0; i < vertices.size(); i++) {
            vertexIndices.put(vertices.get(i), i);
        }
    }

    @Override
    public void removeEdge(Vertex v1, Vertex v2) {
        if (containsVertex(v1) && containsVertex(v2)) {
            int i = vertexIndices.get(v1);
            int j = vertexIndices.get(v2);
            adjacencyMatrix[i][j] = Integer.MAX_VALUE;
            adjacencyMatrix[j][i] = Integer.MAX_VALUE; // Для неориентированного графа
        }
    }

    @Override
    public int numberOfVertices() {
        return vertices.size();
    }

    @Override
    public int numberOfEdges() {
        int edgeCount = 0;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = i + 1; j < adjacencyMatrix[i].length; j++) {
                if (adjacencyMatrix[i][j] != 0 && adjacencyMatrix[i][j] != Double.POSITIVE_INFINITY) {
                    edgeCount++;
                }
            }
        }
        return edgeCount;
    }

    @Override
    public List<Edge> getAdjacentEdges(Vertex vertex) {
        List<Edge> adjacentEdges = new ArrayList<>();
        if (containsVertex(vertex)) {
            int vertexIndex = vertexIndices.get(vertex);
            for (int i = 0; i < adjacencyMatrix[vertexIndex].length; i++) {
                if (adjacencyMatrix[vertexIndex][i] != 0 && adjacencyMatrix[vertexIndex][i] != Double.POSITIVE_INFINITY) {
                    adjacentEdges.add(new Edge(vertex, vertices.get(i), adjacencyMatrix[vertexIndex][i]));
                }
            }
        }
        return adjacentEdges;
    }

    @Override
    public List<Vertex> getAdjacentVertices(Vertex vertex) {
        List<Vertex> adjacentVertices = new ArrayList<>();
        if (containsVertex(vertex)) {
            int vertexIndex = vertexIndices.get(vertex);
            for (int i = 0; i < adjacencyMatrix[vertexIndex].length; i++) {
                if (adjacencyMatrix[vertexIndex][i] != 0 && adjacencyMatrix[vertexIndex][i] != Double.POSITIVE_INFINITY) {
                    adjacentVertices.add(vertices.get(i));
                }
            }
        }
        return adjacentVertices;
    }

    @Override
    public double getEdgeWeight(Vertex v1, Vertex v2) {
        if (containsVertex(v1) && containsVertex(v2)) {
            int i = vertexIndices.get(v1);
            int j = vertexIndices.get(v2);
            return adjacencyMatrix[i][j];
        }
        return Double.POSITIVE_INFINITY; // Или другое значение, обозначающее отсутствие ребра
    }

    @Override
    public void setEdgeWeight(Vertex v1, Vertex v2, int weight) {
        if (containsVertex(v1) && containsVertex(v2)) {
            int i = vertexIndices.get(v1);
            int j = vertexIndices.get(v2);
            adjacencyMatrix[i][j] = weight;
            adjacencyMatrix[j][i] = weight; // Для неориентированного графа
        }
    }

    @Override
    public List<Vertex> getVertices() {
        return new ArrayList<>(vertices);
    }

    @Override
    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = i + 1; j < vertices.size(); j++) {
                if (adjacencyMatrix[i][j] != Integer.MAX_VALUE) {
                    edges.add(new Edge(vertices.get(i), vertices.get(j), adjacencyMatrix[i][j]));
                }
            }
        }
        return edges;
    }

    @Override
    public void printGraph() {
        System.out.println("Adjacency Matrix:");
        for (int[] matrix : adjacencyMatrix) {
            for (int weight : matrix) {
                String weightStr = (weight == Integer.MAX_VALUE) ? "∞" : String.format("%d", weight);
                System.out.print(weightStr + " ");
            }
            System.out.println(); // Новая строка для следующей вершины
        }
    }


    @Override
    public Graph clone() {
        AdjacencyMatrixGraph clonedGraph = new AdjacencyMatrixGraph(vertices.size());
        for (Vertex vertex : vertices) {
            clonedGraph.addVertex(new Vertex(vertex.id(), vertex.name()));
        }
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            System.arraycopy(this.adjacencyMatrix[i], 0, clonedGraph.adjacencyMatrix[i], 0, adjacencyMatrix[i].length);
        }
        clonedGraph.vertexIndices.putAll(this.vertexIndices);
        return clonedGraph;
    }

}
