package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFormController {
    @FXML
    private AnchorPane root;
    @FXML
    private ActionEvent actionEvent;

    public void initialize() throws IOException{
            initializeFirstForm();
    }

    @FXML
    private void btnOrderOnAction() throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/Order_form.fxml"));

        this.root.getChildren().clear();
        this.root.getChildren().add(node);

    }
    @FXML
    public void initializeFirstForm() throws IOException {
        this.actionEvent = actionEvent;
        btnCustomerOnAction();
    }


    public void btnCustomerOnAction() throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/Customer_form.fxml"));

        this.root.getChildren().clear();
        this.root.getChildren().add(node);
    }

    public void btnVehicalOnAction(ActionEvent actionEvent) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/vehical_form.fxml"));

        this.root.getChildren().clear();
        this.root.getChildren().add(node);
    }

    public void btnEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/employee_form.fxml"));

        this.root.getChildren().clear();
        this.root.getChildren().add(node);
    }

    public void btnToolOnAction(ActionEvent actionEvent) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/tool_form.fxml"));

        this.root.getChildren().clear();
        this.root.getChildren().add(node);
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/supplier_form.fxml"));

        this.root.getChildren().clear();
        this.root.getChildren().add(node);
    }
    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_fom.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage =(Stage)root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Order Form");
        stage.centerOnScreen();
    }


}
