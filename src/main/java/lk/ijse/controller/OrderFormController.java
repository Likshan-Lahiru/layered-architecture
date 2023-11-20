package lk.ijse.controller;

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
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.PlaceOrderDto;
import lk.ijse.dto.ToolDto;
import lk.ijse.dto.tm.CartTm;
import lk.ijse.dto.OrderDetailsDto;
import lk.ijse.model.CustomerModel;
import lk.ijse.model.OrderModel;
import lk.ijse.model.OrderPlaceModel;
import lk.ijse.model.ToolModel;
import lk.ijse.util.SystemAlert;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderFormController {

    @FXML
    private TableView <CartTm> tblOrderDetails;
    @FXML
    private TableColumn colToolName;
    @FXML
    private Label lblOrderDate;
    @FXML
    private Label lblNetTotal;
    @FXML
    private TableColumn<?, ?> colOrderDetailsAction;
    @FXML
    private TableColumn<?, ?> colOrderDetailsStatus;
    @FXML
    private TableColumn<?, ?> colOrderDetailsUnitPrice;
    @FXML
    private TableColumn<?, ?> colOrderDetailsQty;
    @FXML
    private TableColumn<?, ?> colOrderDetailsId;
    @FXML
    private TableColumn<?, ?> colOrderDetailsOrderId;
    @FXML
    private TextField txtRentalDays;
    @FXML
    private TextField txtQty;
    @FXML
    private Label lblQutyOnHand;
    @FXML
    private Label lblRentPerDay;
    @FXML
    private TableView<CartTm> tblCart;
    @FXML
    private TableColumn<?, ?> colToolId;
    @FXML
    private TableColumn<?, ?> ColQty;
    @FXML
    private TableColumn<?, ?> ColRentPerDayPrice;
    @FXML
    private TableColumn<?, ?> colRentalDaysCount;
    @FXML
    private TableColumn<?, ?> colTotalPrice;
    @FXML
    private TableColumn<?, ?> colAction;
    @FXML
    private Label lblOrderId;
    @FXML
    private JFXComboBox cmbCustomerId;
    @FXML
    private Label lblCustomerName;
    @FXML
    private JFXComboBox cmbToolID;
    @FXML
    private Label lblDescription;
    @FXML
    private AnchorPane root;

    private final ObservableList<CartTm> obList = FXCollections.observableArrayList();

    public OrderFormController() {
    }

    public void initialize(){
        setCellValueFactory();
        setOrderDetailCellValueFactory();
        generateNextOrderId();
        loadToolid();
        loadCustomerIds();
        loadAllOrderDetails();
        setDateToday();

    }

    private void setDateToday() {
        lblOrderDate.setText(java.time.LocalDate.now().toString());
    }

    private void setCellValueFactory() {
        colOrderDetailsId.setCellValueFactory(new PropertyValueFactory<>("toolId"));
        colOrderDetailsOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderDetailsQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colOrderDetailsUnitPrice.setCellValueFactory(new PropertyValueFactory<>("rentPerDay"));

    }
    private void setOrderDetailCellValueFactory() {
        colToolId.setCellValueFactory(new PropertyValueFactory<>("toolId"));
        colToolName.setCellValueFactory(new PropertyValueFactory<>("lblDescriptionText"));
        ColQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        ColRentPerDayPrice.setCellValueFactory(new PropertyValueFactory<>("rentPerDay"));
        colRentalDaysCount.setCellValueFactory(new PropertyValueFactory<>("rentalDaysText"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }
    public void loadAllOrderDetails(){
        String cmbToolIDValue  = (String) cmbToolID.getValue();
        String txtQtyText = txtQty.getText();
        String lblRentPerDayText = lblRentPerDay.getText();
        String orderId = lblOrderId.getText();
        String orderDate = lblOrderDate.getText();

        new OrderDetailsDto(cmbToolIDValue,orderId,txtQtyText,lblRentPerDayText,orderDate);
        var model = new OrderModel();

        ObservableList<CartTm> OrderDetilsTmObservableList = FXCollections.observableArrayList();
        try {
            List<OrderDetailsDto> orderDetailsDtos =model.getAllOrderDetails();
            for (OrderDetailsDto dto : orderDetailsDtos){
                OrderDetilsTmObservableList.add(
                        new CartTm(
                                dto.getToolId(),
                                dto.getOrderId(),
                                Integer.valueOf(dto.getQty()),
                                Double.valueOf(dto.getUnitprice()),
                                dto.getDate()

                        )
                );
            }
            tblOrderDetails.setItems(OrderDetilsTmObservableList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }


    }


    private void generateNextOrderId() {

        try {
            String orderId = OrderModel.generateNextOrderId();
            lblOrderId.setText(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
    private void loadCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<CustomerDto> cusList = CustomerModel.getAllCustomer();

            for (CustomerDto dto : cusList) {
                obList.add(dto.getCustomerId());
            }
            cmbCustomerId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        try {
            String castToolId = (String) cmbToolID.getValue();
            String castDescription = lblDescription.getText();
            String castCustomer =lblCustomerName.getText();
            String castQty=txtQty.getText();
            String castRentalDays=txtRentalDays.getText();
            String castRentPerDay=lblRentPerDay.getText();

            if (castToolId.isEmpty()||castCustomer.isEmpty()||castDescription.isEmpty()||castQty.isEmpty()||castRentalDays.isEmpty()||castRentPerDay.isEmpty()){
                new SystemAlert(Alert.AlertType.WARNING,"Warning","Please Enter the all details!",ButtonType.OK).show();
                return;
            }
        }catch (Exception e){
            new SystemAlert(Alert.AlertType.WARNING,"Warning","Please Enter the all details!",ButtonType.OK).show();
            return;
        }


        String toolId = (String) cmbToolID.getValue();
        String lblDescriptionText = lblDescription.getText();
        int qty = Integer.valueOf(txtQty.getText());
        int rentalDaysText = Integer.valueOf(txtRentalDays.getText());
        double rentaperDay = Double.parseDouble(lblRentPerDay.getText());
        String lblOrderIdText= lblOrderId.getText();
        String orderDateText = lblOrderDate.getText();


        Double total = calTotal(qty,rentalDaysText,rentaperDay);
        Button btn = new Button("remove");
        btn.setCursor(Cursor.HAND);

        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new SystemAlert(Alert.AlertType.INFORMATION,"Information", "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int index = tblCart.getSelectionModel().getSelectedIndex();

                if (index >= 0 && index < obList.size()) {
                    obList.remove(index);
                    tblCart.refresh();
                    calculateNetTotal();
                } else {

                    new Alert(Alert.AlertType.WARNING, "Please select an item to remove.", ButtonType.OK).show();
                }
            }
        });

        for (int i = 0; i < tblCart.getItems().size(); i++) {
            if (toolId.equals(colToolId.getCellData(i))) {
                qty += (int) colOrderDetailsQty.getCellData(i);
                total = qty * rentaperDay * rentalDaysText;

                obList.get(i).setQty(qty);
                obList.get(i).setTotal(total);

                tblCart.refresh();
                calculateNetTotal();
                return;
            }
        }

        obList.add(new CartTm(toolId, lblDescriptionText ,qty, rentalDaysText, rentaperDay, total, btn,lblOrderIdText,lblDescriptionText));

        tblCart.setItems(obList);
        calculateNetTotal();
        txtQty.clear();
        txtRentalDays.clear();
        lblDescription.setText("");
        lblRentPerDay.setText("");
        lblQutyOnHand.setText("");
        cmbToolID.requestFocus();
        cmbCustomerId.requestFocus();
    }

    private void calculateNetTotal() {
        double total = 0;
        for (int i = 0; i < tblCart.getItems().size(); i++) {
            total += (double) colTotalPrice.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(total));
    }

    private Double calTotal(int qty, int rentalDaysText, double rentaperDay) {
           Double total = qty*rentaperDay*rentalDaysText;
           return total;
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        String orderId = lblOrderId.getText();
        String customerId = (String) cmbCustomerId.getValue();
        String orderDate = lblOrderDate.getText();
        String descriptionText = lblDescription.getText();




        List<CartTm> cartTmList = new ArrayList<>();

        for (CartTm cartTm : obList) {
            cartTmList.add(cartTm);
        }

        var dto = new PlaceOrderDto(orderId, customerId, orderDate, cartTmList,descriptionText);


        OrderPlaceModel model = new OrderPlaceModel();
        try {
            boolean isAdded=model.placeOrder(dto);
            if (isAdded){

                new SystemAlert(Alert.AlertType.CONFIRMATION,"Information","Order Placed Successfully!",ButtonType.OK).show();
                loadAllOrderDetails();
                generateNextOrderId();

            }else {
                new SystemAlert(Alert.AlertType.WARNING,"Error","Order Not Placed!",ButtonType.OK).show();
            }
        }catch (SQLException e){
            new SystemAlert(Alert.AlertType.ERROR,"Error",e.getMessage(),ButtonType.OK).show();
        }

    }



    public void txtQtyOnAction(ActionEvent actionEvent) {
        String toolid = (String) cmbToolID.getValue();

        txtQty.requestFocus();

        try {
            ToolDto dto = ToolModel.searchToolID(toolid);

            lblDescription.setText(dto.getToolName());
            lblRentPerDay.setText(String.valueOf(dto.getRentPerDay()));
            lblQutyOnHand.setText(String.valueOf(dto.getQtyOnhand()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void cmbToolIdOnAction(ActionEvent actionEvent) {
        String code = (String) cmbToolID.getValue();

        txtQty.requestFocus();

        try {
            ToolDto dto = ToolModel.searchToolID(code);

            lblDescription.setText(dto.getToolName());
            lblRentPerDay.setText(String.valueOf(dto.getRentPerDay()));
            lblQutyOnHand.setText(String.valueOf(dto.getQtyOnhand()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbCustomerOnAction(ActionEvent actionEvent) throws SQLException {
        String id = (String) cmbCustomerId.getValue();
        CustomerDto dto = CustomerModel.searchCustomer(id);

        lblCustomerName.setText(dto.getCustomerName());
    }

    public void btnNewCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/supplier_form.fxml"));

        this.root.getChildren().clear();
        this.root.getChildren().add(node);
    }
}
