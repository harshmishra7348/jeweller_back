package com.example.tea.Repository;

import com.example.tea.Model.ContactDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactDetailRepository extends JpaRepository<ContactDetail, Long> {
    /** The single contact record (lowest id) if one exists. */
    Optional<ContactDetail> findTopByOrderByIdAsc();
}
