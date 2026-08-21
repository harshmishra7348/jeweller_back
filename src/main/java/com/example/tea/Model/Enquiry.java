package com.example.tea.Model;

import com.example.tea.Utility.Constant.EnquiryStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * An inquiry created by a logged-in end customer. While {@code status == CART} it is the
 * customer's open inquiry cart; once submitted it becomes a lead the shop owner reviews.
 * Customer contact fields are snapshotted from the logged-in user so a lead stays readable
 * (and we never serialize the user's password).
 */
@Entity
@Table(name = "enquiry")
public class Enquiry extends BaseModel {

    @Column(name = "user_mst_id", nullable = false)
    private Long userMSTId;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "customer_email")
    private String customerEmail;
    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "message", length = 2000)
    private String message;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnquiryStatus status = EnquiryStatus.CART;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    /** Populated by the service when returning a cart/inquiry; not persisted on this row. */
    @Transient
    private List<EnquiryItem> items;

    public Enquiry() {
    }

    public Long getUserMSTId() {
        return userMSTId;
    }

    public void setUserMSTId(Long userMSTId) {
        this.userMSTId = userMSTId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EnquiryStatus getStatus() {
        return status;
    }

    public void setStatus(EnquiryStatus status) {
        this.status = status;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public List<EnquiryItem> getItems() {
        return items;
    }

    public void setItems(List<EnquiryItem> items) {
        this.items = items;
    }
}
