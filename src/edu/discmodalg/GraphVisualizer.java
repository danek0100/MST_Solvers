package edu.discmodalg;

import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxStylesheet;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.ext.JGraphXAdapter;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.graph.SimpleWeightedGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.Map;

public class GraphVisualizer {

    public static void visualize(List<Vertex> vertices, List<Edge> edges, String title) {
        // Используем SimpleWeightedGraph для графа с весом рёбер
        Graph<String, DefaultWeightedEdge> jGraphTGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        for (Vertex v : vertices) {
            jGraphTGraph.addVertex(v.name());
        }

        // Добавление рёбер с весом
        for (Edge e : edges) {
            if (!jGraphTGraph.containsVertex(e.vertex1().name())) {
                jGraphTGraph.addVertex(e.vertex1().name());
            }
            if (!jGraphTGraph.containsVertex(e.vertex2().name())) {
                jGraphTGraph.addVertex(e.vertex2().name());
            }
            if (!e.vertex1().name().equals(e.vertex2().name())) {
                DefaultWeightedEdge edge = jGraphTGraph.addEdge(e.vertex1().name(), e.vertex2().name());
                if (edge != null) {
                    jGraphTGraph.setEdgeWeight(edge, e.weight());
                }
            }
        }

        JGraphXAdapter<String, DefaultWeightedEdge> graphAdapter = new JGraphXAdapter<>(jGraphTGraph);

        // Настройка отображения веса рёбер
        for (DefaultWeightedEdge e : jGraphTGraph.edgeSet()) {
            String weightLabel = String.valueOf(jGraphTGraph.getEdgeWeight(e));
            graphAdapter.getEdgeToCellMap().get(e).setValue(weightLabel);
        }

        mxGraphComponent graphComponent = new mxGraphComponent(graphAdapter);
        mxCircleLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        graphComponent.getGraph().getView().setScale(2);

        JFrame frame = new JFrame();
        frame.getContentPane().add(graphComponent);
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 600));
        graphComponent.setPreferredSize(new Dimension(800, 600));
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                graphComponent.zoomAndCenter();
            }
        });
        frame.setVisible(true);
    }
}
