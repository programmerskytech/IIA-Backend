package com.astro.controller;


import com.astro.dto.workflow.UserDto;
import com.astro.dto.workflow.UserSearchResponseDto;
import com.astro.dto.workflow.userRequestDto;
import com.astro.service.UserService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userMaster")
public class UserMasterController {

    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody userRequestDto userRequestDto) {
        UserDto user = userService.createUser(userRequestDto);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(user), HttpStatus.OK);
    }


    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable int userId, @RequestBody userRequestDto userDTO) {
        UserDto user = userService.updateUser(userId, userDTO);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(user), HttpStatus.OK);

    }


    @GetMapping
    public ResponseEntity<Object> getAllUser() {
        List<UserDto> users= userService.getAllUsers();
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(users), HttpStatus.OK);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable int userId) {
        UserDto user = userService.getUserById(userId);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(user), HttpStatus.OK);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteAsset(@PathVariable int userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("user deleted successfully!");
    }

    // TC_14 FIX: Add password change endpoint
    @PostMapping("/change-password")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordRequest request) {
        userService.changePassword(request.getUserId(), request.getOldPassword(), request.getNewPassword());
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse("Password changed successfully"), HttpStatus.OK);
    }

    // DTO for password change request
    @lombok.Data
    public static class ChangePasswordRequest {
        private Integer userId;
        private String oldPassword;
        private String newPassword;
    }

    // Search users by keyword (username, email, mobile, employee ID, employee name)
    @GetMapping("/search")
    public ResponseEntity<Object> searchUsers(@RequestParam(value = "keyword", required = false) String keyword) {
        List<UserSearchResponseDto> users = userService.searchUsers(keyword);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(users), HttpStatus.OK);
    }

    // Get all users with roles for listing in table
    @GetMapping("/list")
    public ResponseEntity<Object> getAllUsersWithRoles() {
        List<UserSearchResponseDto> users = userService.getAllUsersWithRoles();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(users), HttpStatus.OK);
    }

    // Toggle user active/inactive status
    @PutMapping("/{userId}/toggle-status")
    public ResponseEntity<Object> toggleUserStatus(@PathVariable int userId) {
        UserDto user = userService.toggleUserStatus(userId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(user), HttpStatus.OK);
    }

    // Activate a user
    @PutMapping("/{userId}/activate")
    public ResponseEntity<Object> activateUser(@PathVariable int userId) {
        UserDto user = userService.activateUser(userId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(user), HttpStatus.OK);
    }

    // Deactivate a user
    @PutMapping("/{userId}/deactivate")
    public ResponseEntity<Object> deactivateUser(@PathVariable int userId) {
        UserDto user = userService.deactivateUser(userId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(user), HttpStatus.OK);
    }
}
