package com.example.SMS.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "DeletedMarksData")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeletedMarksData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "RegNo", nullable = false, length = 20)
    private String regNo;

    @Column(name = "Quiz01")
    private Double quiz01;

    @Column(name = "Quiz02")
    private Double quiz02;

    @Column(name = "Quiz03")
    private Double quiz03;

    @Column(name = "Mid")
    private Double mid;

    @Column(name = "End")
    private Double end;

    @Column(name = "DeletedAt")
    private LocalDateTime deletedAt;

    public static DeletedMarksData fromMarks(MarksData m) {
        DeletedMarksData d = new DeletedMarksData();
        d.setRegNo(m.getRegNo());
        d.setQuiz01(m.getQuiz01());
        d.setQuiz02(m.getQuiz02());
        d.setQuiz03(m.getQuiz03());
        d.setMid(m.getMid());
        d.setEnd(m.getEnd());
        d.setDeletedAt(LocalDateTime.now());
        return d;
    }
}
