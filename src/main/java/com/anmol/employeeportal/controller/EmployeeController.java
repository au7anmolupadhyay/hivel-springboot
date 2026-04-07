package com.anmol.employeeportal.controller;

import com.anmol.employeeportal.model.Employee;
import com.anmol.employeeportal.model.PerformanceReview;
import com.anmol.employeeportal.model.ReviewCycle;
import com.anmol.employeeportal.service.EmployeeService;
import com.anmol.employeeportal.service.PerformanceReviewService;
import com.anmol.employeeportal.service.ReviewCycleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final PerformanceReviewService reviewService;
    private final ReviewCycleService cycleService;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee saved = employeeService.createEmployee(employee);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<PerformanceReview>> getEmployeeReviews(@PathVariable Long id) {
        Optional<Employee> empOpt = employeeService.getEmployee(id);
        if (empOpt.isEmpty()) return ResponseEntity.notFound().build();
        List<PerformanceReview> reviews = reviewService.getReviewsByEmployee(empOpt.get());
        return ResponseEntity.ok(reviews);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> filterEmployees(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Double minRating) {
        List<Employee> employees = (department != null) ? employeeService.getEmployeesByDepartment(department) : employeeService.getAllEmployees();
        if (minRating != null) {
            return ResponseEntity.ok(employeeService.getEmployeesWithMinRating(minRating));
        }
        return ResponseEntity.ok(employees);
    }
}