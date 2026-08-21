package com.example.tea.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

/**
 * One "About Us" block. The About page is an array of these, so an admin can show
 * multiple sections, each with its own heading, sub heading, description and image.
 */
@Entity
@Table(name = "about_section")
public class AboutSection extends BaseModel {

    @Column(name = "main_heading")
    private String mainHeading;
    @Column(name = "sub_heading")
    private String subHeading;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "display_order")
    private Integer displayOrder;

    @JsonIgnore
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image_data", columnDefinition = "LONGBLOB")
    private byte[] imageData;
    @Column(name = "image_content_type")
    private String imageContentType;
    @Column(name = "image_name")
    private String imageName;

    public AboutSection() {
    }

    public String getMainHeading() {
        return mainHeading;
    }

    public void setMainHeading(String mainHeading) {
        this.mainHeading = mainHeading;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

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
}
