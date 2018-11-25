package modele;

import java.util.*;
import static java.lang.Math.*;

public class Player extends Tile {

    private final String name;
    private final String classname;
    private int energy;
    private int life;
    private final HashMap<Weapon,Integer> loadout;
    private boolean shield_up = false;
    private PlayerStrategy strategy;
    private final PlayerGrid view;
    public Direction lastMove = Direction.z;
    private boolean selected;
    private final int visionSize;
    private boolean isShooting = false;
    private boolean asTurn = false;
    private boolean isPlanting = false;
    private int baseEnergy;

    /////////////ATTTETION
    private boolean plantingBomb = false;

    public Player(RealGrid g, String name, String classname) {
        super(0,0);
        this.visionSize = GameConfig.PLAYER_FOV;
        this.life = GameConfig.PLAYER_BASE_HP;
        this.energy = GameConfig.PLAYER_BASE_AP;
        this.baseEnergy = GameConfig.PLAYER_BASE_AP;
        this.name = name;
        this.classname = classname;
        this.loadout = new HashMap<>();
        this.strategy = new RandomStrategy(this);
        this.view = new PlayerGrid(g,this);
        this.selected = false;
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

    public int getBaseEnergy() {
        return baseEnergy;
    }

    public void setBaseEnergy(int baseEnergy) {
        this.baseEnergy = baseEnergy;
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
        for(Weapon w : keys){}
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
//        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTT:");
//        for(ArrayList l : t){
//            for(Object i : l){
//                System.out.print(i+":");
//            }
//            System.out.println();
//        }
//        System.out.println("end TTTTTTTTTTTTTTTTTTTTTTTT\n\n\n\n");
        return t;
    }

    public ArrayList<ArrayList<Integer>> visiblesTiles(){
        ArrayList<ArrayList<Integer>> visibles = new ArrayList<>();
        int jX = this.x ;
        int jY = this.y ;
        int mapSizeX = view.getModel().getWidth();
        int mapSizeY = view.getModel().getHeight();

        for(int vX = 0; vX<mapSizeX; vX++){
            for(int vY = 0; vY<mapSizeY; vY++){
                if(!(jX==vX && jY==vX)){
                    /*System.out.println(" test vx :"+vX+" vY:"+vY);*/testView(jX,jY,vX,vY,visibles);
                }
            }
        }
//        for(int vY = 0; vY<mapSizeY; vY++){
//            for(int vX = 0; vX<mapSizeX; vX++){
//                Tile t = view.getModel().getTileAt(vX, vY);
//                ArrayList<Integer> a = new ArrayList<>(); a.add(0,vX); a.add(1,vY);
//                boolean isWall = t instanceof Wall;
//                boolean isContained = visibles.contains(a);
//                if( "#".equals(t.toString()) && !(isContained) ){
//                    System.out.println(" test vx :"+vX+" vY:"+vY+"\nisWall ? "+isWall+"\nisContained ? "+isContained);
//
//                    if(vX>0){System.out.print("droite ");
//                    ArrayList<Integer> testd = new ArrayList<>(); a.add(0,vX-1); a.add(1,vY);
//                    boolean asVisibleD = visibles.contains(testd) ;
//                        if ( !"#".equals(view.getModel().getTileAt(vX-1,vY).toString()) && asVisibleD){
//                            visibles.add(a);
//                        }
//                    }
//
//                    if(vX<mapSizeX){System.out.print("gauche ");
//                    ArrayList<Integer> testg = new ArrayList<>(); a.add(0,vX+1); a.add(1,vY);
//                    boolean asVisibleG = visibles.contains(testg) ;
//                        if ( !"#".equals(view.getModel().getTileAt(vX+1,vY).toString()) && asVisibleG){
//                            visibles.add(a);
//                        }
//                    }
//
//                    if(vY>0){System.out.print("haut ");
//                    ArrayList<Integer> testh = new ArrayList<>(); a.add(0,vX); a.add(1,vY-1);
//                    boolean asVisibleH = visibles.contains(testh) ;
//                        if ( !"#".equals(view.getModel().getTileAt(vX,vY-1).toString()) && asVisibleH){
//                            visibles.add(a);
//                        }
//                    }
//
//                    if(vY<mapSizeY){System.out.println("bas");
//                    ArrayList<Integer> testb = new ArrayList<>(); a.add(0,vX); a.add(1,vY+1);
//                    boolean asVisibleB = visibles.contains(testb) ;
//                        if ( !"#".equals(view.getModel().getTileAt(vX,vY+1).toString()) && asVisibleB){
//                            visibles.add(a);
//                        }
//                    }
//
//                }
//            }
//        }


        //testView(jX,jY,5,5,visibles);

//        System.out.println("visiblesTiles:");
//        for(ArrayList l : visibles){
//            for(Object i : l){
//                System.out.print(i+":");
//            }
//            System.out.println();
//        }
//        System.out.println("end Visibles\n\n\n\n");
        return visibles;
    }

    public void testView(int jX, int jY, int vX, int vY, ArrayList visibles){
        //visibles.removeAll(visibles);
        int mapSizeX = view.getModel().getWidth(); //creation de g[][], liste de valeurs parcourues
        int mapSizeY = view.getModel().getHeight();
        int[][] g = new int[mapSizeX][mapSizeY];

        for(int[] i : g){ //parcours g
            for(int j : i){
                j = 0; //met toutes les valeurs de g à 0
            }
        }

//        affG(g);

        int distanceX = abs(vX-jX); //calcul la distance absolue entre le joueur et le point (X)
        int distanceY = abs(vY-jY); //calcul la distance absolue entre le joueur et le point (X)
        double hypothenuse = sqrt(pow(distanceX,2)+pow(distanceY,2)); //calcul la longueur de l'hypothenuse
        int intHypothenuse = ((int)(hypothenuse))*20; //arrondi la longueur de l'hypothenuse en int

        double movingX = distanceX*20; //calcul la distance par mouvement de test en X
        double movingY = distanceY*20; //calcul la distance par mouvement de test en Y
        if(intHypothenuse!=0){
            movingX/=intHypothenuse; //la divise par la taille de l'hypothenuse si la distance est supérieure à 0
            movingY/=intHypothenuse; //la divise par la taille de l'hypothenuse si la distance est supérieure à 0
        }

        float XX = jX*20; //creer XX valeur en jX * 20
        float YY = jY*20; //creer YY valeur en jY * 20
        boolean revX;
        boolean revY;
        revX = vX-jX<0;
        revY = vY-jY<0;

//        System.out.println("points === jX:"+jX+" jY:"+jY);
//        System.out.println("points === vX:"+vX+" vY:"+vY);
//        System.out.println();

        for(int i=0; i<intHypothenuse; i++){

            if(revX){ XX-=movingX; }else{ XX+=movingX; } //deplace XX de movingX (vers vX*20 donc)
            if(revY){ YY-=movingY; }else{ YY+=movingY; }

            int xtest = (int) floor( ((int)(XX)+10) /20 );
            int ytest = (int) floor( ((int)(YY)+10) /20 );

//            System.out.println("x:"+xtest+" y:"+ytest);
            boolean question =  view.getModel().getTileAt(xtest,ytest).isWalkable() || view.getModel().getTileAt(xtest,ytest) instanceof Player;
//            System.out.println("walkable ? "+question);
            if(question){ g[xtest][ytest] += 1; /*System.out.println("ON CONTINU LE TEST");*/}
            else{ /*System.out.println("ON QUITTE LE TEST");*/ return; }

        }

        //affG(g);
        ArrayList<Integer> tmp = new ArrayList<>(2);
        tmp.add(0,vX);
        tmp.add(1,vY);
        visibles.add(tmp);
//        System.out.println("ON FINI LE TEST pour vX:"+vX+" vY:"+vY);
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
              this.view.getModel().getActivePlayers().remove(this);
            }
        } else {
            this.disableShield();
        }

    }

    public boolean actionIsAvailable(int cost, int weapon) {
      boolean enough_ap = (this.energy>=cost);
      return (weapon>=0 && weapon<PlayerFactory.inventory.size()) ? (enough_ap && this.loadout.get(PlayerFactory.inventory.get(weapon))>0) : enough_ap;
    }

    /* Explosifs */
    public void plant(Mine m, Tile t) {
        if (t!=null) {
            m.setPosition(t.getX(),t.getY());
            this.view.setTileAt(m);
            try {
              this.view.addBomb((Bomb)m);
              this.loadout.put(PlayerFactory.inventory.get(2), this.loadout.get(PlayerFactory.inventory.get(2))-1);
            } catch(ClassCastException not_a_bomb) {
              this.loadout.put(PlayerFactory.inventory.get(1), this.loadout.get(PlayerFactory.inventory.get(1))-1);
            }
            this.energy-=GameConfig.PLANT_COST;
        }
    }

    /* Tir */
    public void fire(Direction d) {
        this.isShooting = true;
        this.lastMove = d;
        for (Weapon w : this.loadout.keySet()) {
            if (w instanceof Rifle) {
              Rifle fire_rifle = new Rifle(this);//nouveau Rifle juste pour le tir
              fire_rifle.fire(this.view.getModel(),d);
              this.loadout.put(PlayerFactory.inventory.get(0), this.loadout.get(PlayerFactory.inventory.get(0))-1);// màj des munitions
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

    public String printLoadout() {
      return "Rifle : " + this.loadout.get(PlayerFactory.inventory.get(0)) + " balles      Mines : " + this.loadout.get(PlayerFactory.inventory.get(1)) + "    Bombes : " + this.loadout.get(PlayerFactory.inventory.get(1));
    }

    public String printStats() {
        return "Classe : " + this.classname + "\nPosition : " + this.x + " " + this.y + "\nEnergie : " + this.energy + "\nPoints de vie : " + this.life + "\nEquipement : "+ this.printLoadout();
    }

    public String printControls() {
      String controls = "\nn : tour auto     p : fin de tour     e : quitter";
      if (actionIsAvailable(GameConfig.MOVE_COST,-1)) {
        controls+="\nz,q,s,d : déplacer joueur\n";
      }
      if (actionIsAvailable(GameConfig.PLANT_COST,1)) {
        controls+="m : poser mine      ";
      }
      if (actionIsAvailable(GameConfig.PLANT_COST,2)) {
        controls+="b : poser bombe     ";
      }
      if (actionIsAvailable(GameConfig.FIRE_COST,0)) {
        controls+="t : tirer     ";
      }
      if (actionIsAvailable(GameConfig.SHIELD_COST,-1) && !this.shield_up) {
        controls+="a : activer bouclier";
      }
      return controls;
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

    @Override
    public String toString() {
        return this.shield_up ? "€" : "@";
    }
}
