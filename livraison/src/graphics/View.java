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
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
        super.paintComponent(g);
        for(int i = 0; i < game.getTileMap().size(); i++){
            ArrayList<Tile> list = game.getTileMap().get(i);
            for(Tile t : list){
                int x = (int)(64 * t.getX());
                int y = (int)(64 * t.getY());
                g.drawImage(t.getImgRepr(), x , y, this);
            }
        }

        Set<Player> players = game.getListPlayers().keySet();
        for(Player p: players){
            BufferedImage img = p.getImg();

            int baseX = (int)(( 64 - sizeImg.getWidth())/2);
            int baseY = (int)(( 64 - sizeImg.getHeight())/2);

            int x = 64 * p.getX() + baseX;
            int y = 64 * p.getY() + baseY;
            displayPlayer(g, p);
            //g.drawImage(img,x ,y , this);

            ArrayList<Direction> possibleMoves = p.possibleMoves();

            g.setColor(new Color(0,255,255, 200));
            for(Direction d : possibleMoves){
                g.fillRect(64 * (p.getX()+d.x()), 64* (p.getY()+d.y()), 64, 64);
            }
        }
        Grid grid = game.getGrid();
    }

    public void displayPlayer(Graphics g, Player p){
        
        BufferedImage img = p.getImg();

        int baseX = (int)(( 64 - sizeImg.getWidth())/2);
        int baseY = (int)(( 64 - sizeImg.getHeight())/2);

        int x = 64 * p.getX() + baseX;
        int y = 64 * p.getY() + baseY;
        
        
        // The required drawing location
        int drawLocationX = 300;
        int drawLocationY = 300;

        // Rotation information

        int angle = 0;
        if(p.lastMove == Direction.z){
            angle = 270;
        }else if(p.lastMove == Direction.s){
            angle = 90;
        }else if(p.lastMove == Direction.q){
            angle = 180;
        }else if(p.lastMove == Direction.d){
            angle = 0;
        }
        
        double rotationRequired = Math.toRadians (angle);
        double locationX = img.getWidth() / 2;
        double locationY = img.getHeight() / 2;
        /*
        at.translate(width/2,height/2);
        at.rotate(rads);
        at.translate(-width/2,-height/2);
        */
        //AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        //AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        AffineTransform tx = new AffineTransform();
        tx.translate(locationX, locationY);
        tx.rotate(Math.toRadians(angle));
        tx.translate(-locationX, -locationY);
        
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        
        // Drawing the rotated image at the required drawing locations
        g.drawImage(op.filter(img, null), x, y, null);
        
    }
    
    public void setEntities(Tile[] l){
        this.entities = l;
    }

    @Override
    public void update(Object source) {
        this.repaint();
    }
}
