    package com.anmol.employeeportal.model;

    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    import jakarta.persistence.*;
    import lombok.Data;
    import com.fasterxml.jackson.annotation.JsonIgnore;

    @Entity
    @Data
    public class Goal {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "employee_id", nullable = false)
        @JsonIgnore
        private Employee employee;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "review_cycle_id", nullable = false)
        @JsonIgnoreProperties({"goals", "reviews"})
        private ReviewCycle reviewCycle;

        @Column(nullable = false)
        private String title;

        @Column(nullable = false)
        private String status; // pending, completed, missed

    }