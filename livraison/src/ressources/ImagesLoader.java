/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ressources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author quentindeme
 */
public class ImagesLoader {
    
    public static ArrayList<BufferedImage> imageList;
    
    //Méthode a appelé une seule, ensuite les images sont accessibles de n'importe ou.
    public static ArrayList<BufferedImage> loadImages(){
        imageList = new ArrayList<BufferedImage>();
        
        BufferedImage tilesheet = null;
        
        try{
            tilesheet = ImageIO.read(new File("src/Images/Tilesheet/tilesheet_complete.png"));
        }catch(IOException e){
            System.out.println(e);
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
