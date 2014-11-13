/**
 * 
 */
package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.Pseudograph;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.sun.istack.internal.Nullable;

/**
 * @author KamikazeOnRoad
 *
 */


public class Dijkstra {
	private static int accessesGraph = 0;
	private static int totalWay = 0;
	
	@Nullable
	public static String[] dijkstraSearch(Graph<String, DefaultEdge> graph, String startNode, String endNode) {
		HashMap<String, String> predecessors = dijkstraAlgorithm(graph, startNode, endNode);
		if (predecessors == null) { return null; }
		ArrayList<String> result = new ArrayList<String>();
		
		String node = endNode;
		while (node != null) {
			result.add(0, node);
			node = predecessors.get(node);
		}
		
		//System.out.println(result);
		//System.out.println(result.toArray());
		//return (String[]) result.toArray();
		String resultString = result.toString();
		String [] resultArray = {resultString, (totalWay)+ "", (result.size() - 1)+ "", (accessesGraph)+ ""};
		accessesGraph = 0;
		return resultArray;
	}
	
	@Nullable
	private static HashMap<String, String> dijkstraAlgorithm(Graph<String, DefaultEdge> graph, String startNode, String endNode) {
		Set<String> allNodes = graph.vertexSet();
		accessesGraph++;
		boolean nodesContained = allNodes.contains(startNode) && allNodes.contains(endNode);
		if (!nodesContained) { return null; };

		ArrayList<String> queue = new ArrayList<String>(allNodes);
		HashMap<String, Integer> distance = new HashMap<>();
		HashMap<String, String> predecessor = new HashMap<>();
		
		// init
		for (String node : queue) {
			distance.put(node, Integer.MAX_VALUE);
			predecessor.put(node, null);
		}
		
		distance.put(startNode, 0);
		
		// run
		HashMap<String, Integer> queueDistance = new HashMap<>(distance);		
		while (!queue.isEmpty() || !queueDistance.isEmpty()) {			
			String actualNode = keyForLowestValue(queueDistance);
			queueDistance.remove(actualNode);
			queue.remove(actualNode);
			
			// find neighbours
			Set<DefaultEdge> incidentEdges = graph.edgesOf(actualNode);
			accessesGraph++;
			if (incidentEdges.isEmpty()) { return null; };
			Set<DefaultEdge> relevantEdges = new HashSet<>();
			
			for (DefaultEdge edge : incidentEdges) {
				String neighbour = edgeTargetNode(graph, edge, actualNode);
				if (neighbour != null) {
					relevantEdges.add(edge);
				}
			}
			
			for (DefaultEdge edge : relevantEdges) {
				int weight = (int) graph.getEdgeWeight(edge);
				accessesGraph++;
				String targetNode = edgeTargetNode(graph, edge, actualNode);
				if (queue.contains(targetNode)) {
					int way = distance.get(actualNode) + weight;
					if (way < distance.get(targetNode)) {
						distance.put(targetNode, way);
						queueDistance.put(targetNode, way);
						predecessor.put(targetNode, actualNode);
					}
				}
			}
		}
		totalWay = distance.get(endNode);
		return predecessor; 
	}
	
	private static String edgeTargetNode(Graph<String, DefaultEdge> graph, DefaultEdge edge, String node) {
		String targetNode = graph.getEdgeTarget(edge);
		accessesGraph++;
		String result = null;
		
		if (!node.equals(targetNode)) {
			result = targetNode;
		}
		else {
			if (graph instanceof SimpleWeightedGraph || graph instanceof Pseudograph) {
				result = graph.getEdgeSource(edge);
				accessesGraph++;
			}
		}
		
		return result;
	}
	
	
	private static String keyForLowestValue(HashMap<String, Integer> distance) {
		Iterator<Entry<String, Integer>> iterator = distance.entrySet().iterator();
		if (!iterator.hasNext()) {
			return null;
		}
		
		Entry<String, Integer> first = iterator.next();
		String key = first.getKey();
		Integer value = first.getValue();
		
		for (Entry<String, Integer> entry : distance.entrySet()) {
			if (entry.getValue() < value) {
				key = entry.getKey(); 
				value = entry.getValue();
			}			
		}		
		return key;
	}
}
