
package testaffichage.Weapon;

import testaffichage.Weapon.Weapon;

/**
 *
 * @author marti
 */
public class NormalWeapon implements Weapon{
    
    private String weapName;
    private double damage;
    private int multiplier;
    private double range;
    private double spread;
    private int nbBullet;
    
    private int apForUse;
    private int apForReload;
    private int apForButtkick;
    
    public NormalWeapon(String n, double d, int m, double r,double s, int b){
        this.weapName=n;
        this.damage=d;
        this.multiplier=m;
        this.range=r;
        this.spread=s;
        this.nbBullet=b;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString(){
        String s="Weapon :"+weapName+"\n\tdamage :"+damage+"\n\tmultiplier :"+multiplier+
                "\n\trange of effect :"+range+"\n\tstealth :"+spread+"\n\tnombre de projectiles"+nbBullet+"\n\n";
        return s;
    }

}
