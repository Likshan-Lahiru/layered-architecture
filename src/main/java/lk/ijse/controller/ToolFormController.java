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
import lk.ijse.util.SystemAlert;

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
        String toolIdText = txtxToolId.getText();
        String toolNameText = txtToolName.getText();
        int qtyOnHandText = Integer.parseInt(txtToolQtyOnHand.getText());
        double perDayPriceText = Double.parseDouble(txtRentPerDayPrice.getText());

        if (toolIdText.isEmpty()|| toolNameText.isEmpty()|| String.valueOf(qtyOnHandText).isEmpty()|| String.valueOf(perDayPriceText).isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Field NotFound!").show();
            return;
        }

        ToolDto dto = new ToolDto(toolIdText,toolNameText,qtyOnHandText,perDayPriceText);
        ToolModel model = new ToolModel();


        try {
            boolean isSaved = model.saveTool(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"New Tool Saved Success!").show();
                loadAllTool();
                clearText();
            }
        } catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            clearText();
        }

    }
    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String toolIdText = txtxToolId.getText();
        String toolNameText = txtToolName.getText();
        int qtyOnHandText = Integer.parseInt(txtToolQtyOnHand.getText());
        double rentPerDayPrice = Double.parseDouble(txtRentPerDayPrice.getText());
        if (toolIdText.isEmpty()||toolNameText.isEmpty()||String.valueOf(qtyOnHandText).isEmpty()||String.valueOf(rentPerDayPrice).isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please enter the all information!").showAndWait();
            return;
        }

        ToolDto dto = new ToolDto(toolIdText, toolNameText, qtyOnHandText, rentPerDayPrice);
        ToolModel model = new ToolModel();
        try {

            boolean isUpdateTool = model.updateToolId(dto);
            if (isUpdateTool){
                new SystemAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Tool Enter successfully!", ButtonType.OK).show();
                loadAllTool();
            }
            else {
                new SystemAlert(Alert.AlertType.WARNING, "Error", "Somehing went wrong!", ButtonType.OK).show();
            }

        }catch (SQLException e){
            e.printStackTrace();
           // new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }



    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearText();
    }

    public void btnSerchOnAction(ActionEvent actionEvent) {
        String searchIdText = txtSearchId.getText();
        if (searchIdText.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Search bar is empty! please enter tool Id!").show();
            return;
        }
        ToolDto dto = new ToolDto(searchIdText);
        ToolModel model = new ToolModel();

        try {
            ToolDto dto1 = model.searchToolID(searchIdText);
            if (dto1 !=null){
                toolSetFields(dto1);
            }else {
                new Alert(Alert.AlertType.CONFIRMATION,"Item Not Found!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }


    }
    private void setItem(){
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
}
