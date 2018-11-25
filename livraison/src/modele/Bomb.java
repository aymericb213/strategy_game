package modele;

/**
* Classe fille de Tile représentant un bonus.
*/
public class Bomb extends Mine {

    //Nombre de tours avant explosion
    private int delay = GameConfig.BOMB_DELAY;
    private int range = GameConfig.BOMB_RANGE;
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
        g.setTileAt(new FreeTile(this.x,this.y));
        g.removeBomb(this);
    }

    public void explode(RealGrid g) {
        if (this.delay==0) {
            for (Tile t : g.getNeighbouringTiles(this, this.range)) {
                if (!(t.isWalkable())) {
                  try {
                    ((Player)t).takeDamage(this.damage);
                  } catch (ClassCastException not_a_player) {
                    g.setTileAt(new FreeTile(t.getX(),t.getY()));
                  }
                }
            }
            g.setTileAt(new FreeTile(this.x,this.y));
            g.removeBomb(this);
        }
    }

    /**
    * Surcharge de hashCode().
    * Nécessaire au bon fonctionnement de la surcharge d'equals.
    * @return Le hashcode de l'objet.
    */
    @Override
    public int hashCode() {
        int code=11;
        code+=77*code+this.owner.getX();
        code+=77*code+this.owner.getY();
        return code;
    }

    /**
    * Surcharge de equals.
    * Prend en compte l'égalité de coordonnées.
    * @param o
    * L'objet à comparer au noeud.
    * @return Le résultat du test d'égalité.
    */
    @Override
    public boolean equals(Object o) {
        if (o==this) {
            return true;
        }
        if (!(o instanceof Bomb)) {
            return false;
        }
        Bomb b = (Bomb)o;
        return this.owner.equals(b.getOwner());
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
