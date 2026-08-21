package com.example.tea.Services;

import com.example.tea.DTO.AboutResponse;
import com.example.tea.Model.AboutSection;
import com.example.tea.Repository.AboutSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AboutService {

    @Autowired
    private AboutSectionRepository aboutSectionRepository;

    public AboutSection create(String mainHeading, String subHeading, String description,
                               Integer displayOrder, MultipartFile image) throws Exception {
        if (mainHeading == null || mainHeading.trim().isEmpty()) {
            throw new Exception("Main heading is required.");
        }
        AboutSection section = new AboutSection();
        section.setMainHeading(mainHeading);
        section.setSubHeading(subHeading);
        section.setDescription(description);
        section.setDisplayOrder(displayOrder);
        section.setActive(true);
        applyImage(section, image); // image is optional for About blocks
        return aboutSectionRepository.save(section);
    }

    public AboutSection update(Long id, String mainHeading, String subHeading, String description,
                               Integer displayOrder, MultipartFile image) throws Exception {
        AboutSection section = getById(id);
        if (mainHeading == null || mainHeading.trim().isEmpty()) {
            throw new Exception("Main heading is required.");
        }
        section.setMainHeading(mainHeading);
        section.setSubHeading(subHeading);
        section.setDescription(description);
        section.setDisplayOrder(displayOrder);
        applyImage(section, image);
        return aboutSectionRepository.save(section);
    }

    private void applyImage(AboutSection section, MultipartFile image) throws Exception {
        if (image == null || image.isEmpty()) {
            return; // keep existing image (on update) / no image (on create)
        }
        String contentType = image.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new Exception("Uploaded file must be an image.");
        }
        try {
            section.setImageData(image.getBytes());
            section.setImageContentType(contentType);
            section.setImageName(image.getOriginalFilename());
        } catch (IOException e) {
            throw new Exception("Failed to read the uploaded image.");
        }
    }

    public AboutSection getById(Long id) {
        return aboutSectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("About section not found."));
    }

    public List<AboutResponse> getAll() {
        return aboutSectionRepository.findAllResponses();
    }

    public List<AboutResponse> getAllActive() {
        return aboutSectionRepository.findActiveResponses();
    }

    public AboutSection delete(Long id) {
        AboutSection section = getById(id);
        section.setActive(false);
        return aboutSectionRepository.save(section);
    }
}
