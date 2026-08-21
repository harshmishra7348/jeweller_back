package com.example.tea.Repository;

import com.example.tea.Model.InvoiceMST;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceMSTRepository extends JpaRepository<InvoiceMST,Long> {
    @Query("""
    SELECT i
    FROM InvoiceMST i
    WHERE i.invoiceNumber LIKE CONCAT('%', :key, '%')
       OR i.userMST.name LIKE CONCAT('%', :key, '%')
    ORDER BY
        CASE
            WHEN i.invoiceNumber LIKE CONCAT(:key, '%') THEN 1
            WHEN i.userMST.name LIKE CONCAT(:key, '%') THEN 2
            WHEN i.invoiceNumber LIKE CONCAT('%', :key, '%') THEN 3
            WHEN i.userMST.name LIKE CONCAT('%', :key, '%') THEN 4
            ELSE 5
        END
""")
    List<InvoiceMST> searchInvoices(@Param("key") String key);

}
