package com.example.tea.Controller;

import com.example.tea.DTO.GenericResponse;
import com.example.tea.Model.ItemMST;
import com.example.tea.Services.ItemMSTService;
import com.example.tea.Utility.Constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Public, unauthenticated API consumed by the client-side website.
 * Only exposes active products (isActive = true) - never admin data.
 */
@RestController
@RequestMapping(value = Constant.PUBLIC + "/itemMST")
public class PublicController {

    @Autowired
    private ItemMSTService itemMSTService;

    /** Active products for the storefront listing. */
    @GetMapping(value = Constant.GET_ALL_ACTIVE)
    public GenericResponse getAllActive() {
        try {
            return GenericResponse.success(itemMSTService.getAllActive());
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    /** Single active product detail. */
    @GetMapping(value = Constant.BY_ID + "/{itemMSTId}")
    public GenericResponse getById(@PathVariable(name = "itemMSTId") Long itemMSTId) {
        try {
            ItemMST item = itemMSTService.getById(itemMSTId);
            if (!item.isActive()) {
                return GenericResponse.error("Item not found.");
            }
            return GenericResponse.success(item);
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    /** Serves the raw product image so the website can render <img src="/public/itemMST/image/{id}">. */
    @GetMapping(value = Constant.IMAGE + "/{itemMSTId}")
    public ResponseEntity<byte[]> image(@PathVariable(name = "itemMSTId") Long itemMSTId) {
        try {
            ItemMST item = itemMSTService.getById(itemMSTId);
            byte[] data = item.getImageData();
            if (data == null || data.length == 0) {
                return ResponseEntity.notFound().build();
            }
            MediaType mediaType = item.getImageContentType() != null
                    ? MediaType.parseMediaType(item.getImageContentType())
                    : MediaType.APPLICATION_OCTET_STREAM;
            return ResponseEntity.ok().contentType(mediaType).body(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
