package modele;

/**
* Classe fille de Tile représentant un bonus.
*/
public class Bomb extends Mine {

    //Nombre de tours avant explosion
    private int delay = GameConfig.BOMB_DELAY;

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
    }

    public void tick() {
        this.delay--;
    }

    @Override
    public void explode(RealGrid g) {
        if (this.delay==0) {
            for (Tile t : g.getNeighbouringTiles(this)) {
                for (Player p : g.getPlayers()) {
                    if (p.getX()==t.getX() && p.getY()==t.getY()) {
                        p.takeDamage(this.damage);
                    }
                }
            }
            g.getGrid()[this.x+this.y*g.getWidth()]=new FreeTile(this.x,this.y);
        } else {
            super.explode(g);
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
}
