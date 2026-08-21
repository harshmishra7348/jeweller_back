package com.example.tea.Repository;

import com.example.tea.Model.TransportMST;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportMSTRepository extends JpaRepository<TransportMST, Long> {
}
