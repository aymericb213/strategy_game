package modele;

import java.util.*;

public class Main {

	public static void main(String[] args) {

		/*
		PlayerFactory factory = PlayerFactory.getInstance();
		Grid g = new Grid(10,10,4);
		Player p1 = factory.buildBasic();
		g.addPlayer(p1);
		Player p2 = factory.buildBasic();
		p2.setPosition(2,3);
		g.addPlayer(p2);
		Player p3 = factory.buildBasic();
		p3.setPosition(1,9);
		g.addPlayer(p3);
		Player p4 = factory.buildBasic();
		p4.setPosition(7,7);
		g.addPlayer(p4);
		System.out.println("\033[H\033[2J");
		g.generateRandomGrid();
		while(true) {
			try {
				Thread.sleep(500);
				System.out.println(g);
				p4.move(g,5,4);
			} catch (InterruptedException e) {}
		}
		*/
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
            
            
            ArrayList<BufferedImage> images = ImagesLoader.loadImages();
            Grid grid = new Grid(0,0,1);
            
            File file = new File("src/Levels/level22.xml");
            try {
                grid.loadGrid(file);
                
                
                Player hitman = new Player(0,0,"Hitman", img);
                //FreeTile ground = new FreeTile(0,0,images.get(0));
                
                //Player two = new Player(1,1,"hitman2", img2);
                Game game = new Game(grid);
                game.addPlayer(hitman);
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
}
