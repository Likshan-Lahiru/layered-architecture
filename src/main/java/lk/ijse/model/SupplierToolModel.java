package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.tm.StockListTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SupplierToolModel {
    public static boolean saveStockList(List<StockListTm> stockListTms) throws SQLException {
        for (StockListTm stockListTm : stockListTms) {
            if(!saveStock(stockListTm)) {
                return false;
            }
        }
        return true;


    }
    public static boolean saveStock(StockListTm stockListTms) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO supplier_tool VALUES (?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);


        pstm.setString(1,stockListTms.getSupplierId());
        pstm.setString(2,stockListTms.getSupplierName());
        pstm.setString(3,stockListTms.getToolId());
        pstm.setString(4,stockListTms.getToolName());
        pstm.setString(5,stockListTms.getOrderDate());
        pstm.setInt(6,stockListTms.getQty());

      boolean isSaved =  pstm.executeUpdate()>0;
        return isSaved;
    }
}
