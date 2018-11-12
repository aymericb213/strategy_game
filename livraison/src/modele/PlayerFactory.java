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

	public Player buildBasic(RealGrid g) {
		PlayerFactory.nb_instances++;
		Player p = new Player(g);
		p.addWeapon(new Rifle(p), GameConfig.RIFLE_BASE_AMMO);
		p.addWeapon(new Bomb(p), GameConfig.BOMB_BASE_COUNT);
		p.addWeapon(new Mine(p), GameConfig.MINE_BASE_COUNT);
		return p;
	}

	public Player buildTank(RealGrid g) {
		PlayerFactory.nb_instances++;
		return new Player(g,0,0,50,10,new String("Player " + this.nb_instances));
	}
}
