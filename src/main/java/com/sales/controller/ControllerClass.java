package com.sales.controller;

import com.sales.model.InvoiceClass;
import com.sales.model.InvoiceTable;
import com.sales.model.ItemsClass;
import com.sales.model.ItemsTable;
import com.sales.view.FrameForTheInvoice;
import com.sales.view.InvoiceDialogScreen;
import com.sales.view.InvoiceItemDialogScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ControllerClass implements ActionListener, ListSelectionListener {

    private FrameForTheInvoice invFrame;
    private InvoiceDialogScreen invScreen;
    private InvoiceItemDialogScreen itmScreen;

    public ControllerClass(FrameForTheInvoice invFrame) {
        this.invFrame = invFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        System.out.println("Action: " + actionCommand);

        switch (actionCommand) {
            case "Load File": {
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

            case "Create New Item":
                createNewItem();

                break;

            case "Delete Item":
                deleteItem();
                break;

            case "Create New Invoice OK":
                createInvoiceOK();
                break;

            case "Create New Invoice Cancel":
                createInvoiceCancel();
                break;

            case "Create Item Ok":
                createItemOK();
                break;

            case "Create Item Cancel":
                createItemCancel();
                break;

        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int theSelectedIndex = invFrame.getInvoiceTable().getSelectedRow();
        if (theSelectedIndex != -1) {
            System.out.println("You have selectrow No.:" + theSelectedIndex);
            InvoiceClass curntInvoice = invFrame.getInvoice().get(theSelectedIndex);
            invFrame.getLabelInvoiceNo().setText("" + curntInvoice.getNumber());
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

            if (theResult == JFileChooser.APPROVE_OPTION) {
                File invoiceHeader = fch.getSelectedFile();
                Path invoiveHeaderPath = Paths.get(invoiceHeader.getAbsolutePath());
                List<String> headerData = Files.readAllLines(invoiveHeaderPath);
                System.out.println("The invoice was read successfully");

                ArrayList<InvoiceClass> invArray = new ArrayList<>();

                for (String headerDataa : headerData) {
                    try {
                        String[] headerDetails = headerDataa.split(",");
                        int invoiceNo = Integer.parseInt(headerDetails[0]);
                        String invoiceDte = headerDetails[1];
                        String invoiceCustomer = headerDetails[2];

                        InvoiceClass invoice = new InvoiceClass(invoiceNo, invoiceDte, invoiceCustomer);
                        //  1,10-04-2019,Mohammed
                        //   2,13-10-2020,Aya
                        //    3,20-12-2021,Samy
                        invArray.add(invoice);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(invFrame, "Error in line format file", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                System.out.println("We are fine");
                theResult = fch.showOpenDialog(invFrame);

                if (theResult == JFileChooser.APPROVE_OPTION) {
                    File invoiceLine = fch.getSelectedFile();
                    Path invoiceLinePath = Paths.get(invoiceLine.getAbsolutePath());
                    List<String> lineData = Files.readAllLines(invoiceLinePath);
                    System.out.println("The Items read successfully");

                    for (String lineDataa : lineData) {
                        try {
                            String[] lineDetails = lineDataa.split(",");
                            int invoiceNo = Integer.parseInt(lineDetails[0]);
                            String itmNAme = lineDetails[1];
                            double itmPrices = Double.parseDouble(lineDetails[2]);
                            int amount = Integer.parseInt(lineDetails[3]);

                            InvoiceClass invv = null;
                            for (InvoiceClass invoice : invArray) {
                                if (invoice.getNumber() == invoiceNo) {
                                    invv = invoice;
                                    break;

                                }
                            }

                            ItemsClass items = new ItemsClass(itmNAme, itmPrices, amount, invv);
                            invv.getItems().add(items);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(invFrame, "Error in line format file", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    System.out.println("We are fine till here");
                }

                invFrame.setInvoice(invArray);
                InvoiceTable InvoiceTable = new InvoiceTable(invArray);
                invFrame.setInvoiceTableModel(InvoiceTable);
                invFrame.getInvoiceTable().setModel(InvoiceTable);
                invFrame.getInvoicesTableModel().fireTableDataChanged();

            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(invFrame, "file format is not recognized", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void saveFile() {
        ArrayList<InvoiceClass> invoices = invFrame.getInvoice();
        String headerFile = "";
        String lineFile = "";
        for (InvoiceClass invoice : invoices) {
            String invoiceCSV = invoice.getFileCSV();
            headerFile += invoiceCSV;
            headerFile += "\n";

            for (ItemsClass items : invoice.getItems()) {
                String lineCSV = items.getFileCSV();
                lineFile += lineCSV;
                lineFile += "\n";
            }
        }
        System.out.println("we are safe here");
        try {
            JFileChooser fch = new JFileChooser();
            int result = fch.showSaveDialog(invFrame);
            if(result == JFileChooser.APPROVE_OPTION){
                File invoiceHeaderFile = fch.getSelectedFile();
                FileWriter hw = new FileWriter(invoiceHeaderFile);
                hw.write(headerFile);
                hw.flush();
                hw.close();
                
                result = fch.showSaveDialog(invFrame);
                if(result == JFileChooser.APPROVE_OPTION){
                    File invoiceLineFile = fch.getSelectedFile();
                    FileWriter lw = new FileWriter(invoiceLineFile);
                    lw.write(headerFile);
                    lw.flush();
                    lw.close();   
                }
            }
            
        } catch (Exception e) {
        }
        
        

    }

    private void createNewInvoice() {
        invScreen = new InvoiceDialogScreen(invFrame);
        invScreen.setVisible(true);

    }

    private void deleteInvoice() {
        int selectedRow = invFrame.getInvoiceTable().getSelectedRow();
        if (selectedRow != -1) {
            invFrame.getInvoice().remove(selectedRow);
            invFrame.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createNewItem() {
        itmScreen = new InvoiceItemDialogScreen(invFrame);
        itmScreen.setVisible(true);

    }

    private void deleteItem() {
        int selectedInvoice = invFrame.getInvoiceTable().getSelectedRow();
        int selectedItem = invFrame.getInvoiceItems().getSelectedRow();

        if (selectedInvoice != -1 && selectedItem != -1) {
            InvoiceClass invoice = invFrame.getInvoice().get(selectedInvoice);
            invoice.getItems().remove(selectedItem);
            ItemsTable itemsTable = new ItemsTable(invoice.getItems());
            invFrame.getInvoiceItems().setModel(itemsTable);
            itemsTable.fireTableDataChanged();
        }

    }

    private void createInvoiceOK() {
        String date = invScreen.getInvoiceDateField().getText();
        String customer = invScreen.getCustomerNameField().getText();
        int number = invFrame.getNextInvoiceNumber();
        try {
            String[] dateParts = date.split("-");
            if (dateParts.length < 3) {
                JOptionPane.showMessageDialog(invFrame, "please enter correct date format", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                if (day > 31 || month > 12) {
                    JOptionPane.showMessageDialog(invFrame, "please enter correct date format", "Error", JOptionPane.ERROR_MESSAGE);
                } else {

                    InvoiceClass invoice = new InvoiceClass(number, date, customer);
                    invFrame.getInvoice().add(invoice);
                    invFrame.getInvoicesTableModel().fireTableDataChanged();

                    invScreen.setVisible(false);
                    invScreen.dispose();
                    invScreen = null;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(invFrame, "please enter correct date format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createInvoiceCancel() {
        invScreen.setVisible(false);
        invScreen.dispose();
        invScreen = null;
    }

    private void createItemOK() {
        String itemName = itmScreen.getItemNameField().getText();
        String amount = itmScreen.getItemAmountField().getText();
        String price = itmScreen.getItemPriceField().getText();
        int count = Integer.parseInt(amount);
        double prices = Double.parseDouble(price);
        int theSelectedInvoice = invFrame.getInvoiceTable().getSelectedRow();

        if (theSelectedInvoice != -1) {
            InvoiceClass invoice = invFrame.getInvoice().get(theSelectedInvoice);
            ItemsClass items = new ItemsClass(itemName, prices, count, invoice);
            invoice.getItems().add(items);
            ItemsTable itemsTable = (ItemsTable) invFrame.getInvoiceItems().getModel();
            itemsTable.fireTableDataChanged();
            invFrame.getInvoicesTableModel().fireTableDataChanged();
        }
        itmScreen.setVisible(false);
        itmScreen.dispose();
        itmScreen = null;
    }

    private void createItemCancel() {
        itmScreen.setVisible(false);
        itmScreen.dispose();
        itmScreen = null;

    }

}
