package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.VehicleDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleModel {
    public boolean saveVehicle(VehicleDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO vehical values (?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,dto.getVehicleId());
        pstm.setString(2, dto.getVehicleStatus());
        pstm.setString(3, dto.getLastServiceDate());
        pstm.setString(4, dto.getNumberPlateNo());

        boolean isSaved = pstm.executeUpdate() > 0;


        return isSaved;
    }
    public List<VehicleDto> getAllvehicle() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql= "SELECT * FROM vehical ";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<VehicleDto> vehicleDtos =new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            String vehical_id =resultSet.getString(1);
            String status =resultSet.getString(2);
            String last_service_date =resultSet.getString(3);
            String number_plate_no  =resultSet.getString(4);


            VehicleDto dto = new VehicleDto(vehical_id,status,last_service_date,number_plate_no);
            vehicleDtos.add(dto);
        }

        return vehicleDtos;
    }
}
