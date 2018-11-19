package modele;

/**
* Classe fille de Tile représentant une mine.
*/
public class Mine extends Tile implements Weapon {

    protected Player owner;
    protected int damage = GameConfig.MINE_DAMAGE;

    /**
    * Constructeur de la classe.
     * @param owner
    */
    public Mine(Player owner) {
        super(-1,-1);
        this.owner = owner;
        this.visible = (GameConfig.MINE_VISIBILITY == 1);
    }

    public Mine(Player owner, int x, int y) {
        super(x,y);
        this.owner = owner;
        this.visible = (GameConfig.MINE_VISIBILITY == 1);
    }

    @Override
    public Player getOwner() {
        return this.owner;
    }

    @Override
    public void fire(Grid g, Direction d) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void explode(RealGrid g, Player p) {
        p.takeDamage(this.damage);       
        g.setTileAt(this.x,this.y,new FreeTile(this.x,this.y));
    }

    /**
    * Surcharge de hashCode().
    * Nécessaire au bon fonctionnement de la surcharge d'equals.
    * @return Le hashcode de l'objet.
    */
    @Override
    public int hashCode() {
        int code=11;
        code+=37*code+this.owner.getX();
        code+=37*code+this.owner.getY();
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
        if (!(o instanceof Mine)) {
            return false;
        }
        Mine m = (Mine)o;
        return this.owner.equals(m.getOwner());
    }

    /**
    * Retourne la représentation de l'objectif.
    * @return Un caractère représentant l'objectif.
    */
    @Override
    public String toString() {
        return ";";
    }
}
