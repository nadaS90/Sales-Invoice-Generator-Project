package com.sales.model;

import java.util.ArrayList;


public class InvoiceClass {
    
    private int number;
    private String date;
    private String customer;
    private ArrayList<ItemsClass> items;

    public InvoiceClass() {
    }

    public InvoiceClass(int number, String date, String customer) {
        this.number = number;
        this.date = date;
        this.customer = customer;
    }

    public int getInvoiceNumber() {
        return number;
    }
    
    public double getInvoiceTotal(){
        double total = 0.0;
        for(ItemsClass item : getItems()){
            total += item.getItemsTotal();
        }
        return total; 
    }

    public ArrayList<ItemsClass> getItems() {
        if (items == null) {
            items = new ArrayList<>();  
        }
        return items;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "InvoiceClass{" + "number=" + number + ", date=" + date + ", customer=" + customer + ", items=" + items + '}';
    }
    
}
