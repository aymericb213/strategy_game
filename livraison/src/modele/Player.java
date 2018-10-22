/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.awt.image.BufferedImage;
import java.util.*;

/**
 *
 * @author quentindeme
 */
public class Player extends Tile {

    private int x;
    private int y;
    private final String name;
    private int energy;
    private int life;
    private HashMap<Weapon,Integer> loadout;
    private boolean shield_up = false;
    private BufferedImage img;

    public Player(int x, int y, int hp, int mp, String name) {
        super(x,y);
        this.energy = hp;
        this.life = mp;
        this.name = name;
        this.loadout = new HashMap<Weapon,Integer>();
    }

    public Player() {
        this(0,0,10,10,new String("Player " + (PlayerFactory.nb_instances)));
    }

    public String printStats(){
        return this.name + ":\nEnergie : " + this.energy + "\nPoints de vie : " + this.life;
    }

    public void useShield() {
        this.shield_up=true;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPosition(int x,int y) {
        this.setX(x);
        this.setY(y);
    }

    public int getLife() {
        return this.life;
    }

    public void setLife(int new_life) {
        this.life=new_life;
    }

    public boolean shieldIsUp() {
        return this.shield_up;
    }

    public void move(Direction d){
        this.x += d.x();
        this.y += d.y();
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }
    
    public ArrayList<Direction> possibleMoves(Grid grid){
        ArrayList<Direction> res = new ArrayList<Direction>();

        if(this.y > 0){
            res.add(Direction.z);
        }
        if(this.y < grid.getWidth() -1){
            res.add(Direction.s);
        }
        if(this.x > 0){
            res.add(Direction.q);
        }
        if(this.x < grid.getWidth() -1){
            res.add(Direction.d);
        }
        return res;
    }

    public String toString() {
        return "@";
    }
}
