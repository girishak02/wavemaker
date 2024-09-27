package Controller;

import Exception.EmployeeNotFoundException;
import Model.Employee;
import Service.EmployeeService;

import java.util.List;

public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(String repoType) {
        this.employeeService = new EmployeeService(repoType);
    }

    public void addEmployee(Employee employee) {
        try {
            employeeService.addEmployee(employee);
            System.out.println("Employee added successfully.");
            System.out.println(employee);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Employee getEmployeeById(int id) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            System.out.printf("%-10s %-20s %-30s %-10s\n", "ID", "Name", "Address", "pin code");
            System.out.println(employee);
            return employee;
        } catch (EmployeeNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        System.out.printf("%-10s %-20s %-30s %-10s\n", "ID", "Name", "Address", "pin code");
        return employees;
    }

    public void updateEmployee(int id, String name, String location, int pin) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            if (employee == null) {
                throw new EmployeeNotFoundException("Employee with ID " + id + " not found.");
            }
            employee.setName(name);
            employee.getAddress().setLocation(location);
            employee.getAddress().setPin(pin);
            employeeService.updateEmployee(employee);
            System.out.println("Employee updated successfully.");
            System.out.println(employee);
        } catch (EmployeeNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while updating the employee: " + e.getMessage());
        }
    }

//    public void deleteEmployee(int id) {
//        try {
//            Employee employee = employeeService.getEmployeeById(id);
//            if (employee == null) {
//                throw new EmployeeNotFoundException("Employee with ID " + "id not found.");
//            }
//            employeeService.deleteEmployee(id);
//            System.out.println("Employee deleted successfully.");
//        } catch (EmployeeNotFoundException e) {
//            System.out.println(e.getMessage());
//        } catch (Exception e) {
//            System.out.println("An error occurred while deleting the employee: " + e.getMessage());
//        }
//    }
public boolean deleteEmployee(int id) {
    try {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee with ID " + id + " not found.");
        }
        employeeService.deleteEmployee(id);
        System.out.println("Employee deleted successfully.");
        return true;
    } catch (EmployeeNotFoundException e) {
        System.out.println(e.getMessage());
        return false;
    } catch (Exception e) {
        System.out.println("An error occurred while deleting the employee: " + e.getMessage());
        return false;
    }
}


    public void updateEmployee() {

    }
}
