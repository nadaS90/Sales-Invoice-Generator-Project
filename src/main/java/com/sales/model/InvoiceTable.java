package com.sales.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class InvoiceTable extends AbstractTableModel {

    private ArrayList<InvoiceClass> invoice;
    private String[] colms = {"InvNo.", "InvDate", "CstmrName", "Amout"};

    public InvoiceTable(ArrayList<InvoiceClass> invoice) {
        this.invoice = invoice;
    }

    @Override
    public int getRowCount() {
        return invoice.size();
    }

    @Override
    public int getColumnCount() {
        return colms.length;
    }

    @Override
    public String getColumnName(int colm) {
        return colms[colm];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceClass invce = invoice.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return invce.getInvoiceNumber();
            case 1:
                return invce.getDate();
            case 2:
                return invce.getCustomer();
            case 3:
                return invce.getInvoiceTotal();
            default:
                return "";

        }

    }

    public void setModel(InvoiceTable InvoiceTable) {
        
    }

}
