package com.example.Humosoft.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.Humosoft.DTO.Request.DepartmentRequest;
import com.example.Humosoft.Model.Department;
import com.example.Humosoft.Service.DepartmentService;

import lombok.RequiredArgsConstructor;

import com.example.Humosoft.DTO.Response.Apiresponse;

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
}

