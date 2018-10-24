/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import modele.*;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author quentindeme
 */
public class GUI extends JFrame{

    private View view;
    private Game game;

    public GUI(){
        this(new Game());
    }

    public GUI(Game game){
        this.game = game;
        this.view = new View(null,game);
        view.setEntities(game.getGrid().getGrid());
        setContentPane(view);
        setTitle("Shooter Game");
        setSize(832,854); //64*20;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        getContentPane().add(new JButton(new AbstractAction("MoveDown"){
            public void actionPerformed(ActionEvent e){
                for(Player p : game.getListPlayers().keySet()){
                    if(p.possibleMoves().contains(Direction.s)){
                      p.move(Direction.s);
                      game.stateChange();
                    }
                }
            }
        }));

        getContentPane().add(new JButton(new AbstractAction("MoveRight"){
            public void actionPerformed(ActionEvent e){
                for(Player p : game.getListPlayers().keySet()){
                  if(p.possibleMoves().contains(Direction.d)){
                    p.move(Direction.d);
                    game.stateChange();
                  }
                }
            }
        }));

        setVisible(true);

    }

    public View getView(){
        return this.view;
    }

    public Game getGame(){
        return this.game;
    }

}
