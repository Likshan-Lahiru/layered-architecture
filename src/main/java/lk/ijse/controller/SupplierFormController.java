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
import lk.ijse.dto.SupplierDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.dto.tm.SupplierTm;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.SupplierModel;

import java.sql.SQLException;
import java.util.List;

public class SupplierFormController {

    @FXML
    private TableView<SupplierTm> tblSupplier;
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
    private TableColumn<?, ?> colSupplierContactNumber;
    @FXML
    private TableColumn<?, ?> colSupplierAddress;
    @FXML
    private TableColumn<?, ?> colSupplierNIC;
    @FXML
    private TableColumn<?, ?> colSupplierName;
    @FXML
    private TableColumn<?, ?> colSupplierD;
    public void initialize(){
        supplierCellvalueFactory();
        loadAllSupplier();
        setSupplier();
    }

    private void setSupplier() {
        tblSupplier.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldvalue,newValue)->{
                    SupplierDto dto = new SupplierDto(
                            newValue.getSupplierId(),
                            newValue.getSupplierName(),
                            newValue.getSupplierNic(),
                            newValue.getSupplierAddress(),
                            newValue.getSupplierContactNumber()
                    );
                    SupplierSetField(dto);
                });
    }

    private void SupplierSetField(SupplierDto dto) {
        txtSupplierId.setText(dto.getSupplierId());
        txtSupplierName.setText(dto.getSupplierName());
        txtSupplierAddress.setText(dto.getSupplierNIC());
        txtSupplierNIC.setText(dto.getSupplierAddress());
        txtSupplierContactNumber.setText(dto.getSupplierContactNumber());
    }

    private void loadAllSupplier() {
        var model = new SupplierModel();

        ObservableList<SupplierTm> supplierTmObservableList = FXCollections.observableArrayList();
        try {
            List<SupplierDto> supplierDtoList =model.getAllSupplier();
            for (SupplierDto dto : supplierDtoList){
                supplierTmObservableList.add(
                        new SupplierTm(
                                dto.getSupplierId(),
                                dto.getSupplierName(),
                                dto.getSupplierNIC(),
                                dto.getSupplierAddress(),
                                dto.getSupplierContactNumber()
                        )
                );
            }
            tblSupplier.setItems(supplierTmObservableList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        
    }

    private void supplierCellvalueFactory() {
        colSupplierD.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colSupplierNIC.setCellValueFactory(new PropertyValueFactory<>("supplierNic"));
        colSupplierAddress.setCellValueFactory(new PropertyValueFactory<>("supplierAddress"));
        colSupplierContactNumber.setCellValueFactory(new PropertyValueFactory<>("supplierContactNumber"));
        
    }

    public void btnSupplierIDSearchOnAction(ActionEvent actionEvent) {
        String searchSupplierIDText = txtSearchSupplierID.getText();
        if (searchSupplierIDText.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please Enter the Supplier Id").showAndWait();
            return;
        }
        SupplierModel model = new SupplierModel();
        try {
            SupplierDto dto = model.searchSupplier(searchSupplierIDText);
            if(dto!=null){
                SupplierSetField(dto);
            }else {
                new Alert(Alert.AlertType.CONFIRMATION,"Supplier Does not Found!").showAndWait();
                clearSupplierField();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }
    }

    public void btnSupplierDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearSupplierField();
    }

    public void btnSupplierUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnCustomerSaveOnAction(ActionEvent actionEvent) {
        String supplierIdText = txtSupplierId.getText();
        String supplierNameText = txtSupplierName.getText();
        String supplierNICText = txtSupplierNIC.getText();
        String supplierAddressText = txtSupplierAddress.getText();
        String supplierContactNumberText = txtSupplierContactNumber.getText();
        if (supplierIdText.isEmpty()||supplierNameText.isEmpty()||supplierNICText.isEmpty()||supplierAddressText.isEmpty()||supplierContactNumberText.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please Enter all Details!").showAndWait();
            return;
        }

        SupplierDto dto = new SupplierDto(supplierIdText, supplierNameText, supplierNICText, supplierAddressText, supplierContactNumberText);
        SupplierModel model = new SupplierModel();

        try {
            boolean isSaved = model.saveSupplier(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"New Supplier is entered!").showAndWait();
                clearSupplierField();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }


    }
    public void clearSupplierField(){
        txtSupplierId.clear();
        txtSupplierName.clear();
        txtSupplierAddress.clear();
        txtSupplierContactNumber.clear();
        txtSupplierNIC.clear();
        txtSearchSupplierID.clear();
    }
}
