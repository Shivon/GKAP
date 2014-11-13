package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.*;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.Graph;


@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private Graph<String, DefaultEdge> graph;
	private JPanel mainPanel, southPanel, northPanel;
	private JTextArea textArea;
	private JButton bfs, dij, fm;
	
	MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("GKA Aufgabe1");
		setSize(new Dimension(800, 600));
		this.setLayout(new BorderLayout());
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu Datei = new JMenu("Datei");
		menuBar.add(Datei);
		
		JMenuItem oeffnen = new JMenuItem("Öffnen");
		oeffnen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					graph = IOGraph.openGraph();
					new GraphVisualisation(graph);
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(),
							"ERROR", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}			
			}
		});
		Datei.add(oeffnen);
	
		JMenuItem speichern = new JMenuItem("Speichern");
		speichern.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (graph != null) {
					IOGraph.saveGraph(graph);
				} else {
					JOptionPane.showMessageDialog(null,
							"Bitte lesen Sie zuerst einen Graphen ein.",
							"ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		Datei.add(speichern);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());
		
		
		final JTextField field = new JTextField("Start Knoten",10);
		final JTextField field2 = new JTextField("End Knoten",10);
		textArea = new JTextArea(20, 5);
		textArea.setEditable(false);
		
		bfs = new JButton("BFS-algorithm");
		bfs.setPreferredSize(new Dimension(200, 50));
		bfs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = field.getText();
				String str2 = field2.getText();
				String[] result =  BFS.breadthFirstSearch(graph, str, str2);
				if(result != null){
					textArea.append("\n ================================================= \n");
					textArea.append("Weg: " + result[0] + "\nbenoetigte Kanten: " + result[1]);
				}else{
					textArea.setText("Kein möglichen Weg gefunden! \nOder unültige Eingabe!");
				}
			}
		});
		
		dij = new JButton("Dijkstra-algorithm");
		dij.setPreferredSize(new Dimension(200, 50));
		dij.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = field.getText();
				String str2 = field2.getText();
				String[] result =  Dijkstra.dijkstraSearch(graph, str, str2);
				if(result != null){
					textArea.append("\n ================================================= \n");
					textArea.append("Weg: " + result[0] + "\nLaenge Weg: " + result[1] + "\nbenoetigte Kanten: " + result[2] + "\nZugriffe Graph: " + result[3]);
				}else{
					textArea.setText("Kein möglichen Weg gefunden! \nOder unültige Eingabe!");
				}
			}
		});
		
		fm = new JButton("Floyd-Warshall-algorithm");
		fm.setPreferredSize(new Dimension(200, 50));
		fm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					//TODO Floyd-Warshall
			}
		});
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new BorderLayout());
		buttons.add(field,BorderLayout.NORTH);
		buttons.add(field2,BorderLayout.SOUTH);
		northPanel.add(buttons,BorderLayout.SOUTH);
		
		JScrollPane sP = new JScrollPane();
		northPanel.add(sP,BorderLayout.NORTH);
		sP.setViewportView(textArea);
		
		southPanel.add(bfs,BorderLayout.NORTH);
		southPanel.add(dij,BorderLayout.CENTER);
		southPanel.add(fm,BorderLayout.SOUTH);
		mainPanel.add(northPanel,BorderLayout.NORTH);
		mainPanel.add(southPanel,BorderLayout.SOUTH);
		this.add(mainPanel);
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		MainFrame test = new MainFrame();
		test.setVisible(true);
	}
}
