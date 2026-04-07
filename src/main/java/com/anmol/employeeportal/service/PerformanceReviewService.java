package com.anmol.employeeportal.service;

import com.anmol.employeeportal.model.PerformanceReview;
import com.anmol.employeeportal.model.Employee;
import com.anmol.employeeportal.model.ReviewCycle;
import com.anmol.employeeportal.repository.PerformanceReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceReviewService {
    @Autowired
    private PerformanceReviewRepository performanceReviewRepository;

    public PerformanceReview submitReview(PerformanceReview review) {
        return performanceReviewRepository.save(review);
    }

    public List<PerformanceReview> getReviewsByEmployee(Employee employee) {
        return performanceReviewRepository.findByEmployee(employee);
    }

    public List<PerformanceReview> getReviewsByReviewCycle(ReviewCycle reviewCycle) {
        return performanceReviewRepository.findByReviewCycle(reviewCycle);
    }
}