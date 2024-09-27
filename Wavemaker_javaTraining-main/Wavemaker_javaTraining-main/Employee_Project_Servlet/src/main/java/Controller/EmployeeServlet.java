package Controller;

import Model.Employee;
import com.google.gson.Gson;
import Exception.EmployeeNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {
    private EmployeeController employeeController;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        System.out.println("Servlet initialized");
        employeeController = new EmployeeController("database"); // or "file" or "jdbc"
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        Employee employee = gson.fromJson(reader, Employee.class);

        try {
            employeeController.addEmployee(employee);
            response.setStatus(HttpServletResponse.SC_CREATED);
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(employee));
            out.flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            out.print("{\"message\": \"" + e.getMessage() + "\"}");
            out.flush();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            if (idParam != null) {
                int id = Integer.parseInt(idParam);
                Employee employee = employeeController.getEmployeeById(id);
                out.print(gson.toJson(employee));
            } else {
                List<Employee> employees = employeeController.getAllEmployees();
                out.print(gson.toJson(employees));
            }
        } catch (EmployeeNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print("{\"message\": \"" + e.getMessage() + "\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"message\": \"Invalid ID format\"}");
        } finally {
            out.flush();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        Employee updatedEmployee = gson.fromJson(reader, Employee.class);

        try {
            employeeController.updateEmployee(updatedEmployee.getId(), updatedEmployee.getName(), updatedEmployee.getAddress().getLocation(), updatedEmployee.getAddress().getPin());
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(updatedEmployee));
            out.flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            PrintWriter out = response.getWriter();
            out.print("{\"message\": \"" + e.getMessage() + "\"}");
            out.flush();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                boolean isDeleted = employeeController.deleteEmployee(id);
                if (isDeleted) {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"message\": \"Employee not found\"}");
                }
            } catch (EmployeeNotFoundException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"message\": \"" + e.getMessage() + "\"}");
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"message\": \"Invalid ID format\"}");
            } finally {
                out.flush();
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"message\": \"Employee ID is required\"}");
            out.flush();
        }
    }
}