/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import modele.Player;
import modele.Tile;

/**
 *
 * @author quentindeme
 */
public class ViewConsole implements ModelListener{
    
    private Tile[] entities;
    private Game game;
    private Player playerToPlay;

    public ViewConsole(Tile[] entities, Game game){
        this.entities = entities;
        this.game = game;
        System.out.println(entities);
        game.addListener(this);
    }
    
    public void display(){
        System.out.println("\033[H\033[2J");
        System.out.println("================ STRATEGY GAME =================\n");
        System.out.println("Tour " + game.getGrid().getTurnNumber());
        System.out.println(playerToPlay.getName() + "\n");
        //System.out.println(g + "\n");//vue globale
        System.out.println(playerToPlay.getView() + "\n");//vues joueur
        
        System.out.println("Déplacements possibles: ");
        System.out.println(playerToPlay.possibleMoves());
    }
    
    @Override
    public void update(Object source) {
        playerToPlay = game.getGrid().getPlayerToPlay();
        display();
    }
    
}
