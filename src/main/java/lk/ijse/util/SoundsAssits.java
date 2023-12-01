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
    public void employeeDelete(){
        String uriString = new File("C:\\Users\\user\\IdeaProjects\\semestser_final_project\\src\\main\\resources\\sounds\\employee_delete.mp3").toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.play();
    }
    public void employeeUpdate(){
        String uriString = new File("C:\\Users\\user\\IdeaProjects\\semestser_final_project\\src\\main\\resources\\sounds\\employeeUpdate.mp3").toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.play();
    }
    public void employeeNotUpdate(){
        String uriString = new File("C:\\Users\\user\\IdeaProjects\\semestser_final_project\\src\\main\\resources\\sounds\\employee_not_update.mp3").toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.play();
    }
    public void employeeDeleteFail(){
        String uriString = new File("C:\\Users\\user\\IdeaProjects\\semestser_final_project\\src\\main\\resources\\sounds\\employee_delete_failed.mp3").toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.play();
    }
    public void employeeNotSaved(){
        String uriString = new File("C:\\Users\\user\\IdeaProjects\\semestser_final_project\\src\\main\\resources\\sounds\\employee_not_saved.mp3").toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.play();
    }
    public void employeeSave(){
        String uriString = new File("C:\\Users\\user\\IdeaProjects\\semestser_final_project\\src\\main\\resources\\sounds\\Employee_saved_success.mp3").toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.play();
    }
    public void employee_valid_id(){
        String uriString = new File("C:\\Users\\user\\IdeaProjects\\semestser_final_project\\src\\main\\resources\\sounds\\employee_valid_id.mp3").toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.play();
    }
    public void employee_Does_not_found(){
        String uriString = new File("C:\\Users\\user\\IdeaProjects\\semestser_final_project\\src\\main\\resources\\sounds\\employee_Does_not_found.mp3").toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.play();
    }
    public void itemDoesNotFound(){
        String uriString = new File("C:\\Users\\user\\IdeaProjects\\semestser_final_project\\src\\main\\resources\\sounds\\item_Does_not_found.mp3").toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.play();
    }
    public void deeletedSucces(){
        String uriString = new File("C:\\Users\\user\\IdeaProjects\\semestser_final_project\\src\\main\\resources\\sounds\\deleted_succes.mp3").toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.play();
    }
    public void savedSucces(){
        String uriString = new File("C:\\Users\\user\\IdeaProjects\\semestser_final_project\\src\\main\\resources\\sounds\\save_success.mp3").toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.play();
    }
    public void updatedSucces(){
        String uriString = new File("C:\\Users\\user\\IdeaProjects\\semestser_final_project\\src\\main\\resources\\sounds\\updated_success.mp3").toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.play();
    }
    public void toolSearchButton(){
        String uriString = new File("C:\\Users\\user\\IdeaProjects\\semestser_final_project\\src\\main\\resources\\sounds\\tool_serachBar.mp3").toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.play();
    }
    public void OrderAreYouSure(){
        String uriString = new File("C:\\Users\\user\\IdeaProjects\\semestser_final_project\\src\\main\\resources\\sounds\\OrderAreYouSure.mp3").toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.play();
    }
    public void orderSuccess(){
        String uriString = new File("C:\\Users\\user\\IdeaProjects\\semestser_final_project\\src\\main\\resources\\sounds\\orderSuccess.mp3").toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.play();
    }
    public void welcome(){
        String uriString = new File("C:\\Users\\user\\IdeaProjects\\semestser_final_project\\src\\main\\resources\\sounds\\welcome.mp3").toURI().toString();
        player = new MediaPlayer( new Media(uriString));
        player.play();
    }



}
