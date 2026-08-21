package com.example.tea.DTO;

/**
 * Lightweight view of {@link com.example.tea.Model.ItemMST} used for list/detail
 * responses. It deliberately omits the raw image bytes so listing products never
 * loads BLOBs; the image is referenced through {@link #imageUrl} instead.
 * Populated directly via a JPQL constructor expression (see ItemMSTRepository).
 */
public class ItemResponse {

    private Long id;
    private String itemName;
    private String itemDescription;
    private Double price;
    private Double sellPrice;
    private Double quantity;
    private Long gst;
    private String unit;
    private String subUnit;
    private Double perUnitQuantity;
    private boolean active;
    private boolean hasImage;
    private String imageUrl;

    public ItemResponse() {
    }

    public ItemResponse(Long id, String itemName, String itemDescription, Double price, Double sellPrice,
                        Double quantity, Long gst, String unit, String subUnit, Double perUnitQuantity, Boolean active, String imageContentType) {
        this.id = id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.price = price;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
        this.gst = gst;
        this.unit = unit;
        this.subUnit = subUnit;
        this.perUnitQuantity = perUnitQuantity;
        this.active = active != null && active;
        this.hasImage = imageContentType != null;
        this.imageUrl = this.hasImage ? "/public/itemMST/image/" + id : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Long getGst() {
        return gst;
    }

    public void setGst(Long gst) {
        this.gst = gst;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSubUnit() {
        return subUnit;
    }

    public void setSubUnit(String subUnit) {
        this.subUnit = subUnit;
    }

    public Double getPerUnitQuantity() {
        return perUnitQuantity;
    }

    public void setPerUnitQuantity(Double perUnitQuantity) {
        this.perUnitQuantity = perUnitQuantity;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
