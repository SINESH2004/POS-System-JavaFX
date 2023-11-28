package model.impl;

import db.DBConnection;
import dto.InvoiceDto;
import dto.ProductsDto;
import model.InvoiceModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class InvoiceModelImpl implements InvoiceModel {
    InvoiceModel invoiceModel = new InvoiceModelImpl();

    @Override
    public InvoiceDto getItem(String code) throws SQLException, ClassNotFoundException {
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

    @Override
    public List<InvoiceDto> allItems() {
        return null;
    }

    @Override
    public boolean deleteInvoice(String id) {
        return false;
    }

    @Override
    public boolean checkOut(String id) {
        return false;
    }
}
