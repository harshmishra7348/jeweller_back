package com.example.tea.Services;

import com.example.tea.Model.Enquiry;
import com.example.tea.Model.EnquiryItem;
import com.example.tea.Model.ItemMST;
import com.example.tea.Model.UserMST;
import com.example.tea.Repository.EnquiryItemRepository;
import com.example.tea.Repository.EnquiryRepository;
import com.example.tea.Utility.Constant.EnquiryStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnquiryService {

    @Autowired
    private EnquiryRepository enquiryRepository;
    @Autowired
    private EnquiryItemRepository enquiryItemRepository;
    @Autowired
    private ItemMSTService itemMSTService;
    @Autowired
    private UserMSTService userMSTService;
    @Autowired
    private MailUtil mailUtil;

    // The shop owner's inbox that submitted inquiries are delivered to.
    @Value("${app.company.email}")
    private String adminEmail;
    // ----- Customer: inquiry cart -----

    /** Add a product to the current customer's cart (creating the cart on first add). */
    @Transactional
    public Enquiry addToCart(Long itemMSTId, Double quantity) {
        if (itemMSTId == null) {
            throw new RuntimeException("Product is required.");
        }
        double qty = (quantity == null || quantity <= 0) ? 1d : quantity;

        UserMST customer = currentUser();
        ItemMST item = itemMSTService.getById(itemMSTId);
        if (!item.isActive()) {
            throw new RuntimeException("This product is no longer available.");
        }

        Enquiry cart = getOrCreateCart(customer);
        EnquiryItem line = enquiryItemRepository
                .findByEnquiryIdAndItemMSTId(cart.getId(), itemMSTId)
                .orElse(null);
        if (line == null) {
            line = new EnquiryItem();
            line.setEnquiryId(cart.getId());
            line.setItemMST(item);
            line.setItemMSTId(item.getId());
            line.setItemName(item.getItemName());
            line.setQuantity(qty);
        } else {
            line.setQuantity(line.getQuantity() + qty);
        }
        enquiryItemRepository.save(line);
        return withItems(cart);
    }

    /** Set the exact quantity for a product already in the cart. */
    @Transactional
    public Enquiry updateQuantity(Long itemMSTId, Double quantity) {
        if (quantity == null || quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than zero.");
        }
        Enquiry cart = requireCart(currentUser());
        EnquiryItem line = enquiryItemRepository.findByEnquiryIdAndItemMSTId(cart.getId(), itemMSTId)
                .orElseThrow(() -> new RuntimeException("Product is not in your cart."));
        line.setQuantity(quantity);
        enquiryItemRepository.save(line);
        return withItems(cart);
    }

    /** Remove a product from the cart. */
    @Transactional
    public Enquiry removeFromCart(Long itemMSTId) {
        Enquiry cart = requireCart(currentUser());
        EnquiryItem line = enquiryItemRepository.findByEnquiryIdAndItemMSTId(cart.getId(), itemMSTId)
                .orElseThrow(() -> new RuntimeException("Product is not in your cart."));
        enquiryItemRepository.delete(line);
        return withItems(cart);
    }

    /** The current customer's cart with its items (empty cart if none yet). */
    public Enquiry getCart() {
        UserMST customer = currentUser();
        Enquiry cart = enquiryRepository
                .findByUserMSTIdAndStatus(customer.getId(), EnquiryStatus.CART)
                .orElse(null);
        if (cart == null) {
            return getOrCreateCart(customer);
        }
        return withItems(cart);
    }

    /** Submit the cart: mark it SUBMITTED and email the shop owner. */
    @Transactional
    public Enquiry submitCart(String message) {
        Enquiry cart = requireCart(currentUser());
        List<EnquiryItem> items = enquiryItemRepository.findByEnquiryId(cart.getId());
        if (items.isEmpty()) {
            throw new RuntimeException("Your inquiry cart is empty.");
        }
        cart.setMessage(message);
        cart.setStatus(EnquiryStatus.SUBMITTED);
        cart.setSubmittedAt(LocalDateTime.now());
        cart = enquiryRepository.save(cart);
        cart.setItems(items);

        // Best-effort email; the inquiry is already persisted so a mail failure never loses the lead.
        try {
            mailUtil.sendEnquiryToAdmin(cart, items, adminEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cart;
    }

    // ----- Admin: review submitted inquiries -----

    public List<Enquiry> getAll() {
        requireAdmin();
        // Everything except still-open carts.
        List<Enquiry> list = enquiryRepository.findByStatusNotOrderBySubmittedAtDesc(EnquiryStatus.CART);
        list.forEach(this::withItems);
        return list;
    }

    public List<Enquiry> getUnresolved() {
        requireAdmin();
        List<Enquiry> list = enquiryRepository.findByStatusOrderBySubmittedAtDesc(EnquiryStatus.SUBMITTED);
        list.forEach(this::withItems);
        return list;
    }

    public Enquiry resolve(Long enquiryId) {
        requireAdmin();
        Enquiry enquiry = enquiryRepository.findById(enquiryId)
                .orElseThrow(() -> new RuntimeException("Inquiry not found."));
        enquiry.setStatus(EnquiryStatus.RESOLVED);
        enquiry = enquiryRepository.save(enquiry);
        return withItems(enquiry);
    }

    // ----- helpers -----

    private Enquiry getOrCreateCart(UserMST customer) {
        return enquiryRepository.findByUserMSTIdAndStatus(customer.getId(), EnquiryStatus.CART)
                .map(this::withItems)
                .orElseGet(() -> {
                    Enquiry cart = new Enquiry();
                    cart.setUserMSTId(customer.getId());
                    cart.setCustomerName(customer.getName());
                    cart.setCustomerEmail(customer.getEmail());
                    cart.setCustomerPhone(customer.getPhoneNumber());
                    cart.setStatus(EnquiryStatus.CART);
                    Enquiry saved = enquiryRepository.save(cart);
                    saved.setItems(List.of());
                    return saved;
                });
    }

    private Enquiry requireCart(UserMST customer) {
        return enquiryRepository.findByUserMSTIdAndStatus(customer.getId(), EnquiryStatus.CART)
                .orElseThrow(() -> new RuntimeException("Your inquiry cart is empty."));
    }

    private Enquiry withItems(Enquiry enquiry) {
        enquiry.setItems(enquiryItemRepository.findByEnquiryId(enquiry.getId()));
        return enquiry;
    }

    /** The logged-in user (resolved from the JWT). */
    private UserMST currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null || "anonymousUser".equals(auth.getName())) {
            throw new RuntimeException("Please login to continue.");
        }
        return userMSTService.getByEmail(auth.getName());
    }

    /** Only admin users (admin = true) may review inquiries. */
    private void requireAdmin() {
        UserMST user = currentUser();
        if (user.getAdmin() == null || !user.getAdmin()) {
            throw new RuntimeException("Not authorized. Admin access only.");
        }
    }
}
