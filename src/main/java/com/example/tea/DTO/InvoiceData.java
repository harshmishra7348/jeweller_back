package com.example.tea.DTO;

import java.util.List;
public class InvoiceData {

    private String invoiceNumber;
    private String invoiceDate;
    private String dueDate;

    private String yourCompanyName;
    private String yourCompanyAddress;
    private String yourCompanyContact;
    private String yourCompanyEmail;

    private String customerName;
    private String customerAddress;
    private String customerEmail;

    private List<InvoiceLineItem> items;

    private Double subtotal;
    private Double taxRate;
    private Double taxAmount;
    private Double grandTotal;

    private Integer paymentTerms;

    private String gst;
    private String hsn;
    private String billFromCompany;
    private String customerGSTNumber;
    
    private String transport;
    private String lrNumber;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String amountInWords;
    private Double discount;
    private Double labour;
    private Double sgstAmount;
    private Double cgstAmount;
    private String sgstRate;
    private String cgstRate;

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getYourCompanyName() {
        return yourCompanyName;
    }

    public void setYourCompanyName(String yourCompanyName) {
        this.yourCompanyName = yourCompanyName;
    }

    public String getYourCompanyAddress() {
        return yourCompanyAddress;
    }

    public void setYourCompanyAddress(String yourCompanyAddress) {
        this.yourCompanyAddress = yourCompanyAddress;
    }

    public String getYourCompanyContact() {
        return yourCompanyContact;
    }

    public void setYourCompanyContact(String yourCompanyContact) {
        this.yourCompanyContact = yourCompanyContact;
    }

    public String getYourCompanyEmail() {
        return yourCompanyEmail;
    }

    public void setYourCompanyEmail(String yourCompanyEmail) {
        this.yourCompanyEmail = yourCompanyEmail;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public List<InvoiceLineItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceLineItem> items) {
        this.items = items;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Integer getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(Integer paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getHsn() {
        return hsn;
    }

    public void setHsn(String hsn) {
        this.hsn = hsn;
    }

    public String getBillFromCompany() {
        return billFromCompany;
    }

    public void setBillFromCompany(String billFromCompany) {
        this.billFromCompany = billFromCompany;
    }

    public String getCustomerGSTNumber() {
        return customerGSTNumber;
    }

    public void setCustomerGSTNumber(String customerGSTNumber) {
        this.customerGSTNumber = customerGSTNumber;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getLrNumber() {
        return lrNumber;
    }

    public void setLrNumber(String lrNumber) {
        this.lrNumber = lrNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getAmountInWords() {
        return amountInWords;
    }

    public void setAmountInWords(String amountInWords) {
        this.amountInWords = amountInWords;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getLabour() {
        return labour;
    }

    public void setLabour(Double labour) {
        this.labour = labour;
    }

    public Double getSgstAmount() {
        return sgstAmount;
    }

    public void setSgstAmount(Double sgstAmount) {
        this.sgstAmount = sgstAmount;
    }

    public Double getCgstAmount() {
        return cgstAmount;
    }

    public void setCgstAmount(Double cgstAmount) {
        this.cgstAmount = cgstAmount;
    }

    public String getSgstRate() {
        return sgstRate;
    }

    public void setSgstRate(String sgstRate) {
        this.sgstRate = sgstRate;
    }

    public String getCgstRate() {
        return cgstRate;
    }

    public void setCgstRate(String cgstRate) {
        this.cgstRate = cgstRate;
    }
}
