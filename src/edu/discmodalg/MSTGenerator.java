package edu.discmodalg;

import java.util.List;

public interface MSTGenerator {
    Graph generateGraph(int numNodes, int maxEdgeWeight, int additionalEdgesCount);
    List<Edge> getKnownMST();
}
