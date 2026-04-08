
package com.anmol.employeeportal.service;

import com.anmol.employeeportal.model.PerformanceReview;
import com.anmol.employeeportal.model.Employee;
import com.anmol.employeeportal.model.ReviewCycle;
import com.anmol.employeeportal.repository.PerformanceReviewRepository;
import com.anmol.employeeportal.dto.request.ReviewRequest;
import com.anmol.employeeportal.dto.response.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class PerformanceReviewService {
    @Autowired
    private PerformanceReviewRepository performanceReviewRepository;

    @Transactional
    public ReviewResponse submitReview(ReviewRequest dto, Employee employee, ReviewCycle cycle) {
        PerformanceReview review = ReviewMapper.toEntity(dto, employee, cycle);
        PerformanceReview saved = performanceReviewRepository.save(review);
        return ReviewMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByEmployee(Employee employee) {
        return performanceReviewRepository.findByEmployee(employee)
                .stream()
                .map(ReviewMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByReviewCycle(ReviewCycle reviewCycle) {
        return performanceReviewRepository.findByReviewCycle(reviewCycle)
                .stream()
                .map(ReviewMapper::toResponse)
                .toList();
    }

    // Dedicated Mapper class for PerformanceReview <-> DTO
    private static class ReviewMapper {
        private static PerformanceReview toEntity(ReviewRequest dto, Employee employee, ReviewCycle cycle) {
            PerformanceReview review = new PerformanceReview();
            review.setEmployee(employee);
            review.setReviewCycle(cycle);
            review.setRating(dto.getRating());
            review.setReviewerNotes(dto.getReviewerNotes());
            review.setSubmittedAt(java.time.LocalDateTime.now());
            return review;
        }

        private static ReviewResponse toResponse(PerformanceReview review) {
            ReviewResponse resp = new ReviewResponse();
            resp.setId(review.getId());
            resp.setRating(review.getRating());
            resp.setReviewerNotes(review.getReviewerNotes());
            resp.setSubmittedAt(review.getSubmittedAt());
            resp.setCycleName(review.getReviewCycle() != null ? review.getReviewCycle().getName() : null);
            return resp;
        }
    }
}
