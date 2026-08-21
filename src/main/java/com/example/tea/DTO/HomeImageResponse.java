package com.example.tea.DTO;

/** Home banner image without the raw bytes; the image is referenced via {@link #imageUrl}. */
public class HomeImageResponse {

    private Long id;
    private String title;
    private Integer displayOrder;
    private boolean active;
    private String imageUrl;

    public HomeImageResponse() {
    }

    public HomeImageResponse(Long id, String title, Integer displayOrder, boolean active, String imageContentType) {
        this.id = id;
        this.title = title;
        this.displayOrder = displayOrder;
        this.active = active;
        this.imageUrl = imageContentType != null ? "/public/home/image/" + id : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
