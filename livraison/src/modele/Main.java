package modele;

import Graphics.GUI;
import Graphics.View;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Main {

	public static void main(String[] args) {
            
            System.out.println(System.getProperty("user.dir"));
            
            

            //Chargement de l'image qui représentera le joueur
            BufferedImage img = null;
            BufferedImage img2 = null;
            try{
                img = ImageIO.read(new File("src/Images/PNG/Hitman1/hitman1_hold.png"));
                img2 = ImageIO.read(new File("src/Images/PNG/Hitman1/hitman1_gun.png"));
            }catch(IOException e){
                System.out.println(e);
            }
            
            Player hitman = new Player(0,0,"Hitman", img);
            Game game = new Game();
            game.addPlayer(hitman);
            
            
            GUI gui = new GUI(game);
            gui.getView().update(null);
	}
}
