package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InviceModel {


    public static boolean invoiceDetailsSave(String orderId, List<CartTm> cartTms) throws SQLException {
        for (CartTm cartTm : cartTms) {
            if(!saveDetails(orderId, cartTm)) {

                return false;

            }
        }

        return true;
    }

    private static boolean saveDetails(String orderId, CartTm cartTm) throws SQLException {
       Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO invoice VALUES(?, ?, ?, ?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, orderId);
        pstm.setString(2, cartTm.getToolId());
        pstm.setString(3, cartTm.getLblDescriptionText());
        pstm.setInt(4,  cartTm.getQty());
        pstm.setDouble(5,cartTm.getTotal());

       return pstm.executeUpdate() >0;

    }
}
