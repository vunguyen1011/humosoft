package com.example.Humosoft.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.Humosoft.DTO.Request.DepartmentRequest;
import com.example.Humosoft.Model.Department;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Service.DepartmentService;

import lombok.RequiredArgsConstructor;

import com.example.Humosoft.DTO.Response.Apiresponse;
import com.example.Humosoft.DTO.Response.UserResponse;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    // Create a new Department
    @PostMapping
    public Apiresponse<Department> createDepartment(@RequestBody DepartmentRequest departmentRequest) {
        Department department = departmentService.createDepartment(departmentRequest);
        return Apiresponse.<Department>builder()
                .code(HttpStatus.CREATED.value())  // HTTP 201
                .message("Department created successfully")
                .result(department)
                .build();
    }

    // Update an existing Department
    @PutMapping("/{id}")
    public Apiresponse<Department> updateDepartment(@PathVariable Integer id, @RequestBody DepartmentRequest departmentRequest) {
        Department updatedDepartment = departmentService.updateDepartment(id, departmentRequest);
        if (updatedDepartment != null) {
            return Apiresponse.<Department>builder()
                    .code(HttpStatus.OK.value())  // HTTP 200
                    .message("Department updated successfully")
                    .result(updatedDepartment)
                    .build();
        } else {
            return Apiresponse.<Department>builder()
                    .code(HttpStatus.NOT_FOUND.value())  // HTTP 404
                    .message("Department not found")
                    .result(null)
                    .build();
        }
    }

    // Get a Department by ID
    @GetMapping("/{id}")
    public Apiresponse<Department> getDepartmentById(@PathVariable Integer id) {
        try {
            Department department = departmentService.getDepartmentById(id);
            return Apiresponse.<Department>builder()
                    .code(HttpStatus.OK.value())  // HTTP 200
                    .message("Department found")
                    .result(department)
                    .build();
        } catch (RuntimeException e) {
            return Apiresponse.<Department>builder()
                    .code(HttpStatus.NOT_FOUND.value())  // HTTP 404
                    .message("Department not found")
                    .result(null)
                    .build();
        }
    }

    // Get all Departments
    @GetMapping
    public Apiresponse<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAll();
        return Apiresponse.<List<Department>>builder()
                .code(HttpStatus.OK.value())  // HTTP 200
                .message("List of all departments")
                .result(departments)
                .build();
    }
    @DeleteMapping("/{id}")
    public Apiresponse<Void> delete(@PathVariable Integer id) {
        departmentService.delete(id);
        return Apiresponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value()) // HTTP 204
                .message("Department deleted successfully")
                .build();
    }
    @PostMapping("/{departmentId}/employees/{employeeId}")
    public Apiresponse<String> addEmployeeToDepartment(
            @PathVariable Integer departmentId, 
            @PathVariable Integer employeeId) {

        departmentService.addEmployee(departmentId, employeeId);
        return Apiresponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Employee added to department successfully")
                .result("Employee " + employeeId + " added to Department " + departmentId)
                .build();
    }
    @GetMapping("/employees")
    public Apiresponse<List<UserResponse>> findUsersByDepartmentName(@RequestParam String departmentName) {
        List<UserResponse> employees = departmentService.findUserInDepartment(departmentName);
        return Apiresponse.<List<UserResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("List of employees in department: " + departmentName)
                .result(employees)
                .build();
    }
    @PostMapping("/{departmentId}/remove-employees/{employeeId}")
    public Apiresponse<Void>removeEmpolyees(@PathVariable Integer employeeId,@PathVariable Integer departmentId){
    	departmentService.deleteEmployee(employeeId, departmentId);
    	  return Apiresponse.<Void>builder()
                  .code(HttpStatus.OK.value())
                  .message("Employee delete to department successfully")
                 
                  .build();
    	
    }
 
}





