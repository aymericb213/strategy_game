/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import modele.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
        
        //Pour lire les entrées claviers
        setFocusable(true);
        requestFocus();


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
        
        //Je mets ça juste pour les tests, on verra ce qu'on en fait
        addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_Z){
                    for(Player p : game.getListPlayers().keySet()){
                        if(p.possibleMoves().contains(Direction.z)){
                          p.move(Direction.z);
                          game.stateChange();
                        }
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_Q){
                    for(Player p : game.getListPlayers().keySet()){
                        if(p.possibleMoves().contains(Direction.q)){
                          p.move(Direction.q);
                          game.stateChange();
                        }
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_S){
                    for(Player p : game.getListPlayers().keySet()){
                        if(p.possibleMoves().contains(Direction.s)){
                          p.move(Direction.s);
                          game.stateChange();
                        }
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_D){
                    for(Player p : game.getListPlayers().keySet()){
                        if(p.possibleMoves().contains(Direction.d)){
                          p.move(Direction.d);
                          game.stateChange();
                        }
                    }
                }
            }
            
        });
        
        getContentPane().addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / 64;
                int y = e.getY() / 64;
                System.out.println(x+" "+y);
                if(game.getGrid().getTileAt(x, y) instanceof Player){
                    Player p = (Player) game.getGrid().getTileAt(x, y);
                    if(!p.isSelected()){
                        p.select();
                    }else{
                        p.unselect();
                    }
                }
                game.stateChange();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
            
        });
        

        setVisible(true);

    }

    public View getView(){
        return this.view;
    }

    public Game getGame(){
        return this.game;
    }

}
