package com.example.tea.Model;

import com.example.tea.Utility.Constant.InvoiceEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * A purchase (stock-in) record: goods bought from a supplier that increase inventory.
 * Mirrors {@link InvoiceMST} but is supplier-oriented rather than customer-oriented.
 */
@Entity
@Table(name = "purchase_mst")
public class PurchaseMST extends BaseModel {

    @Column(name = "supplier_name", nullable = false)
    private String supplierName;
    @Column(name = "supplier_gst")
    private String supplierGst;
    @Column(name = "address")
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "purchase_date", nullable = false)
    private LocalDateTime purchaseDate;
    @Column(name = "purchase_number", nullable = false)
    private String purchaseNumber;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "tax")
    private Double tax;
    @Column(name = "purchase_status")
    @Enumerated(EnumType.STRING)
    private InvoiceEnum purchaseStatus;

    public PurchaseMST() {
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierGst() {
        return supplierGst;
    }

    public void setSupplierGst(String supplierGst) {
        this.supplierGst = supplierGst;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(String purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public InvoiceEnum getPurchaseStatus() {
        return purchaseStatus;
    }

    public void setPurchaseStatus(InvoiceEnum purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }
}
