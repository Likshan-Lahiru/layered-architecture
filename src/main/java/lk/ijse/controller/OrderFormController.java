package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import lk.ijse.db.DbConnection;
import lk.ijse.dto.*;
import lk.ijse.dto.tm.CartTm;
import lk.ijse.model.*;
import lk.ijse.util.Mail;
import lk.ijse.util.SoundsAssits;
import lk.ijse.util.SystemAlert;
import lk.ijse.util.TxtColours;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class OrderFormController {

    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private JFXTextField txtReStatus;
    @FXML
    private Label lblReOrderId;
    @FXML
    private Label lblReToolId;
    @FXML
    private Label lblReQty;
    @FXML
    private TableView<CartTm> tblOrderDetails;
    @FXML
    private TableColumn<?, ?> colToolName;
    @FXML
    private Label lblOrderDate;
    @FXML
    private Label lblNetTotal;

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
    private JFXComboBox cmbCustomerIddd;
    @FXML
    private Label lblCustomerName;
    @FXML
    private JFXComboBox cmbToolID;
    @FXML
    private Label lblDescription;
    @FXML
    private AnchorPane root;

    private String status;
    SoundsAssits soundsAssits =  new SoundsAssits();
    MainFormController mainFormController = new MainFormController();

    private final ObservableList<CartTm> obList = FXCollections.observableArrayList();
    @FXML
    private Label lblCustomerEmail;

    public OrderFormController() {
    }

    public void initialize() {
        setCellValueFactory();
        setOrderDetailCellValueFactory();
        generateNextOrderId();
        loadToolid();
        loadCustomerIds();
        loadAllOrderDetails();
        setDateToday();
        setReOrder();

    }

    private void setDateToday() {
        lblOrderDate.setText(java.time.LocalDate.now().toString());
    }

    private void setCellValueFactory() {
        colOrderDetailsId.setCellValueFactory(new PropertyValueFactory<>("toolId"));
        colOrderDetailsOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderDetailsQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colOrderDetailsUnitPrice.setCellValueFactory(new PropertyValueFactory<>("rentPerDay"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
         colOrderDetailsStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
       if (status == "Pending"){
           colOrderDetailsStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
           setStyleForPending();
       }

    }

    private void setStyleForPending() {
        colOrderDetailsStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colOrderDetailsStatus.setStyle("-fx-text-fill: red;");
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

    public void loadAllOrderDetails() {
        String cmbToolIDValue = (String) cmbToolID.getValue();
        String txtQtyText = txtQty.getText();
        String lblRentPerDayText = lblRentPerDay.getText();
        String orderId = lblOrderId.getText();
        String orderDate = lblOrderDate.getText();
        String status = txtReStatus.getText();

        new OrderDetailsDto(cmbToolIDValue, orderId, txtQtyText, lblRentPerDayText, orderDate, status);
        var model = new OrderModel();

        ObservableList<CartTm> OrderDetilsTmObservableList = FXCollections.observableArrayList();
        try {
            List<OrderDetailsDto> orderDetailsDtos = model.getAllOrderDetails();
            for (OrderDetailsDto dto : orderDetailsDtos) {
                OrderDetilsTmObservableList.add(
                        new CartTm(
                                dto.getToolId(),
                                dto.getOrderId(),
                                Integer.valueOf(dto.getQty()),
                                Double.valueOf(dto.getUnitprice()),
                                dto.getDate(),
                                dto.getStatus()

                        )
                );
            }
            tblOrderDetails.setItems(OrderDetilsTmObservableList);
        } catch (SQLException e) {
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
            cmbCustomerIddd.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        try {
            String castToolId = (String) cmbToolID.getValue();
            String castDescription = lblDescription.getText();
            String castCustomer = lblCustomerName.getText();
            String castQty = txtQty.getText();
            String castRentalDays = txtRentalDays.getText();
            String castRentPerDay = lblRentPerDay.getText();

            if (castToolId.isEmpty() || castCustomer.isEmpty() || castDescription.isEmpty() || castQty.isEmpty() || castRentalDays.isEmpty() || castRentPerDay.isEmpty()) {
                new SystemAlert(Alert.AlertType.WARNING, "Warning", "Please Enter the all details!", ButtonType.OK).show();
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
        } catch (Exception e) {
            new SystemAlert(Alert.AlertType.WARNING, "Warning", "Please Enter the all details!", ButtonType.OK).show();
            try {
                boolean check = mainFormController.check();

                if(check){
                    soundsAssits.insertAllDetail();
                }
            }catch (Exception e1){
                e1.printStackTrace();
            }
            return;
        }


        String toolId = (String) cmbToolID.getValue();
        String lblDescriptionText = lblDescription.getText();
        int qty = Integer.valueOf(txtQty.getText());
        int rentalDaysText = Integer.valueOf(txtRentalDays.getText());
        double rentaperDay = Double.parseDouble(lblRentPerDay.getText());
        String lblOrderIdText = lblOrderId.getText();
        String orderDateText = lblOrderDate.getText();
        status = "pending";


        Double total = calTotal(qty, rentalDaysText, rentaperDay);
        Button btn = new Button("remove");
        btn.setCursor(Cursor.HAND);

        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new SystemAlert(Alert.AlertType.INFORMATION, "Information", "Are you sure to remove?", yes, no).showAndWait();
            try {
                boolean check = mainFormController.check();

                if(check){
                    soundsAssits.OrderAreYouSure();
                }
            }catch (Exception e1){
                e1.printStackTrace();
            }
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

        obList.add(new CartTm(toolId, lblDescriptionText, qty, rentalDaysText, rentaperDay, total, btn, lblOrderIdText, orderDateText, status));

        tblCart.setItems(obList);
        calculateNetTotal();
        clearHistory();
    }


    private void clearHistory() {
        txtQty.clear();
        txtRentalDays.clear();

    }

    private void calculateNetTotal() {
        double total = 0;
        for (int i = 0; i < tblCart.getItems().size(); i++) {
            total += (double) colTotalPrice.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(total));
    }

    private Double calTotal(int qty, int rentalDaysText, double rentaperDay) {
        Double total = qty * rentaperDay * rentalDaysText;
        return total;
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
       try {
           ObservableList<CartTm> dataList = tblCart.getItems();

           if (dataList.isEmpty()) {
               new SystemAlert(Alert.AlertType.WARNING, "Warning", "Cart is Empty!", ButtonType.OK).show();
               return;
           }
       }catch (Exception e){
           System.out.println("TableView is empty");
       }


        String orderId = lblOrderId.getText();
        String customerId = (String) cmbCustomerIddd.getValue();
        String orderDate = lblOrderDate.getText();
        String descriptionText = lblDescription.getText();
        String lblCustomerNameText =  lblCustomerName.getText();
        String lblNetTotalText = lblNetTotal.getText();



        List<CartTm> cartTmList = new ArrayList<>();

        for (CartTm cartTm : obList) {
            cartTmList.add(cartTm);
        }

        var dto = new PlaceOrderDto(orderId, customerId, orderDate, cartTmList, descriptionText);


        OrderPlaceModel model = new OrderPlaceModel();
        try {
            boolean isAdded = model.placeOrder(dto);
            if (isAdded) {

                new SystemAlert(Alert.AlertType.CONFIRMATION, "Information", "Order Successfully!", ButtonType.OK).show();
                generateInvoice(orderId, lblCustomerNameText, lblNetTotalText);
                try {
                    boolean check = mainFormController.check();

                    if(check){
                        soundsAssits.orderSuccess();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                setOrderDetailCellValueFactory();
                loadAllOrderDetails();
                tblCart.getItems().clear();
                generateNextOrderId();

            } else {
                new SystemAlert(Alert.AlertType.WARNING, "Error", "Order Not Placed!", ButtonType.OK).show();
            }
        } catch (SQLException e) {
            new SystemAlert(Alert.AlertType.ERROR, "Error", e.getMessage(), ButtonType.OK).show();
        }

    }

    private void generateInvoice(String orderId, String lblCustomerNameText, String lblNetTotalText) {
        if (orderId != null) {
            new Thread(() -> {
                try {
                    InputStream resourceAsStream = getClass().getResourceAsStream("/report/Order_invoice.jrxml");
                    JasperDesign load = JRXmlLoader.load(resourceAsStream);
                    JasperReport report = JasperCompileManager.compileReport(load);

                    HashMap map = new HashMap();
                    map.put("id", orderId);
                    map.put("Total", lblNetTotalText);
                    map.put("cutsomer Name", lblCustomerNameText);

                    JasperPrint jasperPrint = JasperFillManager.fillReport(report, map, DbConnection.getInstance().getConnection());
                    JasperViewer.viewReport(jasperPrint, false);

                    JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\Pamudi Narthana\\OneDrive\\Desktop\\invoice\\"+orderId+".pdf");
                    Mail mail = new Mail();
                    mail.setFile(new File("C:\\Users\\Pamudi Narthana\\OneDrive\\Desktop\\invoice\\" + orderId + ".pdf"));
                    mail.setTo(lblCustomerEmail.getText());
                    mail.setSubject("Ashen Enterprise Invoice");
                    mail.setMsg("Your Order has Placed and Invoice is attached here");

                    Thread thread = new Thread(mail);
                    thread.start();

                } catch (SQLException | JRException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
                }
            }).start();
        }else {
            new SystemAlert(Alert.AlertType.WARNING,"Warning","Please select an employee",ButtonType.OK).show();
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
       try {
           String id = (String) cmbCustomerIddd.getValue();
           CustomerDto dto = CustomerModel.searchCustomerId(id);


           lblCustomerName.setText(dto.getCustomerName());
           lblCustomerEmail.setText(dto.getCustomerEmail());
       }catch (SQLException e){
           throw new RuntimeException(e);
       }


    }

    public void btnNewCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/Customer_form.fxml"));

        this.root.getChildren().clear();
        this.root.getChildren().add(node);
    }

    private void setReOrder() {
        tblOrderDetails.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldvalue, newValue) -> {
                    OrderDetailsDto dto = new OrderDetailsDto(
                            newValue.getOrderId(),
                            newValue.getToolId(),
                            newValue.getQty()


                    );
                    orderSetfeild(dto);
                });
    }

    private void orderSetfeild(OrderDetailsDto dto) {
        lblReOrderId.setText(dto.getOrderId());
        lblReToolId.setText(dto.getToolId());
        lblReQty.setText(dto.getQty());


    }

    public void btnFinishOnAction(ActionEvent actionEvent) throws SQLException {

        String lblReOrderIdText = lblReOrderId.getText();
        String lblReToolIdText = lblReToolId.getText();
        String lblReQtyText = lblReQty.getText();
        String txtReStatusText = txtReStatus.getText();


             OrderDetailsDto dto = new OrderDetailsDto(lblReOrderIdText, lblReToolIdText, lblReQtyText, txtReStatusText);
            OrderDetailModel model = new OrderDetailModel();


            try {
                boolean isUpdated = model.returnOrderDetails(dto);
                if (isUpdated) {
                    new SystemAlert(Alert.AlertType.CONFIRMATION, "Information", "Order Returned Successfully!", ButtonType.OK).show();
                    loadAllOrderDetails();
                    setReOrder();
                    txtReStatus.clear();
                    lblReOrderId.setText("");
                    lblReToolId.setText("");
                    lblReQty.setText("");
                } else {
                    new SystemAlert(Alert.AlertType.WARNING, "Error", "Order Not Returned!", ButtonType.OK).show();
                }
            } catch (SQLException e) {
                new SystemAlert(Alert.AlertType.ERROR, "Error", e.getMessage(), ButtonType.OK).show();
            }


        }


    @FXML
    void btnPrintOnAction(ActionEvent event) throws IOException, JRException, SQLException {

       }


    @FXML
    void btnDashBoardOnAction(ActionEvent event) {
        try {
            Parent node = FXMLLoader.load(this.getClass().getResource("/view/dashBoard_form.fxml"));
            this.root.getChildren().clear();
            this.root.getChildren().add(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setMainFormController(MainFormController mainFormController) {
        this.mainFormController = mainFormController;
    }
}
