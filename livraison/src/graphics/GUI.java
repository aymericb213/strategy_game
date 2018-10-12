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
        this.view = new View(game);
        setContentPane(view);
        setTitle("Shooter Game");
        setSize(800,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        getContentPane().add(new JButton(new AbstractAction("Update"){
            public void actionPerformed(ActionEvent e){
                for(Player p : game.getPlayers()){
                    p.setX(p.getX() + 1);
                    p.setY(p.getY() + 1);
                    game.stateChange();
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
