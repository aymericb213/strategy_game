package modele;

public class Rifle implements Weapon {

	private Player owner;
	private int damage;
	private int range;

	public Rifle(Player owner, int damage, int range) {
		this.owner = owner;
		this.damage = damage;
		this.range = range;
	}

	@Override
	public void fire(Grid g, Direction d) {

	}

	public void explode(RealGrid g) {
		throw new UnsupportedOperationException("Not supported.");
	}
}
