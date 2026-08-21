package com.example.tea.DTO;

/**
 * A single row on the rendered PDF invoice. Field names match the placeholders
 * used in templates/invoice.html (description, quantity, unitPrice, total).
 */
public class InvoiceLineItem {

    private String description;
    private Double quantity;
    private String perUnitQuantity;
    private Double unitPrice;
    private Double total;

    public InvoiceLineItem() {
    }

    public InvoiceLineItem(String description, Double quantity, Double unitPrice, Double total) {
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = total;
    }

    public InvoiceLineItem(String description, Double quantity, String perUnitQuantity, Double unitPrice, Double total) {
        this.description = description;
        this.quantity = quantity;
        this.perUnitQuantity = perUnitQuantity;
        this.unitPrice = unitPrice;
        this.total = total;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getPerUnitQuantity() {
        return perUnitQuantity;
    }

    public void setPerUnitQuantity(String perUnitQuantity) {
        this.perUnitQuantity = perUnitQuantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
