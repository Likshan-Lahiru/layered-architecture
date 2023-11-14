package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.tm.EmployeeTm;
import lk.ijse.model.EmployeeModel;

import java.sql.SQLException;

public class EmployeeFormController {
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

    public void btnEmployeeIDSearchOnAction(ActionEvent actionEvent) {
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        employeeFeildClear();
    }

    public void btnEmployeeSaveOnAction(ActionEvent actionEvent) {
        String employeeIDText = txtEmployeerId.getText();
        String employeeNameText = txtEmployeeName.getText();
        String employeeNICText = txtEmployeeNIC.getText();
        String employeeAddressText = txtEmployeeAddress.getText();

        if(employeeIDText.isEmpty()||employeeNameText.isEmpty()||employeeNICText.isEmpty()||employeeAddressText.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please enter all details!").showAndWait();
            return;
        }

        EmployeeDto dto = new EmployeeDto(employeeIDText, employeeNameText, employeeNICText, employeeAddressText);
        EmployeeModel model = new EmployeeModel();

        try {
            boolean isSaved = model.saveEmployee(dto);
            if (isSaved){
                    new Alert(Alert.AlertType.CONFIRMATION,"New Employee Entered!").showAndWait();
            }else {
                new Alert(Alert.AlertType.ERROR,"new Employee enter failed!").showAndWait();
            }

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }


    }

    public void btnEmployeeUpdateOnAction(ActionEvent actionEvent) {
    }
    public void employeeFeildClear(){
        txtEmployeerId.clear();
        txtEmployeeNIC.clear();
        txtEmployeeAddress.clear();
        txtEmployeeName.clear();
    }
}
