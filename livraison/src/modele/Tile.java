package modele;

import java.awt.image.BufferedImage;


/**
  * Classe abstraite représentant un objet du plateau de jeu.
*/
public abstract class Tile {

  protected int x;
  protected int y;
  protected BufferedImage imgRepr;

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
   * Version graphique du Tile, ce dernier n'est pas obligatoirement utilisé,
   * on lui passe une image si on utilise une interface graphique.
   * @param x
   * @param y
   * @param img
   */
  public Tile(int x, int y, BufferedImage img){
    this(x,y);
    this.imgRepr = img;
  }
 
  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

	public BufferedImage getImgRepr() {
		return imgRepr;
	}

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

	public void setImgRepr(BufferedImage img) {
		this.imgRepr = img;
	}
  /**
  * Retourne la représentation de la case.
  * @return Un caractère représentant le type de la case.
  */
  public abstract String toString();
}
