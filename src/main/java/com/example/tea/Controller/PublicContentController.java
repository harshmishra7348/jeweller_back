package com.example.tea.Controller;

import com.example.tea.DTO.GenericResponse;
import com.example.tea.Model.AboutSection;
import com.example.tea.Model.HomeImage;
import com.example.tea.Services.AboutService;
import com.example.tea.Services.ContactService;
import com.example.tea.Services.HomeImageService;
import com.example.tea.Utility.Constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Public, unauthenticated content for the client website: home banner images,
 * the About Us sections, and the Contact details.
 */
@RestController
@RequestMapping(value = Constant.PUBLIC)
public class PublicContentController {

    @Autowired
    private HomeImageService homeImageService;
    @Autowired
    private AboutService aboutService;
    @Autowired
    private ContactService contactService;

    // ----- Home banner images -----

    /** Active home banner images for the home-page sidebar. */
    @GetMapping(value = Constant.HOME + Constant.IMAGES)
    public GenericResponse homeImages() {
        try {
            return GenericResponse.success(homeImageService.getAllActive());
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    @GetMapping(value = Constant.HOME + Constant.IMAGE + "/{id}")
    public ResponseEntity<byte[]> homeImage(@PathVariable("id") Long id) {
        try {
            HomeImage image = homeImageService.getById(id);
            return imageResponse(image.getImageData(), image.getImageContentType());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    // ----- About Us -----

    /** Active About Us sections (the array the About page renders). */
    @GetMapping(value = Constant.ABOUT)
    public GenericResponse about() {
        try {
            return GenericResponse.success(aboutService.getAllActive());
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    @GetMapping(value = Constant.ABOUT + Constant.IMAGE + "/{id}")
    public ResponseEntity<byte[]> aboutImage(@PathVariable("id") Long id) {
        try {
            AboutSection section = aboutService.getById(id);
            return imageResponse(section.getImageData(), section.getImageContentType());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    // ----- Contact -----

    /** The shop's contact details for the Contact page. */
    @GetMapping(value = Constant.CONTACT)
    public GenericResponse contact() {
        try {
            return GenericResponse.success(contactService.getContact());
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    /** Website appearance settings (e.g. the home first-block background color). */

    private ResponseEntity<byte[]> imageResponse(byte[] data, String contentType) {
        if (data == null || data.length == 0) {
            return ResponseEntity.notFound().build();
        }
        MediaType mediaType = contentType != null
                ? MediaType.parseMediaType(contentType)
                : MediaType.APPLICATION_OCTET_STREAM;
        return ResponseEntity.ok().contentType(mediaType).body(data);
    }
}
