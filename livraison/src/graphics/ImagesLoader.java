/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

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
            }  
            
            acc++;
            if(acc > 5){
                acc = 0;
                index++;
            }
            
            
        }
        System.out.println("Taille ==> "+imagePlayers.get(0).size());
    }   
    
    public static BufferedImage rotateImageByDegrees(BufferedImage img, double degrees) {
        double rads = Math.toRadians(degrees);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

//        int x = clickPoint == null ? w / 2 : clickPoint.x;
//        int y = clickPoint == null ? h / 2 : clickPoint.y;

        at.rotate(rads);
        g2d.setTransform(at);
//        g2d.drawImage(img, 0, 0, null);
//        g2d.setColor(Color.RED);
//        g2d.drawRect(0, 0, newWidth - 1, newHeight - 1);
//        g2d.dispose();

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
