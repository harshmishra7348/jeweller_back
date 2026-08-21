package com.example.tea.DTO;

import com.example.tea.Model.ItemMST;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class InvoiceMSTRequest {
    List<ItemMST> itemMSTS;
    Long userMSTId;
    Long transportMSTId;

    String address;
    @JsonProperty("gstNumber")
    String GSTNumber;
    Double tax;
    Double labour;
    Double discount;

    public Long getTransportMSTId() {
        return transportMSTId;
    }

    public void setTransportMSTId(Long transportMSTId) {
        this.transportMSTId = transportMSTId;
    }

    public List<ItemMST> getItemMSTS() {
        return itemMSTS;
    }

    public void setItemMSTS(List<ItemMST> itemMSTS) {
        this.itemMSTS = itemMSTS;
    }

    public Long getUserMSTId() {
        return userMSTId;
    }

    public void setUserMSTId(Long userMSTId) {
        this.userMSTId = userMSTId;
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

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
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
