/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.info.iut.sae2.viewer;

import main.java.info.iut.sae2.graphs.Coord;
import main.java.info.iut.sae2.graphs.Edge;
import main.java.info.iut.sae2.graphs.IGraph;
import main.java.info.iut.sae2.graphs.Node;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import static java.lang.Math.max;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rbourqui
 */
public class GraphCanvas extends Canvas {


    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    IGraph graph;

    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    Map<Node, NodeView> nodeMap;

    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    Map<Edge, EdgeView> edgeMap;

    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    boolean smoothEdge = true;
    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    int nodeRadius = 5;
    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    int edgeTransparency = 60;

    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    static final int MARGIN = 20;

    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    static final Color NODE_COLOR = Color.RED;

    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    static Color EDGE_COLOR = new Color(120, 120, 120, 60);
    

    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    GraphCanvas() {
        graph = null;
        nodeMap = new HashMap<>();
        edgeMap = new HashMap<>();
    }
    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    private void initNodeMap() {
        if(graph == null) return;
        ArrayList<Coord> bb = graph.getBoundingBox();
        for (Node n : graph.getNodes()) {
            Coord c = graphPositionToViewPosition(graph.getNodePosition(n), bb, true);
            NodeView nv = new NodeView((int) c.x, (int) c.y, nodeRadius, NODE_COLOR);
            nodeMap.put(n, nv);
        }

    }

    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    private void initEdgeMap() {
        ArrayList<Coord> bb = graph.getBoundingBox();

        for (Edge e : graph.getEdges()) {
            int[] x;
            int[] y;
            ArrayList<Coord> bends = graph.getEdgePosition(e);
            x = new int[bends.size() + 2];
            y = new int[bends.size() + 2];
            int nbPoints = 0;

            Coord src = graphPositionToViewPosition(graph.getNodePosition(graph.source(e)), bb, false);
            x[0] = (int) src.x;
            y[0] = (int) src.y;
            nbPoints++;
            for (Coord cur : bends) {
                cur = graphPositionToViewPosition(cur, bb, false);
                x[nbPoints] = (int) cur.x;
                y[nbPoints] = (int) cur.y;
                nbPoints++;
            }
            Coord tgt = graphPositionToViewPosition(graph.getNodePosition(graph.target(e)), bb, false);
            x[nbPoints] = (int) tgt.x;
            y[nbPoints] = (int) tgt.y;

            EdgeView ev = new EdgeView(x, y, EDGE_COLOR, smoothEdge);
            edgeMap.put(e, ev);
        }
    }

    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    private void initMaps() {
        nodeMap = new HashMap<>();
        edgeMap = new HashMap<>();
        initNodeMap();
        initEdgeMap();
    }
    
    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    @Override
    public void paint(Graphics g) {
        Image img = createImage(getWidth(), getHeight());
        Graphics2D imgG = (Graphics2D) img.getGraphics();
        imgG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        setBackground(Color.WHITE);

        for (EdgeView ev : edgeMap.values()) {
            ev.draw(imgG);
        }

        for (NodeView nv : nodeMap.values()) {
            nv.draw(imgG);
        }

        g.drawImage(img, 0, 0, null);
    }

    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    Coord graphPositionToViewPosition(Coord c, ArrayList<Coord> boundingBox, boolean forNode) {
        double bbWidth = (boundingBox.get(1).x - boundingBox.get(0).x);
        double bbHeight = (boundingBox.get(1).y - boundingBox.get(0).y);

        int width = getWidth() - 2 * MARGIN;
        int height = getHeight() - 2 * MARGIN;

        // scale so as to maximize the drawing
        double distX, distY;
        double ratio = max(bbWidth / width, bbHeight / height);
        if (bbWidth > 0) {
            distX = (c.x - boundingBox.get(0).x);
        } else {
            distX = 0.5;
        }
        if (bbHeight > 0) {
            distY = (c.y - boundingBox.get(0).y);
        } else {
            distY = 0.5;
        }

        int x = (int) (distX / ratio) + MARGIN;
        int y = (int) (distY / ratio) + MARGIN;

        // translate to center the drawing
        if (bbHeight / ratio < height) {
            y += (height - bbHeight / ratio) / 2;
        }
        if (bbWidth / ratio < width) {
            x += (width - bbWidth / ratio) / 2;
        }
        if (forNode) {
            x -= nodeRadius / 2;
            y -= nodeRadius / 2;
        }
        return new Coord(x, y);

    }

    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    void setGraph(IGraph g) {
        graph = g;
        if(graph != null)
            initMaps();
    }

    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    void setRadius(int radius) {
        nodeRadius = radius;
        initNodeMap();
    }

    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    void setEdgeTransparency(int alpha) {
        edgeTransparency = alpha;
        for (EdgeView ev : edgeMap.values()) {
            ev.updateTransparency(alpha);
        }
    }

    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    void setSmooth(boolean smooth) {
        smoothEdge = smooth;
        
        for (EdgeView ev : edgeMap.values()) {
            ev.updateSmoothed(smooth);
        }
    }
}
