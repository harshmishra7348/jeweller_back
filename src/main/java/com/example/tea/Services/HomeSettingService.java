package com.example.tea.Services;

import com.example.tea.Model.HomeSetting;
import com.example.tea.Model.UserMST;
import com.example.tea.Repository.HomeSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeSettingService {

    @Autowired
    private HomeSettingRepository homeSettingRepository;
    @Autowired
    private UserMSTService userMSTService;

    /** Current home settings, or an empty (default) record if none saved yet. */
    public HomeSetting get() {
        return homeSettingRepository.findTopByOrderByIdAsc().orElseGet(HomeSetting::new);
    }

    /** Upsert the single settings record. Admin (merchant) only. */
    public HomeSetting save(HomeSetting incoming) {
        requireAdmin();
        HomeSetting existing = homeSettingRepository.findTopByOrderByIdAsc().orElse(null);
        incoming.setId(existing != null ? existing.getId() : null);
        incoming.setActive(true);
        return homeSettingRepository.save(incoming);
    }

    /** Only shop owners (merchant = true) may change appearance settings. */
    private void requireAdmin() {
        UserMST user = userMSTService.getCurrentUser();
        if (user.getAdmin() == null || !user.getAdmin()) {
            throw new RuntimeException("Not authorized. Admin access only.");
        }
    }
}
