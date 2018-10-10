/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import modele.Game;
import modele.Player;
import modele.Tile;

/**
 *
 * @author quentindeme
 */
public class View extends JPanel implements ModelListener{
    
    private ArrayList<Tile> entities;
    private Game game;
    private Dimension sizeImg;
    
    public View(ArrayList<Tile> entities, Game game) {
        this.entities = entities; 
        this.game = game;
        this.sizeImg = new Dimension(35,43);
        game.addListener(this);
    }
    
    public View(Game game){
        this(new ArrayList<Tile>(), game);
    }
    
    public void addEntity(Tile entity){
        entities.add(entity);
    }
    
    @Override
    public void paintComponent(Graphics g){
        System.out.println();
        List<Player> l = game.getPlayers();
        for(Player p : l){
            BufferedImage img = p.getImg();
            int x = (int)(sizeImg.getWidth()) * p.getX();
            int y = (int)(sizeImg.getHeight()) * p.getY();

            g.drawImage(img,x ,y , this);
        }
    }

    @Override
    public void update(Object source) {
        this.repaint();
    }
}
