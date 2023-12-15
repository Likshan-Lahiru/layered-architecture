package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.model.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO {
    ArrayList<ItemDTO> getAllItem() throws SQLException, ClassNotFoundException;
    void itemDelete(String code) throws SQLException, ClassNotFoundException;
    void saveItem(ItemDTO itemDTO)throws SQLException, ClassNotFoundException;
    boolean isExistItem(String code)throws SQLException, ClassNotFoundException;
    String generateNewItemCode()throws SQLException, ClassNotFoundException;
}
