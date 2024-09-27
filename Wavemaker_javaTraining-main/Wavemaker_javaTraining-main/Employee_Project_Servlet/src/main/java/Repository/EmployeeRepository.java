package Repository;

import Model.Employee;
import Exception.DuplicateEmployeeException;
import Exception.EmployeeNotFoundException;
import java.util.List;

public interface EmployeeRepository {
    void addEmployee(Employee employee) throws DuplicateEmployeeException;

    Employee getEmployeeById(int id) throws EmployeeNotFoundException;

    List<Employee> getAllEmployees();
    void updateEmployee() throws EmployeeNotFoundException;

    void updateEmployee(Employee employee);

    boolean deleteEmployee(int id) throws EmployeeNotFoundException;
}

