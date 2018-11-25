package modele;

public interface Weapon {

    public void fire(Grid g, Direction d);

    public void explode(RealGrid g, Player p);

    public Player getOwner();
}
