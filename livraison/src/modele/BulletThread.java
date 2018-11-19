package modele;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BulletThread extends Thread {
    private int x;	
    private int y;
    private int range;
    private final int rifle_range;
    private final int rifle_damage;
    private final int speed = 10;
    private final int distance = 0;
    private Direction d;
    private Game game = null;;
    private final Player owner;
    private final boolean running = false;
    
    public BulletThread(int x, int y, int range, Direction d, Player p){
        this.x = x *64;
        this.y = y *64;
        //this.range = range;
        this.range = (range-1) * 64;
        this.rifle_range = GameConfig.RIFLE_RANGE;
        this.rifle_damage = GameConfig.RIFLE_DAMAGE;
        this.d = d;        
        this.owner = p;
    }
    
    @Override
    public void run(){
        boolean noTouch = true;
        //while(running){
            int counter = 0;
            while(counter <= range /*+ speed*/ && noTouch){
                System.out.println(counter+" "+range);
                this.x += d.x() * speed;
                this.y += d.y() * speed;
                int caseX = x/64;
                int caseY = y/64;
                System.out.println(caseX+" "+caseY);
                if(game.getGrid().getTileAt(caseX, caseY ) instanceof Player){
                    Player victim = (Player) game.getGrid().getTileAt(caseX, caseY);
                    if(!victim.isShield_up()){
                        victim.takeDamage(owner.getRifle().getDamage());
                    }else{
                        victim.disableShield();
                    }
                    noTouch = false;
                }
                if(game.getGrid().getTileAt(caseX, caseY) instanceof Wall){
                    noTouch = false;
                }
                game.stateChange();
                try {
                    sleep(13);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BulletThread.class.getName()).log(Level.SEVERE, null, ex);
                }
                counter += speed;
            }
            
            owner.notShooting();
            game.stateChange();
            owner.shootIsOver();
        //}
    }

    public void ResetThread(int x, int y, int range, Direction d){
        this.x = (x+d.x()) * 64;
        this.y = (y+d.y()) * 64;
        this.range = range* 64;
        this.d = d;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void setGame(Game g){
        this.game =g;
    }   
}