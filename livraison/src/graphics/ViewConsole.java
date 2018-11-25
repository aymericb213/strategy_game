/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import static java.lang.Thread.sleep;
import java.util.logging.*;
import modele.*;

/**
 *
 * @author quentindeme
 */
public class ViewConsole implements ModelListener{

    private Tile[] entities;
    private Game game;
    private Player playerToPlay;
    private ThreadPlay threadPlay = null;

    public ViewConsole(Tile[] entities, Game game){
        this.entities = entities;
        this.game = game;
        game.addListener(this);
    }

    public void display(){
        System.out.println("On crée un nouveau Thread"+ThreadPlay.counterInstance);
        threadPlay = new ThreadPlay(game);
        System.out.println("\033[H\033[2J");
        System.out.println("================ STRATEGY GAME =================\n");
        System.out.println("Tour " + game.getGrid().getTurnNumber());
        System.out.println(playerToPlay.getName() + "\n");
        //System.out.println(g + "\n");//vue globale
        System.out.println(playerToPlay.getView() + "\n");//vues joueur

        threadPlay.start();
    }

    @Override
    public void update(Object source) {
        playerToPlay = game.getGrid().getPlayerToPlay();
        if(threadPlay != null){
            threadPlay.interrupt();
            threadPlay = null;
        }
        try {
            sleep(100);
        } catch (InterruptedException ex) {
            //Logger.getLogger(ViewConsole.class.getName()).log(Level.SEVERE, null, ex);
        }
        display();
    }

}
