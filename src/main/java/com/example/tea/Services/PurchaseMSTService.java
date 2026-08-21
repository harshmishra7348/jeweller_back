package com.example.tea.Services;

import com.example.tea.DTO.PurchaseLineRequest;
import com.example.tea.DTO.PurchaseRequest;
import com.example.tea.Model.ItemMST;
import com.example.tea.Model.PurchaseItemMapping;
import com.example.tea.Model.PurchaseMST;
import com.example.tea.Repository.ItemMSTRepository;
import com.example.tea.Repository.PurchaseItemMappingRepository;
import com.example.tea.Repository.PurchaseMSTRepository;
import com.example.tea.Utility.Constant.InvoiceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseMSTService {

    @Autowired
    private PurchaseMSTRepository purchaseMSTRepository;
    @Autowired
    private PurchaseItemMappingRepository purchaseItemMappingRepository;
    @Autowired
    private ItemMSTService itemMSTService;
    @Autowired
    private ItemMSTRepository itemMSTRepository;

    /** Records a purchase and increases inventory for each purchased item (atomic). */
    @Transactional
    public PurchaseMST create(PurchaseRequest request) {
        if (request.getSupplierName() == null || request.getSupplierName().trim().isEmpty()) {
            throw new RuntimeException("Supplier name is required.");
        }
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new RuntimeException("Please add items to purchase.");
        }

        PurchaseMST purchase = new PurchaseMST();
        purchase.setSupplierName(request.getSupplierName());
        purchase.setSupplierGst(request.getSupplierGst());
        purchase.setAddress(request.getAddress());
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase.setPurchaseNumber("PUR-" + System.currentTimeMillis());
        purchase.setPurchaseStatus(InvoiceEnum.PENDING);
        purchase = purchaseMSTRepository.save(purchase);

        List<ItemMST> itemsToUpdate = new ArrayList<>();
        List<PurchaseItemMapping> mappings = new ArrayList<>();
        double subtotal = 0d;

        for (PurchaseLineRequest line : request.getItems()) {
            if (line.getItemMSTId() == null) {
                throw new RuntimeException("Each purchase line must reference an item.");
            }
            if (line.getQuantity() == null || line.getQuantity() <= 0) {
                throw new RuntimeException("Purchase quantity must be greater than zero.");
            }
            if (line.getCostPrice() == null || line.getCostPrice() < 0) {
                throw new RuntimeException("Purchase cost price is required.");
            }

            ItemMST item = itemMSTService.getById(line.getItemMSTId());
            // Increase stock and refresh the item's cost price.
            item.setQuantity(item.getQuantity() + line.getQuantity());
            item.setPrice(line.getCostPrice());
            itemsToUpdate.add(item);

            double lineTotal = round(line.getQuantity() * line.getCostPrice());
            PurchaseItemMapping mapping = new PurchaseItemMapping();
            mapping.setItemMST(item);
            mapping.setItemMSTId(item.getId());
            mapping.setPurchaseMST(purchase);
            mapping.setPurchaseMSTId(purchase.getId());
            mapping.setQuantity(line.getQuantity());
            mapping.setPerUnitQuantity(line.getPerUnitQuantity());
            mapping.setCostPrice(line.getCostPrice());
            mapping.setTotalAmount(lineTotal);
            mappings.add(mapping);
            subtotal += lineTotal;
        }

        itemMSTRepository.saveAll(itemsToUpdate);
        purchaseItemMappingRepository.saveAll(mappings);

        double taxRate = request.getTax() != null ? request.getTax() : 0d;
        double tax = round(subtotal * taxRate / 100);
        purchase.setTax(tax);
        purchase.setAmount(round(subtotal + tax));
        purchase.setPurchaseStatus(InvoiceEnum.COMPLETED);
        return purchaseMSTRepository.save(purchase);
    }

    public PurchaseMST getById(Long purchaseMSTId) {
        return purchaseMSTRepository.findById(purchaseMSTId)
                .orElseThrow(() -> new RuntimeException("Purchase not found."));
    }

    public List<PurchaseMST> getAll() {
        return purchaseMSTRepository.findAll();
    }

    public List<PurchaseItemMapping> getItems(Long purchaseMSTId) {
        return purchaseItemMappingRepository.findByPurchaseMSTId(purchaseMSTId);
    }

    public Boolean delete(Long purchaseMSTId) {
        PurchaseMST purchase = getById(purchaseMSTId);
        purchase.setActive(false);
        purchaseMSTRepository.save(purchase);
        return true;
    }

    private double round(double value) {
        return Math.round(value * 100d) / 100d;
    }
}
