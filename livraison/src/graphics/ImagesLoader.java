/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import modele.Grid;
import org.xml.sax.SAXException;

/**
 *
 * @author quentindeme
 */
public class ImagesLoader {

    public static ArrayList<BufferedImage> imageList;
    public static HashMap<Integer, ArrayList<BufferedImage>> imagePlayers;
    private File file;

    public ImagesLoader(File file){
        this.file = file;
    }

    public void loadPlayerImages() throws ParserConfigurationException, IOException, SAXException{

        imagePlayers = new HashMap<Integer,ArrayList<BufferedImage>>();

        BufferedImage spritesheet = null;

        try{
            spritesheet = ImageIO.read(new File("src/Images/Spritesheet/spritesheet_characters.png"));
        }catch(IOException e){
            System.out.println("Loader"+e);
        }

        PlayerImageParser playerHandler = new PlayerImageParser();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
          SAXParser saxParser = factory.newSAXParser();
          saxParser.parse(file,playerHandler);
        } catch (SAXException ex) {
          Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
        }

        for(int i = 0; i < 9; i++){
            imagePlayers.put(i, new ArrayList<BufferedImage>());
        }

        int acc = 0;
        int index = 0;
        for(String name: playerHandler.playerNames){
            ArrayList<Integer> list = playerHandler.playerImages.get(name);
            //x,y,width,height
            int x = list.get(0);
            int y = list.get(1);
            int width = list.get(2);
            int height = list.get(3);
            System.out.println(index);
            BufferedImage temp = spritesheet.getSubimage(x,y,width, height);
            imagePlayers.get(index).add(temp);
            /*
            for(int a = 0 ; a < 3 ; a++){
                int angle;
                if(a == 0){
                    angle = 270;
                }else if(a == 1){
                    angle = 90;
                }else{
                    angle = 180;
                }

                BufferedImage img = rotateImageByDegrees(temp, angle);
            }  */

            acc++;
            if(acc > 5){
                acc = 0;
                index++;
            }


        }
        System.out.println("Taille ==> "+imagePlayers.get(0).size());
    }

    public static BufferedImage lookUp(BufferedImage img) {

        int height = img.getHeight();
        int width = img.getWidth();

        BufferedImage rotated = new BufferedImage(height, width, img.getType());

        for(int y = 0; y < height ; y++){
            for(int x = 0; x < width; x++){
                rotated.setRGB(y, (width-1)-x, img.getRGB(x,y));
            }
        }
        return rotated;
    }

    public static BufferedImage lookDown(BufferedImage img) {

        int height = img.getHeight();
        int width = img.getWidth();

        BufferedImage rotated = new BufferedImage(height, width, img.getType());
        
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height ; y++){            
                rotated.setRGB(y, x, img.getRGB(x,y));
            }
        }
        return rotated;
    }
    
    public static BufferedImage lookRight(BufferedImage img) {

        int height = img.getHeight();
        int width = img.getWidth();

        BufferedImage rotated = new BufferedImage(width, height, img.getType());
        
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height ; y++){            
                rotated.setRGB(x, y, img.getRGB(x,y));
            }
        }
        return rotated;
    }
    
    public static BufferedImage lookLeft(BufferedImage img) {

        int height = img.getHeight();
        int width = img.getWidth();

        BufferedImage rotated = new BufferedImage(width, height, img.getType());
        
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height ; y++){            
                rotated.setRGB((width-1)-x, y, img.getRGB(x,y));
            }
        }
        return rotated;
    }

    //Méthode a appelé une seule, ensuite les images sont accessibles de n'importe ou.
    public static ArrayList<BufferedImage> loadImages(){
        imageList = new ArrayList<BufferedImage>();

        BufferedImage tilesheet = null;

        try{
            tilesheet = ImageIO.read(new File("src/Images/Tilesheet/tilesheet_complete.png"));
        }catch(IOException e){
            System.out.println("Loader"+e);
        }

        int width = tilesheet.getWidth();
        int height = tilesheet.getHeight();
        int size = 64;
        int nbImagesWidth = width / size;
        int nbImagesHeight = height / size;

        System.out.println("Largeur "+ nbImagesWidth);
        System.out.println("Hauteur "+nbImagesHeight);

        /*
        for(int i = 0; i < nbImagesWidth ; i++){
            for(int j = 0 ; j < nbImagesHeight; j++){
                System.out.println(i+" "+j);
                BufferedImage temp = tilesheet.getSubimage(j*size , i*size, size, size);

                imageList.add(temp);
            }
        }
        */
        for(int y = 0 ; y < nbImagesHeight ; y++){
            for(int x = 0 ; x < nbImagesWidth ; x++){
                BufferedImage temp = tilesheet.getSubimage(x*size , y*size, size, size);

                imageList.add(temp);
            }
        }
        return imageList;
    }
}
