package lk.ijse.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.AttandanceDto;
import lk.ijse.dto.tm.AttandanceTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttandanceModel {

    public boolean addAttandance(AttandanceDto dto) throws SQLException {
       Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO employee_attandance VALUES(?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getEmployeeId());
        pstm.setString(2, dto.getEmployeeName());
        pstm.setString(3, dto.getDate());
        pstm.setString(4, dto.getNIC());
        pstm.setString(5, dto.getStatus());

        return pstm.executeUpdate() > 0;
    }

    public static List<AttandanceDto> getAttandanceDetails() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee_attandance";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<AttandanceDto> attandanceDto = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String employee_id = resultSet.getString(1);
            String employee_name = resultSet.getString(2);
            String NIC = resultSet.getString(3);
            String date = resultSet.getString(4);
            String status = resultSet.getString(5);

            AttandanceDto dto = new AttandanceDto(employee_id, employee_name, NIC, date, status);
            attandanceDto.add(dto);
        }

        return attandanceDto;
    }

    public boolean isExist(LocalDate date) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT employee_id FROM employee_attandance WHERE date=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, date.toString());

        ResultSet resultSet = pstm.executeQuery();
        return resultSet.next();
    }

    public ObservableList<AttandanceTm> getAttendanceOfDay(String date) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee_attandance WHERE date = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, date);

        ResultSet resultSet =pstm.executeQuery();
        ObservableList<AttandanceTm> tmList = FXCollections.observableArrayList();

        while(resultSet.next()){
            final var add = tmList.add(new AttandanceTm(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return tmList;

}
}
