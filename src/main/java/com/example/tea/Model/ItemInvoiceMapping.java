package com.example.tea.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "item_invoice_mapping")
public class ItemInvoiceMapping extends BaseModel{
    @Column(name = "quantity",nullable = false)
    private Double quantity;
    @Column(name = "amount",nullable = false)
    private Double amount;
    @Column(name = "total_amount",nullable = false)
    private Double totalAmount;
    @Column(name = "item_mst_id",insertable = false,updatable = false)
    private Long itemMSTId;
    @ManyToOne
    @JoinColumn(name = "item_mst_id")
    private ItemMST itemMST;
    @Column(name = "invoice_mst_id",insertable = false,updatable = false)
    private Long invoiceMSTId;
    @ManyToOne
    @JoinColumn(name = "invoice_mst_id")
    private InvoiceMST invoiceMST;

    public ItemInvoiceMapping(){}

    public ItemInvoiceMapping(Double quantity, Double amount, Double totalAmount, Long itemMSTId, ItemMST itemMST, Long invoiceMSTId, InvoiceMST invoiceMST) {
        this.quantity = quantity;
        this.amount = amount;
        this.totalAmount = totalAmount;
        this.itemMSTId = itemMSTId;
        this.itemMST = itemMST;
        this.invoiceMSTId = invoiceMSTId;
        this.invoiceMST = invoiceMST;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getItemMSTId() {
        return itemMSTId;
    }

    public void setItemMSTId(Long itemMSTId) {
        this.itemMSTId = itemMSTId;
    }

    public ItemMST getItemMST() {
        return itemMST;
    }

    public void setItemMST(ItemMST itemMST) {
        this.itemMST = itemMST;
    }

    public Long getInvoiceMSTId() {
        return invoiceMSTId;
    }

    public void setInvoiceMSTId(Long invoiceMSTId) {
        this.invoiceMSTId = invoiceMSTId;
    }

    public InvoiceMST getInvoiceMST() {
        return invoiceMST;
    }

    public void setInvoiceMST(InvoiceMST invoiceMST) {
        this.invoiceMST = invoiceMST;
    }
}
