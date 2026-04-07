package com.anmol.employeeportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

@Entity
@Data
public class PerformanceReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonIgnoreProperties({"reviews", "goals"})
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_cycle_id", nullable = false)
    @JsonIgnoreProperties({"reviews", "goals"})
    private ReviewCycle reviewCycle;

    @Column(nullable = false)
    @Min(1)
    @Max(5)
    private int rating;

    @Column(name = "reviewer_notes")
    private String reviewerNotes;

    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;
}