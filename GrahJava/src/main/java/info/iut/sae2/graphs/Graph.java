/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.info.iut.sae2.graphs;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import static main.java.info.iut.sae2.algorithms.Algorithm.Prim;
import static main.java.info.iut.sae2.algorithms.Algorithm.bfs;

/**
 *
 * @author kouhmid
 */
public class Graph implements IGraph {

    public ArrayList<Edge> allEdges = new ArrayList<Edge>();
    public ArrayList<Node> allNodes = new ArrayList<Node>();
    public int SousGraphe = 0;
    Graph() {

    }

    public Graph(ArrayList<Node> n, ArrayList<Edge> e) {
        this.allEdges = e;
        this.allNodes = n;

    }
    public Graph(ArrayList<Node> n, ArrayList<Edge> e, int i) {
        this.allEdges = e;
        this.allNodes = n;
        this.SousGraphe = i;

    }

    @Override
    public Node addNode() {
        Node n = new Node();
        allNodes.add(n);
        return n;
    }

    @Override
    public Node addNode(Node n) {
        allNodes.add(n);
        return n;
    }

    @Override
    public Edge addEdge(Edge e) {
        allEdges.add(e);
        return e;
    }

    @Override
    public Edge addEdge(Node src, Node tgt) {
        Edge edge = new Edge(src, tgt);
        allEdges.add(edge);
        return edge;
    }

    @Override
    public void delNode(Node n) {
        allNodes.remove(n);

    }

    @Override
    public void delEdge(Edge e) {
        allEdges.remove(e);

    }

    @Override
    public int numberOfNodes() {
        return allNodes.size();

    }

    @Override
    public int numberOfEdges() {
        return allEdges.size();
    }

    @Override
    public ArrayList<Node> getNeighbors(Node n) {
        ArrayList<Node> neighbors = new ArrayList<Node>();
        for (Edge e : allEdges) {
            if (e.getSource().equals(n)) {
                neighbors.add(e.getDestination());
            }
            else if (e.getDestination().equals(n)) {
                neighbors.add(e.getSource());
            }
        }

        return neighbors;

    }

    @Override
    public ArrayList<Node> getSuccesors(Node n) {
        ArrayList<Node> succesors = new ArrayList<Node>();
        for (Edge e : allEdges) {
            if (e.getSource().equals(n)) {
                System.out.println(n.coordinates.x);
                succesors.add(e.getDestination());
            }
        }
        return succesors;
    }

    @Override
    public ArrayList<Node> getPredecessors(Node n) {
        ArrayList<Node> predecessors = new ArrayList<Node>();
        for (Edge e : allEdges) {
            if (e.getDestination() == n) {
                predecessors.add(e.getSource());
            }
        }
        return predecessors;
    }

    @Override
    public ArrayList<Edge> getInOutEdges(Node n) {
        ArrayList<Edge> InEdges = new ArrayList<Edge>();
        for (Edge e : allEdges) {
            InEdges.add(e);
        }
        return InEdges;
    }

    /**
     * Si le graph est orienté 
     * @param n
     * @return 
     */
    @Override
    public ArrayList<Edge> getInEdges(Node n) {
        ArrayList<Edge> InEdges = new ArrayList<Edge>();
        for (Edge e : allEdges) {
            if (e.getDestination().equals(n)) {
                InEdges.add(e);
            }
        }
        return InEdges;
    }

    /**
     * Si les graph est orienté 
     * @param n
     * @return 
     */
    @Override
    public ArrayList<Edge> getOutEdges(Node n) {
        
        ArrayList<Edge> OutEdge = new ArrayList<Edge>();
        for (Edge e : allEdges) {
            if (e.getSource().equals(n)) {
                OutEdge.add(e);
            }
        }
        return OutEdge;
    }

    @Override
    public ArrayList<Node> getNodes() {
        return allNodes;
    }

    @Override
    public ArrayList<Edge> getEdges() {
        return allEdges;
    }

    @Override
    public Node source(Edge e) {
        return e.getSource();
    }

    @Override
    public Node target(Edge e) {
        return e.getDestination();
    }

    @Override
    public int inDegree(Node n) {
        return getInEdges(n).size();
    }

    @Override
    public int outDegree(Node n) {
        return getOutEdges(n).size();
    }

    @Override
    public int degree(Node n) {
        return getNeighbors(n).size();
    }

    @Override
    public boolean existEdge(Node src, Node tgt, boolean oriented) {
        for (Edge e : allEdges) {
            if (e.getSource() == src && e.getDestination() == tgt) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Edge getEdge(Node src, Node tgt, boolean oriented) {
        for (Edge e : allEdges) {
            if (e.getSource() == src && e.getDestination() == tgt) {
                return e;
            }
        }
        return null;
    }

    @Override
    public Coord getNodePosition(Node n) {
        return n.coordinates;
    }

    @Override
    public ArrayList<Coord> getEdgePosition(Edge e) {
        ArrayList<Coord> coordinates = new ArrayList<Coord>();
        coordinates.add(e.getSource().coordinates);
        if (e.getBendPositions() != null){
            for (Coord c : e.getBendPositions()) {
            coordinates.add(c);
        }
            
        }
        coordinates.add(e.getDestination().coordinates);
        return coordinates;
    }

    @Override
    public void setNodePosition(Node n, Coord c) {
        n.coordinates = c;
    }

@Override
public void setEdgePosition(Edge e, ArrayList<Coord> bends) {
   e.setBends(bends);
}



    @Override
    public void setAllNodesPositions(Coord c) {
        for (Node n : allNodes){
            n.coordinates = c;
        }
    }

    @Override
    public void setAllEdgesPositions(ArrayList<Coord> bends) {
        for (Edge e : allEdges){
            e.setBends(bends);
        }
    }

    @Override
    public ArrayList<Coord> getBoundingBox() {
        ArrayList<Coord> coordinates = new ArrayList<Coord>();
        double xMin = Double.MAX_VALUE;
        double xMax = Double.MIN_VALUE;
        double yMin = Double.MAX_VALUE;
        double yMax = Double.MIN_VALUE;
        for (Node n : allNodes) {
            if (n.coordinates.x > xMax) {
                xMax = n.coordinates.x;
            }
            if (n.coordinates.x < xMin) {
                xMin = n.coordinates.x;
            }
            if (n.coordinates.y > yMax) {
                yMax = n.coordinates.y;
            }
            if (n.coordinates.y < yMin) {
                yMin = n.coordinates.y;
            }
        }
        Coord CoordMin = new Coord(xMin, yMin);
        Coord CoordMax = new Coord(xMax, yMax);
        coordinates.add(CoordMin);
        coordinates.add(CoordMax);
        return coordinates;
    }

    @Override

    public Graph getMinimumSpanningTree() {
        return Prim(this);
    }
    
@Override
public void bundle() {
    Graph mst = getMinimumSpanningTree();   
    for (Edge edge : allEdges) {    
        List<Node> path = bfs(edge.getSource(), edge.getDestination(), mst); // création du chemin
        if (path != null && path.size() > 2) { // vérifier si le chemin n'est pas null et qu'il y a biens des points intérmédiaires
            ArrayList<Coord> bends = new ArrayList<>();
            for (int i = 1; i < path.size() - 1; i++) { // on parcour la liste du chemin en enlevant la source et la déstination
                bends.add(path.get(i).coordinates);
            }
            setEdgePosition(edge, bends);
        } else {   
            setEdgePosition(edge, new ArrayList<>());
        }
    } 
    } 




}







