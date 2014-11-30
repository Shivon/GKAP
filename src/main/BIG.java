package main;

import java.util.ArrayList;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;


//Es werden soviele Verticies erzeugt wie der angegebene Parameter vorgibt und erzeug dann
//die Anzahl an vorgegebenen Edges wo mit Random zwischen zwei Random Verticies eine Edge erzeugt wird
public class BIG {

	private static final ArrayList<String> randomizer = new ArrayList<>();

	public static Graph<String, DefaultEdge> generateBigOne(int numVertex, int numEdge) {
		
		Graph<String, DefaultEdge> result = new DefaultDirectedWeightedGraph<String, DefaultEdge>(
				DefaultWeightedEdge.class);

		for (Integer i = 0; i < numVertex; i++) {
			String vertex = "E" + i.toString();
			result.addVertex(vertex);
			randomizer.add(vertex);
		}

		while (result.edgeSet().size() < numEdge) {
			for (int i = result.edgeSet().size(); i < numEdge; i++) {
				Graphs.addEdge(result, getRandomVertex(), getRandomVertex(),
						getRandomNumber(1, 100));
			}
		}

		System.out.println("--> Generiert");

		return result;
	}

	private static String getRandomVertex() {
		return randomizer.get(getRandomNumber(0, (randomizer.size() - 1)));
	}

	private static int getRandomNumber(int low, int high) {
		return (int) (Math.random() * (high + low));
	}
	
//	public static void main(String[] args) {
//		Graph<String,DefaultEdge> graph;
//		graph = generateBigOne(100,6000);
//		new GraphVisualisation(graph);
//	}
}
