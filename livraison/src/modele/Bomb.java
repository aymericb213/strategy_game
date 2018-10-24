package modele;

/**
	* Classe fille de Tile représentant un bonus.
*/
public class Bomb extends Mine {

	private Player owner;
	//Nombre de tours avant explosion
	private int delay = 3;
	/**
		* Constructeur de la classe.
		* @param x
		* Ordonnée de la case.
		* @param y
		* Abscisse de la case.
	*/

	public Bomb(Player owner) {
		super(owner);
	}

  public Bomb(int x, int y, Player owner) {
    super(owner,x,y);
  }

	public Player getOwner() {
		return this.owner;
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
