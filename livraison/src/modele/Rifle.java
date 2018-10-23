package modele;

public class Rifle implements Weapon {

	private Player owner;
	private int damage = 5;
	private int range = 3;

	public Rifle(Player owner) {
		this.owner = owner;
	}

	@Override
	public void fire(Grid g, Direction d) {

	}

	public void explode(Grid g) {
		throw new UnsupportedOperationException("Not supported.");
	}
}
