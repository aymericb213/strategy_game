package modele;

import java.util.*;

public class Grid {

	private int width;
	public Tile[] tiles;
	private Player[] players;
	private boolean alternate_string = false;

	public Grid(int width, int height, int nb_players) {
		this.width = width;
		this.tiles = new Tile[width*height];
		this.players = new Player[nb_players];
	}

	public void generateRandomGrid() {
		Random r = new Random();
		for (int i=0 ; i<this.tiles.length ; i++) {
		Tile n = new FreeTile(i%this.width,i/this.width);
			double nr = r.nextDouble();
			if (nr < 0.1) {
				n = new Bonus(i%this.width,i/this.width, 50);
			} else if (nr >= 0.1 && nr < 0.4) {
				n = new Wall(i%this.width,i/this.width);
			}
			this.tiles[i]=n;
		}
	}

	public Player[] getPlayers() {
		return this.players;
	}

	public void addPlayer(Player p){
			this.players[PlayerFactory.nbInstance-1]=p;
	}

	public String[] buildStrings() {
		String[] res = new String[2];
		for (int i=0 ; i<this.tiles.length ; i++) {
			if (i%this.width == 0) {
				res[0]+="\n";
				res[1]+="\n";
			}
			res[0]+=this.tiles[i];
			for (Player p : this.players) {
				if (p.getX()==i%this.width && p.getY()==i/this.width) {
					res[1]+=p.toString();
				} else {
					res[1]+=this.tiles[i];
				}
			}
		}
		return res;
	}

	public String toString() {
		System.out.println("\033[H\033[2J");
		this.alternate_string= !(this.alternate_string);
		String[] print = this.buildStrings();
		return (this.alternate_string) ? print[0] : print[1];
	}

}
