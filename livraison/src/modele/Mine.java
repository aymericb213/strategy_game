package modele;

/**
	* Classe fille de Tile représentant une mine.
*/
public class Mine extends Tile implements Weapon {

	private Player owner;
	private int damage = 10;

	/**
		* Constructeur de la classe.
		* @param x
		* Ordonnée de la case.
		* @param y
		* Abscisse de la case.
	*/

	public Mine(Player owner) {
		super(-1,-1);
		this.owner = owner;
	}

  public Mine(Player owner, int x, int y) {
    super(x,y);
		this.owner = owner;
  }

	/**
		* Retourne la représentation de l'objectif.
		* @return Un caractère représentant l'objectif.
	*/
  @Override
  public String toString() {
    return ";";
  }

  @Override
  public void fire(RealGrid g, Direction d) {
    throw new UnsupportedOperationException("Not supported.");
  }

  @Override
  public void explode(RealGrid g) {
		for (Player p : g.getPlayers()) {
			if (p.getX()==this.x && p.getY()==this.y) {
				g.getGrid()[(this.y*g.getWidth())+this.x]=new FreeTile(x,y);
				if (!(p.shieldIsUp())) {
					p.setLife(p.getLife()-this.damage);
				}
			}
		}
  }
}
