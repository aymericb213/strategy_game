package modele;

/**
	* Classe fille de Tile représentant une mine.
*/
public class Mine extends Tile implements Weapon {

	protected Player owner;
	protected int damage = 10;

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

	@Override
	public Player getOwner() {
		return this.owner;
	}

	@Override
	public void fire(Grid g, Direction d) {
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public void explode(RealGrid g) {
		for (Player p : g.getPlayers()) {
			if (p.getX()==this.x && p.getY()==this.y) {
				p.takeDamage(this.damage);
				g.getGrid()[this.x+this.y*g.getWidth()]=new FreeTile(this.x,this.y);
			}
		}
	}

	@Override
	public int getRange(){
		return 1;
	}

	/**
		* Surcharge de hashCode().
		* Nécessaire au bon fonctionnement de la surcharge d'equals.
		* @return Le hashcode de l'objet.
	*/
	@Override
	public int hashCode() {
		int code=11;
		code+=37*code+this.owner.getX();
		code+=37*code+this.owner.getY();
		return code;
	}

	/**
		* Surcharge de equals.
		* Prend en compte l'égalité de coordonnées.
		* @param o
		* L'objet à comparer au noeud.
		* @return Le résultat du test d'égalité.
	*/
	@Override
	public boolean equals(Object o) {
		if (o==this) {
			return true;
		}
		if (!(o instanceof Mine)) {
			return false;
		}
		Mine m = (Mine)o;
		return this.owner.equals(m.getOwner());
	}

	/**
		* Retourne la représentation de l'objectif.
		* @return Un caractère représentant l'objectif.
	*/
  @Override
  public String toString() {
    return ";";
  }

}
