/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.info.iut.sae2.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import main.java.info.iut.sae2.graphs.Edge;
import main.java.info.iut.sae2.graphs.Graph;
import main.java.info.iut.sae2.graphs.Node;

/**
 *
 * @author kouhmid
 */
public class Algorithm {

    public static Graph Prim(Graph g) {
        int SousGraphe = 1;
        ArrayList<Edge> minimumSpanningEdges = new ArrayList<>();
        ArrayList<Node> markedNodes = new ArrayList<>();
        Node startNode = g.getNodes().get(0);
        markedNodes.add(startNode);
        
        boolean machin = false;
        while (markedNodes.size() < g.numberOfNodes()) { // tant qu'il y a des sommets pas marqués
            Edge minEdge = null;
            double minWeight = Double.MAX_VALUE;

            for (Edge edge : g.getEdges()) { // parcour de tout les edges
                // vérification si le edge à un lien avec le noeud marqué
                boolean isValidEdge = (markedNodes.contains(edge.getSource()) && !markedNodes.contains(edge.getDestination()))
                        || (!markedNodes.contains(edge.getSource()) && markedNodes.contains(edge.getDestination()));
                if (isValidEdge) {
                    //Si c'est le cas alors on calcul le coût, soit la distance entre la source et la destination
                    double weight = edge.getSource().getCoordinates().dist(edge.getDestination().getCoordinates());
                    //Phase de sélection du meilleur chemin
                    if (weight < minWeight) {
                        minEdge = edge;
                        minWeight = weight;
                    }
                }
            }
            if (minEdge != null) {
                // on à l'arbre le meilleur noeud
                minimumSpanningEdges.add(minEdge);
                // vérification suplémentaire pour éviter de cloner
                if (markedNodes.contains(minEdge.getSource())) {
                    markedNodes.add(minEdge.getDestination());
                } else {
                    markedNodes.add(minEdge.getSource());
                }
            } else {
                // Pour les graphes non connexe : 
                if (markedNodes.size() < g.getNodes().size()) {
                    //création d'une liste qui contient tout les noeud du graphe original 
                    ArrayList<Node> UnMarkedNode = new ArrayList<>(g.getNodes());
                    //On soustrait tout les noeuds marquées du graphe original
                    UnMarkedNode.removeAll(markedNodes);
                    //On prend le premier sommet de notre liste qui contient tout les sommets non marqués
                    startNode = UnMarkedNode.get(0);
                    //Et on l'ajoute
                    markedNodes.add(startNode); 
                    
                    SousGraphe++; 
                } else {
              
                    break;

                }

            }
        }
        
        System.out.println("nb graphes : " + SousGraphe);
        return new Graph(new ArrayList<>(markedNodes), minimumSpanningEdges, SousGraphe);
    }

    public static ArrayList<Node> bfs(Node src, Node tgt, Graph minimalTree) {
        ArrayList<Node> bestPath = new ArrayList<>();
        HashMap<Node, Node> parents = new HashMap<>();
        ArrayList<Node> queue = new ArrayList<>();
        HashSet<Node> visited = new HashSet<>();

        visited.add(src);
        queue.add(src);
        parents.put(src, null);
        boolean found = false;

        while (!queue.isEmpty() && !found) {
            Node current = queue.remove(0);
            for (Node neighbor : minimalTree.getNeighbors(current)) {

                if (!visited.contains(neighbor)) {

                    visited.add(neighbor);
                    parents.put(neighbor, current);
                    queue.add(neighbor);

                    if (neighbor.equals(tgt)) {
                        found = true;
                    }
                }
            }
        }

        if (found) {
            for (Node at = tgt; at != null; at = parents.get(at)) {
                bestPath.add(at);
            }
            java.util.Collections.reverse(bestPath);
            return bestPath;
        }

        return null; // Chemin non trouvé
    }

}
