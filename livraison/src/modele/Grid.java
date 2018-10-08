package modele;

import java.util.*;

public class Grid {

	public Tile[] tiles;
	public Tile[] players;

	public Grid(int width, int height, int nb_players) {
		this.tiles = new Tile[width*height];
		this.players = new Tile[nb_players];
	}

	

}
