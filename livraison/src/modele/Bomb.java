package modele;

import graphics.SoundLoader;

/**
* Classe fille de Tile représentant un bonus.
*/
public class Bomb extends Mine {

    //Nombre de tours avant explosion
    private int delay = GameConfig.BOMB_DELAY;
    private SoundLoader sound;

    /**
    * Constructeur de la classe.
    * @param owner
    */
    public Bomb(Player owner) {
        super(owner);
        this.damage=GameConfig.BOMB_DAMAGE;
        this.visible=(GameConfig.BOMB_VISIBILITY == 1);
    }

    public Bomb(Player owner, int x, int y) {
        super(owner,x,y);
        this.damage=GameConfig.BOMB_DAMAGE;
        this.visible=(GameConfig.BOMB_VISIBILITY == 1);

    }

    public void tick() {
        this.delay--;
    }

    @Override
    public void explode(RealGrid g, Player p) {        
        p.takeDamage(this.damage);
        g.setTileAt(this.x,this.y,new FreeTile(this.x,this.y));
        g.removeBomb(this);
    }

    public void explode(RealGrid g) {         
        if (this.delay==0) {
            sound = new SoundLoader(1);
            for (Tile t : g.getNeighbouringTiles(this)) {
                if (t instanceof Player) {
                    ((Player)t).takeDamage(this.damage);
                    sound = new SoundLoader(2);
                }
            }
            g.setTileAt(this.x,this.y,new FreeTile(this.x,this.y));
            g.removeBomb(this);
        }
    }

    /**
    * Retourne la représentation de l'objectif.
    * @return Un caractère représentant l'objectif.
    */
    @Override
    public String toString() {
        return ""+this.delay;
    }

    public int getDelay() {
        return delay;
    }


}
