package modele;

import java.util.*;

public class RealGrid implements Grid {

  private int width;
  public Tile[] tiles;
  private Player[] players;
  private boolean alternate_string = false;

  public RealGrid(int width, int height, int nb_players) {
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
          } else if (nr >= 0.1 && nr < 0.3) {
              n = new Wall(i%this.width,i/this.width);
          }
          this.tiles[i]=n;
      }
  }

	@Override
	public Tile getTileAt(int x, int y) {
		return this.tiles[x+(y*this.width)];
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
	}
        
    public void displayGrid(){
        System.out.println(Arrays.toString(this.tiles));
    }

    public String toString() {
        displayGrid();
            StringBuilder res = new StringBuilder();
            for (int i=0 ; i<this.tiles.length ; i++) {
                    if (i>0 && i%this.width == 0) {
                            res.append("\n");
                    }
                    for (Player p : this.players) {
                            if (p.getX()==i%this.width && p.getY()==i/this.width) {
                                    res.append(p.toString());
                                    break;
                            } else if (this.players[PlayerFactory.nb_instances-1]==p){
                                    res.append(this.tiles[i]);
                            }
                    }
            }
        StringBuilder res2 = new StringBuilder(res.toString());
        for (Player p : this.players) {
                int position = p.getX()+(p.getY()*(this.width+1));
                res2.replace(position,position+1,this.tiles[position-p.getY()].toString());
        }
        this.alternate_string= !(this.alternate_string);
        System.out.println("\033[H\033[2J");
        return (this.alternate_string) ? res.toString() : res2.toString();
    }



}
