package model.impl;

import db.DBConnection;
import dto.ProductsDto;
import model.ProductsModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductsModelImpl implements ProductsModel {
    @Override
    public boolean productSaveBtn(ProductsDto dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO products VALUES(?,?,?,?)";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(2,dto.getCode());
        pstm.setString(3,dto.getDescription());
        pstm.setDouble(4,dto.getUnitPrice());
        pstm.setInt(1,dto.getQuantity());

        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean productUpdateBtn(ProductsDto dto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE products SET name=?, address=?, salary=? WHERE id=?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1,dto.getCode());
        pstm.setString(2,dto.getDescription());
        pstm.setDouble(3,dto.getUnitPrice());
        pstm.setInt(4,dto.getQuantity());

        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean productDeleteCustomer(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE from products WHERE id=?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1,id);
        return pstm.executeUpdate()>0;
    }

    @Override
    public List<ProductsDto> productAllCustomers() throws SQLException, ClassNotFoundException {
        List<dto.ProductsDto> list = new ArrayList<>();

        String sql = "SELECT * FROM products";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            list.add(new dto.ProductsDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            ));
        }
        return list;
    }
}