package graphics;

import modele.*;
import java.util.logging.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
        Grid grid = new Grid(0,0,1);

        File file = new File("src/Levels/level22.xml");
        try {
            Game game = new Game(grid);
            game.loadGrid(file);
            GUI gui = new GUI(game);
            //GUI gui2 = new GUI(game);
            //gui.getView().addEntity(ground);
        } catch (IOException ex) {
          Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
          Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
