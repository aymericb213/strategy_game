package modele;

import java.awt.Graphics;
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
  
    public BufferedImage getImg(){
        return this.imageRepr;
    }

    public void paint(Graphics g){
        
    }
	/**
		* Retourne la représentation de la case.
		* @return Un caractère représentant le type de la case.
	*/
  public abstract String toString();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getImageRepr() {
        return imageRepr;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setImageRepr(BufferedImage imageRepr) {
        this.imageRepr = imageRepr;
    }
  
    
}
