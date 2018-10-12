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
  public static int nbInstance = 0;
  private final String name;
  private int energy;
  private int life;
	private HashMap<Weapon,Integer> loadout;

  public Player(int x, int y,String name){
    nbInstance++;
		this.x = x;
		this.y = y;
		this.energy = 10;
    this.life = 10;
    this.name = name;
		this.loadout = new HashMap<Weapon,Integer>();
  }

  public Player(){
		this(0,0,new String("Player " + nbInstance));
  }

  public String printStats(){
    StringBuilder res = new StringBuilder();
    res.append(name+":\n");
    res.append("Energie : "+energy);
    res.append("\nPoints de vie : "+life);
    return res.toString();
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

	public String toString() {
		return "\u001B31;1m@";
	}
}
