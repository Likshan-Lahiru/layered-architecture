package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.OrderDetailsDto;
import lk.ijse.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailModel {
   private final ToolModel toolModel = new ToolModel();
    public static boolean saveOrderDetail(String orderId, List<CartTm> cartTms) throws SQLException {
        for (CartTm cartTm : cartTms) {
            if(!saveOrderDetail(orderId, cartTm)) {

                return false;

            }
        }

        return true;
    }





    private static boolean saveOrderDetail(String orderId, CartTm cartTm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO order_detail VALUES(?, ?, ?, ?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, cartTm.getToolId());
        pstm.setString(2, orderId);
        pstm.setInt(3,  cartTm.getQty());
        pstm.setDouble(4, cartTm.getRentPerDay());
        pstm.setString(5,cartTm.getOrderDate());
        pstm.setString(6,cartTm.getStatus());



        return pstm.executeUpdate() > 0;
    }

    public boolean returnOrderDetails(OrderDetailsDto dto) throws SQLException {
        boolean result = false;
        Connection connection = null;

        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isUpdated = toolModel.updateToolReturnQty(dto);
            if(isUpdated) {
               boolean isOrderDetailSaved  = OrderDetailModel.updateReturnPrderDetail(dto);
               if (isOrderDetailSaved) {
                   connection.commit();
                   result = true;
               }
            }
        }catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return result;

    }

    private static boolean updateReturnPrderDetail(OrderDetailsDto dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE order_detail SET status = ? WHERE order_id = ? AND tool_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getStatus());
        pstm.setString(2, dto.getOrderId());
        pstm.setString(3, dto.getToolId());

      boolean isUpdate =  pstm.executeUpdate()>0;

        return isUpdate;
    }


}
