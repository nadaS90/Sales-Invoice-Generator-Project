package com.sales.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class InvoiceDialogScreen extends JDialog{
    private JTextField customerNameField;
    private JTextField invoiceDateField;
    private JLabel customerNameLabel;
    private JLabel invoiceDateLabel;
    private JButton okBtn;
    private JButton cancelBtn;

    public InvoiceDialogScreen(FrameForTheInvoice frame) {
        
        customerNameLabel = new JLabel("Customer Name");
        customerNameField = new JTextField(20);
        invoiceDateLabel = new JLabel("Invoice Date");
        invoiceDateField = new JTextField(20);
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");
        
        okBtn.setActionCommand("Create New Invoice OK");
        cancelBtn.setActionCommand("Create New Invoice Cancel");
        
        okBtn.addActionListener(frame.getControllerClass());
        cancelBtn.addActionListener(frame.getControllerClass());
        setLayout(new GridLayout(3, 2));
        
        add(invoiceDateLabel);
        add(invoiceDateField);
        add(customerNameLabel);
        add(customerNameField);
        add(okBtn);
        add(cancelBtn);
        
        pack();
        
    }
    

    public JTextField getCustomerNameField() {
        return customerNameField;
    }

    public JTextField getInvoiceDateField() {
        return invoiceDateField;
    }

   

}
