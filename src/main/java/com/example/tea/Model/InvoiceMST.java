package com.example.tea.Model;

import com.example.tea.Utility.Constant.InvoiceEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoice_mst")
public class InvoiceMST extends BaseModel{

    @Column(name = "user_mst_id",updatable = false,insertable = false)
    private Long userMSTId;
    @ManyToOne
    @JoinColumn(name = "user_mst_id")
    private UserMST userMST;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "invoice_date",nullable = false)
    private LocalDateTime invoiceDate;
    @Column(name = "invoice_status")
    @Enumerated(EnumType.STRING)
    private InvoiceEnum invoiceStatus;
    @Column(name = "address")
    private String address;
    @Column(name = "gst_number")
    private String GSTNumber;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "tax",nullable = false)
    private Double tax;

    @Column(name = "invoice_number",nullable = false)
    private String invoiceNumber;

    @Column(name = "transport_mst_id", nullable = true)
    private Long transportMSTId;

    @ManyToOne
    @JoinColumn(name = "transport_mst_id", insertable = false, updatable = false)
    private TransportMST transportMST;

    @Column(name = "labour",nullable = false,columnDefinition = "DOUBLE DEFAULT 0")
    private Double labour;
    @Column(name = "discount",nullable = false,columnDefinition = "DOUBLE DEFAULT 0")
    private Double discount;

    public Long getTransportMSTId() {
        return transportMSTId;
    }

    public void setTransportMSTId(Long transportMSTId) {
        this.transportMSTId = transportMSTId;
    }

    public TransportMST getTransportMST() {
        return transportMST;
    }

    public void setTransportMST(TransportMST transportMST) {
        this.transportMST = transportMST;
    }

    public Long getUserMSTId() {
        return userMSTId;
    }

    public void setUserMSTId(Long userMSTId) {
        this.userMSTId = userMSTId;
    }

    public UserMST getUserMST() {
        return userMST;
    }

    public void setUserMST(UserMST userMST) {
        this.userMST = userMST;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public InvoiceEnum getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(InvoiceEnum invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGSTNumber() {
        return GSTNumber;
    }

    public void setGSTNumber(String GSTNumber) {
        this.GSTNumber = GSTNumber;
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

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public InvoiceMST(Long userMSTId, UserMST userMST, LocalDateTime invoiceDate, InvoiceEnum invoiceStatus, String address, String GSTNumber, Double amount, Double tax, Long transportMSTId,Double labour,Double discount) {
        this.userMSTId = userMSTId;
        this.userMST = userMST;
        this.invoiceDate = invoiceDate;
        this.invoiceStatus = invoiceStatus;
        this.address = address;
        this.GSTNumber = GSTNumber;
        this.amount = amount;
        this.tax = tax;
        this.transportMSTId = transportMSTId;
        this.labour = labour;
        this.discount = discount;
    }

    public Double getLabour() {
        return labour;
    }

    public void setLabour(Double labour) {
        this.labour = labour;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public InvoiceMST(){}
}
