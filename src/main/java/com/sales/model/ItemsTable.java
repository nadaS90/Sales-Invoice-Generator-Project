package com.sales.model;

import java.util.ArrayList;
import javax.swing.event.EventListenerList;
import javax.swing.table.AbstractTableModel;

public class ItemsTable extends AbstractTableModel {

    private ArrayList<ItemsClass> items;
    private String[] colms = {"InvNo.", "Item Name", "Price", "Count", "Total Amount"};

    public ItemsTable(ArrayList<ItemsClass> items) {
        this.items = items;
    }

    public ArrayList<ItemsClass> getItems() {
        return items;
    }

    public String[] getColms() {
        return colms;
    }

    public EventListenerList getListenerList() {
        return listenerList;
    }

    @Override
    public int getRowCount() {
        return items.size();
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
        ItemsClass itms = items.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return itms.getInvoice().getNumber();
            case 1:
                return itms.getItemName();
            case 2:
                return itms.getPrice();
            case 3:
                return itms.getAmount();
            case 4:
                return itms.getItemsTotal();
            default:
                return "";
        }

    }

}
