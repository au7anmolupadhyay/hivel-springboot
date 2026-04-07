package com.anmol.employeeportal.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class ReviewCycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @OneToMany(mappedBy = "reviewCycle", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<PerformanceReview> reviews;

    @OneToMany(mappedBy = "reviewCycle", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Goal> goals;
}