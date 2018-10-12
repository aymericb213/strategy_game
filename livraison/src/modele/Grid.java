package modele;

import java.util.*;

public class Grid {

	private int width;
	public Tile[] tiles;
	public Player[] players;

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

	public String toString() {
		String res = "";
		for (int i=0 ; i<this.tiles.length ; i++) {
			if (i%this.width == 0) {
				res+="\n";
			}
			res+=this.tiles[i];
		}
		return res;
	}

}
