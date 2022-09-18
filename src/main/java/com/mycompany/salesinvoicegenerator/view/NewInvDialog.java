/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.salesinvoicegenerator.view;

import static com.mycompany.salesinvoicegenerator.view.SalesInvoiceF.dateFor;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Sara
 */
public class NewInvDialog extends JDialog {
    private  JTextField nameField;
    private  JTextField dateField;
    private  JLabel nameLab1;
    private  JLabel invDateLab1;
    private  JButton okBut;
    private  JButton cancelBut;

    public NewInvDialog(SalesInvoiceF frame) {
        nameLab1 = new JLabel("Customer Name:");
        nameField = new JTextField(20);
        invDateLab1 = new JLabel("Invoice Date:");
        dateField = new JTextField(20);
        okBut = new JButton("OK");
        cancelBut = new JButton("Cancel");
        
        okBut.setActionCommand("newInvoiceOK");
        cancelBut.setActionCommand("newInvoiceCancel");
        
        okBut.addActionListener(frame.getListener());
        cancelBut.addActionListener(frame.getListener());
        setLayout(new GridLayout(3, 2));
        
        add(invDateLab1);
        add(dateField);
        add(nameLab1);
        add(nameField);
        add(okBut);
        add(cancelBut);
        setModal(true);
        pack();
        
    }

    public  JTextField getNameField() {
        return nameField;
    }

    public  JTextField getDateField() {
        return dateField;
    }
    
}
