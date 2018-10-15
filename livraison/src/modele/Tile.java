package modele;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
	* Classe abstraite représentant un objet du plateau de jeu.
*/
public abstract class Tile {

  protected int x;
  protected int y;
  protected BufferedImage imageRepr;

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
  
  public Tile(int x,int y,BufferedImage img){
      this(x,y);
      this.imageRepr = img;
  }

	/**
		* Retourne la représentation de la case.
		* @return Un caractère représentant le type de la case.
	*/
  public abstract String toString();

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImageRepr() {
        return this.imageRepr;
    }
}
