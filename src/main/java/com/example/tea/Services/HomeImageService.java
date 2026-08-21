package com.example.tea.Services;

import com.example.tea.DTO.HomeImageResponse;
import com.example.tea.Model.HomeImage;
import com.example.tea.Repository.HomeImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class HomeImageService {

    /** Home page shows a small banner set - cap the number of active images. */
    public static final long MAX_ACTIVE_HOME_IMAGES = 5;

    @Autowired
    private HomeImageRepository homeImageRepository;

    public HomeImage create(String title, Integer displayOrder, MultipartFile image) throws Exception {
        if (image == null || image.isEmpty()) {
            throw new Exception("Image is required.");
        }
        if (homeImageRepository.countByIsActiveTrue() >= MAX_ACTIVE_HOME_IMAGES) {
            throw new Exception("Maximum " + MAX_ACTIVE_HOME_IMAGES + " home images are allowed. " +
                    "Delete one before adding a new image.");
        }
        HomeImage homeImage = new HomeImage();
        homeImage.setTitle(title);
        homeImage.setDisplayOrder(displayOrder);
        homeImage.setActive(true);
        applyImage(homeImage, image);
        return homeImageRepository.save(homeImage);
    }

    /** Update title/order and optionally replace the image. */
    public HomeImage update(Long id, String title, Integer displayOrder, MultipartFile image) throws Exception {
        HomeImage homeImage = getById(id);
        homeImage.setTitle(title);
        homeImage.setDisplayOrder(displayOrder);
        if (image != null && !image.isEmpty()) {
            applyImage(homeImage, image);
        }
        return homeImageRepository.save(homeImage);
    }

    private void applyImage(HomeImage homeImage, MultipartFile image) throws Exception {
        String contentType = image.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new Exception("Uploaded file must be an image.");
        }
        try {
            homeImage.setImageData(image.getBytes());
            homeImage.setImageContentType(contentType);
            homeImage.setImageName(image.getOriginalFilename());
        } catch (IOException e) {
            throw new Exception("Failed to read the uploaded image.");
        }
    }

    public HomeImage getById(Long id) {
        return homeImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Home image not found."));
    }

    public List<HomeImageResponse> getAll() {
        return homeImageRepository.findAllResponses();
    }

    public List<HomeImageResponse> getAllActive() {
        return homeImageRepository.findActiveResponses();
    }

    public HomeImage delete(Long id) {
        HomeImage homeImage = getById(id);
        homeImage.setActive(false);
        return homeImageRepository.save(homeImage);
    }
}
