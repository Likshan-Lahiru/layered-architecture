package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.PlaceOrderDto;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderPlaceModel {

    private final ToolModel toolModel = new ToolModel();
    private final OrderDetailModel orderDetailModel =new OrderDetailModel();

    public boolean placeOrder(PlaceOrderDto dto) throws SQLException {
      boolean result = false;
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);


            boolean isOrderSaved = OrderModel.saveOrder(dto.getCustomerId(),dto.getOrderId(),dto.getOrderDate(),dto.getName());
         if (isOrderSaved) {

                boolean isUpdated = toolModel.updateTool(dto.getCartTms());
               if(isUpdated) {
                    boolean isOrderDetailSaved = orderDetailModel.saveOrderDetail(dto.getOrderId(), dto.getCartTms());
                  if(isOrderDetailSaved) {
                      connection.commit();

                        result = true;
                    }
                }
            }
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return result;


    }
}
