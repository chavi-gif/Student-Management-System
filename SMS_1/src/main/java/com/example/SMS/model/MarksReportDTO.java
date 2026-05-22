package com.example.SMS.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarksReportDTO {
    private String regNo;
    private String name;
    private Double quiz01;
    private Double quiz02;
    private Double quiz03;
    private Double mid;
    private Double end;
    private Double total;
    private String grade;
}
