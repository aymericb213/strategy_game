package modele;

/**
	* Classe fille de Tile représentant un bonus.
*/
public class Bomb extends Mine {

	//Nombre de tours avant explosion
	private int delay = 3;
	/**
		* Constructeur de la classe.
		* @param x
		* Ordonnée de la case.
		* @param y
		* Abscisse de la case.
	*/

	public Bomb(Player owner) {
		super(owner);
	}

  public Bomb(Player owner, int x, int y) {
    super(owner,x,y);
  }

	public void tick() {
		this.delay--;
	}

	@Override
	public void explode(RealGrid g) {
		if (this.delay==0) {
			for (Tile t : g.getNeighbouringTiles(this)) {
				for (Player p : g.getPlayers()) {
					if (p.getX()==t.getX() && p.getY()==t.getY()) {
						if (!(p.shieldIsUp())) {
							p.setLife(p.getLife()-this.damage);
						}
					}
				}
			}
			g.getGrid()[this.x+this.y*g.getWidth()]=new FreeTile(this.x,this.y);
		} else {
			super.explode(g);
		}
	}

	/**
		* Retourne la représentation de l'objectif.
		* @return Un caractère représentant l'objectif.
	*/
  @Override
  public String toString() {
    return ""+this.delay;
  }
}
