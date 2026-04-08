package com.anmol.employeeportal.controller;

import com.anmol.employeeportal.dto.request.GoalRequest;
import com.anmol.employeeportal.dto.response.GoalResponse;
import com.anmol.employeeportal.model.Employee;
import com.anmol.employeeportal.model.ReviewCycle;
import com.anmol.employeeportal.service.EmployeeService;
import com.anmol.employeeportal.service.GoalService;
import com.anmol.employeeportal.service.ReviewCycleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;
    private final EmployeeService employeeService;
    private final ReviewCycleService cycleService;

    @PostMapping
    public ResponseEntity<GoalResponse> createGoal(@Valid @RequestBody GoalRequest request) {

        Employee employee = employeeService.getEmployee(request.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        ReviewCycle cycle = cycleService.getReviewCycle(request.getReviewCycleId())
                .orElseThrow(() -> new IllegalArgumentException("Review cycle not found"));

        GoalResponse response = goalService.createGoal(request, employee, cycle);

        return ResponseEntity.ok(response);
    }
}