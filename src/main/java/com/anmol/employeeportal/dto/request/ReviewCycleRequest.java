package com.anmol.employeeportal.dto.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ReviewCycleRequest {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
}
