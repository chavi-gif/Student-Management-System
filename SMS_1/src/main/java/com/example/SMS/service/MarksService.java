package com.example.SMS.service;

import com.example.SMS.model.*;
import com.example.SMS.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MarksService {

    @Autowired
    private MarksDataRepository marksRepo;

    @Autowired
    private StudentDataRepository studentRepo;

    /**
     * Add marks – student must exist in StudentData (not deleted)
     */
    public String addMarks(MarksData marks) {
        String regNo = marks.getRegNo();

        if (!studentRepo.existsByRegNo(regNo)) {
            return "Student with RegNo " + regNo + " does not exist. Cannot add marks.";
        }
        if (marksRepo.existsByRegNo(regNo)) {
            return "Marks for RegNo " + regNo + " already exist. Use update instead.";
        }
        marksRepo.save(marks);
        return "Marks added successfully!";
    }

    /**
     * Get marks for one student
     */
    public Optional<MarksData> findByRegNo(String regNo) {
        return marksRepo.findById(regNo);
    }

    /**
     * Build a MarksReportDTO for a single student
     */
    public MarksReportDTO buildReport(MarksData m) {
        String name = studentRepo.findById(m.getRegNo())
                .map(StudentData::getName)
                .orElse("Unknown");
        return new MarksReportDTO(
                m.getRegNo(), name,
                m.getQuiz01(), m.getQuiz02(), m.getQuiz03(),
                m.getMid(), m.getEnd(),
                m.getTotal(), m.getGrade()
        );
    }

    /**
     * Get all marks as report DTOs
     */
    public List<MarksReportDTO> getAllMarksReport() {
        return marksRepo.findAll()
                .stream()
                .map(this::buildReport)
                .collect(Collectors.toList());
    }

    /**
     * Get individual report
     */
    public MarksReportDTO getIndividualReport(String regNo) {
        Optional<MarksData> opt = marksRepo.findById(regNo);
        return opt.map(this::buildReport).orElse(null);
    }
}
