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

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
				new GameConfig();
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

        File file2 = new File("src/Images/Spritesheet/spritesheet_characters.xml");
        ImagesLoader il = new ImagesLoader(file2);
        il.loadPlayerImages();

	PlayerFactory factory = PlayerFactory.getInstance();
        ArrayList<BufferedImage> images = ImagesLoader.loadImages();

        try {
            il.loadPlayerImages();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        File file = new File("src/Levels/level3.xml");

        try {
            Game game = new Game();
            game.loadGrid(file);
            System.out.println(game.getGrid());
            Player p = factory.buildBasic(game.getGrid());
            Player p2 = factory.buildBasic(game.getGrid());
            //p.setImgRepr(img);
            p.setImgRepr(ImagesLoader.imagePlayers.get(3).get(0));
            p2.setImgRepr(ImagesLoader.imagePlayers.get(5).get(0));
            p2.setX(12);
            p2.setY(0);
            p.setX(0);
            p.setY(12);
            game.addPlayer(p);
            game.addPlayer(p2);
            game.getGrid().addPlayer(p);
            game.getGrid().addPlayer(p2);
            GUI gui = new GUI(game);
            //GUI gui2 = new GUI(game);
            //gui.getView().addEntity(ground);
        } catch (IOException | ParserConfigurationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
