package com.anmol.employeeportal.dto.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ReviewCycleResponse {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
}
