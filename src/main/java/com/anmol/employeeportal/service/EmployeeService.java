package com.anmol.employeeportal.service;

import com.anmol.employeeportal.model.Employee;
import com.anmol.employeeportal.repository.EmployeeRepository;
import com.anmol.employeeportal.repository.PerformanceReviewRepository;
import com.anmol.employeeportal.dto.request.EmployeeRequest;
import com.anmol.employeeportal.dto.response.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PerformanceReviewRepository performanceReviewRepository;

    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        Employee employee = EmployeeMapper.toEntity(employeeRequest);
        Employee saved = employeeRepository.save(employee);
        return EmployeeMapper.toResponse(saved);
    }

    public Optional<Employee> getEmployee(Long id) {
        return employeeRepository.findById(id);
    }

    public List<EmployeeResponse> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department)
                .stream()
                .map(EmployeeMapper::toResponse)
                .toList();
    }

    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeMapper::toResponse)
                .toList();
    }

    public List<EmployeeResponse> getEmployeesWithMinRating(Double minRating) {
        return performanceReviewRepository.findEmployeesWithMinRating(minRating)
                .stream()
                .map(EmployeeMapper::toResponse)
                .toList();
    }

    // Dedicated Mapper class for Employee <-> DTO
    private static class EmployeeMapper {
        private static Employee toEntity(EmployeeRequest dto) {
            Employee employee = new Employee();
            employee.setName(dto.getName());
            employee.setDepartment(dto.getDepartment());
            employee.setRole(dto.getRole());
            employee.setJoiningDate(dto.getJoiningDate());
            return employee;
        }

        private static EmployeeResponse toResponse(Employee employee) {
            EmployeeResponse resp = new EmployeeResponse();
            resp.setId(employee.getId());
            resp.setName(employee.getName());
            resp.setDepartment(employee.getDepartment());
            resp.setRole(employee.getRole());
            return resp;
        }
    }
}