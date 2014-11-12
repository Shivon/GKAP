package main;

import java.awt.Dimension;

import javax.swing.JFrame;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class GraphVisualisation {
	int edgeCount = 0;

	public GraphVisualisation(Graph<String, DefaultEdge> graph) {
		boolean directed = false;
		if (graph instanceof DefaultDirectedGraph) {
			directed = true; // EdgeType später setzen
		}
		edu.uci.ics.jung.graph.Graph<String, String> tempGraph = new SparseMultigraph<String, String>();
		for (String vertex : graph.vertexSet()) {
			tempGraph.addVertex(vertex);
		}
		Integer i = 0;
		for (DefaultEdge edge : graph.edgeSet()) {
			String src = (String) graph.getEdgeSource(edge);
			String target = (String) graph.getEdgeTarget(edge);
			tempGraph.addEdge(new MyLink(graph.getEdgeWeight(edge)).toString(), src, target,
					(directed) ? EdgeType.DIRECTED : EdgeType.UNDIRECTED);
		}

		// The Layout<V, E> is parameterized by the vertex and edge types
		Layout<Integer, String> layout = new FRLayout(tempGraph);
		layout.setSize(new Dimension(600, 600)); // sets the initial size of the
													// space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		BasicVisualizationServer<Integer, String> vv = new BasicVisualizationServer<Integer, String>(
				layout);
		vv.setPreferredSize(new Dimension(350, 350)); // Sets the viewing area
														// size
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		JFrame frame = new JFrame("Simple Graph View");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(vv);
		frame.setSize(new Dimension(800, 800));
		frame.setPreferredSize(new Dimension(800, 800));
		// frame.pack();
		frame.setVisible(true);

	}

	class MyLink {
		double capacity; // should be private
		double weight; // should be private for good practice
		int id;

		public MyLink(double weight) {
			this.id = edgeCount++; // This is defined in the outer class.
			this.weight = weight;
		}

		public String toString() { // Always good for debugging
			return "E" + id + "=" + weight;
		}
	}
}
