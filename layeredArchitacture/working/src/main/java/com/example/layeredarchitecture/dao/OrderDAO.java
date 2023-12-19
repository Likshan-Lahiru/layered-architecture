package com.example.layeredarchitecture.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public interface OrderDAO {
    boolean checkExistsOrderId(String orderId, Connection connection) throws SQLException;
    boolean saveOrder(String orderId, LocalDate orderDate, String customerId, Connection connection) throws SQLException;
}
