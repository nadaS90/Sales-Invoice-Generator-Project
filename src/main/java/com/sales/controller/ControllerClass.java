package com.sales.controller;

import com.sales.model.InvoiceClass;
import com.sales.model.InvoiceTable;
import com.sales.model.ItemsClass;
import com.sales.model.ItemsTable;
import com.sales.view.FrameForTheInvoice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ControllerClass implements ActionListener, ListSelectionListener{

    private FrameForTheInvoice invFrame;
    
    public ControllerClass(FrameForTheInvoice invFrame){
    this.invFrame = invFrame;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        System.out.println("Action: " + actionCommand);
        
        switch(actionCommand){
            case "Load File":
            {
                try {
                    loadFile();
                } catch (IOException ex) {
                    Logger.getLogger(ControllerClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                break;

            
            case "Save File":
                saveFile();
                break;
            
            case "Create New Invoice":
                createNewInvoice();
                break;
            
            case "Delete Invoice":
                deleteInvoice();
                break;
            
            case "Save":
                saveItem();
                break;
            
            case "Cancel":
                cancelItem();
                break;
            
        }
    }

    
      @Override
    public void valueChanged(ListSelectionEvent e) {
          int theSelectedIndex = invFrame.getInvoiceTable().getSelectedRow();
          if(theSelectedIndex != -1){
          System.out.println("You have selectrow No.:" + theSelectedIndex);
          InvoiceClass curntInvoice = invFrame.getInvoice().get(theSelectedIndex);
          invFrame.getLabelInvoiceNo().setText("" + curntInvoice.getInvoiceNumber());
          invFrame.getLabelInvoiceDate().setText(curntInvoice.getDate());
          invFrame.getLabelCustomerName().setText(curntInvoice.getCustomer());
          invFrame.getLabelInvoiceTotal().setText("" + curntInvoice.getInvoiceTotal());
          ItemsTable ItemsTable = new ItemsTable(curntInvoice.getItems());
          invFrame.getInvoiceItems().setModel(ItemsTable);
          ItemsTable.fireTableDataChanged();
          }
    }
    
    
    private void loadFile() throws IOException {
        JFileChooser fch = new JFileChooser();
        try {
            int theResult = fch.showOpenDialog(invFrame);
            
            if (theResult == JFileChooser.APPROVE_OPTION){
            File invoiceHeader = fch.getSelectedFile();
            Path invoiveHeaderPath = Paths.get(invoiceHeader.getAbsolutePath());
            List<String> headerData = Files.readAllLines(invoiveHeaderPath);
            System.out.println("The invoice was read successfully");
            
            ArrayList<InvoiceClass> invArray = new ArrayList<>();
            
            for(String headerDataa : headerData){
                String[] headerDetails = headerDataa.split(",");
                int invoiceNo = Integer.parseInt(headerDetails[0]);
                String invoiceDte = headerDetails[1];
                String invoiceCustomer = headerDetails[2];
                
                InvoiceClass invoice = new InvoiceClass(invoiceNo, invoiceDte, invoiceCustomer);
              //  1,10-04-2019,Mohammed
             //   2,13-10-2020,Aya
            //    3,20-12-2021,Samy
                invArray.add(invoice);
            }
                System.out.println("We are fine");
                theResult = fch.showOpenDialog(invFrame);
                
                if (theResult == JFileChooser.APPROVE_OPTION) {
                    File invoiceLine = fch.getSelectedFile();
                    Path invoiceLinePath = Paths.get(invoiceLine.getAbsolutePath());
                    List<String> lineData = Files.readAllLines(invoiceLinePath);
                    System.out.println("The Items read successfully");
                    
                    for(String lineDataa : lineData){
                        String[] lineDetails = lineDataa.split(",");
                        int invoiceNo = Integer.parseInt(lineDetails[0]);
                        String itmNAme = lineDetails[1];
                        double itmPrices = Double.parseDouble(lineDetails[2]);
                        int amount = Integer.parseInt(lineDetails[3]);
                        
                        InvoiceClass invv = null;
                        for(InvoiceClass invoice : invArray){
                            if (invoice.getNumber() == invoiceNo) {
                             invv = invoice;
                             break;
                                
                            }
                        }
                        
                        ItemsClass items = new ItemsClass(itmNAme, itmPrices, amount, invv);
                        invv.getItems().add(items);
                    }
                    System.out.println("We are fine till here");
                }     
                 
                
               invFrame.setInvoice(invArray);
               InvoiceTable InvoiceTable = new InvoiceTable(invArray);
               invFrame.setInvoiceTableModel(InvoiceTable);
               invFrame.getInvoiceTable().setModel(InvoiceTable);
               invFrame.getInvoicesTableModel().fireTableDataChanged();
               
            }
        }catch (IOException ex) {
                 ex.printStackTrace();        
                 
        }       
        }
    
    
    private void saveFile() {
    }

    private void createNewInvoice() {
    }

    private void deleteInvoice() {
        int selectedRow =invFrame.getInvoiceTable().getSelectedRow();
        if(selectedRow != -1){
            invFrame.getInvoice().remove(selectedRow);
            invFrame.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void saveItem() {
    }

    private void cancelItem() {
        int selectedInvoice = invFrame.getInvoiceTable().getSelectedRow();
        int selectedItem = invFrame.getInvoiceItems().getSelectedRow();
        
        
        if(selectedInvoice != -1 && selectedItem != -1){
            InvoiceClass invoice = invFrame.getInvoice().get(selectedInvoice);
              invoice.getitems
              invFrame.getInvoice().remove(selectedRow);
              invFrame.getInvoicesTableModel().fireTableDataChanged();
        }
        
    }

  
    
}
