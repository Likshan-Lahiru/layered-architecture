package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.SupplierDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {
    public List<SupplierDto> getAllSupplier() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql= "SELECT * FROM supplier ";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<SupplierDto> supplierDtoList =new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            String Supplier_id =resultSet.getString(1);
            String Supplier_name =resultSet.getString(2);
            String Supplier_nic =resultSet.getString(3);
            String Supplier_address=resultSet.getString(4);
            String Supplier_contact =resultSet.getString(5);

            SupplierDto dto = new SupplierDto(Supplier_id,Supplier_name,Supplier_address,Supplier_nic,Supplier_contact);
            supplierDtoList.add(dto);
        }

        return supplierDtoList;

    }

    public boolean saveSupplier(SupplierDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql ="INSERT INTO supplier VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,dto.getSupplierId());
        pstm.setString(2,dto.getSupplierName());
        pstm.setString(3, dto.getSupplierNIC());
        pstm.setString(4, dto.getSupplierAddress());
        pstm.setString(5, dto.getSupplierContactNumber());

       boolean isSvaed = pstm.executeUpdate()>0;

        return isSvaed;
    }

    public SupplierDto searchSupplier(String searchSupplierIDText) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM supplier WHERE supplier_id= ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,searchSupplierIDText);
        ResultSet resultSet = pstm.executeQuery();
        SupplierDto dto = null;
        if (resultSet.next()){
            dto = new SupplierDto(
                    resultSet.getString("supplier_id"),
                    resultSet.getString("supplier_name"),
                    resultSet.getString("NIC"),
                    resultSet.getString("address"),
                    resultSet.getString("contact_number")
            );
        }

        return dto;
    }
}

