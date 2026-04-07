package com.anmol.employeeportal.controller;

import com.anmol.employeeportal.model.PerformanceReview;
import com.anmol.employeeportal.model.Employee;
import com.anmol.employeeportal.model.ReviewCycle;
import com.anmol.employeeportal.service.EmployeeService;
import com.anmol.employeeportal.service.PerformanceReviewService;
import com.anmol.employeeportal.service.ReviewCycleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class PerformanceReviewController {

    private final PerformanceReviewService reviewService;
    private final EmployeeService employeeService;
    private final ReviewCycleService cycleService;

    @PostMapping
    public ResponseEntity<?> submitReview(@RequestBody PerformanceReview review) {

        if (review.getEmployee() == null || review.getEmployee().getId() == null) {
            return ResponseEntity.badRequest().body("Employee ID is required");
        }

        if (review.getReviewCycle() == null || review.getReviewCycle().getId() == null) {
            return ResponseEntity.badRequest().body("Review Cycle ID is required");
        }

        Optional<Employee> empOpt = employeeService.getEmployee(review.getEmployee().getId());
        Optional<ReviewCycle> cycleOpt = cycleService.getReviewCycle(review.getReviewCycle().getId());

        if (empOpt.isEmpty() || cycleOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid employee or review cycle");
        }

        review.setEmployee(empOpt.get());
        review.setReviewCycle(cycleOpt.get());
        review.setSubmittedAt(LocalDateTime.now());

        try {
            PerformanceReview saved = reviewService.submitReview(review);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace(); // 🔥 check console
            return ResponseEntity.internalServerError().body("Error saving review");
        }
    }
}