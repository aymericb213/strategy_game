package graphics;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundLoader {  
    private Media loadSound;
    private MediaPlayer playSound;
    
    public SoundLoader(int action) {
        String mainPath = new File("").getAbsolutePath();
        File audioFiles = new File("src/Sons/");
        File[] fileList = audioFiles.listFiles();
        Arrays.sort(fileList);      
        for (File f: fileList){             
            String sound = mainPath + "/src/Sons/"+fileList[action].getName();            
            try {
                final URL songPath = new File(sound).toURI().toURL();
                loadSound = new Media(songPath.toString());                
            } catch (MalformedURLException ex) {
                Logger.getLogger(SoundLoader.class.getName()).log(Level.SEVERE, null, ex);
            }   
            
            playSound = new MediaPlayer(loadSound);  
            playSound.setVolume(0.3);
            playSound.play();
        }    
    }
    
    public MediaPlayer getTrack(){
        return playSound;
    }
    
    public void loopTrack(){
        playSound.setOnEndOfMedia(() -> {
            playSound.seek(playSound.getStartTime());
        });
    }
}
