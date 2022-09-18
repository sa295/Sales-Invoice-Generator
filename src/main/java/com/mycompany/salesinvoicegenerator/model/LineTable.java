/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salesinvoicegenerator.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Sara
 */
public class LineTable extends AbstractTableModel {
    
    private String[] cols = {"Item", "Unit Price", "Count", "Total"};
    private List<LineData> lines;

    public LineTable(List<LineData> lines) {
        this.lines = lines;
    }

    public List<LineData> getLines() {
        return lines;
    }
    
   

    @Override
    public int getRowCount() {
     
        return lines.size();
        
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return cols[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
     
        LineData line = lines.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return line.getItem();
            case 1: return line.getPrice();
            case 2: return line.getCount();
            case 3: return line.getTotal();
        }
        return "";
    }
    
    
    
}
