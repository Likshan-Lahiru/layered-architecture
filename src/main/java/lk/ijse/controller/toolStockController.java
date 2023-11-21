package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dto.StockListDto;
import lk.ijse.dto.SupplierDto;
import lk.ijse.dto.ToolDto;
import lk.ijse.dto.tm.StockListTm;
import lk.ijse.model.StockListModel;
import lk.ijse.model.SupplierModel;
import lk.ijse.model.ToolModel;
import lk.ijse.util.SystemAlert;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class toolStockController {
    @FXML
    private AnchorPane root;
    @FXML
    private Label lblSuppliedDate;
    @FXML
    private TextField txtToolPriceUnit;
    @FXML
    private TableColumn <?, ?> colToolId;
    @FXML
    private TableColumn <?, ?> colToolName;
    @FXML
    private TableColumn <?, ?> colToolUnitPrice;
    @FXML
    private TableColumn <?, ?> ColQty;
    @FXML
    private TableColumn <?, ?> colTotalPrice;
    @FXML
    private TableColumn <?, ?> colAction;
    @FXML
    private TableView tblSuppliedDetail;
    @FXML
    private Label lblNetTotal;
    @FXML
    private JFXButton btnNewCustomerOnAction;
    @FXML
    private JFXComboBox cmbSupplierId;
    @FXML
    private Label lblSupplierName;
    @FXML
    private JFXComboBox cmbToolID;
    @FXML
    private Label lblToolName;
    @FXML
    private Label lblQtyOnHand;
    @FXML
    private TextField txtToolQuantitySuppliedCount;
    @FXML
    private TableView tblStockList;
    @FXML
    private TableColumn <?, ?> colOrderDetailsOrderId;
    @FXML
    private TableColumn <?, ?>colOrderDetailsId;
    @FXML
    private TableColumn <?, ?> colOrderDetailsQty;
    @FXML
    private TableColumn <?, ?> colOrderDetailsUnitPrice;
    @FXML
    private TableColumn <?, ?> colOrderDetailsAction;
    private Label lblToolId;
    @FXML
    private DatePicker pickerStockListLastUpdateDate;
    @FXML
    private Label lblStckListToolId;
    @FXML
    private Label lblStockToolName;
    @FXML
    private TextField txtStckListWasteCount;
    @FXML
    private TextField txtStckListQtyOnHand;
    private final ObservableList<StockListTm> obList = FXCollections.observableArrayList();

    public void initialize(){

        loadToolid();
        loadSupplierIds();
        setDate();
        setCellValueFactory();


    }

    private void setCellValueFactory() {
        colToolId.setCellValueFactory(new PropertyValueFactory<>("toolId"));
        colToolName.setCellValueFactory(new PropertyValueFactory<>("toolName"));
        colToolUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        ColQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }
    private void setDate() {
        String date = String.valueOf(LocalDate.now());
        lblSuppliedDate.setText(date);
    }

    private void loadToolid() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<ToolDto> toolDtoList = ToolModel.getAllTool();

            for (ToolDto toolDto : toolDtoList) {
                obList.add(toolDto.getToolId());
            }

            cmbToolID.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadSupplierIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<SupplierDto> cusList = SupplierModel.getAllSupplier();

            for (SupplierDto dto : cusList) {
                obList.add(dto.getSupplierId());
            }
            cmbSupplierId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void btnAddToListOnAction(ActionEvent actionEvent) {
        String supplierId = (String) cmbSupplierId.getValue();
        String toolID = (String) cmbToolID.getValue();
        String toolName =lblToolName.getText();
        int  qtyOnHand = Integer.valueOf(lblQtyOnHand.getText());
        int  toolQuantitySuppliedCount = Integer.valueOf(txtToolQuantitySuppliedCount.getText());
        Double toolPriceUnit = Double.valueOf(txtToolPriceUnit.getText());
        String supplierNameText = lblSupplierName.getText();
        String orderDate =lblSuppliedDate.getText();

        if (supplierId.isEmpty() ||
                toolID.isEmpty() ||
                toolName.isEmpty() ||
                String.valueOf(qtyOnHand).isEmpty() ||
                String.valueOf(toolQuantitySuppliedCount).isEmpty()||
                String.valueOf(toolPriceUnit).isEmpty()) {
            new SystemAlert(Alert.AlertType.WARNING, "Warning", "Please Enter the all details!", ButtonType.OK).show();
            return;
        }
       Double total = calTotal(toolPriceUnit,toolQuantitySuppliedCount);
        Button btn = new Button("remove");
        btn.setCursor(Cursor.HAND);

        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new SystemAlert(Alert.AlertType.INFORMATION,"Information", "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int index = tblSuppliedDetail.getSelectionModel().getSelectedIndex();

                if (index >= 0 && index < obList.size()) {
                    obList.remove(index);
                    tblSuppliedDetail.refresh();
                    calculateNetTotal();
                } else {

                    new Alert(Alert.AlertType.WARNING, "Please select an item to remove.", ButtonType.OK).show();
                }
            }
        });
        for (int i = 0; i < tblSuppliedDetail.getItems().size(); i++) {
            if (toolID.equals(colToolId.getCellData(i))) {
                toolQuantitySuppliedCount += (int) colOrderDetailsQty.getCellData(i);
                total += (Double) colTotalPrice.getCellData(i) * toolQuantitySuppliedCount;

                obList.get(i).setQty(toolQuantitySuppliedCount);
                obList.get(i).setTotal(total);

                tblSuppliedDetail.refresh();
                calculateNetTotal();
                return;
            }
        }

        obList.add(new StockListTm(supplierId,supplierNameText,toolID,toolName,qtyOnHand,toolQuantitySuppliedCount,toolPriceUnit,total,btn));
        tblSuppliedDetail.setItems(obList);
        calculateNetTotal();

    }

    private void calculateNetTotal() {

            double total = 0;
            for (int i = 0; i < tblSuppliedDetail.getItems().size(); i++) {
                total += (double) colTotalPrice.getCellData(i);
            }
            lblNetTotal.setText(String.valueOf(total));

    }

    private Double calTotal(Double toolPriceUnit, int toolQuantitySuppliedCount) {
        double total = toolPriceUnit * toolQuantitySuppliedCount;
        return total;
    }

    public void cmbToolIdOnAction(ActionEvent actionEvent) {
        String code = (String) cmbToolID.getValue();



        try {
            ToolDto dto = ToolModel.searchToolID(code);

            lblToolName.setText(dto.getToolName());
            lblQtyOnHand.setText(String.valueOf(dto.getQtyOnhand()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    public void btnPlaceNewStockOnAction(ActionEvent actionEvent) {

       List<StockListTm> stockList = new ArrayList<>();
       for (StockListTm stockListTm : obList) {
            stockList.add(stockListTm);
        }
        var stockListDto = new StockListDto(stockList);
       try {
           boolean isAdded = StockListModel.addStockList(stockListDto);
           if (isAdded) {
               new SystemAlert(Alert.AlertType.INFORMATION, "Information", "Stock List Added Successfully!", ButtonType.OK).show();
           }else {
               new SystemAlert(Alert.AlertType.WARNING, "Error", "Stock List Not Added!", ButtonType.OK).show();
           }
       }catch (SQLException e){
           new SystemAlert(Alert.AlertType.ERROR, "Error", e.getMessage(), ButtonType.OK).show();
       }

    }

    public void cmbSupplierIdOnAction(ActionEvent actionEvent) throws SQLException {
        String id = (String) cmbSupplierId.getValue();
        SupplierDto dto = SupplierModel.searchSupplier(id);

        lblSupplierName.setText(dto.getSupplierName());
    }

    public void btnNewSupplierOnAction(ActionEvent actionEvent) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/Customer_form.fxml"));

        this.root.getChildren().clear();
        this.root.getChildren().add(node);
    }
}
