package com.anmol.employeeportal.repository;

import com.anmol.employeeportal.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartment(String department);


    @Query("SELECT e FROM Employee e " +
        "LEFT JOIN PerformanceReview pr ON pr.employee = e " +
        "WHERE (:department IS NULL OR e.department = :department) " +
        "GROUP BY e " +
        "HAVING (:minRating IS NULL OR AVG(pr.rating) >= :minRating)")
    Page<Employee> findEmployeesByDepartmentAndMinRating(@Param("department") String department, @Param("minRating") Double minRating, Pageable pageable);

    Page<Employee> findAll(Pageable pageable);
}