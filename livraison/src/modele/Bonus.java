package modele;

import java.awt.image.BufferedImage;

/**
* Class that extends Tile, represents a bonus.
*/

public class Bonus extends Tile {

  //Ammount of health/energy/ammunation restored
  private final int value = GameConfig.BONUS_VALUE;

    /**
    * Class constructor.
    * @param x
    * Square ordinate.
    * @param y
    * Square abscissa.
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
    * Returns the representation of the objective.
    * @return A character representing the objective.
    */
    @Override
    public String toString() {
        return ".";
    }
}
