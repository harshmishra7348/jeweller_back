package com.example.tea.Repository;

import com.example.tea.Model.UserMST;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserMSTRepository extends JpaRepository<UserMST,Long> {

    List<UserMST> findByIsActiveTrue();
    Optional<UserMST> findByEmailAndIsActiveTrue(String email);
    boolean existsByEmailAndIsActiveTrue(String email);
}
