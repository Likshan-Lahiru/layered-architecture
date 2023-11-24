package lk.ijse.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;


public class ButtonSounds {
    public void sounds(){
        String uriString = new File("C:\\Users\\user\\IdeaProjects\\semestser_final_project\\src\\main\\resources\\sounds\\button.mp3").toURI().toString();
        MediaPlayer player = new MediaPlayer( new Media(uriString));
        player.play();

    }
}
