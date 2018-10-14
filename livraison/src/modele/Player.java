/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.*;

/**
 *
 * @author quentindeme
 */
public class Player {

	private int x;
	private int y;
  private final String name;
  private int energy;
  private int life;
	private HashMap<Weapon,Integer> loadout;
	private boolean shield_up = false;

  public Player(int x, int y, int hp, int mp, String name) {
		this.x = x;
		this.y = y;
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

	public String toString() {
		return "@";
	}

	public void useShield() {
		this.shield_up=true;
	}
}
