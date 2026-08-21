package com.example.tea.Controller;

import com.example.tea.DTO.AddToCartRequest;
import com.example.tea.DTO.GenericResponse;
import com.example.tea.DTO.SubmitEnquiryRequest;
import com.example.tea.Services.EnquiryService;
import com.example.tea.Utility.Constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Inquiry cart + inquiry management. All endpoints require a logged-in user (JWT).
 * Cart endpoints act on the current customer; admin endpoints require merchant = true.
 */
@RestController
@RequestMapping(value = Constant.ENQUIRY)
public class EnquiryController {

    @Autowired
    private EnquiryService enquiryService;

    // ----- Customer: inquiry cart -----

    /** Add a product to the current customer's inquiry cart. */
    @PostMapping(value = Constant.CART + Constant.ADD, consumes = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse addToCart(@RequestBody AddToCartRequest request) {
        try {
            return GenericResponse.success(
                    enquiryService.addToCart(request.getItemMSTId(), request.getQuantity()),
                    "Added to your inquiry cart.");
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    /** View the current customer's inquiry cart. */
    @GetMapping(value = Constant.CART)
    public GenericResponse getCart() {
        try {
            return GenericResponse.success(enquiryService.getCart());
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    /** Change the quantity of a product already in the cart. */
    @PutMapping(value = Constant.CART + Constant.QUANTITY)
    public GenericResponse updateQuantity(@RequestParam("itemMSTId") Long itemMSTId,
                                          @RequestParam("quantity") Double quantity) {
        try {
            return GenericResponse.success(enquiryService.updateQuantity(itemMSTId, quantity));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    /** Remove a product from the cart. */
    @DeleteMapping(value = Constant.CART + Constant.REMOVE + "/{itemMSTId}")
    public GenericResponse removeFromCart(@PathVariable("itemMSTId") Long itemMSTId) {
        try {
            return GenericResponse.success(enquiryService.removeFromCart(itemMSTId));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    /** Submit the cart for inquiry (emails the shop owner). */
    @PostMapping(value = Constant.CART + Constant.SUBMIT)
    public GenericResponse submitCart(@RequestBody(required = false) SubmitEnquiryRequest request) {
        try {
            String message = request != null ? request.getMessage() : null;
            enquiryService.submitCart(message);
            return GenericResponse.success(null, "Your inquiry has been sent to the seller.");
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    // ----- Admin: review inquiries (merchant only) -----

    /** All submitted/resolved inquiries, newest first. */
    @GetMapping(value = Constant.GET_ALL)
    public GenericResponse getAll() {
        try {
            return GenericResponse.success(enquiryService.getAll());
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    /** Inquiries not yet handled. */
    @GetMapping(value = Constant.UNRESOLVED)
    public GenericResponse getUnresolved() {
        try {
            return GenericResponse.success(enquiryService.getUnresolved());
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    /** Mark an inquiry as handled. */
    @PutMapping(value = Constant.RESOLVE + "/{enquiryId}")
    public GenericResponse resolve(@PathVariable("enquiryId") Long enquiryId) {
        try {
            return GenericResponse.success(enquiryService.resolve(enquiryId));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }
}
