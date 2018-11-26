package modele;

/** Weapon used by the player to shoot.*/
public class Rifle implements Weapon {

    private final Player owner;
    private final int damage = GameConfig.RIFLE_DAMAGE;
    private final int range = GameConfig.RIFLE_RANGE;

    public Rifle(Player owner) {
        this.owner = owner;
    }

    @Override
    public Player getOwner() {
        return this.owner;
    }

    /** Shoots towards the parameter direction.
      * @param g
      * Grid in which the action takes place.
      * @param default:
      * Fire direction.
      */
    @Override
    public void fire(Grid g, Direction d) {
        for (int i=1 ; i<=this.range ; i++) {
            Tile lof = g.getTileAt(this.owner.getX()+(i*d.x()),this.owner.getY()+(i*d.y()));
            if (!(lof==null) && !lof.isWalkable()) {
                try {
                    ((Player)lof).takeDamage(this.damage);
                    break;
                } catch (ClassCastException not_a_player) {
                    break;
                }
            }
        }
    }

    /** Required by interface implementation. Not used in this class.*/
    @Override
    public void explode(RealGrid g, Player p) {
        throw new UnsupportedOperationException("Not supported.");
    }

    /**
    * hashCode() Override.
    * Necessary for the well functioning of equals' Override.
    * @return The object's hashcode.
    */
    @Override
    public int hashCode() {
        int code=17;
        code+=31*code+this.owner.getX();
        code+=31*code+this.owner.getY();
        return code;
    }

    /**
    * equals Override.
    * Checks the equality of the coordinates.
    * @param o
    * The object to compare to this rifle.
    * @return Equality test result.
    */
    @Override
    public boolean equals(Object o) {
        if (o==this) {
            return true;
        }
        if (!(o instanceof Rifle)) {
            return false;
        }
        Rifle r = (Rifle)o;
        return this.owner.equals(r.getOwner());
    }

    public int getRange(){
        return this.range;
    }

    public int getDamage() {
        return damage;
    }
}
