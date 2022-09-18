/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salesinvoicegenerator.controller;

import com.mycompany.salesinvoicegenerator.model.InvoiceData;
import com.mycompany.salesinvoicegenerator.model.InvoiceTable;
import com.mycompany.salesinvoicegenerator.model.LineData;
import com.mycompany.salesinvoicegenerator.model.LineTable;
import com.mycompany.salesinvoicegenerator.view.NewInvDialog;
import com.mycompany.salesinvoicegenerator.view.NewLiDialog;
import com.mycompany.salesinvoicegenerator.view.SalesInvoiceF;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Sara
 */
// class to listen to any action taken in invoice frame (load, save menus and buttons)
public class InvListener implements ActionListener, ListSelectionListener{
    
    //create global var from sales invoice frame
    private SalesInvoiceF invFrame;
    private NewInvDialog invDialog;
    private NewLiDialog lineDialog;
    public InvListener (SalesInvoiceF invFrame)
    {
        this.invFrame = invFrame;
    }

    //check action taken from action command section in propereties
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println ("ActionListener");
        String actionCommand = e.getActionCommand();
        switch(actionCommand){
            case "Create New Invoice":
                CreateInv ();
                break;
                
            case "Delete Invoice" :
                DeleteInv ();
                break;
            
            case "Create New Line" :
                CreateLine ();
                break;
                
            case "Delete Line" :
                DeleteLine ();
                break;
                
            case "Load File" :
                 LoadFile (null, null);
                 break;
                 
            case "Save File" :
                 SaveFile ();
                 break;
                 
            case "newInvoiceOK":
                createInvoice();
                break;
                
            case "newInvoiceCancel":
                CancelNewInvoice();
                break;
                
            case "newLineOK":
                createNewLine();
                break;
                
            case "newLineCancel":
                CancelNewLine();
                break;
                
        }
        
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
       int row = invFrame.getInvoicesTable().getSelectedRow();
        System.out.println("Selected Row: " + row);
        if (row > -1 && row < invFrame.getInvoices().size()) {
            InvoiceData inv = invFrame.getInvoices().get(row);
            invFrame.getCustNameLabel().setText(inv.getCust());
            invFrame.getInvDateLabel().setText(SalesInvoiceF.dateFor.format(inv.getDate()));
            invFrame.getInvNumLabel().setText("" + inv.getNumber());
            invFrame.getInvTotalLabel().setText("" + inv.getTot());

            List<LineData> lines = inv.getLines();
            invFrame.getLinesTable().setModel(new LineTable(lines));
        } else {
            invFrame.getCustNameLabel().setText("");
            invFrame.getInvDateLabel().setText("");
            invFrame.getInvNumLabel().setText("");
            invFrame.getInvTotalLabel().setText("");

            invFrame.getLinesTable().setModel(new LineTable(new ArrayList<LineData>()));
        }

    }

    private void CreateInv() {
        invDialog = new NewInvDialog(invFrame);
        invDialog.setVisible(true);
    }

    private void DeleteInv() {
        int row = invFrame.getInvoicesTable().getSelectedRow();
        if (row != -1) {
            invFrame.getInvoices().remove(row);
            ((InvoiceTable) invFrame.getInvoicesTable().getModel()).fireTableDataChanged();
    }
    }

    private void CreateLine() {
        int selectedInv = invFrame.getInvoicesTable().getSelectedRow();
        if (selectedInv != -1) {
            lineDialog = new NewLiDialog(invFrame);
            lineDialog.setVisible(true);
        }
    }

    private void DeleteLine() {
         int row = invFrame.getLinesTable().getSelectedRow();
        if (row != -1) {
            int headerRow = invFrame.getInvoicesTable().getSelectedRow();
            LineTable lineTable = (LineTable) invFrame.getLinesTable().getModel();
            lineTable.getLines().remove(row);
            lineTable.fireTableDataChanged();
            ((InvoiceTable) invFrame.getInvoicesTable().getModel()).fireTableDataChanged();
            invFrame.getInvoicesTable().setRowSelectionInterval(headerRow, headerRow);
        }
    }

    //show data of the invoice without clicking any button
    public void LoadFile(String invPath, String linePath) {
        
        File invoiceFile = null;
        File lineFile = null;
        
        if(invPath == null && linePath == null)
        {
            JFileChooser jfile = new JFileChooser();
           int result = jfile.showOpenDialog(invFrame);
           if (result == JFileChooser.APPROVE_OPTION)
           {
               invoiceFile = jfile.getSelectedFile();
               result = jfile.showOpenDialog(invFrame);
               if (result == JFileChooser.APPROVE_OPTION)
               {
                   lineFile = jfile.getSelectedFile();
               }
           }
        } else
           {
               invoiceFile = new File (invPath); //the file which contains data of invoice 
               lineFile = new File (linePath);   //the file which contains data of line
           }
           
           //read the data from files
           if (invoiceFile != null && lineFile != null )
           {
               try {
                   List<String> invoiceLines = Files.lines(Paths.get(invoiceFile.getAbsolutePath())).collect(Collectors.toList());
                   List<String> lineLines = Files.lines(Paths.get(lineFile.getAbsolutePath())).collect(Collectors.toList());
                   
                   ArrayList<InvoiceData> invoices = new ArrayList<> ();
                   invFrame.getInvoices().clear();
                  
                   //loop for data in invoice file
                   for (String invLine : invoiceLines)
                   {
                       String [] splitinv = invLine.split(",");//read from file and split data by comma
                       String numStr = splitinv [0];
                       String dateStr = splitinv [1];
                       String name = splitinv [2];
                       
                       int numb = Integer.parseInt(numStr);
                       Date date = SalesInvoiceF.dateFor.parse(dateStr);
                       InvoiceData invData = new InvoiceData (numb, name, date);
                       invFrame.getInvoices().add(invData);
                   }
                   
                    for (String lines : lineLines)
                   {
                       String [] splitLine = lines.split(",");
                       String invNumStr = splitLine [0];
                       String itemLine = splitLine [1];
                       String itPriceStr = splitLine [2];
                       String countStr = splitLine [3];
                       
                       int invNum = Integer.parseInt(invNumStr);
                       double itPrice = Double.parseDouble(itPriceStr);
                       int itCount = Integer.parseInt(countStr);
                       InvoiceData invoiceNum = getInvNum (invNum);
                       LineData lineData = new LineData (itemLine, itPrice, itCount, invoiceNum);
                       invoiceNum.getLines().add(lineData);
                       
                   }
                    invFrame.getInvoicesTable().setModel(new InvoiceTable(invFrame.getInvoices()));
                   
               } catch (Exception ex)
               {
                   
                   ex.printStackTrace();
               }
           }
        }
    
       private InvoiceData getInvNum(int num) {
        for (InvoiceData invoice : invFrame.getInvoices() ) {
            if (num == invoice.getNumber()) {
                return invoice;
            }
        }
        return null;
       }

    private void SaveFile() {
        String invoicesData = "";
        String linesData = "";
        for (InvoiceData invoice : invFrame.getInvoices()) {
            invoicesData += invoice.toCSV();
            invoicesData += "\n";
            for (LineData line : invoice.getLines()) {
                linesData += line.toCSV();
                linesData += "\n";
            }
    }
        JFileChooser jf = new JFileChooser();
        int result = jf.showSaveDialog(invFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File invFile = jf.getSelectedFile();
            result = jf.showSaveDialog(invFrame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File lineFile = jf.getSelectedFile();
                try {
                    FileWriter invFW = new FileWriter(invFile);
                    invFW.write(invoicesData);
                    invFW.flush();
                    invFW.close();

                    FileWriter lineFW = new FileWriter(lineFile);
                    lineFW.write(linesData);
                    lineFW.flush();
                    lineFW.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(invFrame, "Error while saving data", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    
}
   private void createInvoice() {
        String customer = invDialog.getNameField().getText();
        String date =  invDialog.getDateField().getText();
        invDialog.setVisible(false);
        invDialog.dispose();
        int num = getNextInvoiceNum();
        try {
            Date invDate = invFrame.dateFor.parse(date);
            InvoiceData inv = new InvoiceData(num, customer, invDate);
            invFrame.getInvoices().add(inv);
            ((InvoiceTable) invFrame.getInvoicesTable().getModel()).fireTableDataChanged();
        } catch (ParseException pex) {
            JOptionPane.showMessageDialog(invFrame, "Error in date format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private int getNextInvoiceNum() {
        int num = 1;
        for (InvoiceData inv : invFrame.getInvoices()) {
            if (inv.getNumber()> num) {
                num = inv.getNumber();
            }
        }
        return num + 1;
    }
    private void CancelNewInvoice() {
        invDialog.setVisible(false);
        invDialog.dispose();
    }
    
    private void createNewLine() {
        int selectedInv = invFrame.getInvoicesTable().getSelectedRow();
        if (selectedInv != -1) {
            InvoiceData inv = invFrame.getInvoices().get(selectedInv);
            String name = lineDialog.getItemNameField().getText();
            String priceStr = lineDialog.getItemPriceField().getText();
            String countStr = lineDialog.getItemCountField().getText();
            lineDialog.setVisible(false);
            lineDialog.dispose();
            double price = Double.parseDouble(priceStr);
            int count = Integer.parseInt(countStr);
            LineData line = new LineData (name, price, count, inv);
            inv.getLines().add(line);
            ((LineTable) invFrame.getLinesTable().getModel()).fireTableDataChanged();
            ((InvoiceTable) invFrame.getInvoicesTable().getModel()).fireTableDataChanged();
            invFrame.getInvoicesTable().setRowSelectionInterval(selectedInv, selectedInv);
        }
    }
    
     private void CancelNewLine() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
    }
}
