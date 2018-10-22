package graphics;

import modele.*;
import java.util.logging.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, SAXException {

        System.out.println(System.getProperty("user.dir"));

        //Chargement de l'image qui représentera le joueur
        BufferedImage img = null;
        BufferedImage img2 = null;
        try {
          img = ImageIO.read(new File("src/Images/PNG/Hitman1/hitman1_hold.png"));
          img2 = ImageIO.read(new File("src/Images/PNG/Hitman1/hitman1_gun.png"));
        } catch(IOException e) {
          System.out.println(e);
        }

        ArrayList<BufferedImage> images = ImagesLoader.loadImages();
	RealGrid grid = new RealGrid(0,0,1);

        File file = new File("src/Levels/level3.xml");
        try {
            Player p = new Player();
            p.setImg(img);
            Game game = new Game(grid);
            game.addPlayer(p);
            game.loadGrid(file);
            GUI gui = new GUI(game);
//            System.out.println(game.getGrid());
            game.getGrid().displayGrid();
            //GUI gui2 = new GUI(game);
            //gui.getView().addEntity(ground);
        } catch (IOException ex) {
          Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
          Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
