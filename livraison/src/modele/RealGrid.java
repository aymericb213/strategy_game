package modele;

import java.util.*;

public class RealGrid implements Grid {

	private int turn_number = 1;
  private int width;
  private Tile[] tiles;
  private Player[] players;
	private ArrayList<Bomb> bombs;
	private GridStrategy generator;

  public RealGrid(int width, int height, int nb_players) {
    this.width = width;
    this.tiles = new Tile[width*height];
    this.players = new Player[nb_players];
		this.bombs = new ArrayList<Bomb>();
		this.generator = new RandomGeneration();
  }

	public RealGrid() {
		this(0,0,0);
	}

  public void createGrid() {
    generator.generate(this);
  }

	public boolean gameIsOver() {
		int cpt = 0;
		for (Player p : players) {
			if (p.getLife() > 0) {
				cpt++;
			}
		}
		return cpt==1;
	}

	public boolean isInBounds(Tile t) {
		boolean check = false;
		if (t.getX() >= 0 && t.getX() <= this.width && t.getY() >= 0 && t.getY() <= this.tiles.length/this.width) {
			check = true;
		}
		return check;
	}

	public ArrayList<Tile> getNeighbouringTiles(Tile t) {
    ArrayList<Tile> neighbours = new ArrayList<Tile>();
      if (isInBounds(this.getTileAt(t.getX()+1,t.getY()))) {
        neighbours.add(this.getTileAt(t.getX()+1,t.getY()));
			}
      if (isInBounds(this.getTileAt(t.getX()-1,t.getY()))) {
				neighbours.add(this.getTileAt(t.getX()-1,t.getY()));
			}
      if (isInBounds(this.getTileAt(t.getX(),t.getY()+1))) {
				neighbours.add(this.getTileAt(t.getX(),t.getY()+1));
			}
      if (isInBounds(this.getTileAt(t.getX(),t.getY()-1))) {
				neighbours.add(this.getTileAt(t.getX(),t.getY()-1));
			}
      if (isInBounds(this.getTileAt(t.getX()-1,t.getY()+1))) {
				neighbours.add(this.getTileAt(t.getX()-1,t.getY()+1));
			}
      if (isInBounds(this.getTileAt(t.getX()+1,t.getY()-1))) {
				neighbours.add(this.getTileAt(t.getX()+1,t.getY()-1));
			}
      if (isInBounds(this.getTileAt(t.getX()+1,t.getY()+1))) {
				neighbours.add(this.getTileAt(t.getX()+1,t.getY()+1));
			}
      if (isInBounds(this.getTileAt(t.getX()-1,t.getY()-1))) {
				neighbours.add(this.getTileAt(t.getX()-1,t.getY()-1));
			}
    return neighbours;
	}

	public ArrayList<FreeTile> getNeighbouringFreeTiles(Tile t) {
		ArrayList<FreeTile> valids = new ArrayList<FreeTile>();
		for (Tile d : getNeighbouringTiles(t)) {
			if (d instanceof FreeTile) {
				valids.add((FreeTile)d);
			}
		}
		return valids;
	}

	@Override
	public Tile getTileAt(int x, int y) {
		return this.tiles[x+(y*this.width)];
	}

	@Override
	public void setTileAt(int x, int y, Tile t) {
		this.tiles[x+(y*this.width)]=t;
	}

	public void nextTurn() {
		for (Bomb b : this.bombs) {
			b.tick();
			b.explode(this);
		}
		for (Player p : this.players) {
			p.disableShield();
		}
		this.turn_number++;
	}

	public int getTurnNumber() {
		return this.turn_number;
	}

  public Tile[] getGrid() {
    return this.tiles;
  }

	public void setGrid(Tile[] new_grid) {
		this.tiles = new_grid;
	}

	public Player[] getPlayers() {
    return this.players;
	}

  public int getWidth() {
		return this.width;
	}

	public void setWidth(int new_width) {
		this.width = new_width;
	}

	public void addPlayer(Player p){
    this.players[PlayerFactory.nb_instances-1]=p;
		this.tiles[p.getX()+(p.getY()*this.width)]=p;
	}

	public void addBomb(Bomb b){
		this.bombs.add(b);
	}

  public void displayGrid() {
    System.out.println(Arrays.toString(this.tiles));
  }

	@Override
  public String toString() {
    StringBuilder res = new StringBuilder();
    for (int i=0 ; i<this.tiles.length ; i++) {
      if (i>0 && i%this.width == 0) {
        res.append("\n");
      }
      res.append(this.tiles[i]);
    }
    return res.toString();
  }
}
