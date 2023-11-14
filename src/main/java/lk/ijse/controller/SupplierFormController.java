package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SupplierFormController {

    @FXML
    private TableView tblSupplier;
    @FXML
    private TextField txtSearchSupplierID;
    @FXML
    private JFXTextField txtSupplierId;
    @FXML
    private JFXTextField txtSupplierName;
    @FXML
    private JFXTextField txtSupplierNIC;
    @FXML
    private JFXTextField txtSupplierAddress;
    @FXML
    private JFXTextField txtSupplierContactNumber;
    @FXML
    private TableColumn colSupplierContactNumber;
    @FXML
    private TableColumn colSupplierAddress;
    @FXML
    private TableColumn colSupplierNIC;
    @FXML
    private TableColumn colSupplierName;
    @FXML
    private TableColumn colSupplierD;

    public void btnSupplierIDSearchOnAction(ActionEvent actionEvent) {
    }

    public void btnSupplierDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
    }

    public void btnSupplierUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnCustomerSaveOnAction(ActionEvent actionEvent) {
    }
}
