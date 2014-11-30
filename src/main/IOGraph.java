package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.Pseudograph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import java.text.ParseException;
import java.util.HashSet;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class IOGraph {

	public static Graph<String, DefaultEdge> openGraph() throws ParseException {
		File file = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("."));
		fileChooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(".gka")
						|| f.isDirectory();
			}

			public String getDescription() {
				return "GKA-Graph(*.gka)";
			}
		});
		if (fileChooser.showOpenDialog(null) == 0)
			file = fileChooser.getSelectedFile();
		else
			return null;
		return openGraph(file);
	}

	public static Graph<String, DefaultEdge> openGraph(File file)
			throws ParseException {
		boolean weighted = false;
		Graph<String, DefaultEdge> graph = null;
		String v1, v2;
		Double w;

		try {
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			// Read File Line By Line to check which type of graph is used
			// letzte Edge gibt vor ob gerichtet oder ungerichtet
			while (((strLine = br.readLine()) != null)) {
				if (strLine.contains("--")) {
					graph = new Pseudograph<String, DefaultEdge>(
							DefaultEdge.class);
				} else if (strLine.contains("->")) {
					graph = new DefaultDirectedGraph<String, DefaultEdge>(
							DefaultEdge.class);
				} 
				if (strLine.contains(":")){
					weighted = true;
				}
			}
			
			if (graph == null){
				br.close();
				throw new ParseException("Graph konnte nicht gelesen werden", 0);
			}
			
			if(graph instanceof DefaultDirectedGraph){
				if(weighted){
					graph = new DefaultDirectedWeightedGraph<String, DefaultEdge>(DefaultWeightedEdge.class);
				}
			}else if(graph instanceof Pseudograph){
				if(weighted){
					graph = new SimpleWeightedGraph<String, DefaultEdge>(DefaultWeightedEdge.class);
				}
			}

			System.out.println(graph.getClass());

			fstream = new FileInputStream(file);

			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				strLine = strLine.replace(";", " ").trim();

				if (strLine.contains("--")) {
					String[] vString = strLine.split("--");			
					v1 = vString[0].trim();
					if (vString[1].contains(":")) {
						String[] string = vString[1].split(":");
						v2 = string[0].trim();
						w = Double.parseDouble(string[1].trim());
						// Adds the specified source and target vertices to the
						// graph, if not already included,
						// and creates a new weighted edge and adds it to the
						// specified graph similarly to the
						// Graph.addEdge(Object, Object) method.
						Graphs.addEdgeWithVertices(graph, v1, v2, w);
					} else {
						String[] string = vString[1].split(":");
						v2 = string[0].trim();
						Graphs.addEdgeWithVertices(graph, v1, v2);
					}
				} else if (strLine.contains("->")) {
					String[] vString = strLine.split("->");
					v1 = vString[0].trim();
					if (vString[1].contains(":")) {
						String[] string = vString[1].split(":");				
						v2 = string[0].trim();
						w = Double.parseDouble(string[1].trim());
						// Adds the specified source and target vertices to the
						// graph, if not already included,
						// and creates a new weighted edge and adds it to the
						// specified graph similarly to the
						// Graph.addEdge(Object, Object) method.
						Graphs.addEdgeWithVertices(graph, v1, v2, w);
					} else {
						String[] string = vString[1].split(":");
						v2 = string[0].trim();
						Graphs.addEdgeWithVertices(graph, v1, v2);
					}
				} else {
					String[] vString = strLine.split(" ");
					v1 = vString[0].trim();
					if (!v1.isEmpty()) {
					graph.addVertex(v1);
					}
				}

			}

			in.close();			
			return graph;
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return null;
	}
	
	public static void saveGraph(Graph<String, DefaultEdge> graph) {
		File file = null;
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showSaveDialog(null) == 0)
			file = fileChooser.getSelectedFile();
		else
			return;

		saveGraph(graph, file);
	}
	
	public static void saveGraph(Graph<String, DefaultEdge> graph, File file) {
		try {
			// Create file
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			String dir;
			
			if (graph instanceof DefaultDirectedGraph) {
				dir = "->";
			} else {
				dir = "--";
			}
			HashSet<String> touchedVertices = new HashSet<>();
			for (DefaultEdge edge : graph.edgeSet()) {
				String[] edgeString = edge.toString().split(":");
				String v1 = edgeString[0].replace("(", " ").trim();
				String v2 = edgeString[1].replace(")", " ").trim();
				out.write(v1 + " " + dir + " " + v2 + " : " + graph.getEdgeWeight(edge) + ";"
						+ System.getProperty("line.separator"));
				touchedVertices.add(v1);
				touchedVertices.add(v2);
			}
			// einzelne Knoten
			for (String vertex : graph.vertexSet()) {
				if (!touchedVertices.contains(vertex))
					out.write(vertex + ";"
							+ System.getProperty("line.separator"));
			}

			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	
	
	
}
