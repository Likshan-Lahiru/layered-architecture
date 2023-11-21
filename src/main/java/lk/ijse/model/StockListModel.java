package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.StockListDto;

import java.sql.Connection;
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
}
