package com.anmol.employeeportal.service;

import com.anmol.employeeportal.model.Employee;
import com.anmol.employeeportal.model.ReviewCycle;
import com.anmol.employeeportal.repository.GoalRepository;
import com.anmol.employeeportal.repository.PerformanceReviewRepository;
import com.anmol.employeeportal.repository.ReviewCycleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Optional<ReviewCycle> getReviewCycle(Long id) {
        return reviewCycleRepository.findById(id);
    }
    public Map<String, Object> getCycleSummary(Long cycleId) {

        Double avg = performanceReviewRepository.getAverageRatingByCycleId(cycleId);

        List<Employee> topList = performanceReviewRepository.findTopPerformerInCycle(cycleId);
        String topPerformer = topList.isEmpty() ? null : topList.get(0).getName();

        List<Object[]> goalStats = goalRepository.countGoalsByStatus(cycleId);

        long completed = 0;
        long missed = 0;

        for (Object[] row : goalStats) {
            String status = (String) row[0];
            Long count = (Long) row[1];

            if ("COMPLETED".equalsIgnoreCase(status)) completed = count;
            if ("MISSED".equalsIgnoreCase(status)) missed = count;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("averageRating", avg);
        result.put("topPerformer", topPerformer);
        result.put("completedGoals", completed);
        result.put("missedGoals", missed);

        return result;
    }
}