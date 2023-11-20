package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailModel {
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

        String sql = "INSERT INTO order_detail VALUES(?, ?, ?, ?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, cartTm.getToolId());
        pstm.setString(2, orderId);
        pstm.setInt(3,  cartTm.getQty());
        pstm.setDouble(4, cartTm.getRentPerDay());
        pstm.setString(5,cartTm.getLblDescriptionText());



        return pstm.executeUpdate() > 0;
    }
}
