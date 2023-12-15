package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO {
    ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException;
    void saveCustomer(String id, String name, String address) throws SQLException, ClassNotFoundException;
    void updateCustomer(String id, String name, String address) throws SQLException, ClassNotFoundException;
    boolean isExistCustomer(String id) throws SQLException, ClassNotFoundException;
    void deleteCustomer(String id) throws SQLException, ClassNotFoundException;
    String generateNewCustomerId() throws SQLException, ClassNotFoundException;
}
