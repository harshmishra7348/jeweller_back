package com.example.tea.Repository;

import com.example.tea.DTO.HomeImageResponse;
import com.example.tea.Model.HomeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeImageRepository extends JpaRepository<HomeImage, Long> {

    long countByIsActiveTrue();

    @Query("select new com.example.tea.DTO.HomeImageResponse(" +
            "h.id, h.title, h.displayOrder, h.isActive, h.imageContentType) " +
            "from HomeImage h where h.isActive = true order by h.displayOrder asc, h.id asc")
    List<HomeImageResponse> findActiveResponses();

    @Query("select new com.example.tea.DTO.HomeImageResponse(" +
            "h.id, h.title, h.displayOrder, h.isActive, h.imageContentType) " +
            "from HomeImage h order by h.displayOrder asc, h.id asc")
    List<HomeImageResponse> findAllResponses();
}
