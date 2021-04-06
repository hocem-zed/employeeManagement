package fr.hocem.employeemanagement.controller;

import fr.hocem.employeemanagement.exception.ResourceNotFoundException;
import fr.hocem.employeemanagement.model.Employee;
import fr.hocem.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    // get employees
    @GetMapping("employees")
    public List<Employee> getAllEmployee() {
        return this.employeeRepository.findAll();
    }

    // get employee by id
    @GetMapping("employees/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Employee employee = this.employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Not Found for this Id: " + id));

        return ResponseEntity.ok().body(employee);

    }

    // save employee
    @PostMapping("employees")
    public Employee saveEmployee(@RequestBody Employee employee) {
        return this.employeeRepository.save(employee);
    }

    // update employee
    @PutMapping("employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id,
                                                   @Valid @RequestBody Employee employeeDetails)
            throws ResourceNotFoundException {
        Employee employee = this.employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee nor found for this id: " + id));

        employee.setEmail(employeeDetails.getEmail());
        employee.setLastname(employeeDetails.getLastname());
        employee.setFirstname(employeeDetails.getFirstname());
        final Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    // delete employee
    @DeleteMapping("/employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
