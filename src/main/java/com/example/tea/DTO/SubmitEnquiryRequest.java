package com.example.tea.DTO;

/** Submit the current inquiry cart, with an optional note to the seller. */
public class SubmitEnquiryRequest {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
