package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.ItemDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;
import com.jfoenix.controls.JFXComboBox;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    public  boolean updateItemQty(ItemDTO item, Connection connection) throws SQLException {
        PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?");
        pstm.setString(1, item.getDescription());
        pstm.setBigDecimal(2, item.getUnitPrice());
        pstm.setInt(3, item.getQtyOnHand());
        pstm.setString(4, item.getCode());


        if (!(pstm.executeUpdate() > 0)) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        } else {
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        }
    }

    @Override
    public ArrayList<ItemDTO> getAllItem() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Item");
        ArrayList<ItemDTO> getAllItem = new ArrayList<>();
        while (rst.next()){
           ItemDTO itemDTO = new ItemDTO(rst.getString("code"),rst.getString("description"),rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand"));
            getAllItem.add(itemDTO);
        }
        return getAllItem;
    }
    @Override
    public void itemDelete(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM Item WHERE code=?");
            pstm.setString(1, code);
            pstm.executeUpdate();

    }
    @Override
    public void saveItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
                PreparedStatement pstm = connection.prepareStatement("INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)");
                pstm.setString(1, itemDTO.getCode());
                pstm.setString(2, itemDTO.getDescription());
                pstm.setBigDecimal(3, itemDTO.getUnitPrice());
                pstm.setInt(4, itemDTO.getQtyOnHand());
                pstm.executeUpdate();
    }
    @Override
    public boolean isExistItem(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT code FROM Item WHERE code=?");
        pstm.setString(1, code);
        return pstm.executeQuery().next();

    }
    @Override
    public String generateNewItemCode() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        ResultSet rst = connection.createStatement().executeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("code");
            int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemId);
        } else {
            return "I00-001";
        }
    }
    public void laodAllItemCode(JFXComboBox<String> cmbItemCode) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Item");
        while (rst.next()) {
            cmbItemCode.getItems().add(rst.getString("code"));
        }

    }



}
