package modele;

public class PlayerGrid implements Grid {

	private RealGrid model;
	private Player client;

	public PlayerGrid(RealGrid g, Player p) {
		this.model = g;
		this.client = p;
	}


	public boolean playerCanSee(Tile t) {
		return (t instanceof Bomb || t instanceof Mine) && (((Bomb)t).getOwner().equals(this.client) || ((Mine)t).getOwner().equals(this.client));
	}

	@Override
	public Tile getTileAt(int x, int y) {
		Tile test = model.getTileAt(x,y);
		if (playerCanSee(test)) {
			return test;
		} else {
			return new FreeTile(x,y);
		}
	}

	@Override
	public int getWidth() {
		return model.getWidth();
	}

	@Override
	public Tile[] getGrid() {
		return model.getGrid();
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
    for (int i=0 ; i<this.getGrid().length ; i++) {
      if (i>0 && i%this.getWidth() == 0) {
        res.append("\n");
      }
			if (playerCanSee(this.getGrid()[i])) {
				res.append(this.getGrid()[i]);
			} else {
				res.append(new FreeTile(-1,-1).toString());//affichage de FreeTile si la case est une bombe ou une mine adverse
			}
    }
    return res.toString();
	}
}
