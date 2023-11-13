package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.ToolDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ToolModel {

    public boolean saveTool(ToolDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql ="INSERT INTO tool VALUES (?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getToolId());
        pstm.setString(2,dto.getToolName());
        pstm.setInt(3,dto.getQtyOnhand());
        pstm.setDouble(4, dto.getRentPerDay());

        return pstm.executeUpdate() > 0;


    }

    public List<ToolDto> getAllTool() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "Select * FROM tool";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ArrayList<ToolDto> dtoList = new ArrayList<>();
        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
           String toolId = resultSet.getString("tool_id");
           String toolName = resultSet.getString("tool_name");
           int qtyOnHand = Integer.parseInt(String.valueOf(resultSet.getInt("qty_on_hand")));
           double rentPerDayPrice =resultSet.getDouble("rent_per_day_price");

            var dto = new ToolDto(toolId, toolName, qtyOnHand, rentPerDayPrice);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public ToolDto searchToolID(ToolDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM tool WHERE tool_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getToolId());
        ResultSet resultSet = pstm.executeQuery();
        ToolDto toolDto = null;
        if (resultSet.next()){
           toolDto = new ToolDto(
                    resultSet.getString("tool_id"),
                    resultSet.getString("tool_name"),
                    resultSet.getInt("qty_on_hand"),
                    resultSet.getDouble("rent_per_day_price")
            );

        }
        return toolDto;
    }

    public boolean updateToolId(ToolDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql= "UPDATE tool SET  tool_name = ?, qty_on_hand = ?, rent_per_day_price = ? WHERE tool_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getToolId());
        pstm.setString(2, dto.getToolName());
        pstm.setInt(3,dto.getQtyOnhand());
        pstm.setDouble(4,dto.getRentPerDay());

        boolean isUpdate = pstm.executeUpdate() > 0;
        return isUpdate;
    }
}
