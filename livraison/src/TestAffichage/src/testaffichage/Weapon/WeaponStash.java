
package testaffichage.Weapon;

import testaffichage.Weapon.Weapon;
import java.util.HashMap;

/**
 *
 * @author marti
 */
public class WeaponStash {
    
    HashMap<String,Weapon> listWeapon;
    HashMap<String,Weapon> listAccessory;
    
    public WeaponStash(){
        listWeapon = new HashMap<>();        
        /*Nom de l'arme / Nom de l'arme, damage, multiplier, range, spread, nbBullet */
        listWeapon.put("Shotgun1",new NormalWeapon  ("Shotgun_normal"   ,5,5,12,35,10));
        listWeapon.put("Shotgun2",new NormalWeapon  ("Shotgun_slug"     ,45,5,12,15,1));
        listWeapon.put("m4",new NormalWeapon        ("m4"               ,30,4,75,15,3));
        listWeapon.put("Beretta",new NormalWeapon   ("Beretta"          ,12,3,50,0,1));
        
        listAccessory = new HashMap<>();
        listAccessory.put("Grenade",new AccessoryWeapon("Grenade",125,30,20,false));
        listAccessory.put("Mine",new AccessoryWeapon("Mine",125,0,5,true));
    }
    
    public void addNormalWeapon(String n,double d,int m,double r,double s,int nb){
        listWeapon.put(n, new NormalWeapon(n,d,m,r,s,nb));
    }
    public void addAccessoryWeapon(String n,double d,double rT,double rE,boolean s){
        listAccessory.put(n, new AccessoryWeapon(n,d,rT,rE,s));
    }
    
    public HashMap<String,Weapon> getNormalWeapon(){
        return listWeapon;
    }
    public HashMap<String,Weapon> getAccessoryWeapon(){
        return listAccessory;
    }
    
    public void toStringNormal(){
        
    }
    public void toStringAccessory(){
        
    }
        
}
