package edu.discmodalg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class KnownMSTGenerator implements MSTGenerator {
    private List<Edge> knownMST;

    @Override
    public Graph generateGraph(int numNodes, int maxEdgeWeight, int additionalEdgesCount) {
        Random random = new Random();
        List<Vertex> nodes = new ArrayList<>();
        for (int i = 0; i < numNodes; i++) {
            nodes.add(new Vertex(i, "Node " + i));
        }

        Collections.shuffle(nodes);

        knownMST = new ArrayList<>();
        Graph graph = new AdjacencyListGraph(); // Или другая реализация Graph
        nodes.forEach(graph::addVertex);

        for (int i = 0; i < numNodes - 1; i++) {
            int weight = 1 + random.nextInt(maxEdgeWeight);
            graph.addEdge(nodes.get(i), nodes.get(i + 1), weight);
            knownMST.add(new Edge(nodes.get(i), nodes.get(i + 1), weight));
        }

        int maxMSTWeight = knownMST.stream().mapToInt(Edge::weight).max().orElse(0);
        Set<Pair<Vertex, Vertex>> existingEdges = knownMST.stream()
                .map(e -> new Pair<>(e.vertex1(), e.vertex2())).collect(Collectors.toSet());

        int maxAdditionalEdges = (numNodes * (numNodes - 1)) / 2 - (numNodes - 1);
        additionalEdgesCount = Math.min(additionalEdgesCount, maxAdditionalEdges);

        while (additionalEdgesCount > 0) {
            Vertex node1 = nodes.get(random.nextInt(numNodes));
            Vertex node2 = nodes.get(random.nextInt(numNodes));
            Pair<Vertex, Vertex> edgePair = new Pair<>(node1, node2);
            Pair<Vertex, Vertex> reverseEdgePair = new Pair<>(node2, node1);

            if (!node1.equals(node2) && !existingEdges.contains(edgePair) && !existingEdges.contains(reverseEdgePair)) {
                int weight = maxMSTWeight + 1 + random.nextInt(maxEdgeWeight);
                graph.addEdge(node1, node2, weight);
                existingEdges.add(edgePair);
                existingEdges.add(reverseEdgePair);
                additionalEdgesCount--;
            }
        }

        return graph;
    }

    @Override
    public List<Edge> getKnownMST() {
        return knownMST;
    }

    private record Pair<T, U>(T first, U second) {}
}
