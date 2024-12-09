/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.info.iut.sae2;


import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import main.java.info.iut.sae2.viewer.GraphViewer;

/**
 *
 * @author rbourqui
 */
public class Main {
    
    
    /**
    No javadoc is provided, there is no need to understand/modify this part of the code 
     */
    public static void main(String[] args) throws Exception {
        // Apply a look'n feel
        UIManager.setLookAndFeel(new NimbusLookAndFeel());

        GraphViewer myWindow = new GraphViewer();
        myWindow.setVisible(true);
    }
}
