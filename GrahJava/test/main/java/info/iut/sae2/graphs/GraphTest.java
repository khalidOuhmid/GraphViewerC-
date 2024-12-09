/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.info.iut.sae2.graphs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import main.java.info.iut.sae2.algorithms.Algorithm;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author kouhmid
 */
public class GraphTest {

    private static final String NODE_FILE_NAME = "Testnodes.txt";
    private static final String EDGE_FILE_NAME = "Testedges.txt";
    private Graph graph;

    @Before
    public void setUp() throws IOException {
        createTestFiles();
        graph = GraphLoader.loadFromFile(NODE_FILE_NAME, EDGE_FILE_NAME);
    }

    private void createTestFiles() throws IOException {
        // Création du fichier de nodes
        Path nodeFile = Paths.get(NODE_FILE_NAME);
        String nodeContent = "1;0.0 0.0\n"
                + "2;1.0 1.0\n"
                + "3;2.0 2.0\n"
                + "4;3.0 3.0\n"
                + "5;4.0 4.0\n"
                + "6;5.0 5.0\n"
                + "7;6.0 6.0\n"
                + "8;7.0 7.0\n"
                + "9;8.0 8.0\n"
                + "10;9.0 9.0\n";
        Files.write(nodeFile, nodeContent.getBytes());

        // Création du fichier d'edges
        Path edgeFile = Paths.get(EDGE_FILE_NAME);
        String edgeContent = "1;2\n"
                + "2;3\n"
                + "3;4\n"
                + "4;5\n"
                + "5;6\n"
                + "6;7\n"
                + "7;8\n"
                + "8;9\n"
                + "9;10\n"
                + "1;10\n"
                + "2;9\n"
                + "3;8\n"
                + "4;7\n"
                + "5;6\n"; // cet edge existe déjà donc il ne va pas être comptabilisé.
        Files.write(edgeFile, edgeContent.getBytes());
    }

    @Test
    public void testGraphLoadFromFile() {
        assertNotNull(graph);
        assertEquals(10, graph.numberOfNodes());
        assertEquals(13, graph.numberOfEdges()); // Après déduplication

        for (int i = 0; i < 9; i++) {
            assertEquals(graph.getEdges().get(i).getSource(), graph.getNodes().get(i));
            assertEquals(graph.getEdges().get(i).getDestination(), graph.getNodes().get(i + 1));
            System.out.println("Edges " + i + " : source ==> " + graph.getEdges().get(i).getSource().coordinates.x + " destination ==>"
                    + graph.getNodes().get(i + 1).coordinates.y);
        }
        assertEquals(graph.getEdges().get(9).getSource(),graph.getNodes().get(0));
        assertEquals(graph.getEdges().get(9).getDestination(),graph.getNodes().get(graph.numberOfNodes()-1));
        assertEquals(graph.getEdges().get(10).getSource(),graph.getNodes().get(1));
        assertEquals(graph.getEdges().get(10).getDestination(),graph.getNodes().get(graph.numberOfNodes()-2));
        assertEquals(graph.getEdges().get(11).getSource(),graph.getNodes().get(2));
        assertEquals(graph.getEdges().get(11).getDestination(),graph.getNodes().get(graph.numberOfNodes()-3));        
        System.out.println(" //////////////////////////////////////////////////////////////");
        int i = 0;
        for (Edge e : graph.allEdges) {
            i++;
            System.out.println("Edges " + i + " : source ==> " + e.getSource().coordinates.x + " destination ==>" + e.getDestination().coordinates.y);
        }
    }

    @Test
    public void AddAndDelMethod() {
        testAddNode();
        testAddEdge();
        testDelNode();
        testDelEdge();
    }

    @Test
    public void testAddNode() {
        Node node1 = new Node();
        node1.coordinates = new Coord(15, 15);
        assertEquals(10, graph.numberOfNodes());
        graph.addNode(node1);
        assertEquals(11, graph.numberOfNodes());
    }

    @Test
    public void testAddEdge() {
        Node node1 = graph.getNodes().get(0);
        Node node2 = graph.getNodes().get(1);
        assertEquals(13, graph.numberOfEdges());
        Edge edge1 = new Edge(node1, node2);
        graph.addEdge(edge1);
        assertEquals(14, graph.numberOfEdges());
    }

    @Test
    public void testDelNode() {
        Node node1 = graph.getNodes().get(0);
        graph.delNode(node1);
        assertEquals(9, graph.numberOfNodes());
    }

    @Test
    public void testDelEdge() {
        Edge edge1 = graph.getEdges().get(0);
        graph.delEdge(edge1);
        assertEquals(12, graph.numberOfEdges());
    }

    @Test
    public void testDelNode1() {
        Node node1 = graph.getNodes().get(0);
        Node node2 = graph.getNodes().get(1);
        Node node3 = graph.getNodes().get(2);

        assertNotNull(node1);
        assertNotNull(node2);
        assertNotNull(node3);
        assertEquals(10, graph.allNodes.size());

        graph.delNode(node3);
        for (Node x : graph.allNodes) {
            assertFalse(!graph.allNodes.contains(x));
        }
        assertEquals(9, graph.allNodes.size());
    }

    @Test
    public void testGetNeighbors() {
        ArrayList<Node> neighbors = graph.getNeighbors(graph.getNodes().get(1));
        assertEquals(3, neighbors.size());
        assertTrue(neighbors.contains(graph.getNodes().get(8)));
        assertTrue(neighbors.contains(graph.getNodes().get(0)));
        assertTrue(neighbors.contains(graph.getNodes().get(2)));
        assertFalse(neighbors.contains(graph.getNodes().get(5)));
        assertFalse(neighbors.contains(graph.getNodes().get(6)));
    }

    @Test
    public void testGetSuccessors() {
        ArrayList<Node> successors = graph.getSuccesors(graph.getNodes().get(5));
        assertEquals(2, successors.size());
        assertTrue(successors.contains(graph.getNodes().get(6)));
        assertTrue(successors.contains(graph.getNodes().get(8)));
    }

    @Test
    public void testGetPredecessors() {
        Node node2 = graph.getNodes().get(1);
        Node node3 = graph.getNodes().get(2);
        Edge edge2 = new Edge(node2, node3);
        graph.addEdge(edge2);
        ArrayList<Node> predecessors = graph.getPredecessors(node3);
        assertEquals(1, predecessors.size());
        assertTrue(predecessors.contains(node2));
    }

    @Test
    public void testGetInEdges() {
        Node node1 = graph.getNodes().get(0);
        Node node2 = graph.getNodes().get(1);
        Edge edge1 = new Edge(node1, node2);
        graph.addEdge(edge1);
        ArrayList<Edge> inEdges = graph.getInEdges(node2);
        assertEquals(1, inEdges.size());
        assertTrue(inEdges.contains(edge1));
    }

    /**
     * Test des edges sortant du noeud 0 Le noeud 0 à 2 edges sortant : 0 vers 1
     * 0 vers 10 dans le fichier cela nous donne : l'edge 0 et l'dedge 1 (numéro
     * de l'edge -1)
     */
    @Test
    public void testGetOutEdges() {
        testGraphLoadFromFile();
        Node node1 = graph.getNodes().get(0);
        Node node2 = graph.getNodes().get(1);
        ArrayList<Edge> outEdges = new ArrayList<Edge>(graph.getOutEdges(node1));
        assertEquals(2, outEdges.size());
        assertTrue(outEdges.contains(graph.getEdges().get(0)));
        assertTrue(outEdges.contains(graph.getEdges().get(9)));
        assertFalse(outEdges.contains(graph.getEdges().get(7)));
        assertFalse(outEdges.contains(graph.getEdges().get(4)));
    }
    
    /**
     * Le noeud numéro 1 donc 2 dans le fichier à qu'un edge entrant :
     * 1 ====> 2
     * Et 2 edges sortant :
     * 2=====> 3 
     * 2 ====> 9
     */
    @Test
    public void testInDegree() {
        Node node2 = graph.getNodes().get(1);
        assertEquals(1, graph.inDegree(node2));
    }

    /**
     * Le neud numéro 0 donc 1 dans le fichier à 2 edges sortant :
     * 0 =====> 1
     * 0 =====> 9
     */
    @Test
    public void testOutDegree() {
        Node node1 = graph.getNodes().get(0);
        assertEquals(2, graph.outDegree(node1));
    }
    
    /**
     * Le neud numéro 0 donc 1 dans le fichier à 2 edges sortant :
     * 0 =====> 1
     * 0 =====> 9
     */
    @Test
    public void testDegree() {
        testGraphLoadFromFile();
        Node node1 = graph.getNodes().get(0);
        assertEquals(2, graph.degree(node1));
        Node node2 = graph.getNodes().get(9);
        assertEquals(2, graph.degree(node2));
        Node node3 = graph.getNodes().get(1);
        
        assertEquals(3, graph.degree(node2));
        
        
    }

    @Test
    public void testExistEdge() {
        Node node1 = graph.getNodes().get(0);
        Node node2 = graph.getNodes().get(1);
        Edge edge1 = new Edge(node1, node2);
        graph.addEdge(edge1);
        assertTrue(graph.existEdge(node1, node2, true));
    }

    @Test
    public void testGetEdge() {
        Node node1 = graph.getNodes().get(0);
        Node node2 = graph.getNodes().get(1);
        Edge edge1 = new Edge(node1, node2);
        graph.addEdge(edge1);
        Edge foundEdge = graph.getEdge(node1, node2, true);
        assertEquals(edge1, foundEdge);
    }

    @Test
    public void testSetNodePosition() {
        Node node1 = graph.getNodes().get(0);
        Coord coord = new Coord(37, 37);
        graph.setNodePosition(node1, coord);
        assertEquals(coord, node1.coordinates);
    }

    @Test
    public void testGetNodePosition() {
        Node node1 = graph.getNodes().get(0);
        Coord coord = new Coord(38, 38);
        node1.coordinates = coord;
        assertEquals(coord, graph.getNodePosition(node1));
    }
    @Test
    public void testPrim(){
        // Graphe non connexe :
        Graph g = new Graph();
               g = GraphLoader.loadFromFile("example_nodes.csv", "example_edges.csv");
        assertEquals(16,g.getEdges().size());
        assertEquals(12,g.getNodes().size());
        Graph mst = Algorithm.Prim(g);
        assertEquals(g.allNodes.size() - mst.SousGraphe ,mst.getEdges().size());
        assertEquals(12,mst.getNodes().size());
        // Graphe connexe :
        g = new Graph();
               g = GraphLoader.loadFromFile("us_migrations_nodes.csv", "us_migrations_edges.csv");
        assertEquals(5635,g.getEdges().size());
        assertEquals(1090,g.getNodes().size());
        mst = Algorithm.Prim(g);
        assertEquals(g.allNodes.size() - mst.SousGraphe ,mst.getEdges().size());
        assertEquals(1090,mst.getNodes().size());
    }
    
}
