package modele;

import java.util.*;

public class Player extends Tile {

    private final String name;
    private int energy;
    private int life;
    private HashMap<Weapon,Integer> loadout;
    private boolean shield_up = false;
    private PlayerStrategy strategy;
    private PlayerGrid view;
    public Direction lastMove = Direction.z;
    private boolean selected;
    private int visionSize;
    private BulletThread threadShoot;
    private boolean isShooting = false;

    public Player(RealGrid g, int x, int y, int hp, int mp, String name) {
        super(x,y);
        this.visionSize = 3;
        this.life = hp;
        this.energy = mp;
        this.name = name;
        this.loadout = new HashMap<>();
        this.strategy = new RandomStrategy(this);
        this.view = new PlayerGrid(g,this);
        this.selected = false;
        this.threadShoot = new BulletThread(0,0,0,lastMove,this);
    }


    public Player(RealGrid g) {
        this(g,0,0,GameConfig.PLAYER_BASE_HP,GameConfig.PLAYER_BASE_AP, ("Player " + (PlayerFactory.nb_instances)));
        this.visionSize = 3;
    }

    public void act() {
        this.strategy.execute();
    }

    public void setStrategy(PlayerStrategy s) {
        this.strategy = s;
    }

    public void addWeapon(Weapon w, int ammo) {
        this.loadout.put(w,ammo);
    }

    public PlayerGrid getView() {
        return this.view;
    }

    public boolean isSelected(){
        return this.selected;
    }

    public void select(){
        this.selected = true;
    }

    public void unselect(){
        this.selected = false;
    }

    public int getLife() {
        return this.life;
    }

    public void setLife(int new_life) {
        this.life=new_life;
    }

    public int getEnergy() {
        return this.energy;
    }

    public void setEnergy(int new_energy) {
        this.energy=new_energy;
    }

    public boolean isShield_up() {
        return shield_up;
    }

    public void enableShield() {
        this.shield_up=true;
        this.energy-=GameConfig.SHIELD_COST;
    }

    public void disableShield() {
        this.shield_up = false;
    }

    /* Mouvement */
    public void move(Direction d) {
        if (this.possibleMoves().contains(d)) {
            this.view.setTileAt(this.x,this.y,new FreeTile(x,y));
            this.x += d.x();
            this.y += d.y();

            if (this.view.getTileAt(this.x,this.y) instanceof Bonus) {
                this.energy+=((Bonus)this.view.getTileAt(x,y)).getValue();
            }
            this.view.setTileAt(this.x,this.y,this);
            this.lastMove = d;
            this.energy-=GameConfig.MOVE_COST;
        }
    }

    public ArrayList<Direction> possibleMoves() {
        ArrayList<Direction> res = new ArrayList<>();

        if((this.y > 0) && !(view.getTileAt(this.x,this.y-1) instanceof Wall || view.getTileAt(this.x,this.y-1) instanceof Player)) {
            res.add(Direction.z);
        }

        if((this.y < (view.getModel().getGrid().length/view.getModel().getWidth())-1) && !(view.getTileAt(this.x,this.y+1) instanceof Wall || view.getTileAt(this.x,this.y+1) instanceof Player)){
            res.add(Direction.s);
        }

        if((this.x > 0) && !(view.getTileAt(this.x-1,this.y) instanceof Wall || view.getTileAt(this.x-1,this.y) instanceof Player)){
            res.add(Direction.q);
        }

        if((this.x < view.getModel().getWidth() -1) && !(view.getTileAt(this.x+1,this.y) instanceof Wall || view.getTileAt(this.x+1,this.y) instanceof Player)){
            res.add(Direction.d);
        }
        return res;
    }


    public ArrayList<ArrayList> visibleTiles(){
        ArrayList<ArrayList> t = new ArrayList<>();
        System.out.print(view.getTileAt(this.x, this.y));
        int c=1;

        while(c<this.visionSize){
            if(this.x+c>view.getModel().getWidth()){
                break;
            }else{
                if(!(view.getModel().getTileAt(this.x+c, this.y) instanceof FreeTile)){
                    break;
                }
                else{
                    ArrayList<Integer> pos = new ArrayList<>(2); pos.add(0,this.x+c); pos.add(1,this.y); t.add(pos); System.out.println("pos"+pos.toString()+"\n");
                }
            }
            c++;
        }
        c=1;

        while(c<this.visionSize){
            if(this.x-c<0){
                break;
            }else{
                if(!(view.getModel().getTileAt(this.x-c, this.y) instanceof FreeTile)){
                    break;
                }
                else{
                    ArrayList<Integer> pos = new ArrayList<>(2); pos.add(0,this.x-c); pos.add(1,this.y); t.add(pos); System.out.println("pos"+pos.toString()+"\n");
                }
            }
            c++;
        }
        c=1;

        while(c<this.visionSize){
            if(this.y+c>view.getModel().getGrid().length/view.getModel().getWidth()){
                break;
            }else{
                if(!(view.getModel().getTileAt(this.x, this.y+c) instanceof FreeTile)){
                    break;
                }
                else{
                    ArrayList<Integer> pos = new ArrayList<>(2); pos.add(0,this.x); pos.add(1,this.y+c); t.add(pos); System.out.println("pos"+pos.toString()+"\n");
                }
            }
            c++;
        }
        c=1;

        while(c<this.visionSize){
            if(this.y-c<0){
                break;
            }else{
                if(!(view.getModel().getTileAt(this.x, this.y-c) instanceof FreeTile)){
                    break;
                }
                else{
                    ArrayList<Integer> pos = new ArrayList<>(2); pos.add(0,this.x); pos.add(1,this.y-c); t.add(pos); System.out.println("pos"+pos.toString()+"\n");
                }
            }
            c++;
        }
        ArrayList<Integer> pos = new ArrayList<>(2); pos.add(0,this.x); pos.add(1,this.y); t.add(pos);
        //System.out.println(t.toString());
        return t;
    }

    public void takeDamage(int damage) {
        if (!(this.shield_up)) {
            this.life -= damage;
        }
    }

    /* Explosifs */
    public void plant(Mine m, Tile t) {
				if (t!=null) {
						m.setPosition(t.getX(),t.getY());
        		this.view.setTileAt(t.getX(),t.getY(),m);
        		//this.loadout.put(m, this.loadout.get(m)-1);
        		this.energy-=GameConfig.PLANT_COST;
						if (m instanceof Bomb) {
							this.view.addBomb((Bomb)m);
						}
				}
    }

    /* Tir */
    public void fire(Direction d) {
        this.isShooting = true;
        System.out.println("Je tir");
        this.lastMove = d;
        for (Weapon w : this.loadout.keySet()) {
            if (w.equals(new Rifle(this))) {
                //w.fire(this.view,d);
                System.out.println("Portée: "+((Rifle)w).getRange());
                threadShoot.ResetThread(this.x, this.y, ((Rifle)w).getRange(), d);
                threadShoot.start();
                //this.loadout.put(new Rifle(this), this.loadout.get(new Rifle(this))-1);
                this.energy-=GameConfig.FIRE_COST;
            }
        }
    }

    public boolean isShooting() {
        return isShooting;
    }

    public void notShooting(){
        this.isShooting = false;
    }

    public BulletThread getThreadShoot() {
        return threadShoot;
    }

    /**
    * Surcharge de hashCode().
    * Nécessaire au bon fonctionnement de la surcharge d'equals.
    * @return Le hashcode de l'objet.
    */
    @Override
    public int hashCode() {
        int code=13;
        code+=33*code+this.name.length();
        return code;
    }

    /**
    * Surcharge de equals.
    * Prend en compte l'égalité de coordonnées.
    * @param o
    * L'objet à comparer au noeud.
    * @return Le résultat du test d'égalité.*/
    @Override
    public boolean equals(Object o) {
        if (o==this) {
            return true;
        }
        if (!(o instanceof Player)) {
            return false;
        }
        Player p = (Player)o;
        return this.name.equals(p.name);
    }

		public String printStats() {
			return this.name + "\nPosition : " + this.x + " " + this.y + "\nEnergie : " + this.energy + "\nPoints de vie : " + this.life + "\nEquipement : "+ this.loadout;
		}

    @Override
    public String toString() {
        return this.shield_up ? "€" : "@";
    }
}
