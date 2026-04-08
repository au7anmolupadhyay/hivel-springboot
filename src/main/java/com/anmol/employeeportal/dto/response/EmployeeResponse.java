package com.anmol.employeeportal.dto.response;

import lombok.Data;

@Data
public class EmployeeResponse {
    private Long id;
    private String name;
    private String department;
    private String role;
}