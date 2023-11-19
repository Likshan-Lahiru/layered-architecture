package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.ToolDto;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.OrderModel;
import lk.ijse.model.ToolModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
    @FXML
    private AnchorPane root;

    public void initialize(){
        generateNextOrderId();
        loadToolid();
        loadCustomerIds();

    }

    private void generateNextOrderId() {

        try {
            String orderId = OrderModel.generateNextOrderId();
            lblOrderId.setText(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadToolid() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<ToolDto> toolDtoList = ToolModel.getAllTool();

            for (ToolDto toolDto : toolDtoList) {
                obList.add(toolDto.getToolId());
            }

            cmbToolID.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<CustomerDto> cusList = CustomerModel.getAllCustomer();

            for (CustomerDto dto : cusList) {
                obList.add(dto.getCustomerId());
            }
            cmbCustomerId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {

    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
    }

    public void txtQtyOnAction(ActionEvent actionEvent) {

    }

    public void cmbToolIdOnAction(ActionEvent actionEvent) {
        String code = (String) cmbToolID.getValue();

        txtQty.requestFocus();

        try {
            ToolDto dto = ToolModel.searchToolID(code);

            lblDescription.setText(dto.getToolName());
            lblRentPerDay.setText(String.valueOf(dto.getRentPerDay()));
            lblQutyOnHand.setText(String.valueOf(dto.getQtyOnhand()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbCustomerOnAction(ActionEvent actionEvent) throws SQLException {
        String id = (String) cmbCustomerId.getValue();
        CustomerDto dto = CustomerModel.searchCustomer(id);

        lblCustomerName.setText(dto.getCustomerName());
    }

    public void btnNewCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/supplier_form.fxml"));

        this.root.getChildren().clear();
        this.root.getChildren().add(node);
    }
}
