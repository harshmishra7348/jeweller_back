package com.example.tea.Model;

import jakarta.persistence.*;

/** A single line on a {@link PurchaseMST}: an item and the quantity/cost purchased. */
@Entity
@Table(name = "purchase_item_mapping")
public class PurchaseItemMapping extends BaseModel {

    @Column(name = "quantity", nullable = false)
    private Double quantity;
    @Column(name = "per_unit_quantity")
    private String perUnitQuantity;
    @Column(name = "cost_price", nullable = false)
    private Double costPrice;
    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "item_mst_id", insertable = false, updatable = false)
    private Long itemMSTId;
    @ManyToOne
    @JoinColumn(name = "item_mst_id")
    private ItemMST itemMST;

    @Column(name = "purchase_mst_id", insertable = false, updatable = false)
    private Long purchaseMSTId;
    @ManyToOne
    @JoinColumn(name = "purchase_mst_id")
    private PurchaseMST purchaseMST;

    public PurchaseItemMapping() {
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

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
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

    public Long getPurchaseMSTId() {
        return purchaseMSTId;
    }

    public void setPurchaseMSTId(Long purchaseMSTId) {
        this.purchaseMSTId = purchaseMSTId;
    }

    public PurchaseMST getPurchaseMST() {
        return purchaseMST;
    }

    public void setPurchaseMST(PurchaseMST purchaseMST) {
        this.purchaseMST = purchaseMST;
    }
}
