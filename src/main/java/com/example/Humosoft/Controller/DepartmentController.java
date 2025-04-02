	package com.example.Humosoft.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Humosoft.DTO.Request.DepartmentAddEmployeesRequest;
import com.example.Humosoft.DTO.Request.DepartmentRequest;
import com.example.Humosoft.Model.Department;
import com.example.Humosoft.Model.User;
import com.example.Humosoft.Service.DepartmentService;

import lombok.RequiredArgsConstructor;

import com.example.Humosoft.DTO.Response.Apiresponse;
import com.example.Humosoft.DTO.Response.DepartmentResponse;
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
    public Apiresponse<DepartmentResponse> getDepartmentById(@PathVariable Integer id) {
    	DepartmentResponse departmentResponse=departmentService.getDepartmentById(id);
    	 return Apiresponse.<DepartmentResponse>builder()
                 .code(HttpStatus.OK.value())  // HTTP 200
                 .message("Department updated successfully")
                 .result(departmentResponse)
                 .build();
    }

    // Get all Departments
    @GetMapping
    public Apiresponse<List<DepartmentResponse>> getAllDepartments() {
        List<DepartmentResponse> departments = departmentService.getAll();
        return Apiresponse.<List<DepartmentResponse>>builder()
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
    
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/{departmentId}/employees")

    public Apiresponse< List<UserResponse>> addEmployeesToDepartment(
            @RequestBody DepartmentAddEmployeesRequest request) {

    	 List<UserResponse> userResponses=    departmentService.addEmployees(  request );
        return Apiresponse.< List<UserResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Employees added to department successfully")
                .result(userResponses)
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
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/deleteEmployees")
    public Apiresponse< List<UserResponse>>removeEmpolyees( @RequestBody DepartmentAddEmployeesRequest request){
    	 List<UserResponse> userResponses=	departmentService.removeEmployees(request);
    	  return Apiresponse.< List<UserResponse>>builder()
                  .code(HttpStatus.OK.value())
                  .message("Employee delete to department successfully")
                 .result(userResponses)
                  .build();
    	
    }
    @PreAuthorize("hasRole( 'ADMIN')")
    @PostMapping("/{departmentId}/manager/{managerId}")
    public Apiresponse<String> addManagerToDepartment(
            @PathVariable Integer departmentId,
            @PathVariable Integer managerId) {

        departmentService.addManager(managerId, departmentId);

        return Apiresponse.<String>builder()
                .code(HttpStatus.OK.value())  // HTTP 200
                .message("Manager added successfully")
                .result("User " + managerId + " is now the manager of Department " + departmentId)
                .build();
    }
    @PreAuthorize("hasRole( 'ADMIN')")
    @DeleteMapping("/{departmentId}/manager")
    public Apiresponse<String> removeManager(@PathVariable Integer departmentId) {
        departmentService.removeManager(departmentId);

        return Apiresponse.<String>builder()
                .code(HttpStatus.OK.value())  // HTTP 200
                .message("Manager removed successfully")
                .result("Manager of Department " + departmentId + " has been removed.")
                .build();
    }


    
 
}





