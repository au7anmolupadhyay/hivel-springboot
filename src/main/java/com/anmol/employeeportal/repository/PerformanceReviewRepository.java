package com.anmol.employeeportal.repository;

import com.anmol.employeeportal.model.PerformanceReview;
import com.anmol.employeeportal.model.Employee;
import com.anmol.employeeportal.model.ReviewCycle;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long> {
    @EntityGraph(attributePaths = {"employee", "reviewCycle"})
    List<PerformanceReview> findByEmployee(Employee employee);

    @EntityGraph(attributePaths = {"employee", "reviewCycle"})
    List<PerformanceReview> findByEmployeeAndReviewCycle(Employee employee, ReviewCycle reviewCycle);

    @EntityGraph(attributePaths = {"employee", "reviewCycle"})
    List<PerformanceReview> findByReviewCycle(ReviewCycle reviewCycle);
    @Query("""
        SELECT AVG(pr.rating)
        FROM PerformanceReview pr
        WHERE pr.reviewCycle.id = :cycleId
        """)
    Double getAverageRatingByCycleId(Long cycleId);
    @Query("""
SELECT pr.employee
FROM PerformanceReview pr
WHERE pr.reviewCycle.id = :cycleId
GROUP BY pr.employee
ORDER BY AVG(pr.rating) DESC
""")
    List<Employee> findTopPerformerInCycle(Long cycleId);

    @Query("""
SELECT pr.employee
FROM PerformanceReview pr
GROUP BY pr.employee
HAVING AVG(pr.rating) >= :minRating
""")
    List<Employee> findEmployeesWithMinRating(Double minRating);
}