package com.example.SMS.repository;

import com.example.SMS.model.DeletedMarksData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeletedMarksDataRepository extends JpaRepository<DeletedMarksData, Long> {
}
