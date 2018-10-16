package modele;

/**
	* Classe fille de Tile représentant un bonus.
*/
public class Bomb extends Mine {

	//Nombre de tours avant explosion
	private int delay;
	/**
		* Constructeur de la classe.
		* @param x
		* Ordonnée de la case.
		* @param y
		* Abscisse de la case.
	*/
  public Bomb(int x, int y, int damage, int t) {
    super(x,y, damage);
		this.delay=t;
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
