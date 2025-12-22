package com.example.Humosoft.Controller;

import com.example.Humosoft.DTO.Request.DepartmentAddEmployeesRequest;
import com.example.Humosoft.DTO.Request.DepartmentRequest;
import com.example.Humosoft.DTO.Response.Apiresponse;
import com.example.Humosoft.DTO.Response.DepartmentResponse;
import com.example.Humosoft.DTO.Response.UserResponse;
import com.example.Humosoft.Model.Department;
import com.example.Humosoft.Service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public Apiresponse<Department> createDepartment(
            @RequestBody DepartmentRequest request) {

        Department department = departmentService.createDepartment(request);

        return Apiresponse.<Department>builder()
                .code(HttpStatus.CREATED.value())
                .message("Department created successfully")
                .result(department)
                .build();
    }

    @PutMapping("/{id}")
    public Apiresponse<Department> updateDepartment(
            @PathVariable Integer id,
            @RequestBody DepartmentRequest request) {

        Department department = departmentService.updateDepartment(id, request);

        return Apiresponse.<Department>builder()
                .code(HttpStatus.OK.value())
                .message("Department updated successfully")
                .result(department)
                .build();
    }

    @GetMapping("/{id}")
    public Apiresponse<DepartmentResponse> getDepartmentById(
            @PathVariable Integer id) {

        DepartmentResponse response = departmentService.getDepartmentById(id);

        return Apiresponse.<DepartmentResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Department retrieved successfully")
                .result(response)
                .build();
    }

    @GetMapping
    public Apiresponse<List<DepartmentResponse>> getAllDepartments() {

        List<DepartmentResponse> departments = departmentService.getAll();

        return Apiresponse.<List<DepartmentResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("List of all departments")
                .result(departments)
                .build();
    }

    @DeleteMapping("/{id}")
    public Apiresponse<Void> deleteDepartment(@PathVariable Integer id) {

        departmentService.delete(id);

        return Apiresponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Department deleted successfully")
                .build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping("/{departmentId}/employees")
    public Apiresponse<List<UserResponse>> addEmployeesToDepartment(
            @RequestBody DepartmentAddEmployeesRequest request) {

        List<UserResponse> users =
                departmentService.addEmployees(request);

        return Apiresponse.<List<UserResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Employees added to department successfully")
                .result(users)
                .build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping("/deleteEmployees")
    public Apiresponse<List<UserResponse>> removeEmployeesFromDepartment(
            @RequestBody DepartmentAddEmployeesRequest request) {

        List<UserResponse> users =
                departmentService.removeEmployees(request);

        return Apiresponse.<List<UserResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Employees removed from department successfully")
                .result(users)
                .build();
    }

    @GetMapping("/employees")
    public Apiresponse<List<UserResponse>> getEmployeesByDepartmentName(
            @RequestParam String departmentName) {

        List<UserResponse> users =
                departmentService.findUserInDepartment(departmentName);

        return Apiresponse.<List<UserResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("List of employees in department: " + departmentName)
                .result(users)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{departmentId}/manager/{managerId}")
    public Apiresponse<String> addManagerToDepartment(
            @PathVariable Integer departmentId,
            @PathVariable Integer managerId) {

        departmentService.addManager(managerId, departmentId);

        return Apiresponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Manager added successfully")
                .result("User " + managerId + " is now manager of department " + departmentId)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{departmentId}/manager")
    public Apiresponse<String> removeManagerFromDepartment(
            @PathVariable Integer departmentId) {

        departmentService.removeManager(departmentId);

        return Apiresponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Manager removed successfully")
                .result("Manager removed from department " + departmentId)
                .build();
    }
}
