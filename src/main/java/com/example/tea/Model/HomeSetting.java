package com.example.tea.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Appearance settings for the storefront home page. Kept as a single record
 * (like {@link ContactDetail}). Currently holds the hero (first block)
 * background colour, which the admin can change from the admin panel.
 */
@Entity
@Table(name = "home_setting")
public class HomeSetting extends BaseModel {

    /** Any CSS colour for the home hero background, e.g. "#ffffff" or "#0b2b1e". */
    @Column(name = "hero_bg_color")
    private String heroBgColor;

    @Column(name = "header_bg_color")
    private String headerBgColor;

    @Column(name = "footer_bg_color")
    private String footerBgColor;

    @Column(name = "header_primary_font_color")
    private String headerPrimaryFontColor;
    @Column(name = "header_secondary_font_color")
    private String headerSecondaryFontColor;

    @Column(name = "footer_primary_font_color")
    private String footerPrimaryFontColor;
    @Column(name = "footer_secondary_font_color")
    private String footerSecondaryFontColor;

    @Column(name = "header_selected_item_color")
    private String headerSelectedItemColor;
    @Column(name = "header_hover_item_color")
    private String headerHoverItemColor;

    @Column(name = "footer_selected_item_color")
    private String footerSelectedItemColor;
    @Column(name = "footer_hover_item_color")
    private String footerHoverItemColor;

    public HomeSetting() {
    }

    public String getHeroBgColor() {
        return heroBgColor;
    }

    public void setHeroBgColor(String heroBgColor) {
        this.heroBgColor = heroBgColor;
    }

    public String getHeaderBgColor() {
        return headerBgColor;
    }

    public void setHeaderBgColor(String headerBgColor) {
        this.headerBgColor = headerBgColor;
    }

    public String getFooterBgColor() {
        return footerBgColor;
    }

    public void setFooterBgColor(String footerBgColor) {
        this.footerBgColor = footerBgColor;
    }

    public String getHeaderPrimaryFontColor() {
        return headerPrimaryFontColor;
    }

    public void setHeaderPrimaryFontColor(String headerPrimaryFontColor) {
        this.headerPrimaryFontColor = headerPrimaryFontColor;
    }

    public String getHeaderSecondaryFontColor() {
        return headerSecondaryFontColor;
    }

    public void setHeaderSecondaryFontColor(String headerSecondaryFontColor) {
        this.headerSecondaryFontColor = headerSecondaryFontColor;
    }

    public String getFooterPrimaryFontColor() {
        return footerPrimaryFontColor;
    }

    public void setFooterPrimaryFontColor(String footerPrimaryFontColor) {
        this.footerPrimaryFontColor = footerPrimaryFontColor;
    }

    public String getFooterSecondaryFontColor() {
        return footerSecondaryFontColor;
    }

    public void setFooterSecondaryFontColor(String footerSecondaryFontColor) {
        this.footerSecondaryFontColor = footerSecondaryFontColor;
    }

    public String getHeaderSelectedItemColor() {
        return headerSelectedItemColor;
    }

    public void setHeaderSelectedItemColor(String headerSelectedItemColor) {
        this.headerSelectedItemColor = headerSelectedItemColor;
    }

    public String getHeaderHoverItemColor() {
        return headerHoverItemColor;
    }

    public void setHeaderHoverItemColor(String headerHoverItemColor) {
        this.headerHoverItemColor = headerHoverItemColor;
    }

    public String getFooterSelectedItemColor() {
        return footerSelectedItemColor;
    }

    public void setFooterSelectedItemColor(String footerSelectedItemColor) {
        this.footerSelectedItemColor = footerSelectedItemColor;
    }

    public String getFooterHoverItemColor() {
        return footerHoverItemColor;
    }

    public void setFooterHoverItemColor(String footerHoverItemColor) {
        this.footerHoverItemColor = footerHoverItemColor;
    }
}
