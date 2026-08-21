package com.example.tea.Controller;

import com.example.tea.DTO.GenericResponse;
import com.example.tea.Model.ItemMST;
import com.example.tea.Services.ItemMSTService;
import com.example.tea.Utility.Constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Admin-only product management. Secured by JWT (see WebConfiguration).
 * Create/update accept multipart/form-data: an "item" JSON part plus an optional "image" file part.
 */
@RestController
@RequestMapping(value = "/itemMST")
public class ItemMSTController {
    @Autowired
    private ItemMSTService itemMSTService;

    @PostMapping(value = Constant.CREATE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GenericResponse create(@RequestPart("item") ItemMST itemMST,
                                  @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            return GenericResponse.success(itemMSTService.create(itemMST, image));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    @PutMapping(value = Constant.UPDATE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GenericResponse update(@RequestPart("item") ItemMST itemMST,
                                  @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            return GenericResponse.success(itemMSTService.update(itemMST, image));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    /** Admin listing - includes inactive (deleted) items. */
    @GetMapping(value = Constant.GET_ALL)
    public GenericResponse getAll() {
        try {
            return GenericResponse.success(itemMSTService.getAll());
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    @GetMapping(value = Constant.BY_ID + "/{itemMSTId}")
    public GenericResponse getById(@PathVariable(name = "itemMSTId") Long itemMSTId) {
        try {
            return GenericResponse.success(itemMSTService.getById(itemMSTId));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    @DeleteMapping(value = Constant.DELETE + "/{id}")
    public GenericResponse delete(@PathVariable("id") Long itemMSTId) {
        try {
            return GenericResponse.success(itemMSTService.deleteById(itemMSTId));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    /** Inventory management: add or remove stock by a signed delta. */
    @PutMapping(value = Constant.ADJUST_STOCK)
    public GenericResponse adjustStock(@RequestParam("itemMSTId") Long itemMSTId,
                                       @RequestParam("delta") Double delta) {
        try {
            return GenericResponse.success(itemMSTService.adjustStock(itemMSTId, delta));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    /** Inventory management: list active items at or below a stock threshold. */
    @GetMapping(value = Constant.LOW_STOCK)
    public GenericResponse lowStock(@RequestParam(value = "threshold", defaultValue = "10") Double threshold) {
        try {
            return GenericResponse.success(itemMSTService.getLowStock(threshold));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    /** Search products by name or description. */
    @GetMapping(value = Constant.SEARCH)
    public GenericResponse searchProducts(@RequestParam("key") String key) {
        try {
            return GenericResponse.success(itemMSTService.searchProducts(key));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }
}
