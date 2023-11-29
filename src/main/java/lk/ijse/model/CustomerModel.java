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

public class CustomerModel {

    public boolean saveCustomer(CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO customer VALUES (?, ?, ?, ?, ?) ";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getCustomerId());
        pstm.setString(2, dto.getCustomerName());
        pstm.setString(3, dto.getCustomerAddress());
        pstm.setString(4, dto.getCustomerNic());
        pstm.setString(5, dto.getCustomerContactNumber());

        boolean IsSaved =pstm.executeUpdate()>0;
        return IsSaved;
    }

    public static List<CustomerDto> getAllCustomer() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql= "SELECT * FROM customer ";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<CustomerDto> customerDtoList =new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            String customer_id =resultSet.getString(1);
            String customer_name =resultSet.getString(2);
            String customer_address=resultSet.getString(3);
            String customer_nic =resultSet.getString(4);
            String customer_contact =resultSet.getString(5);

            CustomerDto dto = new CustomerDto(customer_id, customer_name, customer_address, customer_nic, customer_contact);
            customerDtoList.add(dto);
        }

        return customerDtoList;
    }

    public static CustomerDto searchCustomer(String txtSearchCustomerIDText) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM customer WHERE customer_id= ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,txtSearchCustomerIDText);
        ResultSet resultSet = pstm.executeQuery();
        CustomerDto dto = null;
        if (resultSet.next()){
             dto = new CustomerDto(
                resultSet.getString("customer_id"),
                resultSet.getString("customer_name"),
                resultSet.getString("address"),
                resultSet.getString("NIC"),
                resultSet.getString("contact_number")
            );
        }

        return dto;
    }


    public boolean updateCustomer(CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE customer SET customer_name=?,address=?,NIC=?,contact_number=? WHERE customer_id=? ";
        PreparedStatement pstm = connection.prepareStatement(sql);


        pstm.setString(1, dto.getCustomerName());
        pstm.setString(2, dto.getCustomerAddress());
        pstm.setString(3, dto.getCustomerNic());
        pstm.setString(4, dto.getCustomerContactNumber());
        pstm.setString(5, dto.getCustomerId());

        boolean IsSaved =pstm.executeUpdate()>0;

        return IsSaved;

    }
}
