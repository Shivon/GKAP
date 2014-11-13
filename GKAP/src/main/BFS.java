package main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.Pseudograph;
import org.jgrapht.graph.SimpleWeightedGraph;

public class BFS {
	
	public static String[] breadthFirstSearch(Graph<String, DefaultEdge> graph, String startVertex, String endVertex){
		
		// Knoten in Set aus Strings gespeichert, um Dublikate auszuschließen
		Set<String> vertices = graph.vertexSet();
		
		// Anfangsbedingung fuer Suche: Start- und Endknoten muessen in Set sein
		if(vertices.contains(startVertex) && vertices.contains(endVertex)){
			HashMap<Integer, Set<String>> distWithVerticies = new HashMap<>();
			
			Set<String> startTmp = new HashSet<>();			
			startTmp.add(startVertex);
			
			// Distance von bekannten Knoten zu Startknoten gemappt
			distWithVerticies.put(0, startTmp);
			
			// Alle Knoten auf unbesucht setzen
			HashMap<String, Boolean> vertexVisited = new HashMap<>();
			for (String string : vertices) {
				vertexVisited.put(string, false);
			}
			
			// Startknoten wird besucht (Anfang)
			vertexVisited.put(startVertex, true);
			boolean ende = false;
			
			for (int i = 0; ende!=true && i < vertices.size(); i++) {
				// alle mit einer Kante erreichbaren Knoten
				Set<String> reachVertices = new HashSet<>();
				Integer ii=i;
				
				// alle aktuellen Knoten
				Set<String> tmpVertices = distWithVerticies.get(ii);
				
				for (String tmpVertex : tmpVertices) {
					// alle Kanten des aktuellen Knotens
					Set<DefaultEdge> tmpEdges = graph.edgesOf(tmpVertex);
					
					// alle Kanten davon durchgehen
					for (DefaultEdge tmpEdge : tmpEdges) {
						// durch die jeweilige Kante erreichbare Knoten
						String tarVertex = null;
						String tarVertex2 = null;
/*IMPORTANT*/						if(graph instanceof Pseudograph || graph instanceof SimpleWeightedGraph){
							tarVertex = graph.getEdgeTarget(tmpEdge);
							tarVertex2 = graph.getEdgeSource(tmpEdge);
							
							//Wenn Ecke noch nicht besucht
							if (vertexVisited.get(tarVertex) == false) {
								vertexVisited.put(tarVertex, true);
								reachVertices.add(tarVertex);
							} else if (vertexVisited.get(tarVertex2) == false) {
								vertexVisited.put(tarVertex2, true);
								reachVertices.add(tarVertex2);
							}
							
						} else {
							tarVertex = graph.getEdgeTarget(tmpEdge);
							//Wenn Ecke noch nicht besucht
							if (vertexVisited.get(tarVertex) == false){
								vertexVisited.put(tarVertex, true);
								reachVertices.add(tarVertex);
							}
						}

						
						
						//Wenn gesuchte Kante
						if (endVertex.equals(tarVertex) || endVertex.equals(tarVertex2)){
							ende=true;
						}
					}
				}
				//Merken der Ecken die mit einer Kante erreichbar sind
				distWithVerticies.put(ii+1, reachVertices);
			}
			if(ende==true){
				return findWay(graph, distWithVerticies, endVertex, startVertex);
			}
		}
		
		// wenn Kante nicht gefunden oä
		return null;
	}
	
	/**
	 * Liefert ein String[] zurück
	 * 1. Position der Weg
	 * 2. Position benötigte Kanten
	 */
	private static String[] findWay(Graph<String, DefaultEdge> graph, HashMap<Integer, Set<String>> distWithVertices, String ende, String anfang){
		StringBuffer res= new StringBuffer();
		
		//Wenn der gesuchte Knoten erreicht wurde
		if (distWithVertices.get(distWithVertices.keySet().size()-1).contains(ende)){
			
			res.append(ende);
			String last = ende;
			
			//von hinten nach vorne durchgehen bis zum StartKnoten
			for (int i=distWithVertices.keySet().size()-2 ; i>=0 ; i--) {
				Set<String> tmp = distWithVertices.get(i);
				for (String knoten : tmp) {
					//Wenn Kante existiert die beide Knoten aus Ebene und Ebene-1 verbindet
					if(graph.containsEdge(knoten, last)){
						//res.append(";" + knoten);
						res.insert(0, knoten + "->");
						last = knoten;
						break;
					}
				}
			}
			String blub = res.toString();
			String[] resAry = {blub, (distWithVertices.keySet().size() - 1)+ ""};
			
			return resAry;
		}
		return null;
	}
}