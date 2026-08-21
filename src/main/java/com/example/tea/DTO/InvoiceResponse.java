package com.example.tea.DTO;

import com.example.tea.Utility.Constant.InvoiceEnum;
import java.time.LocalDateTime;

public class InvoiceResponse {
    private Long id;
    private String invoiceNumber;
    private LocalDateTime invoiceDate;
    private InvoiceEnum invoiceStatus;
    private String address;
    private String GSTNumber;
    private Double amount;
    private Double tax;
    private Long userMSTId;
    private String customerName;
    private Long transportMSTId;
    private String transportName;
    private LocalDateTime createAt;
    private LocalDateTime modifyAt;
    private Double labour;
    private Double discount;

    public InvoiceResponse() {}

    public InvoiceResponse(Long id, String invoiceNumber, LocalDateTime invoiceDate, 
                          InvoiceEnum invoiceStatus, String address, String GSTNumber, 
                          Double amount, Double tax, Long userMSTId, String customerName, 
                          Long transportMSTId, String transportName, LocalDateTime createAt, LocalDateTime modifyAt) {
        this.id = id;
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.invoiceStatus = invoiceStatus;
        this.address = address;
        this.GSTNumber = GSTNumber;
        this.amount = amount;
        this.tax = tax;
        this.userMSTId = userMSTId;
        this.customerName = customerName;
        this.transportMSTId = transportMSTId;
        this.transportName = transportName;
        this.createAt = createAt;
        this.modifyAt = modifyAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
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

    public Long getUserMSTId() {
        return userMSTId;
    }

    public void setUserMSTId(Long userMSTId) {
        this.userMSTId = userMSTId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getTransportMSTId() {
        return transportMSTId;
    }

    public void setTransportMSTId(Long transportMSTId) {
        this.transportMSTId = transportMSTId;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(LocalDateTime modifyAt) {
        this.modifyAt = modifyAt;
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
}
