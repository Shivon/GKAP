package main;

import java.util.ArrayList;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

public class FloydWarshall {
	private static int accessesGraph = 0;
	private final double[][] abstandsMatrix;
	private final int[][] pfad;
	private final String[] knotenArray;
	private final Graph<String, DefaultEdge> graph;
	private ArrayList<String> result;

	public FloydWarshall(Graph<String, DefaultEdge> graph) {
		this.graph = graph;
		accessesGraph++;
		// toArray gibt ObjectSet zurück
		Object[] tempAry = graph.vertexSet().toArray();
		accessesGraph++;
		knotenArray = new String[tempAry.length];
		for (int i = 0; i < tempAry.length; i++) {
			knotenArray[i] = tempAry[i].toString();
		}
		int vertexAnzahl = knotenArray.length;

		abstandsMatrix = new double[vertexAnzahl][vertexAnzahl];
		pfad = new int[vertexAnzahl][vertexAnzahl];

		initialize();
	}

	private void initialize() {
		// initialisierung der Adjazenzmatrix
		for (int i = 0; i < abstandsMatrix.length; i++) {
			for (int j = 0; j < abstandsMatrix.length; j++) {
				// if(i==j){
				// abstandsMatrix[i][j] = 0;
				// }else{
				// abstandsMatrix[i][j] = Double.POSITIVE_INFINITY;
				// }
				pfad[i][j] = Integer.MAX_VALUE;
			}
		}

		// initialisierung der Abstandsmatrix
		for (int i = 0; i < abstandsMatrix.length; i++) {
			for (int j = 0; j < abstandsMatrix.length; j++) {
				if (i == j) {
					abstandsMatrix[i][j] = 0;
				} else {
					DefaultEdge edge = graph.getEdge(knotenArray[i],
							knotenArray[j]);
					accessesGraph++;
					if (edge == null) {
						abstandsMatrix[i][j] = Double.POSITIVE_INFINITY;
					} else {
						abstandsMatrix[i][j] = graph.getEdgeWeight(edge);
						accessesGraph++;
					}
				}
			}
		}
		
		// Algorithmus
		for (int k = 0; k < knotenArray.length; k++) {
			for (int i = 0; i < knotenArray.length; i++) {
				for (int j = 0; j < knotenArray.length; j++) {
					if (abstandsMatrix[i][k] + abstandsMatrix[k][j] < abstandsMatrix[i][j]) {
						abstandsMatrix[i][j] = abstandsMatrix[i][k] + abstandsMatrix[k][j];
						pfad[i][j] = k;
					}
				}
			}
		}
	}
	
	// Gibt die Distanz zurück
	public String[] floydWarshallSearch(String start, String ziel) {
		result = new ArrayList<String>();
		int startKnotenIndex = -1;
		int zielKnotenindex = -1;
		for (int i = 0; i < knotenArray.length; i++) {
			if (start.equals(knotenArray[i])) {
				startKnotenIndex = i;
			}
			if (ziel.equals(knotenArray[i])) {
				zielKnotenindex = i;
			}
		}
		if ((startKnotenIndex == -1) || (zielKnotenindex == -1)) {
			return null;
		}
		
		double distance =  abstandsMatrix[startKnotenIndex][zielKnotenindex];
		calcPath(startKnotenIndex,zielKnotenindex);
		
		if(result.size() == 0){
			return null;
		}
		String [] resultArray = { result.toString() , (int) (distance) + "", (result.size()-1) + "", (accessesGraph)+ ""};
		
		return resultArray;
	}
	
	private void calcPath(int startIndex, int zielIndex) {
		if (!(abstandsMatrix[startIndex][zielIndex] == Double.POSITIVE_INFINITY)) {
			path(startIndex, zielIndex);
		}else{
			 // Es gibt keinen Weg von startIndex nach zielIndex
		}
	}

	private void path(int startIndex, int zielIndex){
		result.add(knotenArray[startIndex]);
		int intermediate = pfad[startIndex][zielIndex];
		if (intermediate == Integer.MAX_VALUE) { // startIndex -> zielIndex ist der kürzeste Weg
			result.add(knotenArray[zielIndex]);
		}else{
			path(intermediate,zielIndex);
		}
	}
	
}
