package com.anmol.employeeportal.dto.response;

import lombok.Data;

@Data
public class GoalResponse {
    private Long id;
    private String title;
    private String status;
    private String employeeName;
    private String reviewCycleName;
}
