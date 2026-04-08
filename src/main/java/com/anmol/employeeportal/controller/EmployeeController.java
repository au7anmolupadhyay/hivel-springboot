package com.anmol.employeeportal.controller;

import com.anmol.employeeportal.model.Employee;
import com.anmol.employeeportal.service.EmployeeService;
import com.anmol.employeeportal.service.PerformanceReviewService;
import com.anmol.employeeportal.dto.request.EmployeeRequest;
import com.anmol.employeeportal.dto.response.EmployeeResponse;
import com.anmol.employeeportal.dto.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final PerformanceReviewService reviewService;

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        EmployeeResponse response = employeeService.createEmployee(employeeRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewResponse>> getEmployeeReviews(@PathVariable Long id) {
        Optional<Employee> empOpt = employeeService.getEmployee(id);
        if (empOpt.isEmpty()) return ResponseEntity.notFound().build();
    List<ReviewResponse> responseList = reviewService.getReviewsByEmployee(empOpt.get());
        return ResponseEntity.ok(responseList);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> filterEmployees(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Double minRating,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort
    ) {
        Pageable pageable = (sort != null && !sort.isEmpty())
                ? PageRequest.of(page, size, Sort.by(sort))
                : PageRequest.of(page, size);

        if (department == null && minRating == null) {
            return ResponseEntity.ok(employeeService.getAllEmployees(pageable));
        }
        return ResponseEntity.ok(employeeService.filterEmployees(department, minRating, pageable));
    }
}