package model;

import dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerModel {
    boolean SaveBtn(CustomerDto dto) throws SQLException, ClassNotFoundException;
    boolean UpdateBtn(CustomerDto  dto) throws SQLException, ClassNotFoundException;
    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
    List<dto.CustomerDto> allCustomers() throws SQLException, ClassNotFoundException;
}
