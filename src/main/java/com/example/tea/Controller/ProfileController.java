package com.example.tea.Controller;

import com.example.tea.DTO.ChangePasswordRequest;
import com.example.tea.DTO.GenericResponse;
import com.example.tea.Model.UserMST;
import com.example.tea.Services.UserMSTService;
import com.example.tea.Utility.Constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Self-service profile for the logged-in customer. Any authenticated user (customer or admin)
 * may view and edit their own profile. Email and role cannot be changed here.
 */
@RestController
@RequestMapping(value = Constant.PROFILE)
public class ProfileController {

    @Autowired
    private UserMSTService userMSTService;

    /** View the current user's profile. */
    @GetMapping
    public GenericResponse getProfile() {
        try {
            return GenericResponse.success(userMSTService.getCurrentUser());
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    /** Update the current user's own details (name, address, phone). Email stays fixed. */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse updateProfile(@RequestBody UserMST userMST) {
        try {
            return GenericResponse.success(userMSTService.updateOwnProfile(userMST));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    /** Change the current user's password (verifies the current password first). */
    @PutMapping(value = Constant.CHANGE_PASSWORD, consumes = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            userMSTService.changePassword(request.getOldPassword(), request.getNewPassword());
            return GenericResponse.success(null, "Password changed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }
}
