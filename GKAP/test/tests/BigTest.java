package tests;

import static org.junit.Assert.*;
import main.BIG;
import main.Dijkstra;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.junit.Before;
import org.junit.Test;

public class BigTest {
	private Graph<String, DefaultEdge> big;

	@Before
	public void init() {
		big = BIG.generateBigOne(100, 6000);
		Graphs.addEdge(big, "1", "2", 1);
		Graphs.addEdge(big, "2", "3", 1);
		Graphs.addEdge(big, "3", "4", 1);
		Graphs.addEdge(big, "4", "5", 1);
	}

	@Test
	public void test1() {
		Dijkstra dijkstra = new Dijkstra(big);
		FloydWarshall fw = new FloydWarshall(big);

		Double d2 = dijkstra.findShortestPathSize("1","5");
		Double d = fw.entfernungVonNach("1","5");

		System.out.println(String.format("Ergebnis FW: %s", d.toString()));
		System.out.println(String.format("Ergebnis Di: %s", d2.toString()));
	}

	@Test
	public void test2() {
		for (int i = 0; i < 10; i++) {
			big = BIG.generateBigOne(100, 6000);
			Dijkstra dijkstra = new Dijkstra(big);
			FloydWarshall fw = new FloydWarshall(big);

			Double d2 = dijkstra.findShortestPathSize("1","5");
			Double d = fw.entfernungVonNach("1", "5");

			System.out.println(String.format("Ergebnis FW: %s", d.toString()));
			System.out.println(String.format("Ergebnis Di: %s", d2.toString()));

			assertEquals(d2, d);
		}
	}
}
