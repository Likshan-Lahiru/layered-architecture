package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeModel {

    public boolean saveEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO employee values (?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getEmployeeid());
        pstm.setString(2,dto.getEmployeeName());
        pstm.setString(3,dto.getEmployeeNIC());
        pstm.setString(4,dto.getEmployeeAddress());

       boolean isSaved = pstm.executeUpdate()>0;

        return isSaved;
    }
}
