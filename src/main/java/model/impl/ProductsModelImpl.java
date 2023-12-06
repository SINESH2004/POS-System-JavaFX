package model.impl;

import db.DBConnection;
import dto.ProductsDto;
import javafx.scene.control.Alert;
import model.ProductsModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class ProductsModelImpl implements ProductsModel {
    @Override
    public boolean productSaveBtn(ProductsDto dto) throws SQLException, ClassNotFoundException {
        try {
            String sql = "INSERT INTO products VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
                pstm.setString(1, dto.getCode());
                pstm.setString(2, dto.getDescription());
                pstm.setDouble(3, dto.getUnitPrice());
                pstm.setInt(4, dto.getQuantity());

                return pstm.executeUpdate() > 0;
            }
        }catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
            return false;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean productUpdateBtn(ProductsDto dto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE products SET qtyOnHand=?, description=?, unitPrice=? WHERE code=?";
        try (PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstm.setInt(1, dto.getQuantity());
            pstm.setString(2, dto.getDescription());
            pstm.setDouble(3, dto.getUnitPrice());
            pstm.setString(4, dto.getCode());
            return pstm.executeUpdate() > 0;
        }
    }


    @Override
    public boolean deleteProduct(String code) throws SQLException, ClassNotFoundException {
        String sql = "DELETE from products WHERE code=?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1,code);
        return pstm.executeUpdate()>0;
    }

    @Override
    public List<ProductsDto> allProducts() throws SQLException, ClassNotFoundException {
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
    public ProductsDto getProductByCode(String code) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM products WHERE code=?";
        try (PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstm.setString(1, code);
            try (ResultSet resultSet = pstm.executeQuery()) {
                if (resultSet.next()) {
                    return new ProductsDto(
                            resultSet.getString("code"),
                            resultSet.getString("description"),
                            resultSet.getDouble("unitPrice"),
                            resultSet.getInt("qtyOnHand")
                    );
                }
            }
        }
        return null;
    }

}
