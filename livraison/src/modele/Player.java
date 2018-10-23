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
	private PlayerStrategy strategy;
  private BufferedImage img;

  public Player(int x, int y, int hp, int mp, String name) {
    super(x,y);
		this.life = hp;
    this.energy = mp;
    this.name = name;
    this.loadout = new HashMap<Weapon,Integer>();
		this.strategy = new RandomStrategy(this);
  }

  public Player() {
    this(0,0,10,10,new String("Player " + (PlayerFactory.nb_instances)));
  }

  public void useShield() {
    this.shield_up=true;
  }

	public void act() {
		this.strategy.execute();
	}

	public void setStrategy(PlayerStrategy s) {
		this.strategy = s;
	}

	public void addWeapon(Weapon w, int ammo) {
		this.loadout.put(w,ammo);
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

  public BufferedImage getImg() {
    return img;
  }

  public void setImg(BufferedImage img) {
    this.img = img;
  }

	public void move(Direction d){
		this.x += d.x();
		this.y += d.y();
	}

  public ArrayList<Direction> possibleMoves(RealGrid grid){
    ArrayList<Direction> res = new ArrayList<Direction>();
    if((this.y > 0) && !(grid.getTileAt(this.x,this.y-1) instanceof Wall || grid.getTileAt(this.x,this.y-1) instanceof Player)) {
      res.add(Direction.z);
    }
    if((this.y < (grid.getGrid().length/grid.getWidth())-1) && !(grid.getTileAt(this.x,this.y+1) instanceof Wall || grid.getTileAt(this.x,this.y+1) instanceof Player)){
      res.add(Direction.s);
    }
    if((this.x > 0) && !(grid.getTileAt(this.x-1,this.y) instanceof Wall || grid.getTileAt(this.x-1,this.y) instanceof Player)){
      res.add(Direction.q);
    }
    if((this.x < grid.getWidth() -1) && !(grid.getTileAt(this.x+1,this.y) instanceof Wall || grid.getTileAt(this.x+1,this.y) instanceof Player)){
      res.add(Direction.d);
    }
    return res;
  }

	public String printStats() {
		return this.name + "\nPosition : " + this.x + " " + this.y + "\nEnergie : " + this.energy + "\nPoints de vie : " + this.life + "\nEquipement : "+ this.loadout;
	}

  public String toString() {
    return "@";
  }
}
