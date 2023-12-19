package com.example.layeredarchitecture.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class OrderDAOImpl implements OrderDAO {

    public boolean checkExistsOrderId(String orderId, Connection connection) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("SELECT oid FROM `Orders` WHERE oid=?");
        stm.setString(1, orderId);

        if (stm.executeQuery().next()){
            connection.setAutoCommit(false);
            return true;
        }
        connection.setAutoCommit(false);
        return false;
    }

    public boolean saveOrder(String orderId, LocalDate orderDate, String customerId, Connection connection) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("INSERT INTO `Orders` (oid, date, customerID) VALUES (?,?,?)");
        stm.setString(1, orderId);
        stm.setDate(2, Date.valueOf(orderDate));
        stm.setString(3, customerId);

        if (!(stm.executeUpdate() > 0)) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        } else {
            return true;
        }
    }
}
