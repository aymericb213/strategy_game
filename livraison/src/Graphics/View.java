/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import modele.Tile;

/**
 *
 * @author quentindeme
 */
public class View extends JPanel{
    
    private ArrayList<Tile> entities;
    
    public View(ArrayList<Tile> entities){
        this.entities = entities; 
    }
    
    public void addEntity(Tile entity){
        entities.add(entity);
    }
    
    @Override
    public void paintComponent(Graphics g){
        for(Tile entity: entities){
            entity.paint(g);
        }
    }
}
