package modele;

public class PlayerGrid implements Grid {

	private RealGrid g;
	private Player p;

	public PlayerGrid(RealGrid g, Player p) {
		this.g = g;
		this.p = p;
	}

	@Override
	public Tile getTileAt(int x, int y) {
		return g.getGrid()[x+(y*g.getWidth())];
	}

	@Override
	public int getWidth() {
		return g.getWidth();
	}

	@Override
	public Tile[] getGrid() {
		return g.getGrid();
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
    for (int i=0 ; i<this.getGrid().length ; i++) {
      if (i>0 && i%this.getWidth() == 0) {
        res.append("\n");
      }
			if (!(this.getGrid()[i] instanceof Bomb || this.getGrid()[i] instanceof Mine) && !(((Bomb)this.getGrid()[i]).getOwner().equals(p) || ((Mine)this.getGrid()[i]).getOwner().equals(p))) {
      	res.append(this.getGrid()[i]);
			} else {
				res.append("_");//affichage de FreeTile si la case est une bombe ou une mine adverse
			}
    }
    return res.toString();
	}
}
