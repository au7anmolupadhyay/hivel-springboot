package com.anmol.employeeportal.dto.request;

import lombok.Data;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class ReviewRequest {
    @NotNull
    private Long employeeId;

    @NotNull
    private Long cycleId;

    @Min(1)
    @Max(5)
    private int rating;

    private String reviewerNotes;
}