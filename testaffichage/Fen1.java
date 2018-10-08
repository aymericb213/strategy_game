
package testaffichage;

import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author marti
 */
public class Fen1 /*extends JFrame*/{
    
    private int width;
    private int height;
    
    public Fen1(int width, int height){
        JFrame f = new JFrame("ma fenetre");
        f.setSize(width,height);
        f.setVisible(true);
    }
    public Fen1(){
        new Fen1(300,500);
    }
    
    
}
