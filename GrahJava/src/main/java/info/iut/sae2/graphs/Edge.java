package main.java.info.iut.sae2.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Edge {
    private Node source;
    private Node destination;
    private ArrayList<Coord> bends;

    // Constructeurs
    public Edge(Edge e) {
        this.source = e.source;
        this.destination = e.destination;
        
    }

    public Edge(Node src, Node dest) {
        this.source = src;
        this.destination = dest;
    }

    // Méthodes
    public Node getSource() {
        return this.source;
    }

    public Node getDestination() {
        return this.destination;
    }

    public ArrayList<Coord> getBendPositions() {
        return this.bends;
    }

    public void setBends(ArrayList<Coord> coords){
        this.bends =  new ArrayList<Coord>();
        this.bends.addAll(coords);
    }


    public void SetEdgeSource(Node n){
        this.source = n;
    }
    
    public void SetEdgeDestination(Node n){
        this.destination = n;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.source);
        hash = 17 * hash + Objects.hashCode(this.destination);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Edge other = (Edge) obj;
        if (!Objects.equals(this.source, other.source)) {
            return false;
        }
        return Objects.equals(this.destination, other.destination);
    }
    
   }


