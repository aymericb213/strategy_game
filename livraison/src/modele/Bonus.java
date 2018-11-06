package modele;

/**
	* Classe fille de Tile représentant un bonus.
*/
public class Bonus extends Tile {

	//Quantité de vie/énergie/munitions restaurée
	private int value;

	/**
		* Constructeur de la classe.
		* @param x
		* Ordonnée de la case.
		* @param y
		* Abscisse de la case.
	*/
  public Bonus(int x, int y, int value) {
    super(x,y);
		this.value=value;
  }

	public int getValue() {
		return this.value;
	}
	/**
		* Retourne la représentation de l'objectif.
		* @return Un caractère représentant l'objectif.
	*/
  @Override
  public String toString() {
    return ".";
  }
}
