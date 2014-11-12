/**
 * 
 */
package main;

import java.util.*;

import main.Matrix;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.Pseudograph;
import org.jgrapht.graph.SimpleWeightedGraph;

/**
 * @author KamikazeOnRoad
 *
 */


public class Dijkstra {

	// TODO: woher bekomme ich Matrix etc?	
	public static String[] dijkstraSearch(Matrix matrix, String startNode, String endNode) {
		boolean containsStartNode = matrix.containsStartNode(startNode);
		boolean startNodeHasEndNode = matrix.startNodeHasEndNode(startNode, endNode);
		
		
		if (!containsStartNode) { return null; };
		if (containsStartNode && !startNodeHasEndNode) { return null; }
		
		String actualSource = startNode;
		String actualPredecessor = startNode;
		int actualDistance = 0;
		Set<String> endNodesActualSource = matrix.getEndNodes(actualSource); 
		
		HashMap<String, Integer> shortestWay = new HashMap<>();
		List<HashMap<String, Integer>> endNodesWithWeight = matrix.getEndNodesAndWeights(startNode);
		
		
		
		for (String actualEndNode : endNodesActualSource) {
			
			
		}
		
		return null; //gleich wegmachen
	}

	
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
