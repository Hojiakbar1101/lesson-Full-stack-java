package myfirst.example.plt.web.Controller;

import myfirst.example.plt.entity.Employee;
import myfirst.example.plt.service.EmployeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeResource {

    private final EmployeService employeeService;

    public EmployeeResource(EmployeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> save(@RequestBody Employee employee) {
        Employee result = employeeService.save(employee);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/employee")
    public ResponseEntity<Employee> update(@RequestBody Employee employee) {
        if (employee.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Employee result = employeeService.save(employee);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.findAll();
        return ResponseEntity.ok(employees);
    }


    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> findById(@PathVariable Long id) {
        Employee result = employeeService.findById(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> getAll(@RequestParam String name,
                                                 @RequestParam String lastName) {
        List<Employee> result = employeeService.findAll(name, lastName);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.ok("Employee deleted successfully!");
    }
}
