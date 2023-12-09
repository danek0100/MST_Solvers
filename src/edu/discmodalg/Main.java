package edu.discmodalg;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Main {
    public static void main(String[] args) {
        checkAlgorithmsCorrectness();
        performPerformanceTests(10);
        visualizeAlgorithms();
    }

    private static void checkAlgorithmsCorrectness() {
        System.out.println("Checking Algorithms Correctness...");
        int[][] testConfigs = {{100, 10, 50}, {500, 20, 100}, {1000, 30, 500}}; // {nodes, maxEdgeWeight, additionalEdges}
        for (int[] config : testConfigs) {
            KnownMSTGenerator mstGenerator = new KnownMSTGenerator();
            Graph graph = mstGenerator.generateGraph(config[0], config[1], config[2]);
            compareAlgorithms(mstGenerator, graph);
        }
    }

    private static void performPerformanceTests(int iterationNum) {
        System.out.println("Performing Performance Tests...");
        int[][] testConfigs = {{1000, 50, 0}, {1000, 50, 100}, {1000, 50, 500}, {1000, 50, 1500}, {1000, 50, 3000},
                {1000, 50, 5000}, {1000, 50, 10000}, {1000, 50, 50000}, {1000, 50, 100000}, {1000, 50, 500000},
                {1000, 50, 1000000}, {10000, 50, 10000000}, }; // {nodes, maxEdgeWeight, additionalEdges}
        for (int[] config : testConfigs) {
            KnownMSTGenerator mstGenerator = new KnownMSTGenerator();
            Graph graph = mstGenerator.generateGraph(config[0], config[1], config[2]);
            long startTime, endTime, durationPrim, durationKruskal;
            long averageDuration = 0;

            for (int i = 0; i < iterationNum; ++i) {
                startTime = System.nanoTime();
                new PrimMSTSolver().solve(graph);
                endTime = System.nanoTime();
                averageDuration += endTime - startTime;
            }

            durationPrim = (averageDuration / iterationNum) / 1_000_000;

            averageDuration = 0;
            for (int i = 0; i < iterationNum; ++i) {
                startTime = System.nanoTime();
                new KruskalMSTSolver().solve(graph);
                endTime = System.nanoTime();
                averageDuration += endTime - startTime;
            }
            durationKruskal = (averageDuration / iterationNum) / 1_000_000;

            System.out.println("Graph with " + config[0] + " nodes: Prim = " + durationPrim + "ms, Kruskal = " + durationKruskal + "ms");
        }
    }

    private static void visualizeAlgorithms() {
        System.out.println("Visualizing Algorithms...");
        KnownMSTGenerator mstGenerator = new KnownMSTGenerator();
        Graph graph = mstGenerator.generateGraph(5, 10, 5);
        graph.printGraph();
        GraphVisualizer.visualize(graph.getVertices(), graph.getEdges(), "General Graph");

        List<Edge> primMST = new PrimMSTSolver().solve(graph);
        System.out.println("Prim MST: " + primMST);
        GraphVisualizer.visualize(graph.getVertices(), primMST, "Prima MST");

        List<Edge> kruskalMST = new KruskalMSTSolver().solve(graph);
        System.out.println("Kruskal MST: " + kruskalMST);
        GraphVisualizer.visualize(graph.getVertices(), kruskalMST, "KruskalMST");
    }

    private static void compareAlgorithms(KnownMSTGenerator mstGenerator, Graph graph) {
        graph.printGraph();
        List<Edge> knownMST = mstGenerator.getKnownMST();
        System.out.println("Known MST: " + knownMST);

        MSTSolver primSolver = new PrimMSTSolver();
        List<Edge> primMST = primSolver.solve(graph);
        System.out.println("Prim MST: " + primMST);

        MSTSolver kruskalSolver = new KruskalMSTSolver();
        List<Edge> kruskalMST = kruskalSolver.solve(graph);
        System.out.println("Kruskal MST: " + kruskalMST);

        boolean primMatches = compareMSTs(knownMST, primMST);
        boolean kruskalMatches = compareMSTs(knownMST, kruskalMST);

        System.out.println("Prim Correct: " + primMatches + ", Kruskal Correct: " + kruskalMatches);
    }

    private static boolean compareMSTs(List<Edge> mst1, List<Edge> mst2) {
        int sum1 = mst1.stream().flatMapToInt(edge -> IntStream.of(edge.weight())).sum();
        int sum2 = mst2.stream().flatMapToInt(edge -> IntStream.of(edge.weight())).sum();
        return (sum1 == sum2) && (mst1.size() == mst2.size());
    }
}
