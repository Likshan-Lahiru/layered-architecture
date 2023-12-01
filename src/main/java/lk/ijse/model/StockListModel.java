package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.StockListDto;
import lk.ijse.dto.ToolWasteDetailDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StockListModel {
    private static final ToolModel toolModel  = new ToolModel();

    public static boolean addStockList(StockListDto stockListDto) throws SQLException {
        boolean result = false;
        Connection connection = null;
        try {

            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isUpdated = toolModel.addStockList(stockListDto.getStockListTms());
           if(isUpdated) {
                boolean isSaved = SupplierToolModel.saveStockList(stockListDto.getStockListTms());
               if(isSaved) {
                    connection.commit();
                    result = true;
                }
            }
        }catch (SQLException e) {
            connection.rollback();
        }finally {
            connection.setAutoCommit(true);
        }


        return result;
    }

    public boolean updateWasteQty(ToolWasteDetailDto dto) throws SQLException {
       Connection connection =  DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO tool_waste VALUES(?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getToolId());
        pstm.setString(2,dto.getToolName());
        pstm.setInt(3,dto.getQtyOnhand());
        pstm.setString(4, dto.getWasteCount());
        pstm.setString(5, dto.getLastupdatedDate());



        boolean isUpdate = pstm.executeUpdate() > 0;
        return isUpdate;

    }
}
