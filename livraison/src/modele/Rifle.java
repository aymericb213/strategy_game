package modele;

public class Rifle implements Weapon {

	private Player owner;
	private int damage = GameConfig.RIFLE_DAMAGE;
	private int range = GameConfig.RIFLE_RANGE;

	public Rifle(Player owner) {
		this.owner = owner;
	}

	@Override
	public Player getOwner() {
		return this.owner;
	}

	@Override
	public void fire(Grid g, Direction d) {
		for (int i=1 ; i<=this.range ; i++) {
			Tile lof = g.getTileAt(this.owner.getX()+(i*d.x()),this.owner.getY()+(i*d.y()));
			if (lof instanceof Player) {
				((Player)lof).takeDamage(this.damage);
			}
		}
	}

	public void explode(RealGrid g) {
		throw new UnsupportedOperationException("Not supported.");
	}

	/**
		* Surcharge de hashCode().
		* Nécessaire au bon fonctionnement de la surcharge d'equals.
		* @return Le hashcode de l'objet.
	*/
	@Override
	public int hashCode() {
		int code=17;
		code+=31*code+this.owner.getX();
		code+=31*code+this.owner.getY();
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
		if (!(o instanceof Rifle)) {
			return false;
		}
		Rifle r = (Rifle)o;
		return this.owner.equals(r.getOwner());
	}

	@Override
	public int getRange(){
		return this.range;
	}
}
