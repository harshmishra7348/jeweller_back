package com.example.tea.DTO;

/** One line of a purchase request: which item, how much, at what cost. */
public class PurchaseLineRequest {

    private Long itemMSTId;
    private Double quantity;
    private String perUnitQuantity;
    private Double costPrice;

    public Long getItemMSTId() {
        return itemMSTId;
    }

    public void setItemMSTId(Long itemMSTId) {
        this.itemMSTId = itemMSTId;
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
}
