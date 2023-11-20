package model;

import dto.CustomerDto;
import dto.ProductsDto;

import java.sql.SQLException;
import java.util.List;

public interface ProductsModel {
    boolean productSaveBtn(ProductsDto dto) throws SQLException, ClassNotFoundException;
    boolean productUpdateBtn(ProductsDto  dto) throws SQLException, ClassNotFoundException;
    boolean productDeleteCustomer(String id) throws SQLException, ClassNotFoundException;
    List<ProductsDto> productAllCustomers() throws SQLException, ClassNotFoundException;
}
