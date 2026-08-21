package com.example.tea.Repository;

import com.example.tea.Model.ItemInvoiceMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemInvoiceMappingRepository extends JpaRepository<ItemInvoiceMapping,Long> {

    List<ItemInvoiceMapping> findByInvoiceMSTId(Long invoiceMSTId);
}
