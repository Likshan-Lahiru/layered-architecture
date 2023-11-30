package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {

    public boolean saveEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO employee values (?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getEmployeeid());
        pstm.setString(2, dto.getEmployeeName());
        pstm.setString(3, dto.getEmployeeNIC());
        pstm.setString(4, dto.getEmployeeAddress());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public static List<EmployeeDto> getAllEmployee() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee ";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<EmployeeDto> employeeDtos = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String employee_id = resultSet.getString(1);
            String employee_name = resultSet.getString(2);
            String customer_nic = resultSet.getString(3);
            String customer_address = resultSet.getString(4);


            EmployeeDto dto = new EmployeeDto(employee_id, employee_name, customer_nic, customer_address);
            employeeDtos.add(dto);
        }

        return employeeDtos;
    }

    public static EmployeeDto searchEmployee(String employeeIDText) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM employee WHERE employee_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, employeeIDText);
        ResultSet resultSet = pstm.executeQuery();
        EmployeeDto dto = null;
        if (resultSet.next()) {
            dto = new EmployeeDto(
                    resultSet.getString("employee_id"),
                    resultSet.getString("employee_name"),
                    resultSet.getString("NIC"),
                    resultSet.getString("address")




            );
            System.out.println(dto.getEmployeeid());
            System.out.println(dto.getEmployeeName());
            System.out.println(dto.getEmployeeNIC());
            System.out.println(dto.getEmployeeAddress());

        }

        return dto;
    }

    public boolean updateEmployee(EmployeeDto dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE employee SET employee_name=?,NIC=?,address=? WHERE employee_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getEmployeeName());
        pstm.setString(2, dto.getEmployeeNIC());
        pstm.setString(3, dto.getEmployeeAddress());
        pstm.setString(4, dto.getEmployeeid());

        boolean isUpdated = pstm.executeUpdate() > 0;

        return isUpdated;
    }

    public boolean deleteEmployee(String employeeId) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM employee WHERE employee_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, employeeId);

       boolean isDeleted =  pstm.executeUpdate()>0;

       return isDeleted;

    }
}