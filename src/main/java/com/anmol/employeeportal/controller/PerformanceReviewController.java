package com.anmol.employeeportal.controller;

import com.anmol.employeeportal.dto.request.ReviewRequest;
import com.anmol.employeeportal.dto.response.ReviewResponse;
import com.anmol.employeeportal.model.Employee;
import com.anmol.employeeportal.model.ReviewCycle;
import com.anmol.employeeportal.service.EmployeeService;
import com.anmol.employeeportal.service.PerformanceReviewService;
import com.anmol.employeeportal.service.ReviewCycleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class PerformanceReviewController {

    private final PerformanceReviewService reviewService;
    private final EmployeeService employeeService;
    private final ReviewCycleService cycleService;

    @PostMapping
    public ResponseEntity<?> submitReview(@Valid @RequestBody ReviewRequest reviewRequest) {
        Optional<Employee> empOpt = employeeService.getEmployee(reviewRequest.getEmployeeId());
        Optional<ReviewCycle> cycleOpt = cycleService.getReviewCycle(reviewRequest.getCycleId());
        if (empOpt.isEmpty() || cycleOpt.isEmpty()) {
            throw new com.anmol.employeeportal.exception.EntityNotFoundException("Invalid employee or review cycle");
        }
        ReviewResponse response = reviewService.submitReview(reviewRequest, empOpt.get(), cycleOpt.get());
        return ResponseEntity.ok(response);
    }
}