package com.example.tea.Repository;

import com.example.tea.Model.PurchaseItemMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseItemMappingRepository extends JpaRepository<PurchaseItemMapping, Long> {
    List<PurchaseItemMapping> findByPurchaseMSTId(Long purchaseMSTId);
}
