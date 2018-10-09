/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import javax.swing.JFrame;

/**
 *
 * @author quentindeme
 */
public class Window extends JFrame{
    
    public Window(View pan){
        setContentPane(pan);
        System.out.println(System.identityHashCode(pan));
        setTitle("Première fenetre");
        setSize(700,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public Window(View pan, String title){
        this(pan);
        setTitle(title);
    }
}
