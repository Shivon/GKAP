/**
 * 
 */
package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

/**
 * @author KamikazeOnRoad
 *
 */


public class Dijkstra {
	private static int accessesGraph = 0;

	public static String[] dijkstraSearch(Graph<String, DefaultEdge> graph, String startNode, String endNode) {
		HashMap<String, String> predecessors = dijkstraAlgorithm(graph, startNode, endNode);
		ArrayList<String> result = new ArrayList<String>();
		
		String node = endNode;
		while (node != null) {
			result.add(0, node);
			node = predecessors.get(node);
		}
		
		System.out.println(accessesGraph);
		accessesGraph = 0;
		return (String[]) result.toArray();
	}
	
	
	public static HashMap<String, String> dijkstraAlgorithm(Graph<String, DefaultEdge> graph, String startNode, String endNode) {
		Set<String> allNodes = graph.vertexSet();
		accessesGraph++;
		boolean nodesContained = allNodes.contains(startNode) && allNodes.contains(endNode);
		if (!nodesContained) { return null; };

		ArrayList<String> queue = new ArrayList(allNodes);
		HashMap<String, Integer> distance = new HashMap<>();
		HashMap<String, String> predecessor = new HashMap<>();
		
		// init
		for (String node : queue) {
			distance.put(node, Integer.MAX_VALUE);
			predecessor.put(node, null);
		}
		
		distance.put(startNode, 0);
		
		// run
		while (!queue.isEmpty()) {
			String actualNode = keyForLowestValue(distance);
			queue.remove(actualNode);
						
			// find neighbours
			Set<DefaultEdge> incidentEdges = graph.edgesOf(actualNode);
			accessesGraph++;
			Set<DefaultEdge> relevantEdges = new HashSet<>();
			for (DefaultEdge edge : incidentEdges) {
				String targetNode = graph.getEdgeTarget(edge);
				accessesGraph++;
				if (actualNode != targetNode) {
					relevantEdges.add(edge);
				}
			}
			
			for (DefaultEdge edge : relevantEdges) {
				int weight = (int) graph.getEdgeWeight(edge);
				accessesGraph++;
				String targetNode = graph.getEdgeTarget(edge);
				accessesGraph++;
				if (queue.contains(targetNode)) {
					int way = distance.get(actualNode) + weight;
					if (way < distance.get(targetNode)) {
						distance.put(targetNode, way);
						predecessor.put(targetNode, actualNode);
					}
				}
			}
		}
		
		return predecessor; 
	}
	
	
	private static String keyForLowestValue(HashMap<String, Integer> distance) {
		String key = null;
		Integer value = Integer.MAX_VALUE;
		
		for (Entry<String, Integer> entry : distance.entrySet()) {
			if (entry.getValue() < value) { 
				key = entry.getKey(); 
				value = entry.getValue();
			}			
		}
		
		return key;
	}
}
