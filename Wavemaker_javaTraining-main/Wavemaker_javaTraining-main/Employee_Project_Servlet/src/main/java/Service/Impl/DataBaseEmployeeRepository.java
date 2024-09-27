package Service.Impl;

import Model.Address;
import Model.Employee;
import Repository.EmployeeRepository;
import Exception.EmployeeNotFoundException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseEmployeeRepository implements EmployeeRepository {

    private static final String URL = "jdbc:mysql://localhost:3307/employeedatabase";
    private static final String USER = "root";
    private static final String PASSWORD = "Girish@02";

    public DataBaseEmployeeRepository() {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addEmployee(Employee employee) {
        String addressQuery = "INSERT INTO address (location, pin) VALUES (?, ?)";
        String employeeQuery = "INSERT INTO employees (id, name, address_id) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement addressStatement = connection.prepareStatement(addressQuery, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement employeeStatement = connection.prepareStatement(employeeQuery)) {

            // Insert into address table
            addressStatement.setString(1, employee.getAddress().getLocation());
            addressStatement.setInt(2, employee.getAddress().getPin());
            addressStatement.executeUpdate();

            // Retrieve generated address ID
            ResultSet generatedKeys = addressStatement.getGeneratedKeys();
            int addressId = 0;
            if (generatedKeys.next()) {
                addressId = generatedKeys.getInt(1);
            }

            // Insert into employees table
            employeeStatement.setInt(1, employee.getId());
            employeeStatement.setString(2, employee.getName());
            employeeStatement.setInt(3, addressId);
            employeeStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Employee getEmployeeById(int id) throws EmployeeNotFoundException {
        String query = "SELECT e.id, e.name, a.location, a.pin " +
                "FROM employees e " +
                "JOIN address a ON e.address_id = a.id " +
                "WHERE e.id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String location = resultSet.getString("location");
                int pin = resultSet.getInt("pin");

                Address address = new Address(location, pin);
                return new Employee(id, name, address);
            } else {
                throw new EmployeeNotFoundException("Employee with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EmployeeNotFoundException("An error occurred while retrieving employee.");
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT e.id, e.name, a.location, a.pin " +
                "FROM employees e " +
                "JOIN address a ON e.address_id = a.id";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String location = resultSet.getString("location");
                int pin = resultSet.getInt("pin");

                Address address = new Address(location, pin);
                Employee employee = new Employee(id, name, address);
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public void updateEmployee() throws EmployeeNotFoundException {

    }

    @Override
    public void updateEmployee(Employee employee) {
        String addressQuery = "UPDATE address SET location = ?, pin = ? WHERE id = ?";
        String employeeQuery = "UPDATE employees SET name = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement addressStatement = connection.prepareStatement(addressQuery);
             PreparedStatement employeeStatement = connection.prepareStatement(employeeQuery)) {

            // Update address table
            addressStatement.setString(1, employee.getAddress().getLocation());
            addressStatement.setInt(2, employee.getAddress().getPin());
            addressStatement.setInt(3, employee.getAddress().getId()); // Assuming Address has an id attribute
            addressStatement.executeUpdate();

            // Update employees table
            employeeStatement.setString(1, employee.getName());
            employeeStatement.setInt(2, employee.getId());
            employeeStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteEmployee(int id) throws EmployeeNotFoundException {
        String query = "DELETE FROM employees WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new EmployeeNotFoundException("Employee with ID " + id + " not found.");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EmployeeNotFoundException("An error occurred while deleting employee.");
        }
    }
}
