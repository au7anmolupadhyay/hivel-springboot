package com.anmol.employeeportal.controller;

import com.anmol.employeeportal.model.ReviewCycle;
import com.anmol.employeeportal.model.PerformanceReview;
import com.anmol.employeeportal.model.Goal;
import com.anmol.employeeportal.service.ReviewCycleService;
import com.anmol.employeeportal.service.PerformanceReviewService;
import com.anmol.employeeportal.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/cycles")
@RequiredArgsConstructor
public class ReviewCycleController {

    private final ReviewCycleService cycleService;
    private final PerformanceReviewService reviewService;
    private final GoalService goalService;

    @GetMapping("/{id}/summary")
    public ResponseEntity<?> getCycleSummary(@PathVariable Long id) {
        Optional<ReviewCycle> cycleOpt = cycleService.getReviewCycle(id);
        if (cycleOpt.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(cycleService.getCycleSummary(id));
    }
}