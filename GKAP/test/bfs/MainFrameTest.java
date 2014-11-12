/**
 * 
 */
package bfs;

import static org.junit.Assert.*;

import java.io.File;
import java.text.ParseException;

import main.BFS;
import main.IOGraph;

import org.hamcrest.core.IsNull;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Test;

/**
 * @author KamikazeOnRoad
 *
 */
public class MainFrameTest {
	
	// positive Tests
	// gerichteter Graph ohne Gewichtung, vorhandene Verbindung testen
	@Test 
	public void testGraph1(){
		//System.out.println("testGraph1: ");
		File file = new File("C:\\Users\\KamikazeOnRoad\\HAW\\3. Semester\\GKA\\GKAP\\Beispielgraphen\\graph1.gka");
		Graph<String, DefaultEdge> graph = null;
		try {
			graph = IOGraph.openGraph(file);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String[] result =  BFS.breadthFirstSearch(graph, "b", "h");
		//System.out.println("Ergebnis: \n"+result[0] +"\n"+result[1] +"\n");
		assertEquals("b->j->a->h", result[0]);
		
		assertEquals("3", result[1]);
		//System.out.println("");
	}
	
	// ungerichteter Graph mit Gewichtung, vorhandene Verbindung testen
	@Test 
	public void testGraph3(){
		File file = new File("C:\\Users\\KamikazeOnRoad\\HAW\\3. Semester\\GKA\\GKAP\\Beispielgraphen\\graph3.gka");
		Graph<String, DefaultEdge> graph = null;
		try {
			graph = IOGraph.openGraph(file);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String[] result =  BFS.breadthFirstSearch(graph, "Bremen", "Kiel");
		assertEquals("Bremen->Bremerhaven->Norderstedt->Husum->Kiel", result[0]);
		
		assertEquals("4", result[1]);
	}
	

	
	// negative Tests
	// gerichteter Graph ohne Gewichtung, nicht vorhandene Verbindung testen
	@Test (expected = NullPointerException.class)
	public void testGraph6MissingEdge(){
		File file = new File("C:\\Users\\KamikazeOnRoad\\HAW\\3. Semester\\GKA\\GKAP\\Beispielgraphen\\graph6.gka");
		Graph<String, DefaultEdge> graph = null;
		try {
			graph = IOGraph.openGraph(file);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String[] result =  BFS.breadthFirstSearch(graph, "10", "11");
		assertEquals(null, result[0]);
	}
	
	// gerichteter Graph, Verbindung in falsche Richtung testen
	@Test (expected = NullPointerException.class)
	public void testGraph6Direction(){
		File file = new File("C:\\Users\\KamikazeOnRoad\\HAW\\3. Semester\\GKA\\GKAP\\Beispielgraphen\\graph6.gka");
		Graph<String, DefaultEdge> graph = null;
		try {
			graph = IOGraph.openGraph(file);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String[] result =  BFS.breadthFirstSearch(graph, "12", "11");
		assertEquals(null, result[0]);
	}
	
	// ungerichteter Graph, nicht vorhandenen Knoten testen
	@Test (expected = NullPointerException.class)
	public void testGraph5MissingNode(){
		File file = new File("C:\\Users\\KamikazeOnRoad\\HAW\\3. Semester\\GKA\\GKAP\\Beispielgraphen\\graph5.gka");
		Graph<String, DefaultEdge> graph = null;
		try {
			graph = IOGraph.openGraph(file);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String[] result =  BFS.breadthFirstSearch(graph, "v5", "v20");
		assertEquals(null, result[0]);
	}	
}
