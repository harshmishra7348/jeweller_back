package com.example.tea.DTO;

import java.util.List;

/** Payload to record a stock purchase from a supplier. */
public class PurchaseRequest {

    private String supplierName;
    private String supplierGst;
    private String address;
    private Double tax; // tax rate in percent
    private List<PurchaseLineRequest> items;

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

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public List<PurchaseLineRequest> getItems() {
        return items;
    }

    public void setItems(List<PurchaseLineRequest> items) {
        this.items = items;
    }
}
