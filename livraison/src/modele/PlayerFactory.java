package modele;

public final class PlayerFactory {

	private static PlayerFactory instance = null;
	public static int nb_instances = 0;

	private PlayerFactory() {
		super();
	}

	public final static PlayerFactory getInstance() {
		if (PlayerFactory.instance == null) {
			PlayerFactory.instance = new PlayerFactory();
		}
		return PlayerFactory.instance;
	}

	public Player buildBasic() {
		PlayerFactory.nb_instances++;
		return new Player();
	}

	public Player buildTank() {
		PlayerFactory.nb_instances++;
		return new Player(0,0,50,10,new String("Player " + this.nb_instances));
	}
}
