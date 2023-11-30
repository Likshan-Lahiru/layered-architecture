package lk.ijse.controller;

import com.google.zxing.WriterException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.tm.EmployeeTm;
import lk.ijse.model.EmployeeModel;
import lk.ijse.qr.QrGenerator;
import lk.ijse.util.RegExPatterns;
import lk.ijse.util.SoundsAssits;
import lk.ijse.util.SystemAlert;
import lk.ijse.util.TxtColours;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;



//
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;

import javafx.scene.control.Alert;


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
    MainFormController mainFormController = new MainFormController();
    SoundsAssits soundsAssits =  new SoundsAssits();
    @FXML
    private JFXComboBox cmbQrEmployeeId;
    @FXML
    private ImageView picture;
    @FXML
    private Label lblQrName;

    @FXML
    private Label lblQrNic;

    @FXML
    private Label lblQrAddress;
    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblQrScannerId;
    private String Employeeid;

    @FXML
    private Label lblQrScannerName;

    @FXML
    private Label lblQrScannerNic;

    @FXML
    private Label lblQrScannerAddress;
    @FXML
    private TableColumn<?, ?> colScannerId;

    @FXML
    private TableColumn<?, ?> colScannerName;

    @FXML
    private TableColumn<?, ?> colScannerDate;

    @FXML
    private TableColumn<?, ?> colScannerStatus;
    @FXML
    private TableColumn<?, ?> colQrEmployeeId;

    @FXML
    private TableColumn<?, ?> colQrEmployeeName;

    @FXML
    private TableColumn<?, ?> colQrEmployeeDate;

    @FXML
    private TableColumn<?, ?> colQrEmployeeStatus;
    @FXML
    private JFXButton btnStart;

    @FXML
    private JFXButton btnEnd;
    @FXML
    private AnchorPane miniPanel;
    @FXML
    private AnchorPane mainPanel;
    private Webcam webcam;
    private WebcamPanel webcamPanel;
    private boolean isReading = false;


    public void initialize(){
        employeeCellvalueFactory();
        loadAllEmployee();
        setEmployee();
        loadEmployeeIds();
    }
    private void setDateToday() {
        lblOrderDate.setText(java.time.LocalDate.now().toString());
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
            try {
                boolean check = mainFormController.check();

                if(check){
                    soundsAssits.employee_valid_id();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return;
        }
        EmployeeModel model = new EmployeeModel();
        try {
            EmployeeDto dto = model.searchEmployee(employeeIDText);
            if(dto!=null){
                employeeSetFeild(dto);
            }else {
                new Alert(Alert.AlertType.CONFIRMATION,"Employee Does not Found!").showAndWait();
                try {
                    boolean check = mainFormController.check();

                    if(check){
                        soundsAssits.employee_Does_not_found();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
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


        EmployeeDto dto = new EmployeeDto(employeeIDText, employeeNameText, employeeNICText, employeeAddressText);
        EmployeeModel model = new EmployeeModel();

        try {
            boolean isSaved = model.saveEmployee(dto);
            if (isSaved){
                    loadAllEmployee();
                    new SystemAlert(Alert.AlertType.CONFIRMATION,"Success","Employee Saved Successfully!").show();
                try {
                    boolean check = mainFormController.check();

                    if (check) {
                        soundsAssits.employeeSave();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                    employeeFeildClear();
            }else {
                new SystemAlert(Alert.AlertType.ERROR,"Error","Employee Not Saved").show();
                try {
                    boolean check = mainFormController.check();

                    if (check) {
                        soundsAssits.employeeNotSaved();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        String employeeIDText = txtEmployeerId.getText();
        String employeeNameText = txtEmployeeName.getText();
        String employeeNICText = txtEmployeeNIC.getText();
        String employeeAddressText = txtEmployeeAddress.getText();

        EmployeeDto dto = new EmployeeDto(employeeIDText, employeeNameText, employeeNICText, employeeAddressText);
        EmployeeModel model = new EmployeeModel();
        try {
            boolean isUpdated = model.updateEmployee(dto);
            if (isUpdated){
                new SystemAlert(Alert.AlertType.CONFIRMATION,"Success","Employee Updated Successfully").show();
                try {
                    boolean check = mainFormController.check();

                    if (check) {
                        soundsAssits.employeeUpdate();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                employeeFeildClear();
            }else {
                new SystemAlert(Alert.AlertType.ERROR,"Error","Employee Not Updated").show();
                try {
                    boolean check = mainFormController.check();

                    if (check) {
                        soundsAssits.employeeNotUpdate();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
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
        String employeeId = txtEmployeerId.getText();

        EmployeeModel model = new EmployeeModel();
        try {
            boolean isDeleted = model.deleteEmployee(employeeId);
            if (isDeleted){
                new SystemAlert(Alert.AlertType.CONFIRMATION,"Success","Employee Deleted Successfully").show();
                try {
                    boolean check = mainFormController.check();

                    if (check) {
                        soundsAssits.employeeDelete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                employeeFeildClear();
            }else {
                new SystemAlert(Alert.AlertType.ERROR,"Error","Employee Delete Failed").show();
                try {
                    boolean check = mainFormController.check();

                    if (check) {
                        soundsAssits.employeeDeleteFail();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }

    }
    public void setMainFormController(MainFormController mainFormController) {
        this.mainFormController = mainFormController;

    }
    private void loadEmployeeIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<EmployeeDto> employeeDtoListList = EmployeeModel.getAllEmployee();

            for ( EmployeeDto dto: employeeDtoListList) {
                obList.add(dto.getEmployeeid());

            }
            cmbQrEmployeeId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getQRBtnOnAction(ActionEvent actionEvent) throws SQLException {

        String employeeId = (String) cmbQrEmployeeId.getValue();
        if (!employeeId.isEmpty()) {

                QrGenerator qrGenerator = new QrGenerator();
                qrGenerator.setData(employeeId);
                try {
                    qrGenerator.getGenerator();
                    setQrDetails();
                } catch (IOException | WriterException e) {
                    new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
                }
                File file = new File(qrGenerator.getPath());
                Image image = new Image(file.toURI().toString());
            picture.setImage(image);
            } else {
                new Alert(Alert.AlertType.ERROR, "Input Data First! ").show();
            }



    }
    public void setQrDetails() throws SQLException {

        String employeeId = (String) cmbQrEmployeeId.getValue();
        EmployeeModel model = new EmployeeModel();
        EmployeeDto dto = model.searchEmployee(employeeId);
        lblQrAddress.setText(dto.getEmployeeAddress());
        lblQrName.setText(dto.getEmployeeName());
        lblQrNic.setText(dto.getEmployeeNIC());
    }
    public void clearQrSetailsLable(){
        lblQrAddress.setText("");
        lblQrName.setText("");
        lblQrNic.setText("");
        picture.setImage(new Image(new File("src/main/resources/icon/qr2.gif").toURI().toString()));

    }


    public void btnStartOnAction(ActionEvent actionEvent) {
        isReading = (!isReading) ? startWebcam() : stopWebcam();
    }

    public void btnEndOnAction(ActionEvent actionEvent) {
        stopWebcam();
    }

    private boolean stopWebcam() {
        if (webcamPanel != null) {
            webcamPanel.stop();
            webcamPanel = null;
        }
        if (webcam != null) {
            webcam.close();
            webcam = null;
        }
        return false;
    }

    private boolean startWebcam() {

        if (webcam == null) {
            Dimension size = WebcamResolution.QVGA.getSize();
            webcam = Webcam.getDefault();
            webcam.setViewSize(size);

            webcamPanel = new WebcamPanel(webcam);
            webcamPanel.setPreferredSize(size);
            webcamPanel.setFPSDisplayed(true);
            webcamPanel.setMirrored(true);

            SwingNode swingNode = new SwingNode();
            swingNode.setContent(webcamPanel);

            miniPanel.getChildren().clear();
            miniPanel.getChildren().add(swingNode);
        }

        Thread thread = new Thread(() -> {
            while (isReading) {
                try {
                    Thread.sleep(1000);
                    BufferedImage image = webcam.getImage();
                    if (image != null) {
                        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
                        Result result = new MultiFormatReader().decode(binaryBitmap);

                         Employeeid = String.valueOf(result);



                        Platform.runLater(() -> {
                            if (result != null) {
                                webcam.close();
                                lblQrScannerId.setText(Employeeid);
                                try {
                                    setDetails();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                new Alert(Alert.AlertType.INFORMATION, "Data Scanned Successfully!").showAndWait();
                            } else {
                                new Alert(Alert.AlertType.ERROR, "No Data Found!").showAndWait();
                            }
                        });
                    }
                } catch (NotFoundException | InterruptedException | RuntimeException ignored) {

                }
            }
        });
        thread.start();
        return true;
    }
    public void setDetails() throws SQLException {

        EmployeeDto dto = new EmployeeModel().searchEmployee(Employeeid);
            lblQrScannerId.setText(dto.getEmployeeid());
           lblQrScannerName.setText(dto.getEmployeeName());
            lblQrScannerAddress.setText(dto.getEmployeeAddress());
            lblQrScannerNic.setText(dto.getEmployeeNIC());

    }
}
