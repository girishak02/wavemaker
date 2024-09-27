
package Service.Impl;

import Model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Exception.DuplicateEmployeeException;
import Exception.EmployeeNotFoundException;
import Repository.EmployeeRepository;

public class InMemoryEmployeeRepository implements EmployeeRepository {
    private final Map<Integer, Employee> employeeMap = new HashMap<>();

    @Override
    public void addEmployee(Employee employee) throws DuplicateEmployeeException {
        if (employeeMap.containsKey(employee.getId())) {
            throw new DuplicateEmployeeException("Employee with ID " + employee.getId() + " already exists.");
        }
        employeeMap.put(employee.getId(), employee);
    }

    @Override
    public Employee getEmployeeById(int id) throws EmployeeNotFoundException {
        Employee employee = employeeMap.get(id);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee with ID " + id + " not found.");
        }
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeMap.values());
    }

    @Override
    public void updateEmployee() throws EmployeeNotFoundException {

    }

    @Override
    public void updateEmployee(Employee employee) throws EmployeeNotFoundException {
        if (!employeeMap.containsKey(employee.getId())) {
            throw new EmployeeNotFoundException("Employee with ID " + employee.getId() + " not found.");
        }
        employeeMap.put(employee.getId(), employee);
    }



    @Override
    public boolean deleteEmployee(int id) throws EmployeeNotFoundException {
        if (!employeeMap.containsKey(id)) {
            throw new EmployeeNotFoundException("Employee with ID " + id + " not found.");
        }
        employeeMap.remove(id);
        return false;
    }
}