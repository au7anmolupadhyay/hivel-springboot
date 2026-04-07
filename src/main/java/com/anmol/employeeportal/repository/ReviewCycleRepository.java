package com.anmol.employeeportal.repository;

import com.anmol.employeeportal.model.ReviewCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewCycleRepository extends JpaRepository<ReviewCycle, Long> {
}