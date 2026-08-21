package com.example.tea.DTO;

/** Add a product to the logged-in customer's inquiry cart. */
public class AddToCartRequest {

    private Long itemMSTId;
    private Double quantity; // optional; defaults to 1

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
}
