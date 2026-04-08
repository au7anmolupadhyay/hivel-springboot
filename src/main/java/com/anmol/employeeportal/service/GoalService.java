package com.anmol.employeeportal.service;

import com.anmol.employeeportal.enums.GoalStatus;
import com.anmol.employeeportal.model.Goal;
import com.anmol.employeeportal.model.Employee;
import com.anmol.employeeportal.model.ReviewCycle;
import com.anmol.employeeportal.repository.GoalRepository;
import com.anmol.employeeportal.dto.request.GoalRequest;
import com.anmol.employeeportal.dto.response.GoalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GoalService {
    @Autowired
    private GoalRepository goalRepository;

    @Transactional
    public GoalResponse createGoal(GoalRequest request, Employee employee, ReviewCycle reviewCycle) {
        Goal goal = GoalMapper.toEntity(request, employee, reviewCycle);
        Goal saved = goalRepository.save(goal);
//        if (true) {
//            throw new RuntimeException("Test rollback");
//        }
        return GoalMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<GoalResponse> getGoalsByEmployeeAndCycle(Employee employee, ReviewCycle reviewCycle) {
        return goalRepository.findByEmployeeAndReviewCycle(employee, reviewCycle)
                .stream()
                .map(GoalMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<GoalResponse> getGoalsByReviewCycle(ReviewCycle reviewCycle) {
        return goalRepository.findByReviewCycle(reviewCycle)
                .stream()
                .map(GoalMapper::toResponse)
                .toList();
    }

    // Dedicated Mapper class for Goal <-> DTO
    private static class GoalMapper {

        private static Goal toEntity(GoalRequest dto, Employee employee, ReviewCycle reviewCycle) {
            Goal goal = new Goal();
            goal.setEmployee(employee);
            goal.setReviewCycle(reviewCycle);
            goal.setTitle(dto.getTitle());
            // Convert String to Enum, fallback to PENDING if invalid
            try {
                goal.setStatus(GoalStatus.valueOf(dto.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid goal status: " + dto.getStatus());
            }
            return goal;
        }

        private static GoalResponse toResponse(Goal goal) {
            GoalResponse resp = new GoalResponse();
            resp.setId(goal.getId());
            resp.setTitle(goal.getTitle());
            // Convert Enum to String for response
            resp.setStatus(goal.getStatus() != null ? goal.getStatus().name() : null);
            resp.setEmployeeName(goal.getEmployee() != null ? goal.getEmployee().getName() : null);
            resp.setReviewCycleName(goal.getReviewCycle() != null ? goal.getReviewCycle().getName() : null);
            return resp;
        }
    }
}