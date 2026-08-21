package com.example.tea.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "item_mst")
public class ItemMST extends BaseModel {
    @Column(name = "item_name",nullable = false)
    private String itemName;
    @Column(name = "item_description")
    private String itemDescription;
    @Column(name = "price",nullable = false,columnDefinition = "DECIMAL(12,2) default 0")
    private Double price;
    @Column(name = "quantity",nullable = false,columnDefinition = "DECIMAL(12,3) default 0")
    private Double quantity;
    @Column(name = "GST",nullable = false,columnDefinition = "BIGINT default 0")
    private Long gst;
    @Column(name = "unit",nullable = false)
    private String unit;
    @Column(name= "sub_unit")
    private String subUnit;
    @Column(name = "per_unit_quantity", columnDefinition = "DECIMAL(12,3)")
    private Double perUnitQuantity;

    @Column(name = "sell_price",nullable = false)
    private Double sellPrice;

    // Product image is stored in the DB and served through the image endpoint.
    // Never serialized into JSON responses (kept out of list payloads) - it is fetched lazily.
    @JsonIgnore
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image_data", columnDefinition = "LONGBLOB")
    private byte[] imageData;
    @Column(name = "image_content_type")
    private String imageContentType;
    @Column(name = "image_name")
    private String imageName;

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
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

    public ItemMST(){}

}
