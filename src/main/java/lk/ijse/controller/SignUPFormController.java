package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.SignUpDto;
import lk.ijse.model.SignUpModel;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;

public class SignUPFormController {
    @FXML
    private JFXTextField txtUserName;
    @FXML
    private JFXTextField txtFirstName;
    @FXML
    private JFXTextField txtSecondName;
    @FXML
    private JFXTextField txtPassword;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private AnchorPane root;



    public void btnCreateAccountOnAction(ActionEvent actionEvent) {
        String FirstName = txtFirstName.getText();
        String SecondName = txtSecondName.getText();
        String UserName = txtUserName.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();
        if(FirstName.isEmpty() ||SecondName.isEmpty()||UserName.isEmpty()||password.isEmpty()||email.isEmpty()){

            if (FirstName.isEmpty()) boarderRedAlert(txtFirstName);
            if (SecondName.isEmpty()) boarderRedAlert(txtSecondName);
            if (UserName.isEmpty()) boarderRedAlert(txtUserName);
            if (password.isEmpty()) boarderRedAlert(txtPassword);
            if (email.isEmpty())boarderRedAlert(txtEmail);
            new Alert(Alert.AlertType.ERROR,"Please enter the all detail!").showAndWait();
            return;
        }

        var dto = new SignUpDto(FirstName,SecondName,UserName,password,email);

        var model = new SignUpModel();

        try {
            boolean isCreateAccount = model.createAccount(dto);
            if (isCreateAccount){
                new Alert(Alert.AlertType.CONFIRMATION,"Account create successfull!").showAndWait();
                resetBoarderColor();
            }

        } catch (SQLException sqlException){
            new Alert(Alert.AlertType.ERROR,sqlException.getMessage()).show();
        }
    }




    public void btnBackOnAction(ActionEvent actionEvent) throws IOException,SQLException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_fom.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage =(Stage)root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Page");
        stage.centerOnScreen();
    }
    public void boarderRedAlert(TextField field){
        field.setStyle("-fx-border-color: #ff004f;");
    }
    public void resetBoarderRedAlert(TextField field){
        field.setStyle("");
    }
    public void resetBoarderColor(){
        resetBoarderRedAlert(txtFirstName);
        resetBoarderRedAlert(txtSecondName);
        resetBoarderRedAlert(txtUserName);
        resetBoarderRedAlert(txtPassword);
        resetBoarderRedAlert(txtEmail);
    }
}


