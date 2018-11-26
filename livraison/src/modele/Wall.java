package modele;

import java.awt.image.BufferedImage;

/**
* Class that extends Tile, represents a wall.
*/
public class Wall extends Tile {
    /**
    * Class constructor.
    * @param x
    * Square ordinate.
    * @param y
    * Square abscissa.
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
    * Returns the representation of a wall.
    * @return a character representing a wall.
    */
    @Override
    public String toString() {
        return "#";
    }
}
