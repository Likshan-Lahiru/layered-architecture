package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.ToolDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.model.CustomerModel;

import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {
    @FXML
    private JFXTextField txtCustomerContactNumber;
    @FXML
    private JFXTextField txtCustomerNIC;
    @FXML
    private JFXTextField txtCustomerAddress;
    @FXML
    private JFXTextField txtCustomerName;
    @FXML
    private JFXTextField txtCustomerId;
    @FXML
    private TextField txtSearchCustomerID;
    @FXML
    private TableColumn<?, ?> colCustomerContactNumber;
    @FXML
    private TableColumn<?, ?> colCustomerNIC;
    @FXML
    private TableColumn<?, ?> colCustomerAddress;
    @FXML
    private TableColumn<?, ?> colcustomerName;
    @FXML
    private TableColumn<?, ?> colCustomerId;
    @FXML
    private TableView<CustomerTm> tblCustomer;

    public void initialize(){
        customerCellvalueFactory();
        loadAllCustomer();
        setCustomer();
    }

    public void customerCellvalueFactory(){
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colcustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        colCustomerNIC.setCellValueFactory(new PropertyValueFactory<>("customerNIC"));
        colCustomerContactNumber.setCellValueFactory(new PropertyValueFactory<>("customerNumber"));
    }
    public void loadAllCustomer(){
       var model = new CustomerModel();

        ObservableList<CustomerTm> customerTmObservableList = FXCollections.observableArrayList();
        try {
            List<CustomerDto> customerDtoList =model.getAllCustomer();
            for (CustomerDto dto : customerDtoList){
                    customerTmObservableList.add(
                      new CustomerTm(
                              dto.getCustomerId(),
                              dto.getCustomerName(),
                              dto.getCustomerAddress(),
                              dto.getCustomerNic(),
                              dto.getCustomerContactNumber()
                      )
                    );
            }
            tblCustomer.setItems(customerTmObservableList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }


    }
    public void btnCustomerIDSearchOnAction(ActionEvent actionEvent) {

    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearCustomerField();

    }

    public void btnCustomerSaveOnAction(ActionEvent actionEvent) {
        String txtCustomerIdText = txtCustomerId.getText();
        String txtCustomerNameText = txtCustomerName.getText();
        String txtCustomerAddressText = txtCustomerAddress.getText();
        String txtCustomerNICText = txtCustomerNIC.getText();
        String txtCustomerContactNumberText = txtCustomerContactNumber.getText();

        if (txtCustomerNameText.isEmpty()||
                txtCustomerIdText.isEmpty()||
                txtCustomerAddressText.isEmpty()||
                txtCustomerNICText.isEmpty()||
                txtCustomerContactNumberText.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please insert All details!");
            return;
        }
        CustomerDto dto = new CustomerDto(txtCustomerIdText, txtCustomerNameText, txtCustomerAddressText, txtCustomerNICText, txtCustomerContactNumberText);
        CustomerModel model = new CustomerModel();
        try {
            boolean isSaved =model.saveCustomer(dto);
            if (isSaved){
                loadAllCustomer();
                new Alert(Alert.AlertType.CONFIRMATION,"New Customer Saved!").showAndWait();
                clearCustomerField();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }



    }
    private void setCustomer(){
        tblCustomer.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldvalue,newValue)->{
                    CustomerDto dto = new CustomerDto(
                            newValue.getCustomerId(),
                            newValue.getCustomerName(),
                            newValue.getCustomerNIC(),
                            newValue.getCustomerAddress(),
                            newValue.getCustomerNumber()
                    );
                    cutomerSetField(dto);
                });
    }

    private void cutomerSetField(CustomerDto dto) {
        txtCustomerId.setText(dto.getCustomerId());
        txtCustomerName.setText(dto.getCustomerName());
        txtCustomerAddress.setText(dto.getCustomerAddress());
        txtCustomerNIC.setText(dto.getCustomerNic());
        txtCustomerContactNumber.setText(dto.getCustomerContactNumber());
    }

    public void btnCustomerUpdateOnAction(ActionEvent actionEvent) {


    }
    public void clearCustomerField(){
        txtCustomerId.clear();
        txtCustomerName.clear();
        txtCustomerNIC.clear();
        txtCustomerAddress.clear();
        txtCustomerContactNumber.clear();
    }
}
