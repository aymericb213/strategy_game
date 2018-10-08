
package testaffichage.Weapon;

import testaffichage.Weapon.Weapon;

/**
 *
 * @author marti
 */
public class AccessoryWeapon implements Weapon{
    
    private String weapName;
    private double damage;
    private double rangeOfThrow;
    private double rangeOfEffect;
    private boolean stealth;
    
    private int apForUse;
    private int apForThrow;
    
    public AccessoryWeapon(String n, double d, double rT, double rE, boolean s){
        this.weapName=n;
        this.damage=d;
        this.rangeOfThrow=rT;
        this.rangeOfEffect=rE;
        this.stealth=s;
    }
    
    public boolean viewable(){
        return stealth;
    }

    @Override
    public void reload() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void use() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buttkick() {
        throw new UnsupportedOperationException("Can't buttkick with grenade."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString(){
        String s="Weapon :"+weapName+"\n\tdamage :"+damage+"\n\trange of throw :"+rangeOfThrow+
                "\n\trange of effect :"+rangeOfEffect+"\n\tstealth :"+stealth+"\n\n";
        return s;
    }

}
