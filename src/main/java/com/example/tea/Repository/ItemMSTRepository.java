package com.example.tea.Repository;

import com.example.tea.DTO.ItemResponse;
import com.example.tea.Model.ItemMST;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemMSTRepository extends JpaRepository<ItemMST,Long> {
    List<ItemMST> findByIsActiveTrue();

    /** Number of active items - used to enforce the max-100 active products rule. */
    long countByIsActiveTrue();

    /**
     * Active items as lightweight DTOs (no image bytes). Used by the client website API.
     */
    @Query("select new com.example.tea.DTO.ItemResponse(" +
            "i.id, i.itemName, i.itemDescription, i.price, i.sellPrice, i.quantity, i.gst, i.unit, i.subUnit, i.perUnitQuantity, i.isActive, i.imageContentType) " +
            "from ItemMST i where i.isActive = true and i.quantity>0 order by i.itemName")
    List<ItemResponse> findActiveItemResponses();

    /**
     * All items (active + inactive) as lightweight DTOs (no image bytes). Used by the admin API.
     */
    @Query("select new com.example.tea.DTO.ItemResponse(" +
            "i.id, i.itemName, i.itemDescription, i.price, i.sellPrice, i.quantity, i.gst, i.unit, i.subUnit, i.perUnitQuantity, i.isActive, i.imageContentType) " +
            "from ItemMST i order by i.itemName")
    List<ItemResponse> findAllItemResponses();

    /** Active items whose stock is at or below the given threshold. */
    List<ItemMST> findByIsActiveTrueAndQuantityLessThanEqual(Double threshold);

    /**
     * Search products by name or description with relevance ranking.
     */
    @Query("""
    SELECT new com.example.tea.DTO.ItemResponse(
                       i.id, i.itemName, i.itemDescription, i.price, i.sellPrice, i.quantity, i.gst, i.unit, i.subUnit, i.perUnitQuantity, i.isActive, i.imageContentType)
     FROM ItemMST i
    WHERE i.itemName LIKE CONCAT('%', :key, '%')
       OR i.itemDescription LIKE CONCAT('%', :key, '%')
    ORDER BY
        CASE
            WHEN i.itemName LIKE CONCAT(:key, '%') THEN 1
            WHEN i.itemDescription LIKE CONCAT(:key, '%') THEN 2
            WHEN i.itemName LIKE CONCAT('%', :key, '%') THEN 3
            WHEN i.itemDescription LIKE CONCAT('%', :key, '%') THEN 4
            ELSE 5
        END,
        i.itemName ASC
    """)
    List<ItemResponse> searchProducts(@Param("key") String key);
}
