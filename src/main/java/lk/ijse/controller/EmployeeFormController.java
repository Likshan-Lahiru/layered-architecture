package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.tm.EmployeeTm;
import lk.ijse.model.EmployeeModel;
import lk.ijse.util.RegExPatterns;
import lk.ijse.util.SystemAlert;
import lk.ijse.util.TxtColours;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EmployeeFormController {
    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton btnImageChoosers;
    @FXML
    private ImageView imageView;
    @FXML
    private ImageView btnImageChooser;
    @FXML
    private AnchorPane rootTestId;
    @FXML
    private JFXTextField txtEmployeeNIC;
    @FXML
    private JFXTextField txtEmployeeAddress;
    @FXML
    private JFXTextField txtEmployeeName;
    @FXML
    private JFXTextField txtEmployeerId;
    @FXML
    private TextField txtSearchEmployeeID;
    @FXML
    private TableColumn<?, ?> colEmployeeAddress;
    @FXML
    private TableColumn <?, ?> colEmployeeNIC;
    @FXML
    private TableColumn<?, ?> colEmployeeName;
    @FXML
    private TableColumn<?, ?> colEmployeeId;
    @FXML
    private TableView<EmployeeTm> tblEmployee;
    private File file;

    public void initialize(){
        employeeCellvalueFactory();
        loadAllEmployee();
        setEmployee();
    }

    private void loadAllEmployee() {
        var model = new EmployeeModel();

        ObservableList<EmployeeTm> employeeTmObservableList = FXCollections.observableArrayList();
        try {
            List<EmployeeDto> employeeDtos  =model.getAllEmployee();
            for (EmployeeDto dto : employeeDtos){
                employeeTmObservableList.add(
                        new EmployeeTm(
                                dto.getEmployeeid(),
                                dto.getEmployeeName(),
                                dto.getEmployeeNIC(),
                                dto.getEmployeeAddress()

                        )
                );
            }
            tblEmployee.setItems(employeeTmObservableList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void employeeCellvalueFactory(){
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colEmployeeNIC.setCellValueFactory(new PropertyValueFactory<>("employeeNIC"));
        colEmployeeAddress.setCellValueFactory(new PropertyValueFactory<>("employeeAddress"));

    }
    public void btnEmployeeIDSearchOnAction(ActionEvent actionEvent) {
        String employeeIDText = txtSearchEmployeeID.getText();
        if (employeeIDText.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please Enter the Employee Id").showAndWait();
        }
        EmployeeModel model = new EmployeeModel();
        try {
            EmployeeDto dto = model.searchEmployee(employeeIDText);
            if(dto!=null){
                employeeSetFeild(dto);
            }else {
                new Alert(Alert.AlertType.CONFIRMATION,"Employee Does not Found!").showAndWait();
                employeeFeildClear();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        employeeFeildClear();
    }

    public void btnEmployeeSaveOnAction(ActionEvent actionEvent) {
        String employeeIDText = txtEmployeerId.getText();
        String employeeNameText = txtEmployeeName.getText();
        String employeeNICText = txtEmployeeNIC.getText();
        String employeeAddressText = txtEmployeeAddress.getText();

        if (!(txtEmployeerId.getText().isEmpty()||txtEmployeeName.getText().isEmpty()||txtEmployeeNIC.getText().isEmpty()||txtEmployeeAddress.getText().isEmpty())){
            if (RegExPatterns.getEmployeeId().matcher(txtEmployeerId.getText()).matches()){
                TxtColours.setDefaultColours(txtEmployeerId);
                if (RegExPatterns.getNamePattern().matcher(txtEmployeeName.getText()).matches()){
                    TxtColours.setDefaultColours(txtEmployeeName);
                    if (RegExPatterns.getNICPattern().matcher(txtEmployeeNIC.getText()).matches()){
                        TxtColours.setDefaultColours(txtEmployeeNIC);
                        if (RegExPatterns.getAddressPattern().matcher(txtEmployeeAddress.getText()).matches()){
                            TxtColours.setDefaultColours(txtEmployeeAddress);
                        }else {
                            TxtColours.setErrorColours(txtEmployeeAddress);
                            new Alert(Alert.AlertType.ERROR,"Please Enter a valid Address!").showAndWait();
                            return;
                        }
                    }else {
                        TxtColours.setErrorColours(txtEmployeeNIC);
                        new Alert(Alert.AlertType.ERROR,"Please Enter a valid NIC!").showAndWait();
                        return;
                    }
                }else {
                    TxtColours.setErrorColours(txtEmployeeName);
                    new Alert(Alert.AlertType.ERROR,"Please Enter a valid Name!").showAndWait();
                    return;
                }
            }else {
                TxtColours.setErrorColours(txtEmployeerId);
                new Alert(Alert.AlertType.ERROR,"Please Enter a valid Employee Id!").showAndWait();
                return;
            }
        }else {
            TxtColours.setErrorColours(txtEmployeeAddress);
            TxtColours.setErrorColours(txtEmployeeNIC);
            TxtColours.setErrorColours(txtEmployeeName);
            TxtColours.setErrorColours(txtEmployeerId);
            new SystemAlert(Alert.AlertType.WARNING,"Warrning","Please Enter the all Details").showAndWait();
            return;
        }


        EmployeeDto dto = new EmployeeDto(employeeIDText, employeeNameText, employeeNICText, employeeAddressText);
        EmployeeModel model = new EmployeeModel();

        try {
            boolean isSaved = model.saveEmployee(dto);
            if (isSaved){
                    loadAllEmployee();
                    new Alert(Alert.AlertType.CONFIRMATION,"New Employee Entered!").showAndWait();
                    employeeFeildClear();
            }else {
                new Alert(Alert.AlertType.ERROR,"new Employee enter failed!").showAndWait();
            }

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }


    }

    public void btnEmployeeUpdateOnAction(ActionEvent actionEvent) {

        if (!(txtEmployeerId.getText().isEmpty()||txtEmployeeName.getText().isEmpty()||txtEmployeeNIC.getText().isEmpty()||txtEmployeeAddress.getText().isEmpty())){
            if (RegExPatterns.getEmployeeId().matcher(txtEmployeerId.getText()).matches()){
                TxtColours.setDefaultColours(txtEmployeerId);
                if (RegExPatterns.getNamePattern().matcher(txtEmployeeName.getText()).matches()){
                    TxtColours.setDefaultColours(txtEmployeeName);
                    if (RegExPatterns.getNICPattern().matcher(txtEmployeeNIC.getText()).matches()){
                        TxtColours.setDefaultColours(txtEmployeeNIC);
                        if (RegExPatterns.getAddressPattern().matcher(txtEmployeeAddress.getText()).matches()){
                            TxtColours.setDefaultColours(txtEmployeeAddress);
                        }else {
                            TxtColours.setErrorColours(txtEmployeeAddress);
                            new Alert(Alert.AlertType.ERROR,"Please Enter a valid Address!").showAndWait();
                            return;
                        }
                    }else {
                        TxtColours.setErrorColours(txtEmployeeNIC);
                        new Alert(Alert.AlertType.ERROR,"Please Enter a valid NIC!").showAndWait();
                        return;
                    }
                }else {
                    TxtColours.setErrorColours(txtEmployeeName);
                    new Alert(Alert.AlertType.ERROR,"Please Enter a valid Name!").showAndWait();
                    return;
                }
            }else {
                TxtColours.setErrorColours(txtEmployeerId);
                new Alert(Alert.AlertType.ERROR,"Please Enter a valid Employee Id!").showAndWait();
                return;
            }
        }else {
            TxtColours.setErrorColours(txtEmployeeAddress);
            TxtColours.setErrorColours(txtEmployeeNIC);
            TxtColours.setErrorColours(txtEmployeeName);
            TxtColours.setErrorColours(txtEmployeerId);
            new SystemAlert(Alert.AlertType.WARNING,"Warrning","Please Enter the all Details").showAndWait();
            return;
        }
        String employeeIDText = txtEmployeerId.getText();
        String employeeNameText = txtEmployeeName.getText();
        String employeeNICText = txtEmployeeNIC.getText();
        String employeeAddressText = txtEmployeeAddress.getText();

        EmployeeDto dto = new EmployeeDto(employeeIDText, employeeNameText, employeeNICText, employeeAddressText);
        EmployeeModel model = new EmployeeModel();
        try {
            boolean isUpdated = model.updateEmployee(dto);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee Updated!").showAndWait();
                employeeFeildClear();
            }else {
                new Alert(Alert.AlertType.ERROR,"Employee Update Failed!").showAndWait();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }


    }
    private void setEmployee(){
        tblEmployee.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldvalue,newValue)->{
                    EmployeeDto dto = new EmployeeDto(
                            newValue.getEmployeeId(),
                            newValue.getEmployeeName(),
                            newValue.getEmployeeNIC(),
                            newValue.getEmployeeAddress()

                    );
                    employeeSetFeild(dto);
                });
    }

    private void employeeSetFeild(EmployeeDto dto) {
        txtEmployeerId.setText(dto.getEmployeeid());
        txtEmployeeName.setText(dto.getEmployeeName());
        txtEmployeeNIC.setText(dto.getEmployeeNIC());
        txtEmployeeAddress.setText(dto.getEmployeeAddress());

    }
    public void employeeFeildClear(){
        txtEmployeerId.clear();
        txtEmployeeNIC.clear();
        txtEmployeeAddress.clear();
        txtEmployeeName.clear();
        txtSearchEmployeeID.clear();
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
