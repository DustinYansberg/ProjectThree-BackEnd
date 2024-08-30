package com.employee.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.models.EmployeeRequest;
import com.employee.services.EmployeeService;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "*")
public class EmployeeController {
    private final EmployeeService service;

    /**
     * Base constructor for EmployeeController: initializes a single EmployeeService.
     * @param service - EmployeeService to initialize.
     */
    public EmployeeController(EmployeeService service) {
	    this.service = service;
    }

    /**
     * getAllEmployees()
     * getAll function for Employees, with pagination.
     * @param index - Pagination index.
     * @param row - Pagination row.
     * @return A ResponseEntity with either a list of employees or an error response.
     */
    @GetMapping("/page/{index}/{row}")
    public ResponseEntity<? extends Object> getAllEmployees(@PathVariable int index, @PathVariable int row) {
	    return service.getAllEmployeesWithPagination(index, row);
    }

    /**
     * getEmployeeById()
     * Function to get the information of a single Employee.
     * @param id - ID of Employee to get information from.
     * @return A ResponseEntity with either employee information or an error response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<? extends Object> getEmployeeById(@PathVariable String id) {
	    return service.getEmployeeById(id);
    }

    /**
     * getEmployeeByEmail()
     * Function to get information of Employee by using email as ID.
     * @param email - Email of employee to get information for.
     * @return A ResponseEntity with either employee information or an error response.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<? extends Object> getEmployeeByEmail(@PathVariable String email) {
	    return service.getEmployeeByEmail(email);
    }

    /**
     * getEmployeesByManagerId()
     * Function to get all employees that have the same manager, denoted by managerId.
     * @param managerId - The ID of the manager to get employees of.
     * @return A ResponseEntity with either employee list or an error response.
     */
    @GetMapping("/manager/{managerId}")
    public ResponseEntity<? extends Object> getEmployeesByManagerId(@PathVariable String managerId) {
	    return service.getEmployeesByManagerId(managerId);
    }

    /**
     * createEmployee()
     * Function to create an employee by taking in an EmployeeRequest object.
     * @param newEmployee - All the details of the Employee to create.
     * @return A ResponseEntity with either the details of the newly created Employee or an error response.
     */
    @PostMapping()
    public ResponseEntity<? extends Object> createEmployee(@RequestBody EmployeeRequest newEmployee) {
	    return service.createEmployee(newEmployee);
    }

    /**
     * updateEmployeeById()
     * Function to update an existing employee denoted by id with new information.
     * @param id - ID of employee to update information of.
     * @param newFields - New information to update this employee with.
     * @return A ResponseEntity with either the updated information or an error response.
     */
    @PutMapping("/{id}")
    public ResponseEntity<? extends Object> updateEmployeeById(@PathVariable String id,
	      @RequestBody EmployeeRequest newFields) {
	    return service.updateEmployee(id, newFields);
    }

    /**
     * deleteEmployeeById()
     * Function to delete an employee by ID.
     * @param id - ID of employee to delete.
     * @return A ResponseEntity with either information about the deleted employee or an error response.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<? extends Object> deleteEmployeeById(@PathVariable String id) {
	    return service.deleteEmployeeById(id);
    }
}
