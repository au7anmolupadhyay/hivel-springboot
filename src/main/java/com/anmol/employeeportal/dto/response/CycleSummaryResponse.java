package com.anmol.employeeportal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CycleSummaryResponse {
    private Double averageRating;
    private String topPerformer;
    private Long completedGoals;
    private Long missedGoals;
}