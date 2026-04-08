package com.anmol.employeeportal.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReviewResponse {
    private Long id;
    private int rating;
    private String reviewerNotes;
    private LocalDateTime submittedAt;
    private String cycleName;
}