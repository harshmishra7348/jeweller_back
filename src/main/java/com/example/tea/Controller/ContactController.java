package com.example.tea.Controller;

import com.example.tea.DTO.GenericResponse;
import com.example.tea.Model.ContactDetail;
import com.example.tea.Services.ContactService;
import com.example.tea.Utility.Constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/** Admin-only management of the shop's contact details. Secured (ADMIN). */
@RestController
@RequestMapping(value = Constant.CONTACT)
public class ContactController {

    @Autowired
    private ContactService contactService;

    /** Create or update the single contact record. */
    @PostMapping(value = Constant.SAVE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse save(@RequestBody ContactDetail contactDetail) {
        try {
            return GenericResponse.success(contactService.save(contactDetail));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    @GetMapping
    public GenericResponse get() {
        try {
            return GenericResponse.success(contactService.getContact());
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }
}
