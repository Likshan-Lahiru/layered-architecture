package lk.ijse.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.LoginDto;
import lk.ijse.dto.SignUpDto;
import lk.ijse.model.LoginModel;
import lk.ijse.util.Mail;
import lk.ijse.util.SystemAlert;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;

public class LoginFormController {
    @FXML
    private Label lblTime;
    @FXML
    private ImageView lblClose;
    @FXML
    private ImageView lblOpenEye;
    @FXML
    private TextField txtShowPassword;
    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private AnchorPane root;


    String time;

    private String hiru = "lahiru212001@gmail.com";
    public void initialize(){
        setTime();
    }
    public void setTime(){
       lblTime.setText(java.time.LocalTime.now().toString());

    }

    public LoginFormController() {
    }

    @FXML
    public void btnSignInOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/signup_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sign up page ");
        stage.centerOnScreen();
    }


    public void btnLoginPageOnAction(ActionEvent actionEvent) throws SQLException, IOException {

        String nameText = txtUserName.getText();

        String passwordText = txtPassword.getText();
        if (nameText.isEmpty()||passwordText.isEmpty()){
            txtPassword.setStyle("-fx-border-color: #ff004f;");
            txtUserName.setStyle("-fx-border-color: #ff004f;");
            return;
        }


       LoginDto dto = new LoginDto(nameText,passwordText);
        LoginModel model = new LoginModel();
        SignUpDto signUpDto = new SignUpDto();

        model.checkCredentianl(dto);




      try {
            boolean checked = model.checkCredentianl(dto);
            if (checked){
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Main_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Order Form");
        stage.centerOnScreen();

             Mail mail = new Mail();
                mail.setMsg(nameText+"emplloyee Login into Ashen Enterprise management System at"+" "+lblTime.getText());

                mail.setTo(hiru);
                mail.setSubject("Ashen Enterprise Management System Login");

                Thread thread = new Thread(mail);
                thread.start();

           } else {
                txtPassword.setStyle("-fx-border-color: #ff004f;");
                txtUserName.setStyle("-fx-border-color: #ff004f;");
                new SystemAlert(Alert.AlertType.WARNING, "Error", "User Name or password is wrong!", ButtonType.OK).show();

            }
        }catch (SQLException e){

            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
      }
    }



    public void showPasswordOnAction(KeyEvent keyEvent) {
      //  Password = txtPassword.getText();
      //  txtShowPassword.setText(Password);
    }

    public void HidePasswordOnAction(KeyEvent keyEvent) {
      //  Password = txtShowPassword.getText();
       // txtPassword.setText(Password);
    }

    public void openOnAction(MouseEvent mouseEvent) {
       // txtShowPassword.setVisible(true);
       // lblOpenEye.setVisible(true);
        //lblClose.setVisible(false);
     //   txtPassword.setVisible(false);
    }

    public void colseOnAction(MouseEvent mouseEvent) {
        txtShowPassword.setVisible(false);
        lblOpenEye.setVisible(false);
        lblClose.setVisible(true);
        txtPassword.setVisible(true);
    }

    public void btnUserNameClearOnAction(MouseEvent mouseEvent) {
        txtUserName.setStyle("");

        txtUserName.clear();
    }

    public void btnUserPasswordClearOnAction(MouseEvent mouseEvent) {
        txtPassword.setStyle("");
        txtPassword.clear();
    }
}
