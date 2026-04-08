package com.anmol.employeeportal.dto.request;

import lombok.Data;

@Data
public class ReviewRequest {
    private Long employeeId;
    private Long cycleId;
    private int rating;
    private String reviewerNotes;
}