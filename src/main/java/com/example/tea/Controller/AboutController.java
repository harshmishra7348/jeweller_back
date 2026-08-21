package com.example.tea.Controller;

import com.example.tea.DTO.GenericResponse;
import com.example.tea.Services.AboutService;
import com.example.tea.Utility.Constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/** Admin-only management of "About Us" sections (array). Secured (ADMIN). */
@RestController
@RequestMapping(value = Constant.ABOUT)
public class AboutController {

    @Autowired
    private AboutService aboutService;

    @PostMapping(value = Constant.CREATE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GenericResponse create(@RequestParam("mainHeading") String mainHeading,
                                  @RequestParam(value = "subHeading", required = false) String subHeading,
                                  @RequestParam(value = "description", required = false) String description,
                                  @RequestParam(value = "displayOrder", required = false) Integer displayOrder,
                                  @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            return GenericResponse.success(
                    aboutService.create(mainHeading, subHeading, description, displayOrder, image));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    @PutMapping(value = Constant.UPDATE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GenericResponse update(@RequestParam("id") Long id,
                                  @RequestParam("mainHeading") String mainHeading,
                                  @RequestParam(value = "subHeading", required = false) String subHeading,
                                  @RequestParam(value = "description", required = false) String description,
                                  @RequestParam(value = "displayOrder", required = false) Integer displayOrder,
                                  @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            return GenericResponse.success(
                    aboutService.update(id, mainHeading, subHeading, description, displayOrder, image));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    @GetMapping(value = Constant.GET_ALL)
    public GenericResponse getAll() {
        try {
            return GenericResponse.success(aboutService.getAll());
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    @DeleteMapping(value = Constant.DELETE + "/{id}")
    public GenericResponse delete(@PathVariable("id") Long id) {
        try {
            return GenericResponse.success(aboutService.delete(id));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }
}
