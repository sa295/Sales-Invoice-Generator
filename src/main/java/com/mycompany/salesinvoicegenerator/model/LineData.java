/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salesinvoicegenerator.model;

/**
 *
 * @author Sara
 */
public class LineData {
    private String item;
    private double price;
    private int count;
    private InvoiceData invoice;

    public LineData(String item, double price, int count, InvoiceData invoice) {
        this.item = item;
        this.price = price;
        this.count = count;
        this.invoice = invoice;
    }
   
    
    public String toCSV() {
        return invoice.getNumber() + "," + item + "," + price + "," + count;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public InvoiceData getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceData invoice) {
        this.invoice = invoice;
    }

    public double getTotal() {
        return getCount() * getPrice();
    } 
    @Override
    public String toString() {
        return "Line{" + "item=" + item + ", price=" + price + ", count=" + count + '}';
    }
    
}
