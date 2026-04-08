package com.anmol.employeeportal.controller;

import com.anmol.employeeportal.model.PerformanceReview;
import com.anmol.employeeportal.dto.request.ReviewRequest;
import com.anmol.employeeportal.dto.response.ReviewResponse;
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
    public ResponseEntity<?> submitReview(@RequestBody ReviewRequest reviewRequest) {
        if (reviewRequest.getEmployeeId() == null) {
            return ResponseEntity.badRequest().body("Employee ID is required");
        }
        if (reviewRequest.getCycleId() == null) {
            return ResponseEntity.badRequest().body("Review Cycle ID is required");
        }
        Optional<Employee> empOpt = employeeService.getEmployee(reviewRequest.getEmployeeId());
        Optional<ReviewCycle> cycleOpt = cycleService.getReviewCycle(reviewRequest.getCycleId());
        if (empOpt.isEmpty() || cycleOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid employee or review cycle");
        }
        try {
            ReviewResponse response = reviewService.submitReview(reviewRequest, empOpt.get(), cycleOpt.get());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error saving review");
        }
    }
}