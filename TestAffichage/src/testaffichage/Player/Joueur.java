
package testaffichage.Player;

import testaffichage.Player.ClassePerso;

/**
 *
 * @author marti
 */
public class Joueur {
    
    private static int count;
    
    private int number;
    private String name;    
    private ClassePerso cP;
    
    public Joueur(String n){
        this.number=count;
        this.name=n;
        this.cP=new ClassePerso();
    }
}
