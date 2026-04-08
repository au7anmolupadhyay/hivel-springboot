package com.anmol.employeeportal.service;

import com.anmol.employeeportal.model.Employee;
import com.anmol.employeeportal.repository.EmployeeRepository;
import com.anmol.employeeportal.dto.request.EmployeeRequest;
import com.anmol.employeeportal.dto.response.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Transactional
    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        Employee employee = EmployeeMapper.toEntity(employeeRequest);
        Employee saved = employeeRepository.save(employee);
        return EmployeeMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Optional<Employee> getEmployee(Long id) {
        return employeeRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponse> filterEmployees(String department, Double minRating) {
        // fallback to old behavior for backward compatibility
        return employeeRepository.findEmployeesByDepartmentAndMinRating(department, minRating, Pageable.unpaged())
                .stream().map(EmployeeMapper::toResponse).toList();
    }


    @Transactional(readOnly = true)
    public List<EmployeeResponse> filterEmployees(String department, Double minRating, Pageable pageable) {
        return employeeRepository.findEmployeesByDepartmentAndMinRating(department, minRating, pageable)
                .stream().map(EmployeeMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable)
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