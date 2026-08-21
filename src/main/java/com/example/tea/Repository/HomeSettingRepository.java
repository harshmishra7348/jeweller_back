package com.example.tea.Repository;

import com.example.tea.Model.HomeSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomeSettingRepository extends JpaRepository<HomeSetting, Long> {
    /** The single home settings record (lowest id) if one exists. */
    Optional<HomeSetting> findTopByOrderByIdAsc();
}
