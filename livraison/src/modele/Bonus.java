package modele;

/**
* Classe fille de Tile représentant un bonus.
*/

public class Bonus extends Tile {

  public enum BonusType {
    HP("life"),
    AP("energy"),
    AMMO("rifle"),
    BOMB("bomb"),
    MINE("mine");
    String desc;

    BonusType(String desc){
        this.desc = desc;
    }
  }

  //Quantité de vie/énergie/munitions restaurée
  private final int value = GameConfig.BONUS_VALUE;
  private BonusType type;

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
