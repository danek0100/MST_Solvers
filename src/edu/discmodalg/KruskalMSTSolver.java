package edu.discmodalg;

import java.util.*;

public class KruskalMSTSolver implements MSTSolver {
    public List<Edge> solve(Graph graph) {
        int numberOfVertices = graph.numberOfVertices();
        List<Edge> mst = new ArrayList<>();
        List<Edge> edges = new ArrayList<>(graph.getEdges());
        DisjointSet disjointSet = new DisjointSet(graph.numberOfVertices());


        Collections.sort(edges); // Сортируем рёбра по весу

        for (Edge edge : edges) {
            int root1 = disjointSet.find(edge.vertex1().id());
            int root2 = disjointSet.find(edge.vertex2().id());

            if (root1 != root2) {
                mst.add(edge);
                disjointSet.union(root1, root2);
            }

            if (mst.size() == numberOfVertices - 1)
                break;
        }

        return mst;
    }
}
