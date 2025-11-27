package myfirst.example.plt.service;

import myfirst.example.plt.entity.Employee;
import myfirst.example.plt.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeService {

    private final EmployeeRepository employeeRepository;

    public EmployeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }


    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public List<Employee> findAll(String name, String lastName) {
        return employeeRepository.findAllByNameAndLastName(name, lastName);
    }

    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}
