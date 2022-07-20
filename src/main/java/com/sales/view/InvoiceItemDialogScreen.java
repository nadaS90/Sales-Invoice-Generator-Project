
package com.sales.view;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class InvoiceItemDialogScreen extends JDialog{
    private JTextField itemNameField;
    private JTextField itemAmountField;
    private JTextField itemPriceField;
    private JLabel itemNameLabel;
    private JLabel itemAmountLabel;
    private JLabel itemPriceLabel;
    private JButton okBtn;
    private JButton cancelBtn;

    public InvoiceItemDialogScreen(FrameForTheInvoice frme) {
        itemNameField = new JTextField(20);
        itemNameLabel = new JLabel("Item Name");
        
        itemAmountField = new JTextField(20);
        itemAmountLabel = new JLabel("Item Count");
        
        itemPriceField = new JTextField(20);
        itemPriceLabel = new JLabel("Item Price");
        
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");
        
        okBtn.setActionCommand("createItemOK");
        cancelBtn.setActionCommand("createItemCancel");
        
        okBtn.addActionListener(frme.getControllerClass());
        cancelBtn.addActionListener(frme.getControllerClass());
        setLayout(new GridLayout(4, 2));
        
        add(itemNameLabel);
        add(itemNameField);
        add(itemAmountLabel);
        add(itemAmountField);
        add(itemPriceLabel);
        add(itemPriceField);
        add(okBtn);
        add(cancelBtn);
        
        pack();
    }

    public JTextField getItemNameField() {
        return itemNameField;
    }

    public JTextField getItemAmountField() {
        return itemAmountField;
    }

    public JTextField getItemPriceField() {
        return itemPriceField;
    }
    
    
    










}
