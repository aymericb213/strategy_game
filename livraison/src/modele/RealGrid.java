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
        this.bombs = new ArrayList<>();
        this.generator = new RandomGeneration();
        System.out.println("Nombre de joueurs: "+nb_players);
    }

    public RealGrid() {
        this(0,0,5);
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

    public boolean isInBounds(int x, int y) {
        boolean check = false;
        if (x>=0 && x<this.width && y>=0 && y<this.tiles.length/this.width) {
            check = true;
        }
        return check;
    }

    public ArrayList<Tile> getNeighbouringTiles(Tile t) {
        ArrayList<Tile> neighbours = new ArrayList<>();
        neighbours.add(this.getTileAt(t.getX()+1,t.getY()));
        neighbours.add(this.getTileAt(t.getX()-1,t.getY()));
        neighbours.add(this.getTileAt(t.getX(),t.getY()+1));
        neighbours.add(this.getTileAt(t.getX(),t.getY()-1));
        neighbours.add(this.getTileAt(t.getX()-1,t.getY()+1));
        neighbours.add(this.getTileAt(t.getX()+1,t.getY()-1));
        neighbours.add(this.getTileAt(t.getX()+1,t.getY()+1));
        neighbours.add(this.getTileAt(t.getX()-1,t.getY()-1));
        return neighbours;
    }

    public ArrayList<FreeTile> getNeighbouringFreeTiles(Tile t) {
        ArrayList<FreeTile> valids = new ArrayList<>();
        for (Tile d : getNeighbouringTiles(t)) {
            if (d instanceof FreeTile) {
                valids.add((FreeTile)d);
            }
        }
        return valids;
    }

    @Override
    public Tile getTileAt(int x, int y) {
        return isInBounds(x,y) ? this.tiles[x+(y*this.width)] : null;
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
            //A voir si le joueur récupère vraiment toute son énergie.
            p.setEnergy(GameConfig.PLAYER_BASE_AP);
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

		
    public void removeBomb(Bomb b) {
        this.bombs.remove(b);	
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
