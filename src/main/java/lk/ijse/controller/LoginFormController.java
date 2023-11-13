package lk.ijse.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.LoginDto;
import lk.ijse.model.LoginModel;
import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;
    @FXML
    private AnchorPane root;

    @FXML
    public void btnSignInOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/signup_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sign up page ");
        stage.centerOnScreen();
    }


    public void btnLoginPageOnAction(ActionEvent actionEvent) throws SQLException,IOException {

        /*String nameText = txtUserName.getText();
        String passwordText = txtPassword.getText();
        if (nameText.isEmpty()||passwordText.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"User Name or password is empty!").show();
            return;
        }


       LoginDto dto = new LoginDto(nameText,passwordText);
        LoginModel model = new LoginModel();

        model.checkCredentianl(dto);

      try {
            boolean checked = model.checkCredentianl(dto);
            if (checked){*/
                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Main_form.fxml"));
                Scene scene = new Scene(anchorPane);
                Stage stage =(Stage)root.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Order Form");
                stage.centerOnScreen();
         /*  } else {
                new Alert(Alert.AlertType.CONFIRMATION,"user name or password invailid...").show();
            }
        }catch (SQLException e){

            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
      }*/
    }
}
