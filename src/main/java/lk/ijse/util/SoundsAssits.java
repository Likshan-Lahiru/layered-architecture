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
    public void insertAllDetail(){
        String uriString = new File("C:\\Users\\user\\IdeaProjects\\semestser_final_project\\src\\main\\resources\\sounds\\EnterTheAllDetail.mp3").toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.play();
    }
    public void customerenteredSucces(){
        String uriString = new File("C:\\Users\\user\\IdeaProjects\\semestser_final_project\\src\\main\\resources\\sounds\\customerEnterSuccesFully.mp3").toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.play();
    }
    public void customerDoesNotFound(){
        String uriString = new File("C:\\Users\\user\\IdeaProjects\\semestser_final_project\\src\\main\\resources\\sounds\\customerDoesNotFound.mp3").toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.play();
    }
    public void customerUpdate(){
        String uriString = new File("C:\\Users\\user\\IdeaProjects\\semestser_final_project\\src\\main\\resources\\sounds\\customer_update.mp3").toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.play();
    }
    public void customerDelete(){
        String uriString = new File("C:\\Users\\user\\IdeaProjects\\semestser_final_project\\src\\main\\resources\\sounds\\customer_delete.mp3").toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.play();
    }
}
