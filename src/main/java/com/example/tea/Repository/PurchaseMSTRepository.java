package com.example.tea.Repository;

import com.example.tea.Model.PurchaseMST;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseMSTRepository extends JpaRepository<PurchaseMST, Long> {
    List<PurchaseMST> findByIsActiveTrue();
}
