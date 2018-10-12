package modele;

/**
	* Classe fille de Tile représentant une case vide.
*/
public class FreeTile extends Tile {

	/**
		* Constructeur de la classe.
		* @param x
		* Ordonnée de la case.
		* @param y
		* Abscisse de la case.
	*/
  public FreeTile(int x, int y) {
    super(x,y);
  }

	/**
		* Retourne la représentation de la case vide.
		* @return Un caractère représentant la case vide.
	*/
  @Override
  public String toString() {
    return "_";
  }
}
