package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.util.SystemAlert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import lk.ijse.controller.CustomerFormController;

public class MainFormController {

    public Button btnImageChoosers;
    public ImageView imageView;
    @FXML
    private ImageView lblUnMute;
    @FXML
    private ImageView lblMute;
    @FXML
    private JFXToggleButton testButton;
    @FXML
    private  JFXToggleButton soundAssistantToggelButton;
    File file;
    public JFXButton btnOrderId;
    @FXML
    private AnchorPane root;
    @FXML
    private ActionEvent actionEvent;
    @FXML
    private JFXButton brnCustomerDetail;

    @FXML
    private JFXButton btnToolDetail;

    @FXML
    private JFXButton btnVehicle;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnStockList;

    @FXML
    private JFXButton btnSupplier;
    private FXMLLoader loader;

    public void initialize() throws IOException{
            dashBoard();


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
        btnOrderOnAction();
    }
    public void dashBoard() throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/dashBoard_form.fxml"));

        this.root.getChildren().clear();
        this.root.getChildren().add(node);
    }

    public void btnCustomerOnAction() throws IOException {

        FXMLLoader loader =  new FXMLLoader(this.getClass().getResource("/view/Customer_form.fxml"));
        Parent node1 = loader.load();

        CustomerFormController customerController = loader.getController();
        customerController.setMainFormController(this);

        this.root.getChildren().clear();
        this.root.getChildren().add(node1);
    }

    public void btnVehicalOnAction(ActionEvent actionEvent) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/transport_form.fxml"));

        this.root.getChildren().clear();
        this.root.getChildren().add(node);
    }

    public void btnEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader =  new FXMLLoader(this.getClass().getResource("/view/employee_form.fxml"));
        Parent node2 = loader.load();

        EmployeeFormController employeeFormController = loader.getController();
        employeeFormController.setMainFormController(this);

        this.root.getChildren().clear();
        this.root.getChildren().add(node2);
    }

    public void btnToolOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader =  new FXMLLoader(this.getClass().getResource("/view/tool_form.fxml"));
        Parent node1 = loader.load();

        ToolFormController toolFormController = loader.getController();
        toolFormController.setMainFormController(this);

        this.root.getChildren().clear();
        this.root.getChildren().add(node1);
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/supplier_form.fxml"));

        this.root.getChildren().clear();
        this.root.getChildren().add(node);
    }
    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        new SystemAlert(Alert.AlertType.INFORMATION, "Logout", "Do you want to Logout!", ButtonType.OK).showAndWait();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_fom.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage =(Stage)root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Order Form");
        stage.centerOnScreen();
    }


    public void btnStockListOnAction(ActionEvent actionEvent) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/tool_sctock_form.fxml"));

        this.root.getChildren().clear();
        this.root.getChildren().add(node);

    }


    public void TestOnAction(MouseEvent mouseEvent) {
       btnOrderId.setStyle("-fx-background-color: #0077b6;");
       /* brnCustomerDetail.setStyle("-fx-background-color:  #023047;");
        btnToolDetail.setStyle("-fx-background-color:  #023047;");
        btnVehicle.setStyle("-fx-background-color:  #023047;");
        btnEmployee.setStyle("-fx-background-color:  #023047;");
        btnStockList.setStyle("-fx-background-color:  #023047;");
        btnSupplier.setStyle("-fx-background-color:  #023047;");*/

        brnCustomerDetail.setStyle("");
        btnToolDetail.setStyle("");
        btnEmployee.setStyle("");
        btnSupplier.setStyle("");
        btnStockList.setStyle("");
    }


    public void customerEffectOnAction(MouseEvent mouseEvent) {
        brnCustomerDetail.setStyle("-fx-background-color: #0077b6;");
      /*  btnOrderId.setStyle("-fx-background-color:  #023047;");
        btnToolDetail.setStyle("-fx-background-color:  #023047;");
        btnVehicle.setStyle("-fx-background-color:  #023047;");
        btnEmployee.setStyle("-fx-background-color:  #023047;");
        btnStockList.setStyle("-fx-background-color:  #023047;");
        btnSupplier.setStyle("-fx-background-color:  #023047;");*/
        btnOrderId.setStyle("");
        btnVehicle.setStyle("");
        btnToolDetail.setStyle("");
        btnEmployee.setStyle("");
        btnSupplier.setStyle("");
        btnStockList.setStyle("");
    }

    public void toolEffectOnAction(MouseEvent mouseEvent) {
        btnToolDetail.setStyle("-fx-background-color: #0077b6;");
       /* btnOrderId.setStyle("-fx-background-color:  #023047;");
        brnCustomerDetail.setStyle("-fx-background-color:  #023047;");
        btnVehicle.setStyle("-fx-background-color:  #023047;");
        btnEmployee.setStyle("-fx-background-color:  #023047;");
        btnStockList.setStyle("-fx-background-color:  #023047;");
        btnSupplier.setStyle("-fx-background-color:  #023047;");*/
        btnOrderId.setStyle("");
        brnCustomerDetail.setStyle("");
        btnVehicle.setStyle("");
        btnEmployee.setStyle("");
        btnSupplier.setStyle("");
        btnStockList.setStyle("");
    }

    public void vehicleEffectOnAction(MouseEvent mouseEvent) {
        btnVehicle.setStyle("-fx-background-color: #0077b6;");
       /* btnOrderId.setStyle("-fx-background-color:  #023047;");
        brnCustomerDetail.setStyle("-fx-background-color:  #023047;");
        btnToolDetail.setStyle("-fx-background-color:  #023047;");
        btnEmployee.setStyle("-fx-background-color:  #023047;");
        btnStockList.setStyle("-fx-background-color:  #023047;");
        btnSupplier.setStyle("-fx-background-color:  #023047;");*/
        btnOrderId.setStyle("");
        brnCustomerDetail.setStyle("");
        btnToolDetail.setStyle("");
        btnEmployee.setStyle("");
        btnSupplier.setStyle("");
        btnStockList.setStyle("");
    }

    public void employeeEffectOnAction(MouseEvent mouseEvent) {
        btnEmployee.setStyle("-fx-background-color: #0077b6;");
      /*  btnOrderId.setStyle("-fx-background-color:  #023047;");
        brnCustomerDetail.setStyle("-fx-background-color:  #023047;");
        btnToolDetail.setStyle("-fx-background-color:  #023047;");
        btnVehicle.setStyle("-fx-background-color:  #023047;");
        btnStockList.setStyle("-fx-background-color:  #023047;");
        btnSupplier.setStyle("-fx-background-color:  #023047;");*/
        btnOrderId.setStyle("");
        brnCustomerDetail.setStyle("");
        btnToolDetail.setStyle("");
        btnVehicle.setStyle("");
        btnSupplier.setStyle("");
        btnStockList.setStyle("");
    }

    public void stockListEffectOnAction(MouseEvent mouseEvent) {
        btnStockList.setStyle("-fx-background-color: #0077b6;");
       /* btnOrderId.setStyle("-fx-background-color:  #023047;");
        brnCustomerDetail.setStyle("-fx-background-color:  #023047;");
        btnToolDetail.setStyle("-fx-background-color:  #023047;");
        btnVehicle.setStyle("-fx-background-color:  #023047;");
        btnEmployee.setStyle("-fx-background-color:  #023047;");
        btnSupplier.setStyle("-fx-background-color:  #023047;");*/
        btnSupplier.setStyle("");
        btnOrderId.setStyle("");
        brnCustomerDetail.setStyle("");
        btnToolDetail.setStyle("");
        btnVehicle.setStyle("");
        btnEmployee.setStyle("");

    }

    public void supplierEffectOnAction(MouseEvent mouseEvent) {

        btnSupplier.setStyle("-fx-background-color: #0077b6;");
       /* btnOrderId.setStyle("-fx-background-color:  #023047;");
        brnCustomerDetail.setStyle("-fx-background-color:  #023047;");
        btnToolDetail.setStyle("-fx-background-color:  #023047;");
        btnVehicle.setStyle("-fx-background-color:  #023047;");
        btnEmployee.setStyle("-fx-background-color:  #023047;");
        btnStockList.setStyle("-fx-background-color:  #023047;");*/
        btnOrderId.setStyle("");
        brnCustomerDetail.setStyle("");
        btnToolDetail.setStyle("");
        btnVehicle.setStyle("");
        btnEmployee.setStyle("");
        btnStockList.setStyle("");

    }
    public void btnImageChooserOnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the image");
        FileChooser.ExtensionFilter imageFilter =
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif", "*.bmp");
        fileChooser.getExtensionFilters().add(imageFilter);
        file = fileChooser.showOpenDialog(btnImageChoosers.getScene().getWindow());
        if (file != null) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                imageView.setImage(new Image(fileInputStream,179,171,false,true));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public boolean check() {
        boolean result = false;
        if (testButton.isSelected()) {

            result = true;

        } else {
            result = false;

        }
        return result;
    }
    public void lblShow(boolean result) {
        if (result) {


        }

    }




}
