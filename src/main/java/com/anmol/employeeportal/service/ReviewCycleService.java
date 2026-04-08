
package com.anmol.employeeportal.service;

import com.anmol.employeeportal.enums.GoalStatus;
import com.anmol.employeeportal.model.Employee;
import com.anmol.employeeportal.model.ReviewCycle;
import com.anmol.employeeportal.repository.GoalRepository;
import com.anmol.employeeportal.repository.PerformanceReviewRepository;
import com.anmol.employeeportal.repository.ReviewCycleRepository;
import com.anmol.employeeportal.dto.request.ReviewCycleRequest;
import com.anmol.employeeportal.dto.response.ReviewCycleResponse;
import com.anmol.employeeportal.dto.response.CycleSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewCycleService {
    private final ReviewCycleRepository reviewCycleRepository;
    private final PerformanceReviewRepository performanceReviewRepository;
    private final GoalRepository goalRepository;

    @Transactional(readOnly = true)
    public Optional<ReviewCycle> getReviewCycle(Long id) {
        return reviewCycleRepository.findById(id);
    }

    @Transactional
    public ReviewCycleResponse createReviewCycle(ReviewCycleRequest request) {
        ReviewCycle cycle = ReviewCycleMapper.toEntity(request);
        ReviewCycle saved = reviewCycleRepository.save(cycle);
        return ReviewCycleMapper.toResponse(saved);
    }
    @Transactional(readOnly = true)
    public CycleSummaryResponse getCycleSummary(Long cycleId) {

        Double avg = performanceReviewRepository.getAverageRatingByCycleId(cycleId);
        if (avg == null) avg = 0.0;

        List<Employee> topList = performanceReviewRepository.findTopPerformerInCycle(cycleId);
        String topPerformer = topList.isEmpty() ? null : topList.get(0).getName();


        List<Object[]> goalStats = goalRepository.countGoalsByStatus(cycleId);

        long completed = 0;
        long missed = 0;

        for (Object[] row : goalStats) {
            GoalStatus status = (GoalStatus) row[0];
            Number countNum = (Number) row[1];
            long count = countNum.longValue();

            if (status == GoalStatus.COMPLETED) completed = count;
            if (status == GoalStatus.MISSED) missed = count;
        }

        return new CycleSummaryResponse(avg, topPerformer, completed, missed);
    }

    // Dedicated Mapper class for ReviewCycle <-> DTO
    private static class ReviewCycleMapper {
        private static ReviewCycle toEntity(ReviewCycleRequest dto) {
            ReviewCycle cycle = new ReviewCycle();
            cycle.setName(dto.getName());
            cycle.setStartDate(dto.getStartDate());
            cycle.setEndDate(dto.getEndDate());
            return cycle;
        }

        private static ReviewCycleResponse toResponse(ReviewCycle cycle) {
            ReviewCycleResponse resp = new ReviewCycleResponse();
            resp.setId(cycle.getId());
            resp.setName(cycle.getName());
            resp.setStartDate(cycle.getStartDate());
            resp.setEndDate(cycle.getEndDate());
            return resp;
        }
    }
}