package com.anmol.employeeportal.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class GoalRequest {
    private Long employeeId;
    private Long reviewCycleId;

    @NotBlank
    private String title;

    @NotNull
    private String status; // pending, completed, missed
}
