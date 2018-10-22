/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import modele.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author quentindeme
 */
public class Game extends AbstractListenableModel {

  private RealGrid grid;
  private HashMap<Integer, ArrayList<Tile>> tileList;
  protected HashMap<Player, BufferedImage> listPlayers = new HashMap<>();

  public Game() {
      this(null);
  }

  public Game(RealGrid grid) {
      this.grid = grid;
  }

  public RealGrid getGrid() {
    return this.grid;
  }

  public void addPlayer(Player p) {
    listPlayers.put(p, p.getImg());
  }

  public HashMap<Player, BufferedImage> getListPlayers() {
    return listPlayers;
  }

  public HashMap<Integer, ArrayList<Tile>> getTileList() {
    return tileList;
  }

  public ArrayList<Tile> loadSimpleGrid() {
    ArrayList<BufferedImage> images = ImagesLoader.loadImages();
    ArrayList<Tile> res = new ArrayList<Tile>();
    Random r = new Random();
    for (int i = 0 ; i < 13 ; i++) {
      for (int j = 0 ; j < 8 ; j++) {
        int nb = r.nextInt(4);
        res.add(new FreeTile(i,j));
      }
    }
    return res;
  }

  public void loadGrid(File file) throws IOException, ParserConfigurationException, SAXException {

    LevelHandlerParser lvlHandler = new LevelHandlerParser();
    SAXParserFactory factory = SAXParserFactory.newInstance();
    try {
      SAXParser saxParser = factory.newSAXParser();
      saxParser.parse(file,lvlHandler);
    } catch (SAXException ex) {
      Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
    }
    this.grid.setWidth(lvlHandler.x);
    System.out.println(lvlHandler.listCase.size());
    String myString = "";
  	for(int i = 0; i < lvlHandler.listCase.size() ; i++) {
      for(int j = 0; j < lvlHandler.listCase.get(i).length(); j++) {
        if(lvlHandler.listCase.get(i).charAt(j) != ' ' || lvlHandler.listCase.get(i).charAt(j) != '\n'){
          myString += lvlHandler.listCase.get(i).charAt(j);
        }
      }
    }
    myString = myString.replaceAll("[^\\d,]", "");
    String[] cases = myString.split(",");
    /*On a cases qui contient TOUTES LES cases de layer. Il faut donc les "empiler"*/
    int nbLayer = lvlHandler.nbLayer;
    HashMap<Integer, ArrayList<ArrayList<Integer>>> layers = new HashMap<Integer, ArrayList<ArrayList<Integer>>>();
    for(int layer = 0; layer < nbLayer ; layer++) {
      ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
      for(int y = 0; y < lvlHandler.y/*cases.length*/ ; y++) {
        temp.add(new ArrayList<Integer>());
        for(int x = 0;  x < lvlHandler.x ; x++) {
          temp.get(y).add(Integer.parseInt(cases[((lvlHandler.x*y) +x) + layer * lvlHandler.x * lvlHandler.y]));
        }
      }
      layers.put(layer,copyList(temp));
    }
    // La HashMap représente les différentes couches.
    System.out.println(layers);
    computeTileGrid(layers);
	}

  public ArrayList<ArrayList<Integer>> copyList(ArrayList<ArrayList<Integer>> l) {
    ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
    for (int i = 0; i < l.size() ; i++) {
      res.add(new ArrayList<Integer>());
      for (int j = 0; j < l.get(i).size() ; j++) {
        res.get(i).add(Integer.valueOf(l.get(i).get(j)));
      }
    }
    return res;
  }

  public void computeTileGrid(HashMap<Integer,ArrayList<ArrayList<Integer>>> l) {
		Tile[] res = new Tile[l.get(0).get(0).size()*l.get(0).size()];
    HashMap<Integer, ArrayList<Tile>> hashTile = new HashMap<Integer, ArrayList<Tile>>();
    int indice = 0;
    for (int i = 0; i < l.size() ; i++) {
      ArrayList<ArrayList<Integer>> list = l.get(i);
      ArrayList<Tile> tileList = new ArrayList<Tile>();
      for (int j = 0; j < list.size() ; j++) {
        for (int x = 0; x < list.get(j).size() ; x++) {
          int index = list.get(j).get(x);
            if (index != 0) {
              if (index > 1) {
                index--;
              }
              if(index == 1){
                index = new Random().nextInt(4);
              }
              BufferedImage img = ImagesLoader.imageList.get(index);
              Tile tile = new FreeTile(x,j, img);
              tileList.add(tile);
							res[x+(j*this.grid.getWidth())]=new FreeTile(x,j);
            }
        }
      }
      hashTile.put(i, tileList);
    }
    this.tileList = hashTile;
		this.grid.setGrid(res);
  }

  public void paint(Graphics g){
  //g.drawImage(imageRepr, super.x, super.y, null);
  }

}
