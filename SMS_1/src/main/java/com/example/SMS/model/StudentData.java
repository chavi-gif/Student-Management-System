package com.example.SMS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "StudentData")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentData {

    @Id
    @Column(name = "RegNo", nullable = false, unique = true, length = 20)
    private String regNo;

    @Column(name = "Name", nullable = false, length = 100)
    @NotBlank(message = "Name is required")
    private String name;

    @Column(name = "DoB", nullable = false)
    @NotNull(message = "Date of Birth is required")
    private LocalDate dob;

    @Column(name = "Email", nullable = false, length = 100)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "Contact", nullable = false, length = 20)
    @NotBlank(message = "Contact number is required")
    private String contact;
}
