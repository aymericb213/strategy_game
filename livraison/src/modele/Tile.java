package modele;

/**
	* Classe abstraite représentant un objet du plateau de jeu.
*/
public abstract class Tile {

  protected int x;
  protected int y;

	/**
		* Constructeur de la classe.
		* @param x
		* Ordonnée de la case.
		* @param y
		* Abscisse de la case.
	*/
  public Tile(int x, int y) {
    this.x = x;
    this.y = y;
  }

	/**
		* Retourne la représentation de la case.
		* @return Un caractère représentant le type de la case.
	*/
  public abstract String toString();
}
