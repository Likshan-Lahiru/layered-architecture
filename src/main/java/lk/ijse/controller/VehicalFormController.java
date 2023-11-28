package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dto.VehicleDto;
import lk.ijse.dto.tm.VehicleTm;
import lk.ijse.model.VehicleModel;
import lk.ijse.util.SystemAlert;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class VehicalFormController {


    @FXML
    private AnchorPane root;
    @FXML
    private TableColumn<?,?> colVehicleServiceDate;
    @FXML
    private DatePicker pickerLastServiceDate;
    @FXML
    private JFXTextField txtVehicleStatus;
    @FXML
    private JFXTextField txtNumPlateNo;
    @FXML
    private JFXTextField txtVehicleId;
    @FXML
    private TableColumn <?,?> colNumberplateNo;
    @FXML
    private TableColumn <?,?> colVehicleStatus;
    @FXML
    private TableColumn <?,?> colVehicleId;
    @FXML
    private TableView<VehicleTm> tblVehicleDetail;

    public void initialize(){
        vehicleCellvalueFactory();
        loadAllvehicle();

    }




    private void VehicleSetField(VehicleDto dto) {
        txtVehicleId.setText(dto.getVehicleId());
        txtVehicleStatus.setText(dto.getVehicleStatus());
        txtNumPlateNo.setText(dto.getNumberPlateNo());


    }

    public void vehicleCellvalueFactory() {
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colVehicleStatus.setCellValueFactory(new PropertyValueFactory<>("vehicleStatus"));
        colVehicleServiceDate.setCellValueFactory(new PropertyValueFactory<>("lastServiceDate"));
        colNumberplateNo.setCellValueFactory(new PropertyValueFactory<>("numberPlateNo"));

    }
    public void loadAllvehicle(){
        var model = new VehicleModel();

        ObservableList<VehicleTm> vehicleTmObservableList = FXCollections.observableArrayList();
        try {
            List<VehicleDto>  vehicleDtos =model.getAllvehicle();
            for (VehicleDto dto : vehicleDtos){
                vehicleTmObservableList.add(
                        new VehicleTm(
                                dto.getVehicleId(),
                                dto.getVehicleStatus(),
                                dto.getLastServiceDate(),
                                dto.getNumberPlateNo()

                        )
                );
            }
            tblVehicleDetail.setItems(vehicleTmObservableList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }


    }
    public void btnVehcleSaveOnAction(ActionEvent actionEvent) {
        String txtVehicleIdText = txtVehicleId.getText();
        String txtNumPlateNoText = txtNumPlateNo.getText();
        String txtVehicleStatusText = txtVehicleStatus.getText();
        String pickerLastServiceDateValue = String.valueOf(pickerLastServiceDate.getValue());

        if (txtNumPlateNoText.isEmpty()||txtNumPlateNoText.isEmpty()||txtVehicleStatusText.isEmpty()||String.valueOf(pickerLastServiceDateValue).isEmpty()){
            new SystemAlert(Alert.AlertType.ERROR,"Error","Please enter the all details!").showAndWait();
            return;
        }

        boolean matches = Pattern.matches("[C][0-9]{4}",txtVehicleIdText);
        if (!matches){
            new SystemAlert(Alert.AlertType.ERROR,"Error","Insert valid  code  ").show();
            return;
        }
        boolean matches1 = Pattern.matches("[A-Z]{2}[0-9]{4}", txtNumPlateNoText);
        if (!matches1){
            new SystemAlert(Alert.AlertType.ERROR,"Error","Insert valid  Vehicle number plate!").show();
            return;
        }
        VehicleDto dto = new VehicleDto(txtVehicleIdText, txtNumPlateNoText, txtVehicleStatusText, pickerLastServiceDateValue);

        VehicleModel model = new VehicleModel();

        try {

            boolean isSaved = model.saveVehicle(dto);
            if (isSaved){
                new SystemAlert(Alert.AlertType.CONFIRMATION,"Confirmation","New Vehicle details is Saved!", ButtonType.OK).show();
            }else {
                new SystemAlert(Alert.AlertType.WARNING,"Warning","Vehicle details has not Saved!",ButtonType.OK).show();
            }
        }catch (SQLException e ){
            new Alert(Alert.AlertType.ERROR,e.getMessage() ).showAndWait();
        }



    }
    public void btnVehcleUpdateOnAction(ActionEvent actionEvent) {
    }
    public void btnVehcleEditOnAction(ActionEvent actionEvent) {
        clearVehicleCol();
    }
    public void btnVehcleDeleteOnAction(ActionEvent actionEvent) {

    }
    public void clearVehicleCol(){
        txtVehicleId.clear();
        txtVehicleStatus.clear();
        txtNumPlateNo.clear();

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
}

