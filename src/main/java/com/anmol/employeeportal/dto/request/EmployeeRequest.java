package com.anmol.employeeportal.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeRequest {
    private String name;
    private String department;
    private String role;
    private LocalDate joiningDate;
}