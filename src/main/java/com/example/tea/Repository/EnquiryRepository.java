package com.example.tea.Repository;

import com.example.tea.Model.Enquiry;
import com.example.tea.Utility.Constant.EnquiryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnquiryRepository extends JpaRepository<Enquiry, Long> {

    /** The customer's current open cart, if any. */
    Optional<Enquiry> findByUserMSTIdAndStatus(Long userMSTId, EnquiryStatus status);

    /** Admin: submitted/resolved inquiries (i.e. everything except open carts), newest first. */
    List<Enquiry> findByStatusNotOrderBySubmittedAtDesc(EnquiryStatus status);

    /** Admin: inquiries in a given status, newest first. */
    List<Enquiry> findByStatusOrderBySubmittedAtDesc(EnquiryStatus status);
}
