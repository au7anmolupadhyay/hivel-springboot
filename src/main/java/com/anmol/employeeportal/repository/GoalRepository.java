package com.anmol.employeeportal.repository;

import com.anmol.employeeportal.model.Goal;
import com.anmol.employeeportal.model.Employee;
import com.anmol.employeeportal.model.ReviewCycle;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    @EntityGraph(attributePaths = {"employee", "reviewCycle"})
    List<Goal> findByEmployeeAndReviewCycle(Employee employee, ReviewCycle reviewCycle);

    @EntityGraph(attributePaths = {"employee", "reviewCycle"})
    List<Goal> findByReviewCycle(ReviewCycle reviewCycle);
    @Query("SELECT g.status, COUNT(g.id) FROM Goal g WHERE g.reviewCycle.id = :cycleId GROUP BY g.status")
    List<Object[]> countGoalsByStatus(Long cycleId);
}