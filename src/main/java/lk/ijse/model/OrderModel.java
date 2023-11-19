package lk.ijse.model;

import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderModel {
    public static String generateNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentOrderId = null;

        if (resultSet.next()) {
            currentOrderId = resultSet.getString(1);
            return splitOrderId(currentOrderId);
        }
        return splitOrderId(null);
    }
    private static String splitOrderId(String currentOrderId) {
        if (currentOrderId != null) {
            String[] split = currentOrderId.split("O");
            int id = Integer.parseInt(split[1]);
            id++;
            return "O00" + id;
        }
        return "O002";
    }
}
