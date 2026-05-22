package com.example.SMS.repository;

import com.example.SMS.model.MarksData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarksDataRepository extends JpaRepository<MarksData, String> {
    boolean existsByRegNo(String regNo);
}
