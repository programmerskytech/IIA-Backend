package com.astro.controller;

import com.astro.dto.workflow.*;
import com.astro.service.EmployeeDepartmentMasterService;
import com.astro.service.RoleMasterService;
import com.astro.service.UserService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employee-department-master")
public class EmployeeDepartmentMasterController {

    @Autowired
    private EmployeeDepartmentMasterService employeeService;

    @Autowired
    private RoleMasterService roleMasterService;

    @Autowired
    private UserService userService;


    @PostMapping("/with-user")
public ResponseEntity<Object> createEmployeeWithUser(@Valid @RequestBody EmployeeDepartmentMasterRequestDto requestDTO) {
    EmployeeDepartmentMasterResponseDto employee = employeeService.createEmployeeDepartmentWithUser(requestDTO);
    return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(employee), HttpStatus.OK);
}
// Get department by employee name
@GetMapping("/department/by-name")
public ResponseEntity<Object> getDepartmentByName(@RequestParam String employeeName) {
    String department = employeeService.getDepartmentByEmployeeName(employeeName);
    
    Map<String, String> response = new HashMap<>();
    response.put("departmentName", department);
    
    return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
}
@GetMapping("/roles")
public ResponseEntity<Object> getAllRoles() {
    List<RoleDto> roles = roleMasterService.getAllRoles();
    return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(roles), HttpStatus.OK);
}

@GetMapping("/user-exists/{employeeId}")
public ResponseEntity<Object> checkUserExists(@PathVariable String employeeId) {
    boolean exists = userService.userExistsByEmployeeId(employeeId);
    Map<String, Boolean> response = new HashMap<>();
    response.put("exists", exists);
    return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
}

    @PostMapping
    public ResponseEntity<Object> createEmployeeMaster(@Valid @RequestBody EmployeeDepartmentMasterRequestDto requestDTO) {
        EmployeeDepartmentMasterResponseDto employee = employeeService.createEmployeeDepartment(requestDTO);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(employee), HttpStatus.OK);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<Object> updateEmployeeMaster(@PathVariable String employeeId,
                                                       @Valid @RequestBody EmployeeDepartmentMasterRequestDto requestDTO) {
        EmployeeDepartmentMasterResponseDto response = employeeService.updateEmployeeDepartmentMaster(employeeId, requestDTO);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getAllEmployeeMaster() {
        List<EmployeeDepartmentMasterResponseDto> response = employeeService.getAllEmployeeDepartmentMasters();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping("/employeeName")
    public ResponseEntity<Object> getAllEmployeeName() {
        List<employeedto> response = employeeService.getAllEmployeeDepartmentMasterswithName();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Object> getEmployeeMasterById(@PathVariable String employeeId) {
        EmployeeDepartmentMasterResponseDto responseDTO = employeeService.getEmployeeDepartmentMasterById(employeeId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }

    // Get employee details by user ID (for indent creation auto-fill)
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<Object> getEmployeeDetailsByUserId(@PathVariable Integer userId) {
        EmployeeDepartmentMasterResponseDto responseDTO = employeeService.getEmployeeDetailsByUserId(userId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployeeMaster(@PathVariable String employeeId) {
        employeeService.deleteEmployeeDepartmentMasterr(employeeId);
        return ResponseEntity.ok("Employee master deleted successfully. EmployeeId: " + employeeId);
    }

    @GetMapping("/employeeSearch")
    public ResponseEntity<Object> searchEmployees(@RequestParam("keyword") String keyword) {
        List<EmployeeSearchResponseDto> results = employeeService.searchEmployees(keyword);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(results), HttpStatus.OK);
    }

    // Activate Employee
    @PutMapping("/{employeeId}/activate")
    public ResponseEntity<Object> activateEmployee(@PathVariable String employeeId,
                                                   @RequestBody Map<String, String> request) {
        String updatedBy = request.get("updatedBy");
        EmployeeDepartmentMasterResponseDto response = employeeService.activateEmployee(employeeId, updatedBy);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    // Deactivate Employee
    @PutMapping("/{employeeId}/deactivate")
    public ResponseEntity<Object> deactivateEmployee(@PathVariable String employeeId,
                                                     @RequestBody Map<String, String> request) {
        String updatedBy = request.get("updatedBy");
        EmployeeDepartmentMasterResponseDto response = employeeService.deactivateEmployee(employeeId, updatedBy);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    // Get all Designations LOV
    @GetMapping("/designations")
    public ResponseEntity<Object> getAllDesignations() {
        List<DesignationDto> designations = employeeService.getAllDesignations();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(designations), HttpStatus.OK);
    }

    // Get all Departments LOV
    @GetMapping("/departments")
    public ResponseEntity<Object> getAllDepartments() {
        List<DepartmentDto> departments = employeeService.getAllDepartments();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(departments), HttpStatus.OK);
    }

    // Save as Draft
@PostMapping("/draft")
public ResponseEntity<Object> saveAsDraft(@RequestBody EmployeeDepartmentMasterRequestDto requestDTO) {
    EmployeeDepartmentMasterResponseDto response = employeeService.saveAsDraft(requestDTO);
    return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
}

// Update existing Draft
@PutMapping("/draft/{employeeId}")
public ResponseEntity<Object> updateDraft(@PathVariable String employeeId,
                                          @RequestBody EmployeeDepartmentMasterRequestDto requestDTO) {
    requestDTO.setEmployeeId(employeeId);
    EmployeeDepartmentMasterResponseDto response = employeeService.saveAsDraft(requestDTO);
    return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
}

// Get all drafts
@GetMapping("/drafts")
public ResponseEntity<Object> getAllDrafts() {
    List<EmployeeDepartmentMasterResponseDto> response = employeeService.getAllDrafts();
    return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
}

// Get drafts by user
@GetMapping("/drafts/user/{userId}")
public ResponseEntity<Object> getDraftsByUser(@PathVariable String userId) {
    List<EmployeeDepartmentMasterResponseDto> response = employeeService.getDraftsByUser(userId);
    return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
}

// Submit draft (convert to final)
@PutMapping("/draft/{employeeId}/submit")
public ResponseEntity<Object> submitDraft(@PathVariable String employeeId,
                                          @RequestBody EmployeeDepartmentMasterRequestDto requestDTO) {
    EmployeeDepartmentMasterResponseDto response = employeeService.submitDraft(employeeId, requestDTO);
    return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
}
@GetMapping("/employeeName/byDepartment")
public ResponseEntity<Object> getEmployeesByDepartment(@RequestParam String department) {
    List<employeedto> response = employeeService.getEmployeesByDepartment(department);
    return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
}

    // TC_15 FIX: Advanced employee search with filters
    @GetMapping("/advanced-search")
    public ResponseEntity<Object> advancedEmployeeSearch(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String location) {
        List<EmployeeDepartmentMasterResponseDto> results = employeeService.advancedSearch(searchTerm, department, location);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(results), HttpStatus.OK);
    }
}