/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.info.iut.sae2.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 *
 * @author kouhmid
 */
public class Node {
    Coord coordinates;

    /**
     * Constructor adding a new node
     */
    Node(){
    }
    /**
     * Constructor addin a given node 
     * @param n node
     */
    Node(Node n){

    }
    
    Node(Node n, Coord c){
        this.coordinates = c;

    }
    Node(Coord c){
      this.coordinates = c;  
    }
    public Coord getCoordinates(){
        return this.coordinates;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.coordinates);
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
        final Node other = (Node) obj;
        return Objects.equals(this.coordinates, other.coordinates);
    }
    
}
