package fr.hocem.employeemanagement.service;

import fr.hocem.employeemanagement.exception.ResourceNotFoundException;
import fr.hocem.employeemanagement.model.Employee;
import fr.hocem.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee findEmployeeById(Long id) throws ResourceNotFoundException {
        return employeeRepository.findEmployeeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " Not found"));
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }
}
