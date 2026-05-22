package com.example.SMS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "MarksData")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarksData {

    @Id
    @Column(name = "RegNo", nullable = false, unique = true, length = 20)
    private String regNo;

    @Column(name = "Quiz01", nullable = false)
    @NotNull(message = "Quiz 01 marks required")
    @Min(value = 0, message = "Marks cannot be negative")
    @Max(value = 100, message = "Marks cannot exceed 100")
    private Double quiz01;

    @Column(name = "Quiz02", nullable = false)
    @NotNull(message = "Quiz 02 marks required")
    @Min(value = 0, message = "Marks cannot be negative")
    @Max(value = 100, message = "Marks cannot exceed 100")
    private Double quiz02;

    @Column(name = "Quiz03", nullable = false)
    @NotNull(message = "Quiz 03 marks required")
    @Min(value = 0, message = "Marks cannot be negative")
    @Max(value = 100, message = "Marks cannot exceed 100")
    private Double quiz03;

    @Column(name = "Mid", nullable = false)
    @NotNull(message = "Mid semester marks required")
    @Min(value = 0, message = "Marks cannot be negative")
    @Max(value = 100, message = "Marks cannot exceed 100")
    private Double mid;

    @Column(name = "End", nullable = false)
    @NotNull(message = "End semester marks required")
    @Min(value = 0, message = "Marks cannot be negative")
    @Max(value = 100, message = "Marks cannot exceed 100")
    private Double end;

    /**
     * Total = 10% from best 02 quizzes + 20% from mid + 70% from end
     * Best 2 quizzes → each contributes 5% (10% total)
     */
    @Transient
    public double getTotal() {
        double[] quizzes = {quiz01, quiz02, quiz03};
        java.util.Arrays.sort(quizzes);
        // Best 2 = quizzes[1] and quizzes[2]
        double bestTwo = quizzes[1] + quizzes[2];
        return Math.round(((bestTwo * 0.10 / 2) + (mid * 0.20) + (end * 0.70)) * 100.0) / 100.0;
    }

    @Transient
    public String getGrade() {
        double total = getTotal();
        if (total >= 85) return "A+";
        else if (total >= 75) return "A";
        else if (total >= 70) return "A-";
        else if (total >= 65) return "B+";
        else if (total >= 60) return "B";
        else if (total >= 55) return "B-";
        else if (total >= 50) return "C+";
        else if (total >= 45) return "C";
        else if (total >= 40) return "C-";
        else if (total >= 35) return "D+";
        else if (total >= 30) return "D";
        else return "F";
    }
}
