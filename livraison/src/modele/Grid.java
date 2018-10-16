package modele;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import ressources.ImagesLoader;
import ressources.LevelHandlerParser;

public class Grid {

    private int width;
    public Tile[] tiles;
    private Player[] players;
    private boolean alternate_string = false;

    public Grid(int width, int height, int nb_players) {
        this.width = width;
        this.tiles = new Tile[width*height];
        this.players = new Player[nb_players];
    }

    public void generateRandomGrid() {
        Random r = new Random();
        for (int i=0 ; i<this.tiles.length ; i++) {
        Tile n = new FreeTile(i%this.width,i/this.width);
            double nr = r.nextDouble();
            if (nr < 0.1) {
                n = new Bonus(i%this.width,i/this.width, 50);
            } else if (nr >= 0.1 && nr < 0.3) {
                n = new Wall(i%this.width,i/this.width);
            }
            this.tiles[i]=n;
        }
    }


        public ArrayList<Tile> loadSimpleGrid(){
        ArrayList<BufferedImage> images = ImagesLoader.loadImages();
        ArrayList<Tile> res = new ArrayList<Tile>();
        Random r = new Random();

        for(int i = 0 ; i < 13 ; i++){
            for(int j = 0 ; j < 8 ; j++){
                int nb = r.nextInt(4);
                res.add(new FreeTile(i,j, images.get(nb)));
            }
        }
        return res;
    }

        public void loadGrid(File file) throws IOException, ParserConfigurationException, SAXException{

        LevelHandlerParser lvlHandler = new LevelHandlerParser();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try{
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(file,lvlHandler);
        } catch (SAXException ex) {
            Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.width = lvlHandler.x;

        System.out.println(lvlHandler.listCase.size());

        String myString = "";

        for(int i = 0; i < lvlHandler.listCase.size() ; i++){
            for(int j = 0; j < lvlHandler.listCase.get(i).length(); j++){
                if(lvlHandler.listCase.get(i).charAt(j) != ' ' || lvlHandler.listCase.get(i).charAt(j) != '\n'){
                    myString += lvlHandler.listCase.get(i).charAt(j);
                }
            }
        }
        myString = myString.replaceAll("[^\\d,]", "");
        String[] cases = myString.split(",");
        /*
        On a cases qui contient TOUTES LES cases de layer. Il faut donc les "empiler"
        */
        int nbLayer = lvlHandler.nbLayer;
        HashMap<Integer, ArrayList<ArrayList<Integer>>> layers = new HashMap<Integer, ArrayList<ArrayList<Integer>>>();

        for(int layer = 0; layer < nbLayer ; layer++){
            ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
            for(int y = 0; y < lvlHandler.y/*cases.length*/ ; y++){
                
                temp.add(new ArrayList<Integer>());
                
                for(int x = 0;  x < lvlHandler.x ; x++){
                    
                    temp.get(y).add(Integer.parseInt(cases[((lvlHandler.x * y) + x) + layer*lvlHandler.x*lvlHandler.y]));
                }
            }
            layers.put(layer,copyList(temp));
            
        }
        // La HashMap représente les différen`tes couches.
        computeTileGrid(layers);

    }

    public ArrayList<ArrayList<Integer>> copyList(ArrayList<ArrayList<Integer>> l){
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < l.size() ; i++){
            res.add(new ArrayList<Integer>());
            for(int j = 0; j < l.get(i).size() ; j++){
                res.get(i).add(new Integer(l.get(i).get(j)));
            }
        }
        return res;
    }

    public void computeTileGrid(HashMap<Integer,ArrayList<ArrayList<Integer>>> l){

        int size = l.get(0).size() * l.get(0).get(0).size() * l.size();
        
        System.out.println("Taille --> "+size);

        Tile[] res = new Tile[size];
        Random r = new Random();

        int indice = 0;

        for(int i = 0 ; i < l.size() ; i++){
            ArrayList<ArrayList<Integer>> list = l.get(i);
            for(int y = 0 ; y < list.size(); y++){
                for(int x = 0 ; x < list.get(y).size() ; x++){
                    int index = list.get(y).get(x);
                    if(index != 0){
                        if(index > 1){
                            index--;
                        }
                        if(index == 1){
                            index = r.nextInt(4);
                        }
                        res[indice] = (new FreeTile(x,y, ImagesLoader.imageList.get(index)));
                        indice++;
                    }
                }
            }
        }
        this.tiles = res;
    }

    public void computeTileGrid(ArrayList<ArrayList<Integer>> l){

        Tile[] res = new Tile[l.size()*l.get(0).size()];
        Random r = new Random();

        int indice = 0;

        for(int i = 0 ; i < l.size() ; i++){
            for(int j = 0 ; j < l.get(i).size() ; j++){
                int index = l.get(i).get(j);
                if(index > 1){
                    index--;
                }
                if(index == 1){
                    index = r.nextInt(4);
                }
                res[indice] = (new FreeTile(j,i, ImagesLoader.imageList.get(index)));
                indice++;
            }
        }
        this.tiles = res;
    }

    public Tile[] getGrid() {
        return tiles;
    }
	public Player[] getPlayers() {
            return this.players;
	}

    	public int getWidth() {
		return this.width;
	}

	public void addPlayer(Player p){
            this.players[PlayerFactory.nb_instances-1]=p;
	}


	public String toString() {
		StringBuilder res = new StringBuilder();
		for (int i=0 ; i<this.tiles.length ; i++) {
			if (i>0 && i%this.width == 0) {
				res.append("\n");
			}
			for (Player p : this.players) {
				if (p.getX()==i%this.width && p.getY()==i/this.width) {
					res.append(p.toString());
					break;
				} else if (this.players[PlayerFactory.nb_instances-1]==p){
					res.append(this.tiles[i]);
				}
			}
		}
		StringBuilder res2 = new StringBuilder(res.toString());
		for (Player p : this.players) {
			int position = p.getX()+(p.getY()*(this.width+1));
			res2.replace(position,position+1,this.tiles[position-p.getY()].toString());
		}
		this.alternate_string= !(this.alternate_string);
		System.out.println("\033[H\033[2J");
		return (this.alternate_string) ? res.toString() : res2.toString();
	}



}
