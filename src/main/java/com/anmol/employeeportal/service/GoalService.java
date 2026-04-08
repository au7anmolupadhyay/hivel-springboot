package com.anmol.employeeportal.service;

import com.anmol.employeeportal.model.Goal;
import com.anmol.employeeportal.model.Employee;
import com.anmol.employeeportal.model.ReviewCycle;
import com.anmol.employeeportal.repository.GoalRepository;
import com.anmol.employeeportal.dto.request.GoalRequest;
import com.anmol.employeeportal.dto.response.GoalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {
    @Autowired
    private GoalRepository goalRepository;

    public GoalResponse createGoal(GoalRequest request, Employee employee, ReviewCycle reviewCycle) {
        Goal goal = GoalMapper.toEntity(request, employee, reviewCycle);
        Goal saved = goalRepository.save(goal);
        return GoalMapper.toResponse(saved);
    }

    public List<GoalResponse> getGoalsByEmployeeAndCycle(Employee employee, ReviewCycle reviewCycle) {
        return goalRepository.findByEmployeeAndReviewCycle(employee, reviewCycle)
                .stream()
                .map(GoalMapper::toResponse)
                .toList();
    }

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
            goal.setStatus(dto.getStatus());
            return goal;
        }

        private static GoalResponse toResponse(Goal goal) {
            GoalResponse resp = new GoalResponse();
            resp.setId(goal.getId());
            resp.setTitle(goal.getTitle());
            resp.setStatus(goal.getStatus());
            resp.setEmployeeName(goal.getEmployee() != null ? goal.getEmployee().getName() : null);
            resp.setReviewCycleName(goal.getReviewCycle() != null ? goal.getReviewCycle().getName() : null);
            return resp;
        }
    }
}