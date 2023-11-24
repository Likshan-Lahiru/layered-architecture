package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.SignUpDto;
import lk.ijse.model.SignUpModel;
import lk.ijse.util.ButtonSounds;
import lk.ijse.util.RegExPatterns;
import lk.ijse.util.SystemAlert;
import lk.ijse.util.TxtColours;
import java.io.IOException;
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

            if (!(FirstName.isEmpty() ||SecondName.isEmpty()||UserName.isEmpty()||password.isEmpty()||email.isEmpty())){
                if (RegExPatterns.getNamePattern().matcher(FirstName).matches()){
                    TxtColours.setDefaultColours(txtFirstName);
                    if (RegExPatterns.getNamePattern().matcher(SecondName).matches()){
                        TxtColours.setDefaultColours(txtSecondName);
                        if (RegExPatterns.getUserName().matcher(UserName).matches()){
                            TxtColours.setDefaultColours(txtUserName);
                                if(RegExPatterns.getPasswordPattern().matcher(password).matches()) {
                                 TxtColours.setDefaultColours(txtPassword);
                                  if (RegExPatterns.getEmailPattern().matcher(email).matches()) {
                                     TxtColours.setDefaultColours(txtEmail);
                                  } else {
                                     boarderRedAlert(txtEmail);
                                     txtEmail.setText("Please enter valid email!");
                                     return;
                                 }
                             }else {
                                 boarderRedAlert(txtPassword);
                                 txtPassword.setText("Password must minimum 5 or more character and need to include number");
                                 return;
                             }
                        }else {
                            boarderRedAlert(txtUserName);
                            txtUserName.setText("UserName must minimum 8 length and include 1 lowercase and uppercase special character, number");
                            new SystemAlert(Alert.AlertType.INFORMATION, "Information", "UserName must minimum 8 length and include 1 lowercase and uppercase special character, number").showAndWait();
                            return;
                        }
                    }else {
                        boarderRedAlert(txtSecondName);
                        txtSecondName.setText("Name must minimum 4 or more character");
                        return;
                    }
                }else {
                    boarderRedAlert(txtFirstName);
                    txtFirstName.setText("Name must minimum 4 or more character");
                    return;
                }
            }else {
                boarderRedAlert(txtFirstName);
                boarderRedAlert(txtSecondName);
                boarderRedAlert(txtUserName);
                boarderRedAlert(txtPassword);
                boarderRedAlert(txtEmail);
                new SystemAlert(Alert.AlertType.WARNING, "Warrning", "Please Enter the all Details").showAndWait();
                return;
            }

        var dto = new SignUpDto(FirstName,SecondName,UserName,password,email);

        var model = new SignUpModel();

        try {
            boolean isCreateAccount = model.createAccount(dto);
            if (isCreateAccount){
               new SystemAlert(Alert.AlertType.INFORMATION, "Information", "Account Created Successfully").show();
                resetBoarderColor();
                new ButtonSounds().sounds();
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


    @FXML
    public void btnfirstnameOn(MouseEvent mouseEvent) {
        resetBoarderRedAlert(txtFirstName);
        txtFirstName.clear();
    }
    @FXML
    void btnEmail(MouseEvent event) {
        resetBoarderRedAlert(txtEmail);
        txtEmail.clear();
    }

    @FXML
    void btnPassword(MouseEvent event) {
        resetBoarderRedAlert(txtPassword);
        txtPassword.clear();
    }

    @FXML
    void btnSecondname(MouseEvent event) {
        resetBoarderRedAlert(txtSecondName);
        txtSecondName.clear();
    }

    @FXML
    void btnUserName(MouseEvent event) {
        resetBoarderRedAlert(txtUserName);
        txtUserName.clear();
    }

}


