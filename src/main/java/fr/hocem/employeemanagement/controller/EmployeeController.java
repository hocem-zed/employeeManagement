package fr.hocem.employeemanagement.controller;

import fr.hocem.employeemanagement.exception.ResourceNotFoundException;
import fr.hocem.employeemanagement.model.Employee;
import fr.hocem.employeemanagement.repository.EmployeeRepository;
import fr.hocem.employeemanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @GetMapping("/employees/version")
    public String getAppDetails() {
        return appName + " - " + appVersion;
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployee() {
        return this.employeeService.findAllEmployees();
    }

    // get employee by id
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(this.employeeService.findEmployeeById(id));
    }

    // save employee
    @PostMapping("/employees")
    public Employee saveEmployee(@RequestBody Employee employee) {
        return this.employeeService.addEmployee(employee);
    }

    // update employee
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id,
                                                   @Valid @RequestBody Employee employeeDetails)
            throws ResourceNotFoundException {
        Employee employee = this.employeeService.findEmployeeById(id);

        employee.setName(employeeDetails.getName());
        employee.setAge(employeeDetails.getAge());
        employee.setEmail(employeeDetails.getEmail());
        employee.setLocation(employeeDetails.getLocation());
        employee.setDepartment(employeeDetails.getDepartment());
        final Employee updatedEmployee = employeeService.addEmployee(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException {

        employeeService.deleteEmployee(employeeId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
