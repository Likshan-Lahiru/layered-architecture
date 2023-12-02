package lk.ijse.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.LoginDto;
import lk.ijse.dto.SignUpDto;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    public AnchorPane root;

    public boolean checkCredentianl(LoginDto dto) throws SQLException, IOException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select * from user where user_name = ? and password = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getUsername());
        pstm.setString(2, dto.getPassword());


        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return true;
        } else {
            return false;
        }



    }



    public static SignUpDto getName(String nameText) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select * from user where user_name = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, nameText);
        ResultSet resultSet = pstm.executeQuery();
        SignUpDto dto = null;
        if (resultSet.next()) {
            dto = new SignUpDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)

            );
        }

        return dto;
    }
}
