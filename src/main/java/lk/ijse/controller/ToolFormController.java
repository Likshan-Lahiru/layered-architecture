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
import lk.ijse.dto.ToolDto;
import lk.ijse.dto.tm.ToolTm;
import lk.ijse.model.ToolModel;
import lk.ijse.util.RegExPatterns;
import lk.ijse.util.SystemAlert;
import lk.ijse.util.TxtColours;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ToolFormController {
    @FXML
    private AnchorPane root;
    @FXML
    private TextField txtSearchId;
    @FXML
    private JFXTextField txtRentPerDayPrice;
    @FXML
    private JFXTextField txtToolQtyOnHand;
    @FXML
    private JFXTextField txtToolName;
    @FXML
    private JFXTextField txtxToolId;
    @FXML
    private TableColumn <?,?> colManage;
    @FXML
    private TableColumn <?,?> colRentPerDayPrice;
    @FXML
    private TableColumn <?,?> colQtyOnHand;
    @FXML
    private TableColumn <?,?> colToolName;
    @FXML
    private TableColumn <?,?> colToolID;
    @FXML
    private TableView <ToolTm>  tblTool;
    public void initialize(){
        setCellValueFactory();
        loadAllTool();
        setItem();

    }
    private void setCellValueFactory(){
        colToolID.setCellValueFactory(new PropertyValueFactory<>("toolId"));
        colToolName.setCellValueFactory(new PropertyValueFactory<>("toolName"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colRentPerDayPrice.setCellValueFactory(new PropertyValueFactory<>("rentPerDay"));

    }


    private void loadAllTool(){

        ToolModel model = new ToolModel();

        ObservableList<ToolTm> toolList = FXCollections.observableArrayList();
        try {
            List<ToolDto> dtoList = model.getAllTool();
            if (dtoList != null) {
                for (ToolDto dto : dtoList) {
                    toolList.add(new ToolTm(
                            dto.getToolId(),
                            dto.getToolName(),
                            dto.getQtyOnhand(),
                            dto.getRentPerDay()
                    ));
                }
            }
            tblTool.setItems(toolList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    public void btnSaveOnActionS(ActionEvent actionEvent) {

        if(!(txtxToolId.getText().isEmpty()||txtToolName.getText().isEmpty()||txtToolQtyOnHand.getText().isEmpty()||txtRentPerDayPrice.getText().isEmpty())){
            if (RegExPatterns.gettoolId().matcher(txtxToolId.getText()).matches()){
                TxtColours.setDefaultColours(txtxToolId);
                if (RegExPatterns.getToolName().matcher(txtToolName.getText()).matches()){
                    TxtColours.setDefaultColours(txtToolName);
                    if (RegExPatterns.getDoublePattern().matcher(txtRentPerDayPrice.getText()).matches()){
                        TxtColours.setDefaultColours(txtRentPerDayPrice);
                        if (RegExPatterns.getIntPattern().matcher(txtToolQtyOnHand.getText()).matches()){
                            TxtColours.setDefaultColours(txtToolQtyOnHand);

                            String toolIdText = txtxToolId.getText();
                            String toolNameText = txtToolName.getText();
                            int qtyOnHandText = Integer.parseInt(txtToolQtyOnHand.getText());
                            double perDayPriceText = Double.parseDouble(txtRentPerDayPrice.getText());

                            ToolDto dto = new ToolDto(toolIdText,toolNameText,qtyOnHandText,perDayPriceText);
                            ToolModel model = new ToolModel();

                            try {
                                boolean isSaved = model.saveTool(dto);
                                if (isSaved){
                                    new SystemAlert(Alert.AlertType.CONFIRMATION,"SUCCESS","Tool Save Successfully!",ButtonType.CLOSE).show();
                                    loadAllTool();
                                    clearText();
                                }
                            } catch (SQLException e){
                                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
                                clearText();
                            }

                        }else {
                            TxtColours.setErrorColours(txtToolQtyOnHand);
                            return;
                        }
                    }else{
                        TxtColours.setErrorColours(txtRentPerDayPrice);
                        return;
                    }
                }else{
                    TxtColours.setErrorColours(txtToolName);
                    return;
                }
            }else {
                TxtColours.setErrorColours(txtxToolId);
                return;
            }
        }else {
            new SystemAlert(Alert.AlertType.WARNING,"WARNING","Please Enter The All Details!",ButtonType.CLOSE).show();
            TxtColours.setErrorColours(txtxToolId);
            TxtColours.setErrorColours(txtToolName);
            TxtColours.setErrorColours(txtToolQtyOnHand);
            TxtColours.setErrorColours(txtRentPerDayPrice);
        }

    }
    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if(!(txtxToolId.getText().isEmpty()||txtToolName.getText().isEmpty()||txtToolQtyOnHand.getText().isEmpty()||txtRentPerDayPrice.getText().isEmpty())){
            if (RegExPatterns.gettoolId().matcher(txtxToolId.getText()).matches()){
                TxtColours.setDefaultColours(txtxToolId);

                if (RegExPatterns.getToolName().matcher(txtToolName.getText()).matches()){
                    TxtColours.setDefaultColours(txtToolName);

                    if (RegExPatterns.getDoublePattern().matcher(txtRentPerDayPrice.getText()).matches()){
                        TxtColours.setDefaultColours(txtRentPerDayPrice);

                        if (RegExPatterns.getIntPattern().matcher(txtToolQtyOnHand.getText()).matches()){
                            TxtColours.setDefaultColours(txtToolQtyOnHand);

                            String toolIdText = txtxToolId.getText();
                            String toolNameText = txtToolName.getText();
                            int qtyOnHandText = Integer.parseInt(txtToolQtyOnHand.getText());
                            double rentPerDayPrice = Double.parseDouble(txtRentPerDayPrice.getText());

                            ToolDto dto = new ToolDto(toolIdText, toolNameText, qtyOnHandText, rentPerDayPrice);
                            ToolModel model = new ToolModel();
                            try {

                                boolean isUpdateTool = model.updateToolId(dto);
                                if (isUpdateTool){
                                    new SystemAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Tool Enter successfully!", ButtonType.OK).show();
                                    loadAllTool();
                                }
                                else {
                                    new SystemAlert(Alert.AlertType.WARNING, "Error", "Something went wrong!", ButtonType.OK).show();
                                }

                            }catch (SQLException e){
                                e.printStackTrace();
                            }
                            TxtColours.setDefaultColours(txtToolQtyOnHand);
                        }else {
                            TxtColours.setErrorColours(txtToolQtyOnHand);
                            return;
                        }
                    }else{
                        TxtColours.setErrorColours(txtRentPerDayPrice);
                        return;
                    }
                }else{
                    TxtColours.setErrorColours(txtToolName);
                    return;
                }
            }else {
                TxtColours.setErrorColours(txtxToolId);
                return;
            }
        }else {
            new SystemAlert(Alert.AlertType.WARNING,"WARNING","Please Enter The All Details!",ButtonType.CLOSE).show();
            TxtColours.setErrorColours(txtxToolId);
            TxtColours.setErrorColours(txtToolName);
            TxtColours.setErrorColours(txtToolQtyOnHand);
            TxtColours.setErrorColours(txtRentPerDayPrice);
        }






    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearText();
    }

    public void btnSerchOnAction(ActionEvent actionEvent) {
        String searchIdText = txtSearchId.getText();
        if (searchIdText.isEmpty()){
            new SystemAlert(Alert.AlertType.WARNING, "Warning", "Search bar is empty! please enter tool Id!", ButtonType.OK).show();
            return;
        }
        ToolDto dto = new ToolDto(searchIdText);
        ToolModel model = new ToolModel();

        try {
            ToolDto dto1 = model.searchToolID(searchIdText);
            if (dto1 !=null){
                toolSetFields(dto1);
            }else {

                new SystemAlert(Alert.AlertType.WARNING, "Warning", "Item Not Found!", ButtonType.OK).show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }


    }
    private void setItem(){
        try {
            tblTool.getSelectionModel().selectedItemProperty()
                    .addListener((observable, oldvalue,newValue)->{
                        ToolDto dto = new ToolDto(
                                newValue.getToolId(),
                                newValue.getToolName(),
                                newValue.getQtyOnHand(),
                                newValue.getRentPerDay()
                        );
                        toolSetFields(dto);
                    });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void toolSetFields(ToolDto dto1) {
        txtxToolId.setText(dto1.getToolId());
        txtToolName.setText(dto1.getToolName());
        txtToolQtyOnHand.setText(String.valueOf(dto1.getQtyOnhand()));
        txtRentPerDayPrice.setText(String.valueOf(dto1.getRentPerDay()));

    }

    public void clearText(){
        txtxToolId.clear();
        txtToolName.clear();
        txtToolQtyOnHand.clear();
        txtRentPerDayPrice.clear();
        txtSearchId.clear();

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

        if(!(txtxToolId.getText().isEmpty()||txtToolName.getText().isEmpty()||txtToolQtyOnHand.getText().isEmpty()||txtRentPerDayPrice.getText().isEmpty())){

            if (RegExPatterns.gettoolId().matcher(txtxToolId.getText()).matches()){
                TxtColours.setDefaultColours(txtxToolId);

                if (RegExPatterns.getToolName().matcher(txtToolName.getText()).matches()){
                    TxtColours.setDefaultColours(txtToolName);

                    if (RegExPatterns.getDoublePattern().matcher(txtRentPerDayPrice.getText()).matches()){
                        TxtColours.setDefaultColours(txtRentPerDayPrice);

                        if (RegExPatterns.getIntPattern().matcher(txtToolQtyOnHand.getText()).matches()){
                            TxtColours.setDefaultColours(txtToolQtyOnHand);

                            String toolId = txtxToolId.getText();
                            String toolName = txtToolName.getText();
                            Double perDayPrice = Double.valueOf(txtRentPerDayPrice.getText());
                            int toolQtyOnHand = Integer.parseInt(txtToolQtyOnHand.getText());

                            ToolDto dto = new ToolDto(toolId,toolName,toolQtyOnHand,perDayPrice);
                            ToolModel model = new ToolModel();
                            try {
                                boolean isDeleted = model.deleteTool(dto);
                                if(isDeleted){
                                    new SystemAlert(Alert.AlertType.CONFIRMATION,"Confirmation","Item Deleted!",ButtonType.OK).show();

                                }else {
                                    new SystemAlert(Alert.AlertType.WARNING,"Warning","Item Does Not Found!",ButtonType.OK).show();
                                }
                            }catch (SQLException e){
                                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
                            }
                        }else {
                            TxtColours.setErrorColours(txtToolQtyOnHand);
                            return;
                        }
                   }else{
                      TxtColours.setErrorColours(txtRentPerDayPrice);
                      return;
                    }
                }else{
                    TxtColours.setErrorColours(txtToolName);
                }
            }else {
                TxtColours.setErrorColours(txtxToolId);
                return;
            }
        }else {
            new SystemAlert(Alert.AlertType.WARNING,"WARNING","Please Enter The All Details!",ButtonType.CLOSE).show();
            TxtColours.setErrorColours(txtxToolId);
            TxtColours.setErrorColours(txtToolName);
            TxtColours.setErrorColours(txtToolQtyOnHand);
            TxtColours.setErrorColours(txtRentPerDayPrice);
        }
    }
}
