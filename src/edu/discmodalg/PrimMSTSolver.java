package edu.discmodalg;

import java.util.*;

public class PrimMSTSolver implements MSTSolver {

    Map<Vertex, VertexWithWeight> vertexToHeapNode = new HashMap<>();

    public List<Edge> solve(Graph graph) {
        int numVertices = graph.numberOfVertices();
        Vertex[] parent = new Vertex[numVertices];
        int[] key = new int[numVertices];
        boolean[] inMST = new boolean[numVertices];
        Arrays.fill(key, Integer.MAX_VALUE);

        MinHeap<VertexWithWeight> minHeap = new MinHeap<>();
        Vertex startVertex = graph.getVertices().stream().sorted(Comparator.comparingInt(Vertex::id)).toList().get(0);
        key[startVertex.id()] = 0;
        minHeap.insert(new VertexWithWeight(startVertex, 0));

        while (!minHeap.isEmpty()) {
            VertexWithWeight u = minHeap.extractMin();
            inMST[u.getVertex().id()] = true;

            for (Edge edge : graph.getAdjacentEdges(u.getVertex()).stream().sorted(Comparator.comparingInt(Edge::weight)).toList()) {
                Vertex v = (edge.vertex1().equals(u.getVertex())) ? edge.vertex2() : edge.vertex1();
                if (!inMST[v.id()] && key[v.id()] > edge.weight()) {
                    key[v.id()] = edge.weight();
                    parent[v.id()] = u.getVertex();

                    VertexWithWeight newVertexWithWeight = new VertexWithWeight(v, key[v.id()]);
                    if (vertexToHeapNode.containsKey(v)) {
                        minHeap.decreaseKey(vertexToHeapNode.get(v), newVertexWithWeight);
                        vertexToHeapNode.put(v, newVertexWithWeight);
                    } else {
                        minHeap.insert(newVertexWithWeight);
                        vertexToHeapNode.put(v, newVertexWithWeight);
                    }
                }
            }
        }

        Map<Integer, Vertex> idToVertexMap = new HashMap<>();
        graph.getVertices().forEach(vertex -> idToVertexMap.put(vertex.id(), vertex));

        List<Edge> mst = new ArrayList<>();
        for (int i = 1; i < numVertices; i++) {
            if (parent[i] != null) {
                Vertex childVertex = idToVertexMap.get(i);
                mst.add(new Edge(parent[i], childVertex, key[i]));
            }
        }

        return mst;
    }
}
