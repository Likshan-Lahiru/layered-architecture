package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class OrderFormController {

    @FXML
    private TableColumn colOrderDetailsAction;
    @FXML
    private TableColumn colOrderDetailsStatus;
    @FXML
    private TableColumn colOrderDetailsUnitPrice;
    @FXML
    private TableColumn colOrderDetailsQty;
    @FXML
    private TableColumn colOrderDetailsId;
    @FXML
    private TableColumn colOrderDetailsOrderId;
    @FXML
    private JFXButton btnAddToCart;
    @FXML
    private TextField txtRentalDays;
    @FXML
    private TextField txtQty;
    @FXML
    private Label lblQutyOnHand;
    @FXML
    private Label lblRentPerDay;
    @FXML
    private TableView tblCart;
    @FXML
    private TableColumn colToolId;
    @FXML
    private TableColumn ColQty;
    @FXML
    private TableColumn ColRentPerDayPrice;
    @FXML
    private TableColumn colRentalDaysCount;
    @FXML
    private TableColumn colTotalPrice;
    @FXML
    private TableColumn colAction;
    @FXML
    private Label lblOrderId;
    @FXML
    private JFXComboBox cmbCustomerId;
    @FXML
    private Label lblCustomerName;
    @FXML
    private JFXComboBox cmbToolID;
    @FXML
    private Label lblDescription;

    public void btnAddToCartOnAction(ActionEvent actionEvent) {

    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
    }

    public void txtQtyOnAction(ActionEvent actionEvent) {
    }

    public void cmbToolIdOnAction(ActionEvent actionEvent) {
    }

    public void cmbCustomerOnAction(ActionEvent actionEvent) {
        
    }

    public void btnNewCustomerOnAction(ActionEvent actionEvent) {
    }
}
