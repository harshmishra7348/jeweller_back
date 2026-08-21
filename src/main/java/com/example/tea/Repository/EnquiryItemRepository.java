package com.example.tea.Repository;

import com.example.tea.Model.EnquiryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnquiryItemRepository extends JpaRepository<EnquiryItem, Long> {
    List<EnquiryItem> findByEnquiryId(Long enquiryId);
    Optional<EnquiryItem> findByEnquiryIdAndItemMSTId(Long enquiryId, Long itemMSTId);
}
