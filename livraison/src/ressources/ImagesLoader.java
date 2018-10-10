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
    
    //The last elements of the first 8 lines or empty
    public static ArrayList<BufferedImage> loadImages(){
        imageList = new ArrayList<BufferedImage>();
        
        BufferedImage tilesheet = null;
        
        try{
            tilesheet = ImageIO.read(new File("src/Images/Tilesheet/tilesheet_complete.png"));
        }catch(IOException e){
            System.out.println(e);
        }
        
        int width = tilesheet.getWidth();
        int size = 64;
        int nbImages = width / size;
        System.out.println(width/size);        
        for(int i = 0; i < nbImages ; i++){
            
            BufferedImage temp = tilesheet.getSubimage(i*size , 0, size, size);
            imageList.add(temp);
        }
        
        return imageList;
    }
}
