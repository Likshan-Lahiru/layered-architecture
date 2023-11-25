package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.OrderDetailsDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {

    public static boolean saveOrder(String customerId,String orderId,String orderDate, String name) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO orders VALUES (?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,customerId);
        pstm.setString(2,orderId);
        pstm.setDate(3, Date.valueOf(orderDate));
        pstm.setString(4,name);

       boolean issaved =  pstm.executeUpdate()>0;



                return issaved;



    }
    public static String generateNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentOrderId = null;

        if (resultSet.next()) {
            currentOrderId = resultSet.getString(1);
            return splitOrderId(currentOrderId);
        }
        return splitOrderId(null);
    }
    private static String splitOrderId(String currentOrderId) {
        if (currentOrderId != null) {
            String[] split = currentOrderId.split("O");
            int id = Integer.parseInt(split[1]);
            System.out.println(id);
            if (id == 11) {
                id++;
                System.out.println(id);
                return "O0" + id;
            }
            id++;
            return "O0" + id;
        }
        return "O01";
    }


    public List<OrderDetailsDto> getAllOrderDetails() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM order_detail";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<OrderDetailsDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String tool_id = resultSet.getString(1);
            String order_id = resultSet.getString(2);
            String qty = resultSet.getString(3);
            String unit_price  = resultSet.getString(4);
            String order_date = resultSet.getString(5);
            String status = resultSet.getString(6);


            var dto = new OrderDetailsDto(tool_id, order_id,qty, unit_price,order_date,status);
            dtoList.add(dto);
        }
        return dtoList;

    }




}
