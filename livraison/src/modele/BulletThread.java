package modele;

import graphics.Game;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BulletThread extends Thread{

	private int x;
	private int y;
    private int range;
    private int speed = 2;
    private int distance = 0;
    private Direction d;
    private Game game = null;
    private Player owner;
    private boolean running = false;
    
    public BulletThread(int x, int y, int range, Direction d, Player p){
        this.x = x;
        this.y = y;
        this.range = range;
        this.d = d;
        this.owner = p;
    }
    
    @Override
    public void run(){
        //while(running){
            int counter = 0;
            while(counter != range){
                this.x += d.x();
                this.y += d.y();
                game.stateChange();
                System.out.println("range: "+range);
                System.out.println("counteur: "+counter);
                try {
                    sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BulletThread.class.getName()).log(Level.SEVERE, null, ex);
                }
                counter++;
            }
            owner.notShooting();
            game.stateChange();
        //}
    }

    public void ResetThread(int x, int y, int range, Direction d){
        this.x = x;
        this.y = y;
        this.range = range;// * 64;
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