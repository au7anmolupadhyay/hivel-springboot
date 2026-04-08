package com.anmol.employeeportal.dto.request;

import lombok.Data;

@Data
public class GoalRequest {
    private Long employeeId;
    private Long reviewCycleId;
    private String title;
    private String status; // pending, completed, missed
}
