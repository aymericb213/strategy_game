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

  private final String name;
  private int energy;
  private int life;
  private HashMap<Weapon,Integer> loadout;
  private boolean shield_up = false;
	private PlayerStrategy strategy;
	private Grid view;
  public Direction lastMove = Direction.z;
  private boolean selected;

  public Player(RealGrid g, int x, int y, int hp, int mp, String name) {
    super(x,y);
		this.life = hp;
    this.energy = mp;
    this.name = name;
    this.loadout = new HashMap<Weapon,Integer>();
		this.strategy = new RandomStrategy(this);
		this.view = new PlayerGrid(g,this);
    this.selected = false;
  }

  public Player(RealGrid g) {
    this(g,0,0,10,10,new String("Player " + (PlayerFactory.nb_instances)));
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


    public boolean isSelected(){
        return this.selected;
    }

    public void select(){
        this.selected = true;
    }

    public void unselect(){
        this.selected = false;
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

	/* Mouvement */
	public void move(Direction d) {
		try {
			this.view.getGrid()[x+(y*this.view.getWidth())]=new FreeTile(x,y);
			this.x += d.x();
			this.y += d.y();
			this.view.getGrid()[x+(y*this.view.getWidth())]=this;
      this.lastMove = d;
		} catch (ArrayIndexOutOfBoundsException e) {
			this.x -= d.x();
			this.y -= d.y();
			this.view.getGrid()[x+(y*this.view.getWidth())]=this;
			System.out.println("Mouvement non autorisé");
		}
	}

  public ArrayList<Direction> possibleMoves() {
    ArrayList<Direction> res = new ArrayList<Direction>();
    if((this.y > 0) && !(view.getTileAt(this.x,this.y-1) instanceof Wall || view.getTileAt(this.x,this.y-1) instanceof Player)) {
      res.add(Direction.z);
    }
    if((this.y < (view.getGrid().length/view.getWidth())-1) && !(view.getTileAt(this.x,this.y+1) instanceof Wall || view.getTileAt(this.x,this.y+1) instanceof Player)){
      res.add(Direction.s);
    }
    if((this.x > 0) && !(view.getTileAt(this.x-1,this.y) instanceof Wall || view.getTileAt(this.x-1,this.y) instanceof Player)){
      res.add(Direction.q);
    }
    if((this.x < view.getWidth() -1) && !(view.getTileAt(this.x+1,this.y) instanceof Wall || view.getTileAt(this.x+1,this.y) instanceof Player)){
      res.add(Direction.d);
    }
    return res;
  }

	/* Explosifs */
	public void plantMine(Tile t) {
		this.view.getGrid()[t.getX()+t.getY()*this.view.getWidth()]= new Mine(this, t.getX(), t.getY());
		this.loadout.put(new Mine(this), 42);
	}

	public void plantBomb(Tile t) {
		this.view.getGrid()[t.getX()+t.getY()*this.view.getWidth()]= new Bomb(this, t.getX(), t.getY());
		this.loadout.put(new Bomb(this), 51);
	}

	/* Tir */
	public void fire(Direction d) {

	}

	/**
		* Surcharge de hashCode().
		* Nécessaire au bon fonctionnement de la surcharge d'equals.
		* @return Le hashcode de l'objet.
	*/
	@Override
	public int hashCode() {
		int code=13;
		code+=33*code+this.name.length();
		return code;
	}

	/**
		* Surcharge de equals.
		* Prend en compte l'égalité de coordonnées.
		* @param o
		* L'objet à comparer au noeud.
		* @return Le résultat du test d'égalité.
	*/
	@Override
	public boolean equals(Object o) {
		if (o==this) {
			return true;
		}
		if (!(o instanceof Player)) {
			return false;
		}
		Player p = (Player)o;
		return this.name==p.name;
	}

	public String printStats() {
		return this.name + "\nPosition : " + this.x + " " + this.y + "\nEnergie : " + this.energy + "\nPoints de vie : " + this.life + "\nEquipement : "+ this.loadout;
	}

  public String toString() {
    return "@";
  }
}
