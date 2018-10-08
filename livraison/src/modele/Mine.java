package modele;

/**
	* Classe fille de Tile représentant une mine.
*/
public class Mine extends Tile implements Weapon {

	/**
		* Constructeur de la classe.
		* @param x
		* Ordonnée de la case.
		* @param y
		* Abscisse de la case.
	*/
  public Mine(int x, int y) {
    super(x,y);
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
