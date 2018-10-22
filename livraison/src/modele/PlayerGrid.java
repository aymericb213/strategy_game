package modele;

public class PlayerGrid implements Grid {

	private RealGrid g;
	private Player p;

	@Override
	public Tile getTileAt(int x, int y) {
		return g.getGrid()[x+(y*g.getWidth())];
	}

	@Override
	public String toString() {
		return "";
	}
}
