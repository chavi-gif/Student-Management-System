package com.example.SMS.repository;

import com.example.SMS.model.StudentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDataRepository extends JpaRepository<StudentData, String> {
    boolean existsByRegNo(String regNo);
}
