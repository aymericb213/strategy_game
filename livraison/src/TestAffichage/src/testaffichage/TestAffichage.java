
package testaffichage;

import testaffichage.Weapon.WeaponStash;

/**
 *
 * @author marti
 */
public class TestAffichage {

    public static void main(String[] args) {
        
        WeaponStash w = new WeaponStash();
        System.out.println("normal      :"+w.getNormalWeapon());
        System.out.println("accessoires :"+w.getAccessoryWeapon());
        //Fen1 f1 = new Fen1();
    }
    
}
