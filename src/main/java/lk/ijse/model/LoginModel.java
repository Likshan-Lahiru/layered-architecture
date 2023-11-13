package lk.ijse.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.LoginDto;

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

        if (resultSet.next()){
            return true;
        }else {
            return false;
        }



    }
}
