package com.example.tea.Controller;

import com.example.tea.DTO.GenericResponse;
import com.example.tea.Model.HomeSetting;
import com.example.tea.Services.HomeSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Home page appearance (hero background colour).
 *  - GET /public/home/settings : public read for the storefront (permitAll via /public/**).
 *  - GET /homeSetting          : admin read to prefill the form (authenticated).
 *  - POST /homeSetting/save     : admin save (service enforces merchant-only).
 * Paths are declared per-method so no changes to the security config are needed.
 */
@RestController
public class HomeSettingController {

    @Autowired
    private HomeSettingService homeSettingService;

    @GetMapping("/public/home/settings")
    public GenericResponse publicGet() {
        try {
            return GenericResponse.success(homeSettingService.get());
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    @GetMapping("/homeSetting")
    public GenericResponse get() {
        try {
            return GenericResponse.success(homeSettingService.get());
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    @PostMapping(value = "/homeSetting/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse save(@RequestBody HomeSetting homeSetting) {
        try {
            return GenericResponse.success(homeSettingService.save(homeSetting), "Home settings saved.");
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }
}
