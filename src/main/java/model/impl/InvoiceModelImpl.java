package model.impl;

import dto.InvoiceDto;
import model.InvoiceModel;

public class InvoiceModelImpl implements InvoiceModel {
    InvoiceModel invoiceModel = new InvoiceModelImpl();

    @Override
    public boolean saveInvoice(InvoiceDto dto) {
        return false;
    }
}
