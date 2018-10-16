/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import modele.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author quentindeme
 */
public class View extends JPanel implements ModelListener{
    private Tile[] entities;
    private Game game;
    private Dimension sizeImg;

    public View(Tile[] entities, Game game) {
        this.entities = entities;
        this.game = game;
        this.sizeImg = new Dimension(35,43);
        game.addListener(this);
    }
    /*
    public void addEntity(Tile entity){
        entities.add(entity);
    }
    */

    @Override
    public void paintComponent(Graphics g){
        System.out.println();
        for(Tile t : entities){
            if(t==null){
                continue;
            }else{
                int x = (int)(64 * t.getX());
                int y = (int)(64 * t.getY());
                g.drawImage(game.getTileImg(), x , y, this);
            }
        }

        Grid grid = game.getGrid();
        /*
        List<Player> l = game.getPlayers();
        for(Player p : l){
            BufferedImage img = p.getImg();

            int baseX = (int)(( 64 - sizeImg.getWidth())/2);
            int baseY = (int)(( 64 - sizeImg.getHeight())/2);

            int x = 64 * p.getX() + baseX;
            int y = 64 * p.getY() + baseY;
            g.drawImage(img,x ,y , this);

            ArrayList<Direction> possibleMoves = p.possibleMoves(grid);
            System.out.println(possibleMoves);

            g.setColor(new Color(0,255,255, 200));
            for(Direction d : possibleMoves){
                g.fillRect(64 * (p.getX()+d.x()), 64* (p.getY()+d.y()), 64, 64);
            }

        }
        */
    }

    public void setEntities(Tile[] l){
        this.entities = l;
    }

    @Override
    public void update(Object source) {
        this.repaint();
    }
}
