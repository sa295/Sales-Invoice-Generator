/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salesinvoicegenerator.model;

import com.mycompany.salesinvoicegenerator.view.SalesInvoiceF;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Sara
 */
public class InvoiceTable extends AbstractTableModel {
    
    private String[] cols = {"Num", "Customer", 
        "Date", "Total"};
    private List<InvoiceData> inv;
    
    public InvoiceTable(List<InvoiceData> inv) {
        this.inv = inv;
    }

    @Override
    public int getRowCount() {
         return inv.size();
    }

    @Override
    public int getColumnCount() {
       return cols.length;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        return cols[columnIndex];
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       InvoiceData invoice = inv.get(rowIndex);
        switch (columnIndex) {
            case 0: return invoice.getNumber();
            case 1: return invoice.getCust();
            case 2: return SalesInvoiceF.dateFor.format(invoice.getDate());
            case 3: return invoice.getTot();
        }
        return "";
    }
    
    
}
