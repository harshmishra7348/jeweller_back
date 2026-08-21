package com.example.tea.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/** One product line inside an {@link Enquiry} cart. */
@Entity
@Table(name = "enquiry_item")
public class EnquiryItem extends BaseModel {

    @Column(name = "enquiry_id", nullable = false)
    private Long enquiryId;

    @Column(name = "item_mst_id", insertable = false, updatable = false)
    private Long itemMSTId;
    @ManyToOne
    @JoinColumn(name = "item_mst_id")
    private ItemMST itemMST;

    // Snapshot so the line stays readable even if the product is later renamed/deleted.
    @Column(name = "item_name")
    private String itemName;
    @Column(name = "quantity", nullable = false)
    private Double quantity;

    public EnquiryItem() {
    }

    @JsonIgnore
    public Long getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(Long enquiryId) {
        this.enquiryId = enquiryId;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
