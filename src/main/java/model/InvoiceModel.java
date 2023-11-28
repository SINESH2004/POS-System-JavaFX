package model;

import dto.InvoiceDto;

import java.sql.SQLException;
import java.util.List;

public interface InvoiceModel {
    InvoiceDto getItem(String code) throws SQLException, ClassNotFoundException;
    List<InvoiceDto> allItems();
    boolean deleteInvoice(String id);
    boolean checkOut(String id);
}
