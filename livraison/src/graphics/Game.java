/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import modele.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author quentindeme
 */
public class Game extends AbstractListenableModel {

    private List<Player> players;
    private Grid grid;

    public Game(){
        this(null);
    }

    public Game(Grid grid){
        this.grid = grid;
        this.players = new ArrayList<Player>();
    }

    public void addPlayer(Player p){
        players.add(p);
    }

    public List<Player> getPlayers(){
        return players;
    }

}
