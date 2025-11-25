package com.astro.controller;

import com.astro.dto.workflow.RoleDto;
import com.astro.dto.workflow.RoleRequestDto;
import com.astro.service.RoleMasterService;

import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roleMaster")
public class RoleMasterController {

    @Autowired
    private RoleMasterService roleMasterService;

    @PostMapping
    public ResponseEntity<Object> createRole(@RequestBody RoleRequestDto roleRequestDto) {
        RoleDto role= roleMasterService.createRole(roleRequestDto);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(role), HttpStatus.OK);
    }


    @PutMapping("/{roleId}")
    public ResponseEntity<Object> updateRole(@PathVariable int roleId, @RequestBody RoleRequestDto roleDTO) {
        RoleDto role = roleMasterService.updateRole(roleId, roleDTO);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(role), HttpStatus.OK);

    }


    @GetMapping
    public ResponseEntity<Object> getAllRole() {
        List<RoleDto> roles= roleMasterService.getAllRoles();
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(roles), HttpStatus.OK);
    }


    @GetMapping("/{roleId}")
    public ResponseEntity<Object> getUserById(@PathVariable int roleId) {
        RoleDto role = roleMasterService.getRoleById(roleId);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(role), HttpStatus.OK);
    }


    @DeleteMapping("/{roleId}")
    public ResponseEntity<String> deleteAsset(@PathVariable int roleId) {
         roleMasterService.deleteRole(roleId);
        return ResponseEntity.ok("role deleted successfully!");
    }





}
