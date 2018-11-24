package modele;

import java.awt.image.BufferedImage;

/**
* Classe fille de Tile représentant un bonus.
*/

public class Bonus extends Tile {

  //Quantité de vie/énergie/munitions restaurée
  private final int value = GameConfig.BONUS_VALUE;

    /**
    * Constructeur de la classe.
    * @param x
    * Ordonnée de la case.
    * @param y
    * Abscisse de la case.
    * @param value
    */

    public Bonus(int x, int y) {
        super(x,y);
    }
    
    public Bonus(int x, int y, BufferedImage img){
        super(x,y,img);
    }

    public int getValue() {
        return this.value;
    }

    public void boost(Player p) {
      //p.setEnergy(p.getEnergy()+this.value);
      p.setBaseEnergy(p.getBaseEnergy()+1);
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
