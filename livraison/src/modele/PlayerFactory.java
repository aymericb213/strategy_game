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

    public Player build(RealGrid g, String classname, int hp_mod, int ap_mod, int rifle_ammo_mod, int bombs_mod, int mines_mod) {
      PlayerFactory.nb_instances++;
      Player p = new Player(g,"Player "+PlayerFactory.nb_instances, classname);
      p.setLife(p.getLife()+hp_mod);
      p.setEnergy(p.getEnergy()+ap_mod);
      p.addWeapon(new Rifle(p), GameConfig.RIFLE_BASE_AMMO+rifle_ammo_mod);
      p.addWeapon(new Mine(p), GameConfig.MINE_BASE_COUNT+mines_mod);
      p.addWeapon(new Bomb(p), GameConfig.BOMB_BASE_COUNT+bombs_mod);
      return p;
    }

    public Player buildBasic(RealGrid g) {
      return build(g,"Basic",0,0,0,0,0);
    }

    public Player buildTank(RealGrid g) {
      return build(g,"Tank",20,0,0,0,0);
    }

    public Player buildMarksman(RealGrid g) {
      return build(g,"Marksman",-5,4,30,-5,-2);
    }

    public Player buildEngineer(RealGrid g) {
      return build(g,"Engineer",0,0,-15,5,3);
    }
}
