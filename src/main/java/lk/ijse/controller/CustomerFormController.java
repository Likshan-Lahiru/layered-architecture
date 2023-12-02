package lk.ijse.controller;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.model.CustomerModel;
import lk.ijse.util.RegExPatterns;
import lk.ijse.util.SoundsAssits;
import lk.ijse.util.SystemAlert;
import lk.ijse.util.TxtColours;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class CustomerFormController {
    @FXML
    private AnchorPane root;
    @FXML
    private JFXToggleButton soundsAssistToggelBtn;
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
    SoundsAssits soundsAssits =  new SoundsAssits();
    MainFormController mainFormController = new MainFormController();
    @FXML
    private JFXTextField txtCustomerEmail;
    @FXML
    private TableColumn<?, ?> colCustomerEmail;



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
        colCustomerEmail.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
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
                              dto.getCustomerContactNumber(),
                              dto.getCustomerEmail()
                      )
                    );
            }
            tblCustomer.setItems(customerTmObservableList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }


    }
    public void btnCustomerIDSearchOnAction(ActionEvent actionEvent) {
        String txtSearchCustomerIDText = txtSearchCustomerID.getText();
        if (txtSearchCustomerIDText.isEmpty()){

            new SystemAlert(Alert.AlertType.ERROR, "Error", "Please Enter the valid customer Id!", ButtonType.OK).show();
            try {
                   boolean check = mainFormController.check();
                if(check){

                    soundsAssits.customerContactSound();
                }
            }catch (Exception e){
                e.printStackTrace();
            }


            return;
        }
        CustomerModel model = new CustomerModel();
        try {
            CustomerDto dto = model.searchCustomer(txtSearchCustomerIDText);
            if(dto!=null){
                cutomerSetField(dto);
            }else {
                new SystemAlert(Alert.AlertType.ERROR, "Error", "Customer Does not Found!", ButtonType.OK).show();
                try {
                    boolean check = mainFormController.check();
                    if(check){

                        soundsAssits.customerDoesNotFound();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                clearCustomerField();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }

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
        String customerEmailText = txtCustomerEmail.getText();

        if (!(txtCustomerId.getText().isEmpty()||txtCustomerName.getText().isEmpty()||txtCustomerAddress.getText().isEmpty()||txtCustomerNIC.getText().isEmpty()||txtCustomerContactNumber.getText().isEmpty()||customerEmailText.isEmpty())){
            if (RegExPatterns.getCustomerId().matcher(txtCustomerId.getText()).matches()){
                TxtColours.setDefaultColours(txtCustomerId);
                if (RegExPatterns.getNamePattern().matcher(txtCustomerName.getText()).matches()){
                    TxtColours.setDefaultColours(txtCustomerName);
                    if (RegExPatterns. getNICPattern().matcher(txtCustomerNICText).matches()){
                        TxtColours.setDefaultColours(txtCustomerNIC);
                        if (RegExPatterns.getContactNumberPattern().matcher(txtCustomerContactNumberText).matches()){
                            TxtColours.setDefaultColours(txtCustomerContactNumber);
                            if (RegExPatterns.getEmailPattern().matcher(customerEmailText).matches()){
                                TxtColours.setDefaultColours(txtCustomerEmail);
                            }else {
                                TxtColours.setErrorColours(txtCustomerEmail);
                                return;
                            }
                        }else {
                            TxtColours.setErrorColours(txtCustomerContactNumber);
                            return;

                        }
                    }else {
                        TxtColours.setErrorColours(txtCustomerNIC);
                        return;
                    }
                }else {
                    TxtColours.setErrorColours(txtCustomerName);
                    return;
                }
            }else {
                TxtColours.setErrorColours(txtCustomerId);
                return;
            }
        } else {
            TxtColours.setErrorColours(txtCustomerId);
            TxtColours.setErrorColours(txtCustomerName);
            TxtColours.setErrorColours(txtCustomerAddress);
            TxtColours.setErrorColours(txtCustomerNIC);
            TxtColours.setErrorColours(txtCustomerContactNumber);
            TxtColours.setErrorColours(txtCustomerEmail);


            new SystemAlert(Alert.AlertType.WARNING,"Warrning","Please Enter the all Details").show();
            try {
                boolean check = mainFormController.check();

                if(check){
                    soundsAssits.insertAllDetail();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return;
        }



        CustomerDto dto = new CustomerDto(txtCustomerIdText, txtCustomerNameText, txtCustomerAddressText, txtCustomerNICText, txtCustomerContactNumberText, customerEmailText);
        CustomerModel model = new CustomerModel();
        try {
            boolean isSaved =model.saveCustomer(dto);
            if (isSaved){
                loadAllCustomer();
                new SystemAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Customer Enter successfully!", ButtonType.OK).show();
                try {
                    boolean check = mainFormController.check();

                    if(check){
                        soundsAssits.customerenteredSucces();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                clearCustomerField();
            }
        }catch (SQLException e){
            new SystemAlert(Alert.AlertType.ERROR, "Error", "Something went wrong!", ButtonType.OK).show();
        }
    }

    private void setCustomer(){
        tblCustomer.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldvalue,newValue)->{
                    CustomerDto dto = new CustomerDto(
                            newValue.getCustomerId(),
                            newValue.getCustomerName(),
                            newValue.getCustomerAddress(),
                            newValue.getCustomerNIC(),
                            newValue.getCustomerNumber(),
                            newValue.getCustomerEmail()
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
        txtCustomerEmail.setText(dto.getCustomerEmail());
    }

    public void btnCustomerUpdateOnAction(ActionEvent actionEvent) {

        if (!(txtCustomerId.getText().isEmpty() || txtCustomerName.getText().isEmpty() || txtCustomerAddress.getText().isEmpty() || txtCustomerNIC.getText().isEmpty() || txtCustomerContactNumber.getText().isEmpty()||txtCustomerEmail.getText().isEmpty())) {
            if (RegExPatterns.getCustomerId().matcher(txtCustomerId.getText()).matches()) {
                TxtColours.setDefaultColours(txtCustomerId);
                if (RegExPatterns.getNamePattern().matcher(txtCustomerName.getText()).matches()) {
                    TxtColours.setDefaultColours(txtCustomerName);
                    if (RegExPatterns.getNICPattern().matcher(txtCustomerNIC.getText()).matches()) {
                        TxtColours.setDefaultColours(txtCustomerNIC);
                        if (RegExPatterns.getContactNumberPattern().matcher(txtCustomerContactNumber.getText()).matches()) {
                            TxtColours.setDefaultColours(txtCustomerContactNumber);
                            if (RegExPatterns.getEmailPattern().matcher(txtCustomerEmail.getText()).matches()) {
                                TxtColours.setDefaultColours(txtCustomerEmail);
                            } else {
                                TxtColours.setErrorColours(txtCustomerEmail);
                                return;
                            }
                        } else {
                            TxtColours.setErrorColours(txtCustomerContactNumber);
                            return;

                        }
                    } else {
                        TxtColours.setErrorColours(txtCustomerNIC);
                        return;
                    }
                } else {
                    TxtColours.setErrorColours(txtCustomerName);
                    return;
                }
            } else {
                TxtColours.setErrorColours(txtCustomerId);
                return;
            }
        } else {
            TxtColours.setErrorColours(txtCustomerId);
            TxtColours.setErrorColours(txtCustomerName);
            TxtColours.setErrorColours(txtCustomerAddress);
            TxtColours.setErrorColours(txtCustomerNIC);
            TxtColours.setErrorColours(txtCustomerContactNumber);
            TxtColours.setErrorColours(txtCustomerEmail);


            new SystemAlert(Alert.AlertType.WARNING, "Warrning", "Please Enter the all Details").show();
            try {
                boolean check = mainFormController.check();

                if (check) {
                    soundsAssits.insertAllDetail();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        String txtCustomerIdText = txtCustomerId.getText();
        String txtCustomerNameText = txtCustomerName.getText();
        String txtCustomerAddressText = txtCustomerAddress.getText();
        String txtCustomerNICText = txtCustomerNIC.getText();
        String txtCustomerContactNumberText = txtCustomerContactNumber.getText();
        String customerEmailText = txtCustomerEmail.getText();


        CustomerDto dto = new CustomerDto(txtCustomerIdText, txtCustomerNameText, txtCustomerAddressText, txtCustomerNICText, txtCustomerContactNumberText, customerEmailText);
        CustomerModel model = new CustomerModel();

        try {
            boolean isUpdated = model.updateCustomer(dto);
            if (isUpdated) {
                new SystemAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Customer Update successfully!", ButtonType.OK).show();
                try {
                    boolean check = mainFormController.check();
                    if(check){

                        soundsAssits.customerUpdate();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


            }

        }catch (SQLException e){
            new SystemAlert(Alert.AlertType.ERROR, "Error", e.getMessage(), ButtonType.OK).show();

        }
    }

    public void clearCustomerField(){
        txtCustomerId.clear();
        txtCustomerName.clear();
        txtCustomerNIC.clear();
        txtCustomerAddress.clear();
        txtCustomerContactNumber.clear();
        txtSearchCustomerID.clear();
        txtCustomerEmail.clear();
    }


    public void setMainFormController(MainFormController mainFormController) {
        this.mainFormController = mainFormController;

    }

    public void btnDashBoardOnAction(ActionEvent actionEvent) {
        try {
            Parent node = FXMLLoader.load(this.getClass().getResource("/view/dashBoard_form.fxml"));
            this.root.getChildren().clear();
            this.root.getChildren().add(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnCustomerDeleteOnAction(ActionEvent event) {
        if (!(txtCustomerId.getText().isEmpty() || txtCustomerName.getText().isEmpty() || txtCustomerAddress.getText().isEmpty() || txtCustomerNIC.getText().isEmpty() || txtCustomerContactNumber.getText().isEmpty()|| txtCustomerEmail.getText().isEmpty())) {
            if (RegExPatterns.getCustomerId().matcher(txtCustomerId.getText()).matches()) {
                TxtColours.setDefaultColours(txtCustomerId);
                if (RegExPatterns.getNamePattern().matcher(txtCustomerName.getText()).matches()) {
                    TxtColours.setDefaultColours(txtCustomerName);
                    if (RegExPatterns.getNICPattern().matcher(txtCustomerNIC.getText()).matches()) {
                        TxtColours.setDefaultColours(txtCustomerNIC);
                        if (RegExPatterns.getContactNumberPattern().matcher(txtCustomerContactNumber.getText()).matches()) {
                            TxtColours.setDefaultColours(txtCustomerContactNumber);
                            if (RegExPatterns.getEmailPattern().matcher(txtCustomerEmail.getText()).matches()) {
                                TxtColours.setDefaultColours(txtCustomerEmail);
                            }else {
                                TxtColours.setErrorColours(txtCustomerEmail);
                                return;
                            }
                        } else {
                            TxtColours.setErrorColours(txtCustomerContactNumber);
                            return;

                        }
                    } else {
                        TxtColours.setErrorColours(txtCustomerNIC);
                        return;
                    }
                } else {
                    TxtColours.setErrorColours(txtCustomerName);
                    return;
                }
            } else {
                TxtColours.setErrorColours(txtCustomerId);
                return;
            }
        } else {
            TxtColours.setErrorColours(txtCustomerId);
            TxtColours.setErrorColours(txtCustomerName);
            TxtColours.setErrorColours(txtCustomerAddress);
            TxtColours.setErrorColours(txtCustomerNIC);
            TxtColours.setErrorColours(txtCustomerContactNumber);
            TxtColours.setErrorColours(txtCustomerEmail);


            new SystemAlert(Alert.AlertType.WARNING, "Warrning", "Please Enter the all Details").show();
            try {
                boolean check = mainFormController.check();

                if (check) {
                    soundsAssits.insertAllDetail();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        String txtCustomerIdText = txtCustomerId.getText();
        CustomerModel model = new CustomerModel();
        try {
           boolean isDeleted = model.deleteCustomer(txtCustomerIdText);
           if (isDeleted) {
               new SystemAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Customer Deleted successfully!", ButtonType.OK).show();
               try {
                   boolean check = mainFormController.check();

                   if (check) {
                       soundsAssits.customerDelete();
                   }
               } catch (Exception e) {
                   e.printStackTrace();
               }
        }
    }catch (SQLException e){
        new SystemAlert(Alert.AlertType.ERROR, "Error", e.getMessage(), ButtonType.OK).show();
    }
    }


}
