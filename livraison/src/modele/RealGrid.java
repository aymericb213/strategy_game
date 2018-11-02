package modele;

import java.util.*;

public class RealGrid implements Grid {

	private int turn_number = 1;
  private int width;
  public Tile[] tiles;
  private Player[] players;

  public RealGrid(int width, int height, int nb_players) {
    this.width = width;
    this.tiles = new Tile[width*height];
    this.players = new Player[nb_players];
  }

  public void generateRandomGrid() {
    Random r = new Random();
    for (int i=0 ; i<this.tiles.length ; i++) {
    	Tile n = new FreeTile(i%this.width,i/this.width);
      double nr = 1;
      if (nr < 0.1) {
        n = new Bonus(i%this.width,i/this.width, 50);
      } else if (nr >= 0.1 && nr < 0.3) {
        n = new Wall(i%this.width,i/this.width);
      }
      this.tiles[i]=n;
    }
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

	public boolean isFreeInBounds(Tile t) {
		boolean check = false;
		if (t instanceof FreeTile && t.getX() >= 0 && t.getX() <= this.width && t.getY() >= 0 && t.getY() <= this.tiles.length/this.width) {
			check = true;
		}
		return check;
	}

	public ArrayList<FreeTile> getNeighbouringFreeTiles(Tile t) {
    ArrayList<FreeTile> valids = new ArrayList<FreeTile>();
      if (isFreeInBounds(this.getTileAt(t.getX()+1,t.getY()))) {
        valids.add((FreeTile)this.getTileAt(t.getX()+1,t.getY()));
			}
      if (isFreeInBounds(this.getTileAt(t.getX()-1,t.getY()))) {
				valids.add((FreeTile)this.getTileAt(t.getX()-1,t.getY()));
			}
      if (isFreeInBounds(this.getTileAt(t.getX(),t.getY()+1))) {
				valids.add((FreeTile)this.getTileAt(t.getX(),t.getY()+1));
			}
      if (isFreeInBounds(this.getTileAt(t.getX(),t.getY()-1))) {
				valids.add((FreeTile)this.getTileAt(t.getX(),t.getY()-1));
			}
      if (isFreeInBounds(this.getTileAt(t.getX()-1,t.getY()+1))) {
				valids.add((FreeTile)this.getTileAt(t.getX()-1,t.getY()+1));
			}
      if (isFreeInBounds(this.getTileAt(t.getX()+1,t.getY()-1))) {
				valids.add((FreeTile)this.getTileAt(t.getX()+1,t.getY()-1));
			}
      if (isFreeInBounds(this.getTileAt(t.getX()+1,t.getY()+1))) {
				valids.add((FreeTile)this.getTileAt(t.getX()+1,t.getY()+1));
			}
      if (isFreeInBounds(this.getTileAt(t.getX()-1,t.getY()-1))) {
				valids.add((FreeTile)this.getTileAt(t.getX()-1,t.getY()-1));
			}
    return valids;
	}

	@Override
	public Tile getTileAt(int x, int y) {
		return this.tiles[x+(y*this.width)];
	}

	public int getTurnNumber() {
		return this.turn_number;
	}

	public void nextTurn() {
		this.turn_number++;
	}

  public Tile[] getGrid() {
    return tiles;
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
