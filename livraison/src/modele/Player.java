/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author quentindeme
 */
public class Player extends Tile{
    
    public static int nbInstance = 0;
    private final String name;
    private int energy;
    private int life;
    
    public Player(int x, int y,String name, BufferedImage img){
        super(x,y,img);
        nbInstance++;
        this.name = name;
        initPlayer();
    }
    
    public Player(){
        super(0,0,null);
        nbInstance++;
        String defaultName = "Player ";
        defaultName += Integer.toString(nbInstance);
        this.name = defaultName;
        initPlayer();
    }
    
    public void initPlayer(){
        this.energy = 10;
        this.life = 10;
    }
    
    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        
        res.append(name+":\n");
        res.append("Energie : "+energy);
        res.append("\nPoints de vie : "+life);
        return res.toString();
    }
    
    @Override
    public void paint(Graphics g){
        g.drawImage(imageRepr, super.x, super.y, null);
    }
}
