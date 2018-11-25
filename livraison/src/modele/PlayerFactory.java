package modele;

import java.util.*;

public final class PlayerFactory {

    private static PlayerFactory instance = null;
    public static int nb_instances = 0;
    public static ArrayList<Weapon> inventory = new ArrayList<Weapon>();

    private PlayerFactory() {
        super();
        fullLoadout();
    }

    public final static PlayerFactory getInstance() {
        if (PlayerFactory.instance == null) {
            PlayerFactory.instance = new PlayerFactory();
        }
        return PlayerFactory.instance;
    }

    public ArrayList<Weapon> fullLoadout() {
      inventory.add(new Rifle(new Player(new RealGrid(),"","")));
      inventory.add(new Mine(new Player(new RealGrid(),"","")));
      inventory.add(new Bomb(new Player(new RealGrid(),"","")));
      return inventory;
    }

    public Player build(RealGrid g, String classname, int hp_mod, int ap_mod, int rifle_ammo_mod, int bombs_mod, int mines_mod) {
      PlayerFactory.nb_instances++;
      Player p = new Player(g,"Player "+PlayerFactory.nb_instances, classname);
      p.setLife(p.getLife()+hp_mod);
      p.setBaseEnergy(p.getBaseEnergy()+ap_mod);
      p.addWeapon(this.inventory.get(0), GameConfig.RIFLE_BASE_AMMO+rifle_ammo_mod);
      p.addWeapon(this.inventory.get(1), GameConfig.MINE_BASE_COUNT+mines_mod);
      p.addWeapon(this.inventory.get(2), GameConfig.BOMB_BASE_COUNT+bombs_mod);
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
