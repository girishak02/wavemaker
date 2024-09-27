package Service;

import Factory.RepositoryFactory;
import Model.Employee;
import Repository.EmployeeRepository;
import Exception.*; // Ensure this is imported
//import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(String employeeRepository) {
        this.employeeRepository = RepositoryFactory.getEmployeeRepository(employeeRepository);
    }

    public void addEmployee(Employee employee) throws DuplicateEmployeeException {
        employeeRepository.addEmployee(employee);
    }

    public Employee getEmployeeById(int id) throws EmployeeNotFoundException {
        Employee employee = employeeRepository.getEmployeeById(id);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee not found with ID: " + id);
        }
        return employee;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees(); // Let exceptions propagate
    }

    public void updateEmployee(Employee employee) {
        try {
            employeeRepository.updateEmployee(); // Let exceptions propagate
        } catch (EmployeeNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

//    public boolean deleteEmployee(int id) throws EmployeeNotFoundException {
//        try {
//            if (!employeeRepository.deleteEmployee(id)) {
//                throw new EmployeeNotFoundException("Employee not found with ID: " + id);
//            }
//        } catch (EmployeeNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        return false;
//    }
public boolean deleteEmployee(int id) throws EmployeeNotFoundException {
    try {
        boolean isDeleted = employeeRepository.deleteEmployee(id);
        if (!isDeleted) {
            throw new EmployeeNotFoundException("Employee not found with ID: " + id);
        }
        return true;
    } catch (EmployeeNotFoundException e) {
        throw new RuntimeException(e);
    }
}

}