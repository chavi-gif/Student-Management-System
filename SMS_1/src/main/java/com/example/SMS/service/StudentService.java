package com.example.SMS.service;

import com.example.SMS.model.*;
import com.example.SMS.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentDataRepository studentRepo;

    @Autowired
    private DeletedStudentDataRepository deletedStudentRepo;

    @Autowired
    private MarksDataRepository marksRepo;

    @Autowired
    private DeletedMarksDataRepository deletedMarksRepo;

    /**
     * Check if RegNo exists in either StudentData or DeletedStudentData
     */
    public boolean isRegNoTaken(String regNo) {
        return studentRepo.existsByRegNo(regNo) || deletedStudentRepo.existsByRegNo(regNo);
    }

    /**
     * Add a new student after validating RegNo uniqueness
     */
    public String addStudent(StudentData student) {
        if (isRegNoTaken(student.getRegNo())) {
            return "Registration number already exists. Cannot reuse.";
        }
        studentRepo.save(student);
        return "Student added successfully!";
    }

    /**
     * Find student by RegNo
     */
    public Optional<StudentData> findByRegNo(String regNo) {
        return studentRepo.findById(regNo);
    }

    /**
     * Update student name
     */
    public String updateName(String regNo, String newName) {
        Optional<StudentData> opt = studentRepo.findById(regNo);
        if (opt.isEmpty()) return "Student not found.";
        StudentData s = opt.get();
        s.setName(newName);
        studentRepo.save(s);
        return "Name updated successfully!";
    }

    /**
     * Update student date of birth
     */
    public String updateDob(String regNo, java.time.LocalDate newDob) {
        Optional<StudentData> opt = studentRepo.findById(regNo);
        if (opt.isEmpty()) return "Student not found.";
        StudentData s = opt.get();
        s.setDob(newDob);
        studentRepo.save(s);
        return "Date of Birth updated successfully!";
    }

    /**
     * Update student contact number
     */
    public String updateContact(String regNo, String newContact) {
        Optional<StudentData> opt = studentRepo.findById(regNo);
        if (opt.isEmpty()) return "Student not found.";
        StudentData s = opt.get();
        s.setContact(newContact);
        studentRepo.save(s);
        return "Contact number updated successfully!";
    }

    /**
     * Delete a student: move to DeletedStudentData, move marks to DeletedMarksData
     */
    @Transactional
    public String deleteStudent(String regNo) {
        Optional<StudentData> opt = studentRepo.findById(regNo);
        if (opt.isEmpty()) return "Student not found.";

        StudentData student = opt.get();

        // Archive student
        deletedStudentRepo.save(DeletedStudentData.fromStudent(student));

        // Archive marks if they exist
        Optional<MarksData> marksOpt = marksRepo.findById(regNo);
        marksOpt.ifPresent(marks -> {
            deletedMarksRepo.save(DeletedMarksData.fromMarks(marks));
            marksRepo.delete(marks);
        });

        // Remove student
        studentRepo.delete(student);
        return "Student deleted successfully and data archived.";
    }

    public boolean existsInActive(String regNo) {
        return studentRepo.existsByRegNo(regNo);
    }
}
