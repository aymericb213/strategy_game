
package testaffichage;

import java.util.HashMap;

/**
 *
 * @author marti
 */
public class WeaponStash {
    
    HashMap<String,Weapon> listWeapon;
    HashMap<String,Weapon> listAccessory;
    
    public WeaponStash(){
        
        HashMap<String,Weapon> listWeapon = new HashMap<String,Weapon>();        
        /*Nom de l'arme / Nom de l'arme, damage, multiplier, range, spread, nbBullet */
        listWeapon.put("Shotgun1",new NormalWeapon  ("Shotgun_normal"   ,5,5,12,35,10));
        listWeapon.put("Shotgun2",new NormalWeapon  ("Shotgun_slug"     ,45,5,12,15,1));
        listWeapon.put("m4",new NormalWeapon        ("m4"               ,24,4,75,15,3));
        listWeapon.put("Beretta",new NormalWeapon   ("Beretta"          ,30,3,50,0,1));
        
        HashMap<String,Weapon> listAccessory = new HashMap<String,Weapon>();
        listAccessory.put("Grenade",new AccessoryWeapon());
        
    }
        
}
