package modele;

/**
* Class that extends Tile, represents a bomb.
*/
public class Bomb extends Mine {
    
    private int delay = GameConfig.BOMB_DELAY; //Number of turns before explosion
    private int range = GameConfig.BOMB_RANGE;
    
    /**
    * Class constructor.
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
                  } catch (ClassCastException not_a_player) {}
                }
            }
            g.setTileAt(new FreeTile(this.x,this.y));
            g.removeBomb(this);
        }
    }

    /**
    * hashCode() Override.
    * Necessary for the well functioning of equals' Override.
    * @return The object's hashcode.
    */
    @Override
    public int hashCode() {
        int code=11;
        code+=77*code+this.owner.getX();
        code+=77*code+this.owner.getY();
        return code;
    }

    /**
    * equals Override.
    * Checks the equality of the coordinates.
    * @param o
    * The object to compare to a node.
    * @return Equality test result.
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
    * Returns the representation of the objective.
    * @return A character representing the objective.
    */
    @Override
    public String toString() {
        return ""+this.delay;
    }

    public int getDelay() {
        return delay;
    }
}
