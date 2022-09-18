/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salesinvoicegenerator.model;

import com.mycompany.salesinvoicegenerator.view.SalesInvoiceF;
import java.util.Date;
import java.util.ArrayList;

/**
 *
 * @author Sara
 */
public class InvoiceData {
    
    private int number;
    private String cust;
    private Date date;
    private ArrayList <LineData> lines;

    public InvoiceData(int number, String cust, Date date) {
        this.number = number;
        this.cust = cust;
        this.date = date;
    }
 
    public String toCSV() {
        return number + "," + SalesInvoiceF.dateFor.format(date) + "," + cust;
    }
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCust() {
        return cust;
    }

    public void setCust(String cust) {
        this.cust = cust;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList <LineData> getLines() {
        if (lines == null) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    public void setLines(ArrayList <LineData> lines) {
        this.lines = lines;
    }
    
   public double getTot() {
        double total = 0.0;
        for (LineData line : getLines()) {
            total += line.getTotal();
        }
        return total;
    } 
    @Override
   public String toString() {
        return "Invoice{" + "num=" + number + ", customer=" + cust + ", date=" + date + '}';
    }
   
    
}
