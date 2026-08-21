package com.example.tea.Services;

import com.example.tea.Model.ContactDetail;
import com.example.tea.Repository.ContactDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    private ContactDetailRepository contactDetailRepository;

    /** The shop's contact details, or an empty object if the admin has not set them yet. */
    public ContactDetail getContact() {
        return contactDetailRepository.findTopByOrderByIdAsc().orElseGet(ContactDetail::new);
    }

    /** Upsert the single contact record. */
    public ContactDetail save(ContactDetail incoming) {
        ContactDetail existing = contactDetailRepository.findTopByOrderByIdAsc().orElse(null);
        incoming.setId(existing != null ? existing.getId() : null);
        incoming.setActive(true);
        return contactDetailRepository.save(incoming);
    }
}
