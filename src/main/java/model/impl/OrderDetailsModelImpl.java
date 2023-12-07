package model.impl;

import db.DBConnection;
import dto.OrderDetailsDto;
import model.OrderDetailsModel;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailsModelImpl implements OrderDetailsModel {
    @Override
    public boolean saveOrderDetails(List<OrderDetailsDto> list) throws SQLException, ClassNotFoundException {
        boolean isDetailsSaved = true;
        for (OrderDetailsDto dto:list) {
            String sql = "INSERT INTO orderdetail VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1,dto.getOrderID());
            preparedStatement.setString(2,dto.getProductID());
            preparedStatement.setInt(3,dto.getQuantity());
            preparedStatement.setDouble(4,dto.getUnitPrice());

            if (!(preparedStatement.executeUpdate() >0)){
                isDetailsSaved = false;
            }
        }
        return isDetailsSaved;
    }
}
