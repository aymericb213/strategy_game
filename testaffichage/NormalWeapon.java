
package testaffichage;

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
        throw new UnsupportedOperationException("Can't buttkick with grenade ."); //To change body of generated methods, choose Tools | Templates.
    }

}
