package com.example.tea.DTO;

/** An About Us block without the raw image bytes; the image is referenced via {@link #imageUrl}. */
public class AboutResponse {

    private Long id;
    private String mainHeading;
    private String subHeading;
    private String description;
    private Integer displayOrder;
    private boolean active;
    private String imageUrl;

    public AboutResponse() {
    }

    public AboutResponse(Long id, String mainHeading, String subHeading, String description,
                         Integer displayOrder, boolean active, String imageContentType) {
        this.id = id;
        this.mainHeading = mainHeading;
        this.subHeading = subHeading;
        this.description = description;
        this.displayOrder = displayOrder;
        this.active = active;
        this.imageUrl = imageContentType != null ? "/public/about/image/" + id : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
