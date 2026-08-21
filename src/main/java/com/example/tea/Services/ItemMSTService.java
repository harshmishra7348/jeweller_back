package com.example.tea.Services;

import com.example.tea.DTO.ItemResponse;
import com.example.tea.Model.ItemMST;
import com.example.tea.Repository.ItemMSTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ItemMSTService {

    /** Maximum number of active items the admin may keep at once (requirement #9). */
    public static final long MAX_ACTIVE_ITEMS = 100;

    @Autowired
    private ItemMSTRepository itemMSTRepository;

    public ItemMST create(ItemMST itemMST, MultipartFile image) throws Exception {
        validate(itemMST);
        // Enforce the max-100 active-items limit. Deleting an item frees a slot (isActive=false).
        long activeCount = itemMSTRepository.countByIsActiveTrue();
        if (activeCount >= MAX_ACTIVE_ITEMS) {
            throw new Exception("Maximum " + MAX_ACTIVE_ITEMS + " active items are allowed. " +
                    "Delete an existing item before adding a new one.");
        }
        itemMST.setActive(true);
        applyImage(itemMST, image);
        return itemMSTRepository.save(itemMST);
    }

    public ItemMST update(ItemMST itemMST, MultipartFile image) throws Exception {
        ItemMST existing = getById(itemMST.getId());
        validate(itemMST);
        // Preserve the existing image unless a new one is supplied in this request.
        if (image != null && !image.isEmpty()) {
            applyImage(itemMST, image);
        } else {
            itemMST.setImageData(existing.getImageData());
            itemMST.setImageContentType(existing.getImageContentType());
            itemMST.setImageName(existing.getImageName());
        }
        return itemMSTRepository.save(itemMST);
    }

    private void validate(ItemMST itemMST) throws Exception {
        if (itemMST.getItemName() == null || itemMST.getItemName().trim().isEmpty()) {
            throw new Exception("Item name is required.");
        }
        if (itemMST.getPrice() == null) {
            throw new Exception("Item price is required.");
        }
        if (itemMST.getSellPrice() == null) {
            throw new Exception("Item sell price is required.");
        }
        if (itemMST.getGst() == null) {
            throw new Exception("Item GST tax is required.");
        }
        if (itemMST.getQuantity() == null) {
            throw new Exception("Item quantity is required.");
        }
        if (itemMST.getUnit() == null || itemMST.getUnit().trim().isEmpty()) {
            throw new Exception("Item Unit is required.");
        }
        
        // If subUnit is provided, perUnitQuantity must also be provided
        if (itemMST.getSubUnit() != null && !itemMST.getSubUnit().trim().isEmpty()) {
            if (itemMST.getPerUnitQuantity() == null || itemMST.getPerUnitQuantity().equals(0.0)) {
                throw new Exception("Per Unit Quantity is required when Sub Unit is provided.");
            }
        }
    }

    private void applyImage(ItemMST itemMST, MultipartFile image) throws Exception {
        if (image == null || image.isEmpty()) {
            return;
        }
        String contentType = image.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new Exception("Uploaded file must be an image.");
        }
        try {
            itemMST.setImageData(image.getBytes());
            itemMST.setImageContentType(contentType);
            itemMST.setImageName(image.getOriginalFilename());
        } catch (IOException e) {
            throw new Exception("Failed to read the uploaded image.");
        }
    }

    public ItemMST getById(Long itemMSTId) {
        return itemMSTRepository.findById(itemMSTId)
                .orElseThrow(() -> new RuntimeException("Item not found."));
    }

    /** All items (active + inactive) as lightweight DTOs - admin API. */
    public List<ItemResponse> getAll() {
        return itemMSTRepository.findAllItemResponses();
    }

    /** Active items only as lightweight DTOs - client website API. */
    public List<ItemResponse> getAllActive() {
        return itemMSTRepository.findActiveItemResponses();
    }

    /** Soft delete: mark inactive so the admin can add a new item in its place. */
    public ItemMST deleteById(Long itemMSTId) {
        ItemMST byId = getById(itemMSTId);
        byId.setActive(false);
        return itemMSTRepository.save(byId);
    }

    /** Adjust stock by a signed delta (positive to add, negative to remove) - inventory management. */
    public ItemMST adjustStock(Long itemMSTId, Double delta) throws Exception {
        if (delta == null) {
            throw new Exception("Adjustment quantity is required.");
        }
        ItemMST item = getById(itemMSTId);
        double newQuantity = item.getQuantity() + delta;
        if (newQuantity < 0) {
            throw new Exception("Stock cannot go below zero. Current stock: " + item.getQuantity());
        }
        item.setQuantity(newQuantity);
        return itemMSTRepository.save(item);
    }

    /** Active items whose stock is at or below the given threshold - inventory management. */
    public List<ItemMST> getLowStock(Double threshold) {
        return itemMSTRepository.findByIsActiveTrueAndQuantityLessThanEqual(threshold);
    }

    /** Search products by name or description with relevance ranking. */
    public List<ItemResponse> searchProducts(String key) {
        if (key == null || key.trim().isEmpty()) {
            return getAllActive();
        }
        return itemMSTRepository.searchProducts(key.trim());
    }
}
