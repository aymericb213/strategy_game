package modele;

public class Rifle implements Weapon {

	private Player owner;
	private int damage = 5;
	private int range = 3;

	public Rifle(Player owner) {
		this.owner = owner;
	}

	@Override
	public void fire(RealGrid g, Direction d) {

	}

	public void explode(RealGrid g) {
		throw new UnsupportedOperationException("Not supported.");
	}
}
