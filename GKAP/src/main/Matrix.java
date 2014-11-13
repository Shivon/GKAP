/**
 * 
 */
package main;

import java.util.*;

import org.jgrapht.Graph;
import org.jgraph.graph.*; 

/**
 * @author KamikazeOnRoad
 *
 */
public class Matrix {
	
//	HashMap<String, Integer> endNodesAndWeight = new HashMap<>();
//	endNodesAndWeight.put("v1", 1);
//	endNodesAndWeight.put("v2", 2);
//	endNodesAndWeight.put("v3", 3);
//	System.out.println(endNodesAndWeight);
//	HashMap<String, HashMap<String, Integer>> startNodesWEdges = new HashMap<>();
//	startNodesWEdges.put("v8", endNodesAndWeight);
//	System.out.println(startNodesWEdges);
//	startNodesWEdges.put("v9", endNodesAndWeight);
//	System.out.println(startNodesWEdges);
	
	private HashMap<String, HashMap<String, Integer>> matrix;
	
	
	public Matrix() {
		matrix = new HashMap<>();
	}
	
	
	public Matrix(Graph<String,DefaultEdge> graph) {
		matrix = new HashMap<>();
		Set<String> vertexSet = graph.vertexSet();
		
		for (String vertex : vertexSet) {
			Set<DefaultEdge> edgeSet = graph.edgesOf(vertex);
			
			for (DefaultEdge dE : edgeSet) {
				setEdge(dE.getSource().toString(), dE.getTarget().toString(), (int) graph.getEdgeWeight(dE)); 
			}
		}
	}
	
	
	/**
	 * Returns HashMap with all StartNodes, EndNodes and Weights
	 * @author KamikazeOnRoad
	 */
	public HashMap<String, HashMap<String, Integer>> getMatrix() {
		return matrix;
	}
	
	
	/**  
	 * Returns set of all StartNodes in matrix
	 * @author KamikazeOnRoad
	 */
	public Set<String> getAllStartNodes() {
		return this.getMatrix().keySet();
	}
	
	
	/**  
	 * Returns Hashmap with all EndNodes and Weight for specific StartNode.
	 * @author KamikazeOnRoad
	 */
	public HashMap<String, Integer> getEndNodesAndWeights(String startNode) {
		return this.getMatrix().get(startNode);
	}
	
	
	/**  
	 * Returns set of EndNodes for specific StartNode without Weight
	 * @author KamikazeOnRoad
	 */
	public Set<String> getEndNodes(String startNode) {
		HashMap<String, Integer> endNodesAndWeight = this.getEndNodesAndWeights(startNode);
		
		return endNodesAndWeight.keySet();
	}
	
	
	/**  
	 * Returns set of all EndNodes in matrix
	 * @author KamikazeOnRoad
	 */
	public Set<String> getAllEndNodes() {
		Set<String> startNodes = this.getAllStartNodes();
		Set<String> allEndNodes = new HashSet<>();
		
		for (String startNode : startNodes) {
			allEndNodes.addAll(this.getEndNodes(startNode));			
		}
		
		return allEndNodes;
	}	
	
	
	/**  
	 * Returns the weight as int. 
	 * Returns null when startNode is not included in matrix. 
	 * Returns MAX_VALUE when there is no edge.
	 * @author KamikazeOnRoad
	 */
	public int getWeight(String startNode, String endNode) {
		HashMap<String, Integer> rowStartNode = this.getEndNodesAndWeights(startNode);
		if (rowStartNode == null) { return (Integer) null; }
		
		Integer weight = rowStartNode.get(endNode);
		if (weight != null) { return weight; };
		
		return Integer.MAX_VALUE;
	}

	
	public void setMatrix(HashMap<String, HashMap<String, Integer>> matrix) {
		this.matrix = matrix;
	}


	// Attention regarding overwriting...
	public void setRow(String startNode, HashMap<String, Integer> endNodesAndWeights) {
		this.getMatrix().put(startNode, endNodesAndWeights);
	}
	

	/**
	 * Adds edge to matrix if specific edge doesn't exist already. 
	 * If it exists, does not change the matrix
	 * @author KamikazeOnRoad
	 */
	public void setEdge(String startNode, String endNode, int weight) {
		boolean startNodeAlreadyInMatrix = this.containsStartNode(startNode);
		boolean startNodeHasEndNode = this.startNodeHasEndNode(startNode, endNode);
		
		if (startNodeAlreadyInMatrix && !startNodeHasEndNode) {
			this.getEndNodesAndWeights(startNode).put(endNode, weight);			
		} else if (!startNodeAlreadyInMatrix) {
			HashMap<String, Integer> newEdge = new HashMap<>();
			newEdge.put(endNode, weight);
			this.setRow(startNode, newEdge);
		} 		
	}

	
	
	public boolean containsStartNode(String startNode) {
		return this.getAllStartNodes().contains(startNode);
	}
	
	
	/**
	 * Returns bool if specific EndNode is somewhere in matrix
	 * @author KamikazeOnRoad
	 */	
	public boolean containsEndNode(String endNode) {
		Set<String> startNodes = this.getAllStartNodes();
		
		for (String startNode : startNodes) {
			if (this.startNodeHasEndNode(startNode, endNode)) {
				return true;
			}
		}
		
		return false;
	}
	
	
	/**
	 * Returns bool if specific StartNode already has specific EndNode
	 * @author KamikazeOnRoad
	 */		
	public boolean startNodeHasEndNode(String startNode, String endNode) {
		if (this.containsStartNode(startNode)) {
			return this.getEndNodes(startNode).contains(endNode);
		}
		
		return false;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((matrix == null) ? 0 : matrix.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Matrix)) {
			return false;
		}
		Matrix other = (Matrix) obj;
		if (matrix == null) {
			if (other.matrix != null) {
				return false;
			}
		} else if (!matrix.equals(other.matrix)) {
			return false;
		}
		return true;
	}


	
	

	public static void main(String[] args) {
		// example graph from page 107 of vl03
		// generate matrix
		Matrix matrix1 = new Matrix();
		
		// generate row for startNode v2
		HashMap<String, Integer> rowV2 = new HashMap<>();
		rowV2.put("v1", 1);
		rowV2.put("v3", 5);
		rowV2.put("v5", 3);
		rowV2.put("v6", 2);
		matrix1.setRow("v2", rowV2);

		
		// generate row for startNode v4
		HashMap<String, Integer> rowV4 = new HashMap<>();
		rowV4.put("v3", 1);
		rowV4.put("v5", 3);
		matrix1.setRow("v4", rowV4);
		
		System.out.println(rowV2);
		System.out.println(matrix1.getMatrix());
		System.out.println(matrix1.getEndNodesAndWeights("v1"));
		System.out.println(matrix1.getEndNodesAndWeights("v2"));
		System.out.println(matrix1.getEndNodesAndWeights("v4"));
		System.out.println(matrix1.getEndNodes("v2"));
		System.out.println(matrix1.getAllStartNodes());	
		matrix1.setEdge("v4", "v1", 3);
		System.out.println(matrix1.getMatrix());
		matrix1.setEdge("v3", "v5", 8);
		System.out.println(matrix1.getMatrix());
		matrix1.setEdge("v3", "v1", 1);
		System.out.println(matrix1.getMatrix());
		
		HashMap<String, Integer> endNodesAndWeight = new HashMap<>();
		endNodesAndWeight.put("v1", 1);
		endNodesAndWeight.put("v2", 2);
		endNodesAndWeight.put("v3", 3);
		System.out.println(endNodesAndWeight);
		HashMap<String, HashMap<String, Integer>> startNodesWEdges = new HashMap<>();
		startNodesWEdges.put("v8", endNodesAndWeight);
		System.out.println(startNodesWEdges);
		startNodesWEdges.put("v9", endNodesAndWeight);
		System.out.println(startNodesWEdges);
		System.out.println(matrix1.getAllEndNodes());

	}

}
