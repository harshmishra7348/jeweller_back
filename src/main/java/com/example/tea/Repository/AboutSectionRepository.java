package com.example.tea.Repository;

import com.example.tea.DTO.AboutResponse;
import com.example.tea.Model.AboutSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AboutSectionRepository extends JpaRepository<AboutSection, Long> {

    @Query("select new com.example.tea.DTO.AboutResponse(" +
            "a.id, a.mainHeading, a.subHeading, a.description, a.displayOrder, a.isActive, a.imageContentType) " +
            "from AboutSection a where a.isActive = true order by a.displayOrder asc, a.id asc")
    List<AboutResponse> findActiveResponses();

    @Query("select new com.example.tea.DTO.AboutResponse(" +
            "a.id, a.mainHeading, a.subHeading, a.description, a.displayOrder, a.isActive, a.imageContentType) " +
            "from AboutSection a order by a.displayOrder asc, a.id asc")
    List<AboutResponse> findAllResponses();
}
