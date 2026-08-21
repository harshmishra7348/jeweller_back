package com.example.tea.Controller;

import com.example.tea.DTO.GenericResponse;
import com.example.tea.Services.HomeImageService;
import com.example.tea.Utility.Constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/** Admin-only management of home page banner images. Secured (ADMIN). */
@RestController
@RequestMapping(value = Constant.HOME_IMAGE)
public class HomeImageController {

    @Autowired
    private HomeImageService homeImageService;

    @PostMapping(value = Constant.CREATE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GenericResponse create(@RequestParam(value = "title", required = false) String title,
                                  @RequestParam(value = "displayOrder", required = false) Integer displayOrder,
                                  @RequestPart("image") MultipartFile image) {
        try {
            return GenericResponse.success(homeImageService.create(title, displayOrder, image));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    /** Update title/order and optionally replace the image. */
    @PutMapping(value = Constant.UPDATE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GenericResponse update(@RequestParam("id") Long id,
                                  @RequestParam(value = "title", required = false) String title,
                                  @RequestParam(value = "displayOrder", required = false) Integer displayOrder,
                                  @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            return GenericResponse.success(homeImageService.update(id, title, displayOrder, image));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    @GetMapping(value = Constant.GET_ALL)
    public GenericResponse getAll() {
        try {
            return GenericResponse.success(homeImageService.getAll());
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    @DeleteMapping(value = Constant.DELETE + "/{id}")
    public GenericResponse delete(@PathVariable("id") Long id) {
        try {
            return GenericResponse.success(homeImageService.delete(id));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }
}
