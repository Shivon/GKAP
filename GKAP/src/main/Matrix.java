/**
 * 
 */
package main;

import java.util.*;


/**
 * @author KamikazeOnRoad
 *
 */
public class Matrix {
	private HashMap<String, List<HashMap<String, Integer>>> matrix;
	
	
	public Matrix() {
		matrix = new HashMap<>();
	}
	
	
	/**
	 * Returns HashMap with all StartNodes, EndNodes and Weights
	 * @author KamikazeOnRoad
	 */
	public HashMap<String, List<HashMap<String, Integer>>> getMatrix() {
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
	 * Returns list with all hashmapped EndNodes and Weight for specific StartNode.
	 * @author KamikazeOnRoad
	 */
	public List<HashMap<String, Integer>> getEndNodesAndWeights(String startNode) {
		return this.getMatrix().get(startNode);
	}
	
	
	/**  
	 * Returns set of EndNodes for specific StartNode without Weight
	 * @author KamikazeOnRoad
	 */
	public Set<String> getEndNodes(String startNode) {
		List<HashMap<String, Integer>> endNodesAndWeight = this.getEndNodesAndWeights(startNode);
		Set<String> setEndNodes = new HashSet<>();
		
		for (HashMap<String, Integer> endNode : endNodesAndWeight) {
			setEndNodes.addAll(endNode.keySet());
		}
		
		return setEndNodes;
	}
	
	
	
	/**  
	 * Returns the weight as Integer. 
	 * Returns null when startNode is not included in matrix. 
	 * Returns MAX_VALUE when there is no edge.
	 * @author KamikazeOnRoad
	 */
	public int getWeight(String startNode, String endNode) {
		List<HashMap<String, Integer>> rowStartNode = this.getMatrix().get(startNode);
		if (rowStartNode == null) { return (Integer) null; }
		
		for (HashMap<String, Integer> column : rowStartNode) {
			if (column.get(endNode) != null) { return column.get(endNode); }			
		}
		
		return Integer.MAX_VALUE;
	}

	
	public void setMatrix(HashMap<String, List<HashMap<String, Integer>>> matrix) {
		this.matrix = matrix;
	}


	// Attention regarding overwriting...
	public void setRow(String startNode, List<HashMap<String, Integer>> endNodesAndWeights) {
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
			HashMap<String, Integer> newEdge = new HashMap<>();
			newEdge.put(endNode, weight);
			
			this.getEndNodesAndWeights(startNode).add(newEdge);
			
		} else if (!startNodeAlreadyInMatrix) {
			List<HashMap<String, Integer>> listEndNodes = new ArrayList<>();
			HashMap<String, Integer> newEdge = new HashMap<>();
			newEdge.put(endNode, weight);
			listEndNodes.add(newEdge);
			
			this.setRow(startNode, listEndNodes);			
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
			if (this.getEndNodes(startNode).contains(endNode)) {
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
		
		// generate columns for rowV2
		HashMap<String, Integer> column1 = new HashMap<>();
		column1.put("v1", 1);
		HashMap<String, Integer> column2 = new HashMap<>();
		column2.put("v3", 5);
		HashMap<String, Integer> column3 = new HashMap<>();
		column3.put("v5", 3);
		HashMap<String, Integer> column4 = new HashMap<>();
		column4.put("v6", 2);
		
		// generate row for v2
		List<HashMap<String, Integer>> rowV2 = new ArrayList<>();
		rowV2.add(column1);
		rowV2.add(column2);
		rowV2.add(column3);
		rowV2.add(column4);
		matrix1.setRow("v2", rowV2);
		
		// generate columns for rowV4
		HashMap<String, Integer> column31 = new HashMap<>();
		column31.put("v3", 1);
		HashMap<String, Integer> column32 = new HashMap<>();
		column32.put("v5", 3);
		
		// generate row for v4
		List<HashMap<String, Integer>> rowV4 = new ArrayList<>();
		rowV4.add(column31);
		rowV4.add(column32);
		matrix1.setRow("v4", rowV4);
		
		System.out.println(column1);
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
	}

}
