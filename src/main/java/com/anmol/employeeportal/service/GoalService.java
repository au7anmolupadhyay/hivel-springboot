package com.anmol.employeeportal.service;

import com.anmol.employeeportal.model.Goal;
import com.anmol.employeeportal.model.Employee;
import com.anmol.employeeportal.model.ReviewCycle;
import com.anmol.employeeportal.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {
    @Autowired
    private GoalRepository goalRepository;

    public List<Goal> getGoalsByEmployeeAndCycle(Employee employee, ReviewCycle reviewCycle) {
        return goalRepository.findByEmployeeAndReviewCycle(employee, reviewCycle);
    }

    public List<Goal> getGoalsByReviewCycle(ReviewCycle reviewCycle) {
        return goalRepository.findByReviewCycle(reviewCycle);
    }
}