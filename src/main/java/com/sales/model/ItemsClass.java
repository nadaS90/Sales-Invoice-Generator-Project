package com.sales.model;

public class ItemsClass {

    private String itemName;
    private double price;
    private int amount;
    private InvoiceClass invoice;

    public ItemsClass() {
    }

    public ItemsClass(String itemName, double price, int amount, InvoiceClass invoice) {
        this.itemName = itemName;
        this.price = price;
        this.amount = amount;
        this.invoice = invoice;
    }

    public double getItemsTotal() {
        return price * amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ItemsClass{" + "itemNumber=" + invoice.getNumber() + ", itemName=" + itemName + ", price=" + price + ", amount=" + amount + '}';
    }

    public InvoiceClass getInvoice() {
        return invoice;
    }

    public String getFileCSV() {
        return invoice.getNumber() + "," + itemName + "," + price + "," + amount;
    }
}
