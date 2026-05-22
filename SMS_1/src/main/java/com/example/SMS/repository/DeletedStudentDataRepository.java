package com.example.SMS.repository;

import com.example.SMS.model.DeletedStudentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeletedStudentDataRepository extends JpaRepository<DeletedStudentData, Long> {
    boolean existsByRegNo(String regNo);
}
