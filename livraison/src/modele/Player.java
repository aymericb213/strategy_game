package modele;

import java.util.*;
import java.lang.*;

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
    private boolean isShooting = false;
    private int index;
    private int nb_player;
    private boolean asTurn = false;
    private boolean isPlanting = false;

    /////////////ATTTETION
    private boolean plantingBomb = false;

    public Player(RealGrid g, int x, int y, int hp, int mp, String name) {
        super(x,y);
        this.nb_player = PlayerFactory.nb_instances;
        this.visionSize = GameConfig.PLAYER_FOV;
        this.life = hp;
        this.energy = mp;
        this.name = name;
        this.loadout = new HashMap<>();
        this.strategy = new RandomStrategy(this);
        this.view = new PlayerGrid(g,this);
        this.selected = false;
        this.nb_player = PlayerFactory.nb_instances;
        this.walkable=false;
    }


    public Player(RealGrid g) {
        this(g,0,0,GameConfig.PLAYER_BASE_HP,GameConfig.PLAYER_BASE_AP, ("Player " + (PlayerFactory.nb_instances)));
        this.walkable=false;
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

    public void setAsTurn(boolean asTurn) {
        this.asTurn = asTurn;
    }

    public boolean getAsTurn() {
        return asTurn;
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

    public void enablePlant(){
        isPlanting = true;
    }

    public void disablePlant(){
        isPlanting = false;
    }

    public void setLife(int new_life) {
        this.life=new_life;
    }

    public boolean isPlanting(){
        return isPlanting;
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
            this.view.setTileAt(new FreeTile(x,y));
            this.x += d.x();
            this.y += d.y();

            try {
              ((Bonus)this.view.getTileAt(x,y)).boost(this);
            } catch (ClassCastException not_a_bonus) {}
            try {
              ((Weapon)this.view.getModel().getTileAt(x,y)).explode(this.view.getModel(), this);
            } catch(ClassCastException not_a_weapon) { }

            this.view.setTileAt(this);
            this.lastMove = d;
            this.energy-=GameConfig.MOVE_COST;
        }
    }

    public ArrayList<Direction> possibleMoves() {
        ArrayList<Direction> res = new ArrayList<>();
        if((this.y > 0) && view.getTileAt(this.x,this.y-1).isWalkable()) {
            res.add(Direction.z);
        }

        if((this.y < (view.getModel().getGrid().length/view.getModel().getWidth())-1) && view.getTileAt(this.x,this.y+1).isWalkable()){
            res.add(Direction.s);
        }

        if((this.x > 0) && view.getTileAt(this.x-1,this.y).isWalkable()){
            res.add(Direction.q);
        }

        if((this.x < view.getModel().getWidth() -1) && view.getTileAt(this.x+1,this.y).isWalkable()){
            res.add(Direction.d);
        }
        return res;
    }

    public Weapon getWeapon(Weapon a){
        Set<Weapon> keys = this.loadout.keySet();
        for(Weapon w : keys){

        }
        return null;
    }









    public ArrayList<ArrayList> visibleTiles(){
        ArrayList<ArrayList> t = new ArrayList<>();
        //System.out.print(view.getTileAt(this.x, this.y));
        int c=1;

        while(c<this.visionSize){
            if(this.x+c>view.getModel().getWidth()){
                break;
            }else{
                if(!(view.getModel().getTileAt(this.x+c, this.y) instanceof FreeTile)){
                    break;
                }
                else{
                    ArrayList<Integer> pos = new ArrayList<>(2); pos.add(0,this.x+c); pos.add(1,this.y); t.add(pos); //System.out.println("pos"+pos.toString()+"\n");
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
                    ArrayList<Integer> pos = new ArrayList<>(2); pos.add(0,this.x-c); pos.add(1,this.y); t.add(pos); //System.out.println("pos"+pos.toString()+"\n");
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
                    ArrayList<Integer> pos = new ArrayList<>(2); pos.add(0,this.x); pos.add(1,this.y+c); t.add(pos); //System.out.println("pos"+pos.toString()+"\n");
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
                    ArrayList<Integer> pos = new ArrayList<>(2); pos.add(0,this.x); pos.add(1,this.y-c); t.add(pos); //System.out.println("pos"+pos.toString()+"\n");
                }
            }
            c++;
        }
        ArrayList<Integer> pos = new ArrayList<>(2); pos.add(0,this.x); pos.add(1,this.y); t.add(pos);
        //System.out.println(t.toString());
        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTT:");
        for(ArrayList l : t){
            for(Object i : l){
                System.out.print(i+":");
            }
            System.out.println();
        }
        System.out.println("end TTTTTTTTTTTTTTTTTTTTTTTT\n\n\n\n");
        return t;
    }





    public ArrayList<ArrayList<Integer>> visiblesTiles(){
        ArrayList<ArrayList<Integer>> visibles = new ArrayList<>();
        int jX = this.x ;
        int jY = this.y ;
        int mapSizeX = view.getModel().getWidth();
        int mapSizeY = view.getModel().getHeight();

//        for(int vX = 0; vX<mapSizeX/2; vX++){
//            for(int vY = 0; vY<mapSizeY/2; vY++){
//                if(!(jX==vX && jY==vX)){
//                    System.out.println(" test vx :"+vX+" vY:"+vY);testView(jX,jY,vX,vY,visibles);
//                }
//            }
//        }
        testView(jX,jY,5,5,visibles);

        System.out.println("visiblesTiles:");
        for(ArrayList l : visibles){
            for(Object i : l){
                System.out.print(i+":");
            }
            System.out.println();
        }
        System.out.println("end Visibles\n\n\n\n");
        return visibles;
    }

    public void testView(int jX, int jY, int vX, int vY, ArrayList visibles){
        visibles.removeAll(visibles);
        int mapSizeX = view.getModel().getWidth();
        int mapSizeY = view.getModel().getHeight();
        int[][] g = new int[mapSizeX][mapSizeY];

        for(int[] i : g){
            for(int j : i){
                j = 0;
            }
        }

        //affG(g);

        int distanceX = Math.abs(vX-jX);
        int distanceY = Math.abs(vY-jY);
        double hypothenuse = Math.sqrt(Math.pow(distanceX,2)+Math.pow(distanceY,2));
        int intHypothenuse = (int)(hypothenuse*20);

        double movingX = x*20;
        double movingY = y*20;
        if(intHypothenuse!=0){
            movingX/=intHypothenuse;
            movingY/=intHypothenuse;
        }

        float XX = jX*20;
        float YY = jY*20;
        boolean revX;
        boolean revY;
        revX = vX-jX<0;
        revY = vY-jY<0;

        System.out.println("points === jX:"+jX+" jY:"+jY);
        System.out.println("points === vX:"+vX+" vY:"+vY);
        System.out.println();

        for(int i=0; i<intHypothenuse; i++){

            if(revX){ XX-=movingX; }else{ XX+=movingX; }
            if(revY){ YY-=movingY; }else{ YY+=movingY; }

            int xtest = (int) Math.floor( ((int)(XX)+10) /20 );
            int ytest = (int) Math.floor( ((int)(YY)+10) /20 );

            System.out.println("x:"+xtest+" y:"+ytest);
            boolean question =  view.getModel().getTileAt(xtest,ytest).isWalkable() || view.getModel().getTileAt(xtest,ytest) instanceof Player;
            System.out.println("walkable ? "+question);
            if(question){ g[xtest][ytest] += 1; /*System.out.println("ON CONTINU LE TEST");*/}
            else{ /*System.out.println("ON QUITTE LE TEST");*/ return; }

        }

        //affG(g);
        ArrayList<Integer> tmp = new ArrayList<>(2);
        tmp.add(0,vX);
        tmp.add(1,vY);
        visibles.add(tmp);
        System.out.println("ON FINI LE TEST pour vX:"+vX+" vY:"+vY);
        return;

    }



    public void affG(int[][] g){
        System.out.println("start");
        for(int[] i : g){
            for(int j : i){
                System.out.print(j+" ");
            }
            System.out.println();
        }
        System.out.println("end");
    }

















    public void takeDamage(int damage) {
        if (!(this.shield_up)) {
            this.life -= damage;
            if (this.life<=0) {
              this.energy=0;
              this.view.setTileAt(new FreeTile(this.x,this.y));
            }
        } else {
            this.disableShield();
        }

    }

    /* Explosifs */
    public void plant(Mine m, Tile t) {
        if (t!=null) {
            m.setPosition(t.getX(),t.getY());
            this.view.setTileAt(m);
            //this.loadout.put(m, this.loadout.get(m)-1);
            this.energy-=GameConfig.PLANT_COST;
            try {
              this.view.addBomb((Bomb)m);
            } catch(ClassCastException not_a_bomb) {}
        }
    }

    /* Tir */
    public void fire(Direction d) {
        this.isShooting = true;
        System.out.println("Je tir");
        this.lastMove = d;
        for (Weapon w : this.loadout.keySet()) {
            if (w.equals(new Rifle(this))) {
                w.fire(this.view.getModel(),d);
                System.out.println("Portée: "+((Rifle)w).getRange());
                //this.loadout.put(this.getRifle(), this.loadout.get(this.getRifle())-1);
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

    public String getName() {
      return this.name;
    }

    public String printStats() {
        return "Position : " + this.x + " " + this.y + "\nEnergie : " + this.energy + "\nPoints de vie : " + this.life + "\nEquipement : "+ this.loadout;
    }

    public String printControls() {
      String controls = "\nn : tour auto     p : fin de tour     e : quitter";
      if (this.energy>=GameConfig.MOVE_COST) {
        controls+="\nz,q,s,d : déplacer joueur\n";
      }
      if (this.energy>=GameConfig.PLANT_COST) {
        controls+="m : poser mine      b : poser bombe     ";
      }
      if (this.energy>=GameConfig.FIRE_COST) {
        controls+="t : tirer     ";
      }
      if (this.energy>=GameConfig.SHIELD_COST && !this.shield_up) {
        controls+="a : activer bouclier";
      }
      return controls;
    }

    @Override
    public String toString() {
        return this.shield_up ? "€" : "@";
    }

    public boolean isPlantingBomb() {
        return plantingBomb;
    }

    public void enablePlantingBomb(){
        this.plantingBomb = true;
    }

    public void disablePlantingBomb(){
        this.plantingBomb = false;
    }
}
