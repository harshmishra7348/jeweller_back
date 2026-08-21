package com.example.tea.Utility.Constant;

/**
 * Lifecycle of a customer inquiry.
 * CART      - the customer's open inquiry cart (still being built).
 * SUBMITTED - customer sent the cart for inquiry; the shop owner has been emailed.
 * RESOLVED  - the shop owner has handled the inquiry.
 */
public enum EnquiryStatus {
    CART,
    SUBMITTED,
    RESOLVED
}
