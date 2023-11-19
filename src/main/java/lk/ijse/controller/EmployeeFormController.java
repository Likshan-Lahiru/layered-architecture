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
import javafx.scene.layout.AnchorPane;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.tm.EmployeeTm;
import lk.ijse.model.EmployeeModel;

import java.sql.SQLException;
import java.util.List;

public class EmployeeFormController {
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

        if(employeeIDText.isEmpty()||employeeNameText.isEmpty()||employeeNICText.isEmpty()||employeeAddressText.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please enter all details!").showAndWait();
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


}
