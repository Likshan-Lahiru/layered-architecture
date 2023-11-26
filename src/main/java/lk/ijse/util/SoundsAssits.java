package lk.ijse.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SoundsAssits {
    MediaPlayer player;
    public  void customerContactSound(){
        String uriString = new File("C:\\Users\\user\\IdeaProjects\\semestser_final_project\\src\\main\\resources\\sounds\\customerContatct.mp3").toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.play();
    }
}
