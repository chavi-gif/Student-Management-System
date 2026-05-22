package com.example.SMS.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "DeletedStudentData")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeletedStudentData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "RegNo", nullable = false, length = 20)
    private String regNo;

    @Column(name = "Name", length = 100)
    private String name;

    @Column(name = "DoB")
    private LocalDate dob;

    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "Contact", length = 20)
    private String contact;

    @Column(name = "DeletedAt")
    private LocalDateTime deletedAt;

    public static DeletedStudentData fromStudent(StudentData s) {
        DeletedStudentData d = new DeletedStudentData();
        d.setRegNo(s.getRegNo());
        d.setName(s.getName());
        d.setDob(s.getDob());
        d.setEmail(s.getEmail());
        d.setContact(s.getContact());
        d.setDeletedAt(LocalDateTime.now());
        return d;
    }
}
