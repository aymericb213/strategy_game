package modele;

/**
	* Classe fille de Tile représentant une mine.
*/
public class Mine extends Tile implements Weapon {

	private int damage;

	/**
		* Constructeur de la classe.
		* @param x
		* Ordonnée de la case.
		* @param y
		* Abscisse de la case.
	*/
  public Mine(int x, int y, int damage) {
    super(x,y);
		this.damage = damage;
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
  public void fire(Grid g) {
    throw new UnsupportedOperationException("Not supported.");
  }

  @Override
  public void explode(Grid g) {
		for (Player p : g.getPlayers()) {
			if (p.getX()==this.x && p.getY()==this.y) {
				
				p.setLife(p.getLife()-this.damage);
			}
		}
  }
}
