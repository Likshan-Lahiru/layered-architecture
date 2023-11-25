package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.OrderDetailsDto;
import lk.ijse.dto.ToolDto;
import lk.ijse.dto.tm.CartTm;
import lk.ijse.dto.tm.StockListTm;

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

    public static List<ToolDto> getAllTool() throws SQLException {
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

    public static ToolDto  searchToolID(String dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM tool WHERE tool_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto);
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





    public boolean updateTool(List<CartTm> tmList) throws SQLException {
        for (CartTm cartTm : tmList) {
            if(!updateQty(cartTm)) {
                return false;
            }
        }
        return true;
    }

    private boolean updateQty(CartTm cartTm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE tool SET qty_on_hand = qty_on_hand - ? WHERE tool_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, cartTm.getQty());
        pstm.setString(2, cartTm.getToolId());

        return pstm.executeUpdate() > 0;
    }

    public boolean addStockList(List<StockListTm> stockListTms) throws SQLException {
        for (StockListTm stockListTm : stockListTms) {
            if(!updateQty2(stockListTm)) {
                return false;
            }
        }
        return true;


    }

    private boolean updateQty2(StockListTm stockListTm) throws SQLException {
        System.out.printf(String.valueOf(stockListTm.getQty()));
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE tool SET qty_on_hand = qty_on_hand + ? WHERE tool_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setInt(1, stockListTm.getQty());
        pstm.setString(2, stockListTm.getToolId());



        return pstm.executeUpdate() > 0;

    }

    public boolean updateToolReturnQty(OrderDetailsDto dto) throws SQLException {

            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "UPDATE tool SET qty_on_hand = qty_on_hand + ? WHERE tool_id = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, Integer.parseInt(dto.getQty()));
            pstm.setString(2, dto.getToolId());
            boolean issaved = pstm.executeUpdate()>0;


            return issaved;

    }
}
