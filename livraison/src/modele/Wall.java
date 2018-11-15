package modele;

import java.awt.image.BufferedImage;

/**
* Classe fille de Tile représentant un mur.
*/
public class Wall extends Tile {
    /**
    * Constructeur de la classe.
    * @param x
    * Ordonnée de la case.
    * @param y
    * Abscisse de la case.
    */
    public Wall(int x, int y) {
        super(x,y);
        this.walkable = false;
    }

    public Wall(int x, int y, BufferedImage img){
        super(x,y,img);
        this.walkable = false;
    }

    /**
    * Retourne la représentation du mur.
    * @return Un caractère représentant le mur.
    */
    @Override
    public String toString() {
        return "#";
    }
}
