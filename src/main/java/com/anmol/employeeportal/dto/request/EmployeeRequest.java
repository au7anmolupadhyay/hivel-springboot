package com.anmol.employeeportal.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class EmployeeRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String department;

    @NotBlank
    private String role;

    @NotNull
    private LocalDate joiningDate;
}